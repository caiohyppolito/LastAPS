package com.unip.apppedido.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.unip.apppedido.R;
import com.unip.apppedido.adapters.EstabelecimentoAdapter;
import com.unip.apppedido.models.CategoryModel;
import com.unip.apppedido.models.EstabelecimentoModel;
import com.unip.apppedido.utils.AppControllerUtil;
import com.unip.apppedido.utils.AppPackageUtil;
import com.unip.apppedido.utils.HttpVolleyUtil;
import com.unip.apppedido.utils.ResponseJsonUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        EstabelecimentoAdapter.IEstabelecimentoListener{

    private List<EstabelecimentoModel> mListEstabelecimento;
    private EstabelecimentoAdapter mEstabelecimentoAdapter;

    private View mView;

    private ProgressBar mProgressBar;
    private LinearLayout mLinearLayoutProgress;

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
        mView = findViewById(R.id.coordinatorLayoutMainAct);

        setupProgress();
        setupToolbar();
        loadEstabelecimentos();
    }

    private void setupProgress(){
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mLinearLayoutProgress = (LinearLayout) findViewById(R.id.linearLayoutProgress);
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

    private void loadEstabelecimentos()
    {
        showProgress(true);

        HttpVolleyUtil request = new HttpVolleyUtil(Request.Method.GET, "Estabelecimento/Get", null, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showProgress(false);

                if(response != null){
                    try{
                        JSONObject jsonObject = new JSONObject(response);

                        String error = jsonObject.has("MensagemErro") ? jsonObject.getString("MensagemErro") : null;

                        if(error == null || error.equals("")){
                            mListEstabelecimento = new Gson().fromJson(ResponseJsonUtil.getListJson(response), new TypeToken<List<EstabelecimentoModel>>(){}.getType());

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

    private void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mView.setVisibility(show ? View.GONE : View.VISIBLE);
        mLinearLayoutProgress.setVisibility(show ? View.VISIBLE : View.GONE);
        mView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mView.setVisibility(show ? View.GONE : View.VISIBLE);
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
    public void onClickListener(EstabelecimentoModel estabelecimento) {
        /*Tratando o clique em um elemento da lista de estabelecimentos*/

        // Salvando o id no application, de forma que todas as acts possam acessar
        setIdEstabelecimento(estabelecimento.getId());

        Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);
        startActivity(intent);
    }
}