package com.unip.apppedido.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductModel implements Parcelable{
    private int mId;
    private String mName;
    private int mIdCategory;
    private double mValue;

    public ProductModel() {
    }

    public ProductModel(int id, String name, double value) {
        this.mId = id;
        this.mName = name;
        this.mValue = value;
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

    public void setName(String name) {
        this.mName = name;
    }

    public double getValue() {
        return mValue;
    }

    public void setValue(double value) {
        this.mValue = value;
    }

    public int getIdCategory() {
        return mIdCategory;
    }

    public void setIdCategory(int idCategory) {
        this.mIdCategory = idCategory;
    }

    protected ProductModel(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mIdCategory = in.readInt();
        mValue = in.readDouble();
    }

    public static final Creator<ProductModel> CREATOR = new Creator<ProductModel>() {
        @Override
        public ProductModel createFromParcel(Parcel in) {
            return new ProductModel(in);
        }

        @Override
        public ProductModel[] newArray(int size) {
            return new ProductModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mId);
        parcel.writeString(mName);
        parcel.writeInt(mIdCategory);
        parcel.writeDouble(mValue);
    }
}
