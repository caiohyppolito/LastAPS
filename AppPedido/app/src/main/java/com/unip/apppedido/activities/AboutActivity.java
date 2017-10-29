package com.unip.apppedido.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.unip.apppedido.R;
import com.unip.apppedido.utils.AppPackageUtil;
import com.unip.apppedido.utils.DateUtil;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        initialize();
    }

    private void initialize() {
        TextView textViewVersionApp = (TextView) findViewById(R.id.text_view_version_app);
        TextView textViewCopyright = (TextView) findViewById(R.id.text_view_copyright);
        textViewVersionApp.setText(getString(R.string.textViewVersion, AppPackageUtil.getVersionName(this)));
        textViewCopyright.setText(getString(R.string.textViewCopyright, DateUtil.getYearCurrent()));
    }
}
