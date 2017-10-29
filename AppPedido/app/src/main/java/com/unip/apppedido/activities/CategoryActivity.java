package com.unip.apppedido.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.unip.apppedido.R;
import com.unip.apppedido.adapters.CategoryAdapter;
import com.unip.apppedido.models.CategoryModel;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends BaseActivity
    implements CategoryAdapter.ICategoryListener{

    private CategoryAdapter mCategoryAdapter;
    private List<CategoryModel> mListCategory;

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

        mListCategory = new ArrayList<>();

        setupToolbar();
        setupRecyclerView();
        loadDataFake();
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

    private void loadDataFake()
    {
        for (int i = 0; i < 10; i++) {
            mListCategory.add(new CategoryModel(i, "Teste: " + i));
        }

        mCategoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClickListener(CategoryModel category) {
        /*Tratando o clique em um elemento da lista de categorias*/
        Intent intent = new Intent(getApplicationContext(), ProductActivity.class);
        intent.putExtra(ProductActivity.PUT_EXTRA_ID_CATEGORY, category.getId());
        startActivity(intent);
    }
}
