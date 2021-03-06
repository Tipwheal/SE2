package VO;


import order_bl_serv.OrderBlServ;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Administrator on 2016/11/22.
 */
public class HotelVO {
    private final String name;
    private final String CBD;
    private final String location;
    private final ArrayList<CommentVO> comments;
    private final int starLevel;
    private final String intro;
    private final double minPrice;
    private final ArrayList<RoomVO> rooms;
    private final ArrayList<String> cooperativeEnterprise;
    private final ArrayList<StrategyVO> strategies;

    public HotelVO(String name,String CBD, String location, ArrayList<CommentVO> comments, int starLevel, String intro, ArrayList<RoomVO> rooms
            ,double minPrice, ArrayList<String> cooperativeEnterprise, ArrayList<StrategyVO> strategies) {
        this.name = name;
        this.CBD = CBD;
        this.location = location;
        this.comments = comments;
        this.starLevel = starLevel;
        this.intro = intro;
        this.minPrice = minPrice;
        this.rooms = rooms;
        this.cooperativeEnterprise = cooperativeEnterprise;
        this.strategies = strategies;
    }

    public String getName() {return name;}

    public String getCBD() {
        return CBD;
    }

    public String getLocation() {
        return location;
    }

    public Iterator<CommentVO> getComments() {
        return comments.iterator();
    }

    public int getStarLevel() {
        return starLevel;
    }

    public String getIntro() {
        return intro;
    }

    public double getAverageCommentLv() {
        Iterator<CommentVO> commentVOIterator = this.getComments();
        double totalLv = 0;
        int count = 0;
        while(commentVOIterator.hasNext()) {
            double lv = commentVOIterator.next().getLevel();
            totalLv += lv;
            count++;
        }
        return totalLv/count;
    }

    public boolean haveOrdered(String userId) {
        Iterator<OrderVO> orderVOIterator = OrderBlServ.getInstance().getOrderList(userId);
        while(orderVOIterator.hasNext()) {
            OrderVO orderVO = orderVOIterator.next();
            if(orderVO.getHotel().getHotelName().equals(this.getName())) {
                return true;
            }
        }
        return false;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public Iterator<RoomVO> getRooms() {
        return rooms.iterator();
    }

    public Iterator<String> getCooperativeEnterprise() {
        return cooperativeEnterprise.iterator();
    }

    public Iterator<StrategyVO> getStrategies() {
        return strategies.iterator();
    }
}
