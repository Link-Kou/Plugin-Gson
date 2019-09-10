package com.gsontest.enumtest;

/**
 * @author lk
 * @version 1.0
 * @date 2019/9/2 16:00
 */
public class User {

    private Sex sex;

    private Nation nation;


    public Sex getSex() {
        return this.sex;
    }

    public User setSex(Sex sex) {
        this.sex = sex;
        return this;
    }

    public Nation getNation() {
        return this.nation;
    }

    public User setNation(Nation nation) {
        this.nation = nation;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "sex=" + sex +
                ", nation=" + nation +
                '}';
    }
}
