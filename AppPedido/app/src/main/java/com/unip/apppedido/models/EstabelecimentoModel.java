package com.unip.apppedido.models;

import com.google.gson.annotations.SerializedName;

public class EstabelecimentoModel {
    @SerializedName("idEstabelecimento")
    private int mId;

    @SerializedName("nomeExibicao")
    private String mName;

    public EstabelecimentoModel(int id, String name) {
        this.mId = id;
        this.mName = name;
    }

    public EstabelecimentoModel() {
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
}