package com.unip.apppedido.models;

import com.google.gson.annotations.SerializedName;

public class RegisterRequest {

    @SerializedName("deviceId")
    private int mDeviceId;

    @SerializedName("senha")
    private String mSenha;

    @SerializedName("usuario")
    private String mUsuario;

    @SerializedName("celular")
    private String mCelular;

    public RegisterRequest(int mDeviceId, String mSenha, String mUsuario, String mCelular) {
        this.mDeviceId = mDeviceId;
        this.mSenha = mSenha;
        this.mUsuario = mUsuario;
        this.mCelular = mCelular;
    }

    public int getDeviceId() {
        return mDeviceId;
    }

    public void setDeviceId(int mDeviceId) {
        this.mDeviceId = mDeviceId;
    }

    public String getSenha() {
        return mSenha;
    }

    public void setSenha(String mSenha) {
        this.mSenha = mSenha;
    }

    public String getUsuario() {
        return mUsuario;
    }

    public void setUsuario(String mUsuario) {
        this.mUsuario = mUsuario;
    }

    public String getCelular() {
        return mCelular;
    }

    public void setCelular(String mCelular) {
        this.mCelular = mCelular;
    }
}