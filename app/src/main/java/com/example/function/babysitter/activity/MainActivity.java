package com.example.function.babysitter.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.example.function.babysitter.R;
import com.example.function.babysitter.Utils;
import com.example.function.babysitter.adapter.FragmentsAdapter;
import com.example.function.babysitter.fragment.ShareFrag;
import com.example.function.babysitter.fragment.TimeTreeFrag;
import com.example.function.babysitter.util.ArcMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

public class MainActivity extends BaseDrawerActivity {


    @InjectView(R.id.tab_layout)
    TabLayout mTabLayout;
    @InjectView(R.id.viewpager)
    ViewPager mViewPager;
    @InjectView(R.id.arc_menu)
    ArcMenu arcMenu;

    private boolean pendingIntroAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {
            pendingIntroAnimation = true;
        }
        setUpTabLayout();
        arcMenu.setOnMenuItemClickListener(new ArcMenu.OnMenuItemClickListener() {
            @Override
            public void onClick(View view, int pos) {
                switch (pos) {
                    case 4:
                        Intent intent = new Intent(getApplicationContext(), KeepTime.class);
                        startActivity(intent);
                }
            }
        });

    }

    private void setUpTabLayout() {
        List<String> titles = new ArrayList<>();
        titles.add("我的宝贝");
        titles.add("社区");


        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(1)));

        List<Fragment> fragments = new ArrayList<>();
        TimeTreeFrag timeTreeFrag = new TimeTreeFrag();
        ShareFrag shareFrag = new ShareFrag();
        fragments.add(timeTreeFrag);
        fragments.add(shareFrag);

        FragmentsAdapter fragmentsAdapter = new FragmentsAdapter(getSupportFragmentManager(), fragments, titles);
        mViewPager.setAdapter(fragmentsAdapter);
        mTabLayout.setTabsFromPagerAdapter(fragmentsAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }


    private void startIntroAnimation() {
        arcMenu.setTranslationY(2 * getResources().getDimensionPixelOffset(R.dimen.btn_fab_size));

        int actionbarSize = Utils.dpToPx(56);
        getToolbar().setTranslationY(-actionbarSize);
        getIvLogo().setTranslationY(-actionbarSize);
        getInboxMenuItem().getActionView().setTranslationY(-actionbarSize);

        mTabLayout.setAlpha(0);

        getToolbar().animate()
                .translationY(0)
                .setDuration(300)
                .setStartDelay(300);
        getIvLogo().animate()
                .translationY(0)
                .setDuration(300)
                .setStartDelay(450);
        getInboxMenuItem().getActionView().animate()
                .translationY(0)
                .setDuration(300)
                .setStartDelay(600)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        //startContentAnimation();
                        mTabLayout.animate()
                                .alpha(1)
                                .setDuration(300);
                        arcMenu.animate()
                                .translationY(0)
                                .setInterpolator(new OvershootInterpolator(1.f))
                                .setDuration(300)
                                .setStartDelay(300)
                                .start();
                    }
                })
                .start();

    }

    private void startContentAnimation() {
        arcMenu.animate()
                .translationY(0)
                .setInterpolator(new OvershootInterpolator(1.f))
                .setDuration(300)
                .setStartDelay(300)
                .start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if(pendingIntroAnimation) {
            pendingIntroAnimation = false;
            startIntroAnimation();
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && arcMenu.getStatus() == ArcMenu.Status.OPEN) {
            arcMenu.toggleMenu(100);
            return true;
        } else
            return super.onKeyDown(keyCode, event);
    }
}
