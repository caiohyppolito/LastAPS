package com.unip.apppedido.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.unip.apppedido.R;
import com.unip.apppedido.utils.AppControllerUtil;
import com.unip.apppedido.utils.HttpVolleyUtil;
import org.json.JSONObject;

public class RegisterActivity extends BaseActivity {
    private EditText mNameEditText;
    private EditText mPasswordEditText;
    private View mLoginFormView;

    private ProgressBar mProgressBar;
    private LinearLayout mLinearLayoutProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initialize();
    }

    private void initialize() {
        setupToolbar();

        mNameEditText = (EditText) findViewById(R.id.name);
        mPasswordEditText = (EditText) findViewById(R.id.password);

        Button buttonRegister = (Button) findViewById(R.id.buttonRegister);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);

        setupProgress();
    }

    private void setupProgress(){
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mLinearLayoutProgress = (LinearLayout) findViewById(R.id.linearLayoutProgress);
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void attemptLogin() {
        // Reset errors.
        mNameEditText.setError(null);
        mPasswordEditText.setError(null);

        String name = mNameEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(password)) {
            mPasswordEditText.setError(getString(R.string.error_field_required));
            focusView = mPasswordEditText;
            cancel = true;
        }

        if (TextUtils.isEmpty(name)) {
            mNameEditText.setError(getString(R.string.error_field_required));
            focusView = mNameEditText;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);

            HttpVolleyUtil request = new HttpVolleyUtil(Request.Method.GET, "login?usuario=" + name + "&senha=" + password +"&tipoLogin=2", null, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    showProgress(false);

                    if(response != null){
                        try{
                            JSONObject jsonObject = new JSONObject(response);


                            String error = jsonObject.has("ErroMsg") ? jsonObject.getString("ErroMsg") : null;
                            int idUser = jsonObject.has("loginId") ? jsonObject.getInt("loginId") : 0;

                            if((error == null || error.equals("")) && idUser > 0){
                                // Seta no application, para usar em outras activities
                                setIdUser(idUser);

                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            }
                            else
                            {
                                Snackbar.make(mLoginFormView, error, Snackbar.LENGTH_LONG).show();
                            }

                        }catch (Exception e){
                            Snackbar.make(mLoginFormView, R.string.error_login, Snackbar.LENGTH_LONG).show();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    showProgress(false);

                    Snackbar.make(mLoginFormView, R.string.error_login, Snackbar.LENGTH_LONG).show();
                }
            });

            // Adding request to request queue
            AppControllerUtil.getInstance(this).addToRequestQueue(request);
        }
    }

    private void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        mLinearLayoutProgress.setVisibility(show ? View.VISIBLE : View.GONE);
        mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
}