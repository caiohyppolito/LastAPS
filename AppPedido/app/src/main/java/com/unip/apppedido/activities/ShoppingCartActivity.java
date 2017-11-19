package com.unip.apppedido.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.unip.apppedido.R;
import com.unip.apppedido.adapters.ShoppingCartAdapter;
import com.unip.apppedido.models.ProductModel;
import com.unip.apppedido.models.ShoppingCartModel;
import com.unip.apppedido.utils.AppControllerUtil;
import com.unip.apppedido.utils.HttpVolleyUtil;
import com.unip.apppedido.utils.MoneyFormatUtil;
import com.unip.apppedido.utils.ResponseJsonUtil;

import org.json.JSONObject;

import java.util.List;

public class ShoppingCartActivity extends BaseActivity implements ShoppingCartAdapter.IShoppingCartListener {
    public static final String PUT_EXTRA_PRODUCT = "PutExtraProduct";

    private List<ShoppingCartModel> mListShoppingCart;
    private ShoppingCartAdapter mShoppingCartAdapter;

    private View mView;

    private ProgressBar mProgressBar;
    private LinearLayout mLinearLayoutProgress;

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

        mView = findViewById(R.id.linearLayout);

        setTotalShoppingCart();

        setupProgress();

        setupToolbar();
        setupRecyclerView();

        Button buttonCheckout = (Button) findViewById(R.id.buttonCheckout);

        buttonCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress(true);

                HttpVolleyUtil request = new HttpVolleyUtil(Request.Method.POST, "Pedido", new Gson().toJson(mListShoppingCart), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response != null){
                            try{
                                JSONObject jsonObject = new JSONObject(response);

                                String error = jsonObject.has("Erro") ? jsonObject.getString("Erro") : null;

                                if(error == null || error.equals("")){
                                    mView.setVisibility(View.GONE);

                                    Snackbar.make(mView, getString(R.string.success_sell), Snackbar.LENGTH_LONG).show();

                                    final Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                        }
                                    }, 1000);
                                }
                                else
                                {
                                    Snackbar.make(mView, error, Snackbar.LENGTH_LONG).show();
                                }

                            }catch (Exception e){
                                Snackbar.make(mView, R.string.error_load, Snackbar.LENGTH_LONG).show();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showProgress(false);

                        Snackbar.make(mView, R.string.error_load, Snackbar.LENGTH_LONG).show();
                    }
                });

                // Adding request to request queue
                AppControllerUtil.getInstance(ShoppingCartActivity.this).addToRequestQueue(request);
            }
        });
    }

    private void setupProgress(){
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mLinearLayoutProgress = (LinearLayout) findViewById(R.id.linearLayoutProgress);
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

    private void setTotalShoppingCart(){
        TextView textViewTotalShoppingCart = (TextView) findViewById(R.id.textViewTotalCart);

        double total = 0;

        if(mListShoppingCart != null){
            int size = mListShoppingCart.size();

            for (int i = 0; i < size; i++) {
                total += mListShoppingCart.get(i).getTotalProduct();
            }
        }

        textViewTotalShoppingCart.setText(MoneyFormatUtil.formatToMoney(total));
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

    private void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewShoppingCart);

        recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
        mLinearLayoutProgress.setVisibility(show ? View.VISIBLE : View.GONE);
        recyclerView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressBar.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    public void onClickListener(View view, ShoppingCartModel itemShoppingCart, boolean isToRemoveQuantity) {
        if(isToRemoveQuantity && itemShoppingCart.getQuantity() == 1){
            // Remover do carrinho

            mListShoppingCart.remove(itemShoppingCart);

            if(mListShoppingCart.size() == 0)
            {
                startActivity(new Intent(getApplicationContext(), CategoryActivity.class));
            }
        }else{
            itemShoppingCart.setQuantity(isToRemoveQuantity ? (itemShoppingCart.getQuantity() - 1) : (itemShoppingCart.getQuantity() + 1));

            TextView textViewQuantity = (TextView) view.findViewById(R.id.textViewQuantity);
            TextView textViewTotalProduct = (TextView) view.findViewById(R.id.textViewTotalProduct);

            textViewQuantity.setText(String.valueOf(itemShoppingCart.getQuantity()));
            textViewTotalProduct.setText(MoneyFormatUtil.formatToMoney(itemShoppingCart.getTotalProduct()));

            setTotalShoppingCart();
        }
    }
}
