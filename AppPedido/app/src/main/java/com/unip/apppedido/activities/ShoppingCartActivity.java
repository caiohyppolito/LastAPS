package com.unip.apppedido.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.unip.apppedido.R;
import com.unip.apppedido.adapters.ShoppingCartAdapter;
import com.unip.apppedido.models.ProductModel;
import com.unip.apppedido.models.ShoppingCartModel;
import com.unip.apppedido.utils.MoneyFormatUtil;

import java.util.List;

public class ShoppingCartActivity extends BaseActivity implements ShoppingCartAdapter.IShoppingCartListener {
    public static final String PUT_EXTRA_PRODUCT = "PutExtraProduct";

    private List<ShoppingCartModel> mListShoppingCart;
    private ShoppingCartAdapter mShoppingCartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        initialize(savedInstanceState);
    }

    private void initialize(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            ProductModel product = getIntent().getParcelableExtra(PUT_EXTRA_PRODUCT);

            if (product != null) {
                mListShoppingCart = addItemShoppingCart(product, 1); // Adiciona com a qtde = 1
            }
        }

        if (!(mListShoppingCart != null && mListShoppingCart.size() > 0)) {
            mListShoppingCart = getListShoppingCart();
        }

        TextView textViewTotalShoppingCart = (TextView) findViewById(R.id.textViewTotalCart);
        textViewTotalShoppingCart.setText(MoneyFormatUtil.formatToMoney(getTotalShoppingCart()));

        setupToolbar();
        setupRecyclerView();
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewShoppingCart);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        setupAdapter(recyclerView);
    }

    private void setupAdapter(RecyclerView recyclerView) {
        mShoppingCartAdapter = new ShoppingCartAdapter(mListShoppingCart, this);

        recyclerView.setAdapter(mShoppingCartAdapter);
    }

    private double getTotalShoppingCart(){
        if(mListShoppingCart != null){
            int size = mListShoppingCart.size();
            double total = 0;

            for (int i = 0; i < size; i++) {
                total += mListShoppingCart.get(i).getTotalProduct();
            }

            return total;
        }

        return 0;
    }

    @Override
    public void onClickListener(ShoppingCartModel itemShoppingCart) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_shopping_cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuAddMoreItems:
                Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }
}
