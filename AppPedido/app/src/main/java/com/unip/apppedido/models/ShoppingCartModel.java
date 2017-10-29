package com.unip.apppedido.models;

public class ShoppingCartModel {
    private ProductModel mProduct;
    private int mQuantity;

    public ShoppingCartModel(ProductModel product, int quantity) {
        this.mProduct = product;
        this.mQuantity = quantity;
    }

    public ShoppingCartModel() {
    }

    public ProductModel getProduct() {
        return mProduct;
    }

    public void setProduct(ProductModel product) {
        this.mProduct = product;
    }

    public int getQuantity() {
        return mQuantity;
    }

    public void setQuantity(int quantity) {
        this.mQuantity = quantity;
    }

    public double getTotalProduct() {
        return mProduct.getValue() * mQuantity;
    }
}