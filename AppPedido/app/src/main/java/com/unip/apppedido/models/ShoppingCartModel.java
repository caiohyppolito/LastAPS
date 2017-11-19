package com.unip.apppedido.models;

import com.google.gson.annotations.SerializedName;

public class ShoppingCartModel {
    @SerializedName("produtoId")
    private int mIdProduto;

    private transient String mName;
    private transient double mValue;

    @SerializedName("ConsumidorId")
    private int mIdUser;

    @SerializedName("EstabelecimentoId")
    private int mIdEstabelecimento;

    @SerializedName("quantidade")
    private int mQuantity;

    public ShoppingCartModel() {
    }

    public ShoppingCartModel(int idProduto, String name, double value, int idUser, int idEstabelecimento, int quantity) {
        this.mIdProduto = idProduto;
        this.mName = name;
        this.mValue = value;
        this.mIdUser = idUser;
        this.mIdEstabelecimento = idEstabelecimento;
        this.mQuantity = quantity;
    }

    public int getIdProduto() {
        return mIdProduto;
    }

    public void setIdProduto(int idProduto) {
        this.mIdProduto = idProduto;
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

    public int getIdUser() {
        return mIdUser;
    }

    public void setIdUser(int idUser) {
        this.mIdUser = idUser;
    }

    public int getIdEstabelecimento() {
        return mIdEstabelecimento;
    }

    public void setIdEstabelecimento(int idEstabelecimento) {
        this.mIdEstabelecimento = idEstabelecimento;
    }

    public int getQuantity() {
        return mQuantity;
    }

    public void setQuantity(int quantity) {
        this.mQuantity = quantity;
    }

    public double getTotalProduct(){
        return mValue * mQuantity;
    }
}