package com.unip.apppedido.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.unip.apppedido.R;
import com.unip.apppedido.adapters.ProductAdapter;
import com.unip.apppedido.models.CategoryModel;
import com.unip.apppedido.models.ProductModel;
import com.unip.apppedido.utils.AppControllerUtil;
import com.unip.apppedido.utils.HttpVolleyUtil;
import com.unip.apppedido.utils.ResponseJsonUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends BaseActivity implements ProductAdapter.IProductListener {

    public static final String PUT_EXTRA_ID_CATEGORY = "PutExtraIdCategory";

    private View mView;

    private ProductAdapter mProductAdapter;
    private List<ProductModel> mListProduct;
    private int mIdCategory;

    private ProgressBar mProgressBar;
    private LinearLayout mLinearLayoutProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        initialize(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();

        setupButtonFab();
    }

    private void initialize(Bundle savedInstanceState) {

        if (savedInstanceState == null) {
            // Pagando o id da categoria que foi clicado
            mIdCategory = getIntent().getIntExtra(PUT_EXTRA_ID_CATEGORY, 0);
        }

        setupProgress();
        setupToolbar();

        mView = findViewById(R.id.coordinatorLayout);

        loadProducts();
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
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewProduct);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        setupAdapter(recyclerView);
    }

    private void setupAdapter(RecyclerView recyclerView) {
        mProductAdapter = new ProductAdapter(mListProduct, this);

        recyclerView.setAdapter(mProductAdapter);
    }

    private void setupButtonFab(){
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabShoppingCart);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ShoppingCartActivity.class));
            }
        });

        if(getListShoppingCart().size() > 0) {
            fab.show();
        }
    }

    private void loadProducts() {
        showProgress(true);

        // O id do estabelecimento está "salvo" no application. Função que retorna "getIdEstabelecimento"


        HttpVolleyUtil request = new HttpVolleyUtil(Request.Method.GET, "Produtos/GetProdutosPorCategoria?IdEmpresa=" + getIdEstabelecimento() + "&idCategoria=" + String.valueOf(mIdCategory), null, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                showProgress(false);

                if(response != null){
                    try{
                        JSONObject jsonObject = new JSONObject(response);

                        String error = jsonObject.has("Erro") ? jsonObject.getString("Erro") : null;

                        if(error == null || error.equals("") || error.equals("null")){
                            mListProduct = new Gson().fromJson(ResponseJsonUtil.getListJson(response), new TypeToken<List<ProductModel>>(){}.getType());

                            setupRecyclerView();
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
        AppControllerUtil.getInstance(this).addToRequestQueue(request);
    }

    private void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewProduct);

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
    public void onClickListener(ProductModel product) {
        Intent intent = new Intent(getApplicationContext(), ShoppingCartActivity.class);
        intent.putExtra(ShoppingCartActivity.PUT_EXTRA_PRODUCT, (Parcelable) product);
        startActivity(intent);
    }
}