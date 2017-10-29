package com.unip.apppedido.models;

public class CategoryModel {
    private int mId;
    private String mName;

    public CategoryModel(int id, String name) {
        this.mId = id;
        this.mName = name;
    }

    public CategoryModel() {
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setmName(String name) {
        this.mName = name;
    }
}