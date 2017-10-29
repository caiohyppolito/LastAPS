package com.unip.apppedido.activities;

import android.content.Intent;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.unip.apppedido.R;
import com.unip.apppedido.adapters.ProductAdapter;
import com.unip.apppedido.models.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends BaseActivity implements ProductAdapter.IProductListener {

    public static final String PUT_EXTRA_ID_CATEGORY = "PutExtraIdCategory";

    private ProductAdapter mProductAdapter;
    private List<ProductModel> mListProduct;
    private int mIdCategory;

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

        mListProduct = new ArrayList<>();

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

    private void loadDataFake() {
        // O id do estabelecimento está "salvo" no application. Função que retorna "getIdEstabelecimento"

        int idEstabelecimento = getIdEstabelecimento();

        for (int i = 0; i < 10; i++) {
            mListProduct.add(new ProductModel(i, "Teste: " + i, 10 * (i + 1)));
        }

        mProductAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClickListener(ProductModel product) {
        Intent intent = new Intent(getApplicationContext(), ShoppingCartActivity.class);
        intent.putExtra(ShoppingCartActivity.PUT_EXTRA_PRODUCT, (Parcelable) product);
        startActivity(intent);
    }
}