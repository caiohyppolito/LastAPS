package com.unip.apppedido.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.unip.apppedido.R;
import com.unip.apppedido.adapters.CategoryAdapter;
import com.unip.apppedido.models.CategoryModel;
import com.unip.apppedido.utils.AppControllerUtil;
import com.unip.apppedido.utils.HttpVolleyUtil;
import com.unip.apppedido.utils.ResponseJsonUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends BaseActivity
    implements CategoryAdapter.ICategoryListener{

    private CategoryAdapter mCategoryAdapter;
    private List<CategoryModel> mListCategory;

    private View mView;

    private ProgressBar mProgressBar;
    private LinearLayout mLinearLayoutProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        initialize(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();

        setupButtonFab();
    }

    private void initialize(Bundle savedInstanceState){
        if(getIdEstabelecimento() == 0){
            finish(); // O id do estabelecimento não está configurado
        }

        setupToolbar();
        setupProgress();

        mView = findViewById(R.id.coordinatorLayout);
        loadCategories();
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

    private void setupRecyclerView(){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewCategory);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        setupAdapter(recyclerView);
    }

    private void setupAdapter(RecyclerView recyclerView){
        mCategoryAdapter = new CategoryAdapter(mListCategory, this);

        recyclerView.setAdapter(mCategoryAdapter);
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

    private void loadCategories()
    {
        showProgress(true);
        HttpVolleyUtil request = new HttpVolleyUtil(Request.Method.GET, "TipoProdutos?idEstabelecimento=" + getIdEstabelecimento(), null, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showProgress(false);

                if(response != null){
                    try{
                        JSONObject jsonObject = new JSONObject(response);

                        String error = jsonObject.has("Erro") ? jsonObject.getString("Erro") : null;

                        if(error == null || error.equals("")){
                            mListCategory = new Gson().fromJson(ResponseJsonUtil.getListJson(response), new TypeToken<List<CategoryModel>>(){}.getType());

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

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewCategory);

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
    public void onClickListener(CategoryModel category) {
        /*Tratando o clique em um elemento da lista de categorias*/
        Intent intent = new Intent(getApplicationContext(), ProductActivity.class);
        intent.putExtra(ProductActivity.PUT_EXTRA_ID_CATEGORY, category.getId());
        startActivity(intent);
    }
}
