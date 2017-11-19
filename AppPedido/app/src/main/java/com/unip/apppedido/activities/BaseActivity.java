package com.unip.apppedido.activities;

import android.support.v7.app.AppCompatActivity;

import com.unip.apppedido.application.AppPedidoApplication;
import com.unip.apppedido.models.ProductModel;
import com.unip.apppedido.models.ShoppingCartModel;

import java.util.List;

public class BaseActivity extends AppCompatActivity{

    public AppPedidoApplication getAppPedidoApplication(){
        return (AppPedidoApplication) getApplication();
    }

    public List<ShoppingCartModel> getListShoppingCart() {
        return getAppPedidoApplication().getListShoppingCart();
    }

    public List<ShoppingCartModel> addItemShoppingCart(ProductModel product, int quantity){
        return getAppPedidoApplication().addItemShoppingCart(product, quantity);
    }

    public List<ShoppingCartModel> removeItemShoppingCart(ShoppingCartModel itemShoppingCart){
        return getAppPedidoApplication().removeItemShoppingCart(itemShoppingCart);
    }

    public List<ShoppingCartModel> updateQuantityShoppingCart(int index, int newQuantity){
        return getAppPedidoApplication().updateQuantityShoppingCart(index, newQuantity);
    }

    public void resetListShoppingCart(){
        getAppPedidoApplication().resetListShoppingCart();
    }

    public void setIdEstabelecimento(int id){
        getAppPedidoApplication().setIdEstabelecimento(id);
    }

    public int getIdEstabelecimento() {
        return getAppPedidoApplication().getIdEstabelecimento();
    }

    public int getIdUser() {
        return getAppPedidoApplication().getIdUser();
    }

    public void setIdUser(int idUser) {
        this.getAppPedidoApplication().setIdUser(idUser);
    }
}