package com.gsontest.numbertest;

import com.google.gson.Gson;
import com.linkkou.gson.GsonBuild;
import org.junit.Test;

/**
 * @author lk
 * @version 1.0
 *
 */
public class TestDemo {

    @Test
    public void Number() {
        NumberTest1();
        NumberTest2();
    }

    public void NumberTest1() {
        Gson g = GsonBuild.gson;
        Order order = new Order();
        order.setAmount(100.23);
        order.setWeight(0.23f);
        order.setLenght(100);
        order.setNumber(10);
        order.setAmount2(Double.NaN);
        final String s = g.toJson(order);
        System.out.println(String.format("JSON:%s", s));
        final Order order1 = g.fromJson(s, Order.class);
        System.out.println(String.format("ToString:%s", order1.toString()));
    }

    public void NumberTest2() {
        Gson g = GsonBuild.gson;
        Order order = new Order();
        order.setAmount(0.00);
        order.setWeight(0.00f);
        order.setLenght(0);
        order.setNumber(0);
        final String s = g.toJson(order);
        System.out.println(String.format("JSON:%s", s));
        final Order order1 = g.fromJson(s, Order.class);
        System.out.println(String.format("ToString:%s", order1.toString()));
    }
}
