package com.example.function.babysitter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.function.babysitter.R;

/**
 * Created by KongFanyang on 2015/11/21.
 */
public class RegisterActivity extends BaseActivity {
    private Button regisLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regisLoginButton = (Button) findViewById(R.id.regiser_login);

        regisLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(getApplication(), MainActivity.class);
                startActivity(loginIntent);
            }
        });

    }

    @Override
    public void setupToolBar() {
        if(getToolbar() != null) {
            setSupportActionBar(getToolbar());
            getToolbar().setNavigationIcon(R.drawable.ic_menu_back);
            getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }
}
