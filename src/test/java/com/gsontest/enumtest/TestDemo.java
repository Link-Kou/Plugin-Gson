package com.gsontest.enumtest;

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
    public void Enum() {
        Gson g = GsonBuild.gson;
        User user = new User();
        user.setSex(Sex.BOY);
        user.setNation(Nation.HAN);
        final String s = g.toJson(user);
        System.out.println(String.format("JSON:%s", s));
        final User user1 = g.fromJson(s, User.class);
        System.out.println(String.format("ToString:%s", user1.toString()));
    }

}
