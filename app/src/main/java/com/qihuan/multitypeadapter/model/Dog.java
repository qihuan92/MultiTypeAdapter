package com.qihuan.multitypeadapter.model;

/**
 * Cat
 *
 * @author qi
 * @date 2018/11/27
 */
public class Dog {

    private String name;
    private String imageUrl;

    public String getName() {
        return name;
    }

    public Dog setName(String name) {
        this.name = name;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Dog setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }
}
