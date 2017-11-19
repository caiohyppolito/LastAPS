package com.unip.apppedido.application;

import android.app.Application;

import com.unip.apppedido.models.ProductModel;
import com.unip.apppedido.models.ShoppingCartModel;

import java.util.ArrayList;
import java.util.List;

public class AppPedidoApplication extends Application {
    private List<ShoppingCartModel> mListShoppingCart = new ArrayList<>();
    private int mIdEstabelecimento;
    private int mIdUser;

    public AppPedidoApplication() {
        super();
    }

    public List<ShoppingCartModel> getListShoppingCart() {
        return mListShoppingCart;
    }

    public List<ShoppingCartModel> addItemShoppingCart(ProductModel product, int quantity) {
        mListShoppingCart.add(0,
                new ShoppingCartModel
                        (
                                product.getId(),
                                product.getName(),
                                product.getValue(),
                                mIdUser,
                                mIdEstabelecimento,
                                quantity
                        )
        ); // Sempre adiciona na 1 posição

        return mListShoppingCart;
    }

    public List<ShoppingCartModel> removeItemShoppingCart(ShoppingCartModel itemShoppingCart) {
        mListShoppingCart.remove(itemShoppingCart);

        return mListShoppingCart;
    }

    public List<ShoppingCartModel> updateQuantityShoppingCart(int index, int newQuantity) {
        if (newQuantity <= 0) {
            // Remover o item da lista

            return removeItemShoppingCart(mListShoppingCart.get(index));
        } else {
            mListShoppingCart.get(index).setQuantity(newQuantity);

            return mListShoppingCart;
        }
    }

    public void resetListShoppingCart() {
        mListShoppingCart = new ArrayList<>();

        mIdEstabelecimento = 0;
    }

    public int getIdEstabelecimento() {
        return mIdEstabelecimento;
    }

    public void setIdEstabelecimento(int idEstabelecimento) {
        this.mIdEstabelecimento = idEstabelecimento;
    }

    public int getIdUser() {
        return mIdUser;
    }

    public void setIdUser(int idUser) {
        this.mIdUser = idUser;
    }
}