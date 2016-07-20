package com.tom.stock.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.Date;

/**
 * Created by Administrator on 2016/7/20.
 */
@Entity
public class Stock {
    @Id
    String id;
    @Index
    String name;
    float price;
    Date time;
    public Stock(){

    }
    public Stock(String id, String name, float price, Date time) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
