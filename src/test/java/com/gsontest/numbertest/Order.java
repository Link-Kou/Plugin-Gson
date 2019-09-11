package com.gsontest.numbertest;

/**
 * @author lk
 * @version 1.0
 *
 */
public class Order {

    private double amount;
    private Double amount2;
    private long lenght;
    private float weight;
    private int number;

    public double getAmount() {
        return amount;
    }

    public Order setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public Double getAmount2() {
        return amount2;
    }

    public Order setAmount2(Double amount2) {
        this.amount2 = amount2;
        return this;
    }

    public long getLenght() {
        return lenght;
    }

    public Order setLenght(long lenght) {
        this.lenght = lenght;
        return this;
    }

    public float getWeight() {
        return weight;
    }

    public Order setWeight(float weight) {
        this.weight = weight;
        return this;
    }

    public int getNumber() {
        return number;
    }

    public Order setNumber(int number) {
        this.number = number;
        return this;
    }

    @Override
    public String toString() {
        return "Order{" +
                "amount=" + amount +
                ", amount2=" + amount2 +
                ", lenght=" + lenght +
                ", weight=" + weight +
                ", number=" + number +
                '}';
    }
}
