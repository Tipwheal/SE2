package book_bl_servImpl;

import PO.HotelPO;
import PO.OrderPO;
import PO.UserPO;
import VO.*;
import book_bl_serv.BookBlServ;
import helper.ParseHelper;
import hotel_bl_serv.HotelBlServ;
import order_bl_serv.OrderBlServ;
import rmi.RemoteHelper;
import room_bl_serv.RoomBlServ;
import strategy_bl_serv.HotelStrategyBlServ;
import strategy_bl_serv.WebStrategyBlServ;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by Administrator on 2016/11/30.
 */
public class BookBlServImpl implements BookBlServ {

    @Override
    public Iterator<RoomAndNum> getNumAvailable(String hotelName, int inTime, int outTime) {
        if (hotelName == null) {
            return null;
        }
        ArrayList<RoomAndNum> roomAndNums = new ArrayList<>();
        HotelPO hotelPO = null;
        try {
            RemoteHelper.getInstance().getHotelDataServ().getHotel(hotelName);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if (hotelPO == null) {
            return null;
        }
        HotelVO hotelVO = ParseHelper.toHotelVO(hotelPO);
        Iterator<RoomVO> roomVOIterator = hotelVO.getRooms();
        while (roomVOIterator.hasNext()) {
            RoomVO roomVO = roomVOIterator.next();
            int available[] = roomVO.getAvailable();
            int min = 0;
            for (int i = inTime; i < outTime; i++) {
                if (min == 0) {
                    min = available[i];
                } else {
                    min = Math.min(available[i], min);
                }
            }
            roomAndNums.add(new RoomAndNum(roomVO.getType(), min));
        }
        return roomAndNums.iterator();
    }

    @Override
    /**
     * 这个方法似乎需要改成私有的了
     * 只是用来把vo转成po并且让数据层更新一下
     */
    public boolean produceOrder(OrderVO orderVO) {
        if (orderVO == null) {
            return false;
        }
        boolean success = false;
        try {
            OrderPO orderPO = ParseHelper.toOrderPO(orderVO);
            success = RemoteHelper.getInstance().getOrderDataServ().insertOrder(orderPO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    @Override
    public BookResult book(String hotelName, String userId, ArrayList<RoomAndNum> roomAndNums, boolean children, int persons, int inTime, int outTime, int execTime) {
        if (hotelName == null || userId == null || roomAndNums == null) {
            return new FailBookResult("");
        }
        String id = null;
        try {
            UserPO userPO = RemoteHelper.getInstance().getUserDataServ().getUser(userId);
            double totalCredit = userPO.getCreditTol();
            if (totalCredit < 0) {
                return new FailBookResult("not enough credit");
            }
            String contactName = userPO.getName();
            String contact = userPO.getContactInfo();
            UserInOrder userInOrder = new UserInOrder(userId, contactName, contact);
            ArrayList<RoomInOrder> roomInOrders = new ArrayList<>();
            double total = 0;
            for (RoomAndNum roomAndNum : roomAndNums) {
                for (int i = inTime; i < outTime; i++) {
                    String type = roomAndNum.getName();
                    int num = roomAndNum.getNum();
                    RoomVO roomVO = RoomBlServ.getInstance().getRoomInfo(hotelName, type);
                    double price = roomVO.getPrice();
                    Date curTime = null;
                    try {
                        curTime = RemoteHelper.getInstance().getTimeServ().getTime();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(curTime);
                    calendar.add(Calendar.DATE, i);
                    Date inTimeDate = calendar.getTime();
                    HotelVO hotelVO = HotelBlServ.getInstance().getHotelInfo(hotelName);
                    double discount = Math.min(HotelStrategyBlServ.getInstance().getMinDiscount(hotelName, userId, inTimeDate, num), WebStrategyBlServ.getInstance().getMinDiscount(userId, inTimeDate, hotelVO.getCBD()));
                    double subTotal = num * price * discount;
                    total += subTotal;
                    RoomInOrder roomInOrder = new RoomInOrder(type, num, price, subTotal);
                    roomInOrders.add(roomInOrder);
                }
            }
            HotelInOrder hotelInOrder = new HotelInOrder(hotelName, roomInOrders);
            OrderState state = OrderState.WAITING;
            Date inTimeDate, outTimeDate, execTimeDate;
            Calendar time = Calendar.getInstance();
            time.add(Calendar.DAY_OF_MONTH, inTime - 1);
            inTimeDate = time.getTime();
            if (execTime == 0) {
                time.set(Calendar.HOUR_OF_DAY, 18);
                time.set(Calendar.MINUTE, 0);
            } else {
                time.set(Calendar.HOUR_OF_DAY, 22);
                time.set(Calendar.MINUTE, 0);
            }
            execTimeDate = time.getTime();
            time = Calendar.getInstance();
            time.set(Calendar.DAY_OF_MONTH, outTime);
            outTimeDate = time.getTime();
            OrderVO orderVO = new OrderVO(null, userInOrder, hotelInOrder, state, children, inTimeDate, outTimeDate, execTimeDate, persons, total);
            boolean success = this.produceOrder(orderVO);
            if (!success) {
                return new FailBookResult("Exception");
            } else {
                RoomBlServ.getInstance().changeRoomNum(orderVO,null);
            }
            id = OrderBlServ.getInstance().getLatestOrder(userId).getId();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return new SuccessBookResult(id);
    }
}
