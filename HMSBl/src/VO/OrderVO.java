package VO;

import java.util.Date;

/**
 * Created by Administrator on 2016/11/22.
 */
public class OrderVO {
    private final String id;
    private final UserInOrder user;
    private final HotelInOrder hotel;
    private final OrderState state;
    private final boolean children;
    private final Date inTime;
    private final Date outTime;
    private final Date execTime;
    private final double total;
    private final int person;

    public String getId() {
        return id;
    }

    public UserInOrder getUser() {
        return user;
    }

    public HotelInOrder getHotel() {
        return hotel;
    }

    public OrderState getState() {
        return state;
    }

    public boolean isChildren() {
        return children;
    }

    public Date getInTime() {
        return inTime;
    }

    public Date getOutTime() {
        return outTime;
    }

    public Date getExecTime() {
        return execTime;
    }

    public double getTotal() {
        return total;
    }

    public int getPerson() {
        return person;
    }

    public OrderVO(String id,UserInOrder user, HotelInOrder hotel, OrderState state, boolean children, Date inTime, Date outTime, Date execTime, int person,double total) {
        this.id = id;
        this.user = user;
        this.hotel = hotel;
        this.state = state;
        this.children = children;
        this.inTime = inTime;
        this.outTime = outTime;
        this.execTime = execTime;
        this.person = person;
        this.total = total;
    }
}
