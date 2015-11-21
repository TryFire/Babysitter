package com.example.function.babysitter.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.function.babysitter.R;
import com.example.function.babysitter.Utils;


/**
 * Created by KongFanyang on 2015/10/31.
 */
public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView appName;
    private LinearLayout headerView;
    private Button registerButton;
    private Button loginButton;
    private int screenHeight;
    private boolean login = false;
    private ImageView appPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);

        screenHeight = Utils.getScreenHeight(this);  // 屏幕高度（像素）

        initView();

        headerView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

            @Override
            public boolean onPreDraw() {
                headerView.getViewTreeObserver().removeOnPreDrawListener(this);
                anim();
                return true;
            }
        });


    }

    private void initView() {
        appPic = (ImageView) findViewById(R.id.app_pic);
        appName = (TextView) findViewById(R.id.app_name);
        headerView = (LinearLayout) findViewById(R.id.header_view);
        registerButton = (Button) findViewById(R.id.button_register);
        loginButton = (Button) findViewById(R.id.button_login);
        registerButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_register:
                Intent loginIntent = new Intent(this, RegisterActivity.class);
                startActivity(loginIntent);
                break;
            case R.id.button_login:
                break;
        }
    }

    private void anim() {

        if (login) {
            registerButton.setVisibility(View.GONE);
            loginButton.setVisibility(View.GONE);
        } else {
            registerButton.setVisibility(View.VISIBLE);
            loginButton.setVisibility(View.VISIBLE);
        }
        appPic.setTranslationY(-appPic.getY() - appPic.getHeight());
        appName.setTranslationY(-appName.getY() - appName.getHeight());
        headerView.setTranslationY(-headerView.getY() - headerView.getHeight());
        registerButton.setTranslationY((screenHeight - registerButton.getY()));
        loginButton.setTranslationY((screenHeight - loginButton.getY()));

        headerView.animate()
                .translationY(0)
                .setInterpolator(new OvershootInterpolator(1.f))
                .setDuration(650)
                .setStartDelay(300)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (login) {
                            headerView.animate()
                                    .translationY(screenHeight / 2 * 1.4f)
                                    .setDuration(400)
                                    .start();
                        }
                    }
                });

        appName.animate()
                .translationY(0)
                .setInterpolator(new OvershootInterpolator(1.f))
                .setDuration(550)
                .setStartDelay(500);

        appPic.animate()
                .translationY(0)
                .setInterpolator(new OvershootInterpolator(1.f))
                .setDuration(550)
                .setStartDelay(530);

        registerButton.animate()
                .translationY(0)
                .setInterpolator(new DecelerateInterpolator())
                .setDuration(300)
                .setStartDelay(1000);

        loginButton.animate()
                .translationY(0)
                .setInterpolator(new DecelerateInterpolator())
                .setDuration(300)
                .setStartDelay(1100)
                .start();
    }
}
