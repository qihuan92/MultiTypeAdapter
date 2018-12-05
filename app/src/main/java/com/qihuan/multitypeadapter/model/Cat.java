package com.qihuan.multitypeadapter.model;

/**
 * Cat
 *
 * @author qi
 * @date 2018/11/27
 */
public class Cat {

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
}
