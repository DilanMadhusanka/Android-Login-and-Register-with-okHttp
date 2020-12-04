package com.mindorks.framework.androidloginandregisterwithsqlitedatabase.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.mindorks.framework.androidloginandregisterwithsqlitedatabase.R;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatTextView textViewLinkGoBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        initViews();
        initListeners();
    }

    private void initViews() {
        textViewLinkGoBack = (AppCompatTextView) findViewById(R.id.textViewLinkGoBack);
    }

    private void initListeners() {
        textViewLinkGoBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textViewLinkGoBack:
                Intent intentLogin = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intentLogin);
        }
    }
}