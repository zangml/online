package com.koala.learn.async;

/**
 * Created by nowcoder on 2016/7/30.
 */
public enum EventType {
    TESTSUCCESS(0);
//    LIKE(0),
//    COMMENT(1),
//    LOGIN(2),
//    MAIL(3),
//    FOLLOW(4),
//    UNFOLLOW(5),
//    ADD_QUESTION(6);

    private int value;
    EventType(int value) { this.value = value; }
    public int getValue() { return value; }
}
