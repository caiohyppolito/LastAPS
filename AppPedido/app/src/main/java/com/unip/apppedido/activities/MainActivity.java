package com.unip.apppedido.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.unip.apppedido.R;
import com.unip.apppedido.adapters.EstabelecimentoAdapter;
import com.unip.apppedido.models.EstabelecimentoModel;
import com.unip.apppedido.utils.AppPackageUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        EstabelecimentoAdapter.IEstabelecimentoListener{

    private List<EstabelecimentoModel> mListEstabelecimento;
    private EstabelecimentoAdapter mEstabelecimentoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }

    @Override
    protected void onResume() {
        super.onResume();

        resetListShoppingCart(); // Zera a lista que está no application para controle do carrinho
    }

    private void initialize(){
        mListEstabelecimento = new ArrayList<>();

        setupToolbar();
        setupRecyclerView();
        loadDataFake();
    }

    private void setupToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupDrawerLayout(toolbar);
    }

    private void setupRecyclerView(){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewEstabelecimento);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        setupAdapter(recyclerView);
    }

    private void setupAdapter(RecyclerView recyclerView){
        mEstabelecimentoAdapter = new EstabelecimentoAdapter(mListEstabelecimento, this);

        recyclerView.setAdapter(mEstabelecimentoAdapter);
    }

    private void setupDrawerLayout(Toolbar toolbar){
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setupTextNavigationHeader(navigationView.getHeaderView(0));
    }

    private void setupTextNavigationHeader(View viewNavigation) {
        TextView textViewVersion = (TextView) viewNavigation.findViewById(R.id.textViewVersionNavHeader);

        textViewVersion.setText(getString(R.string.textViewVersion, AppPackageUtil.getVersionName(this)));
    }

    private void loadDataFake()
    {
        for (int i = 0; i < 10; i++) {
            mListEstabelecimento.add(new EstabelecimentoModel(i, "Teste: " + i));
        }

        mEstabelecimentoAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.navAbout) {
            startActivity(new Intent(getApplicationContext(), AboutActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClickListener(EstabelecimentoModel estabelecimento) {
        /*Tratando o clique em um elemento da lista de estabelecimentos*/

        // Salvando o id no application, de forma que todas as acts possam acessar
        setIdEstabelecimento(estabelecimento.getId());

        Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);
        startActivity(intent);
    }
}