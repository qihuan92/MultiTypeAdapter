package com.qihuan.multitypeadapter.model;

import com.qihuan.multitypeadapter.adapter.TypeFactory;
import com.qihuan.multitypeadapter.adapter.Item;

/**
 * Cat
 *
 * @author qi
 * @date 2018/11/27
 */
public class Cat implements Item {

    private String name;
    private String imageUrl;

    public String getName() {
        return name;
    }

    public Cat setName(String name) {
        this.name = name;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Cat setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }
}
