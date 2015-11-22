package com.example.function.babysitter.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.function.babysitter.R;
import com.example.function.babysitter.Utils;
import com.example.function.babysitter.util.SquaredImageView;

import butterknife.InjectView;

public class CardDetailActivity extends BaseActivity {

    @InjectView(R.id.content_root)
    View contentRoot;
    @InjectView(R.id.sq_iv)
    SquaredImageView squaredImageView;
    @InjectView(R.id.tv_detail)
    TextView textViewDetail;
    public static final String ARG_DRAWING_START_LOCATION = "animation";
    public static final String TIME_ITEM = "time";

    private int startDrawingLocation;
    private int itemPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_detail);

        startDrawingLocation = getIntent().getIntExtra(ARG_DRAWING_START_LOCATION, 0);
        itemPosition = getIntent().getIntExtra(TIME_ITEM, 0);
        setupImageView(itemPosition);
        if (savedInstanceState == null) {
            contentRoot.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    contentRoot.getViewTreeObserver().removeOnPreDrawListener(this);
                    startIntroAnimation();
                    return true;
                }
            });
        }
    }

    private void setupImageView(int position) {
        if(position % 5 == 0) {
            squaredImageView.setImageResource(R.drawable.one);
            textViewDetail.setText(getString(R.string.detail_one));
        } else if (position % 5 == 1) {
            squaredImageView.setImageResource(R.drawable.two);
            textViewDetail.setText(getString(R.string.detail_two));
        } else if (position % 5 == 2) {
            squaredImageView.setImageResource(R.drawable.three);
            textViewDetail.setText(getString(R.string.detail_three));
        } else if (position % 5 == 3) {
            squaredImageView.setImageResource(R.drawable.four);
            textViewDetail.setText(getString(R.string.detail_four));
        } else {
            squaredImageView.setImageResource(R.drawable.five);
            textViewDetail.setText(getString(R.string.detail_five));
        }
    }

    private void startIntroAnimation() {
        //  ViewCompat.setElevation(getToolbar(), 0);
        contentRoot.setScaleY(0.1f);
        contentRoot.setPivotY(startDrawingLocation);

        contentRoot.animate()
                .scaleY(1)
                .setDuration(200)
                .setInterpolator(new AccelerateInterpolator())
                .start();
    }

    @Override
    public void onBackPressed() {
        contentRoot.animate()
                .translationY(Utils.getScreenHeight(this))
                .setDuration(200)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        CardDetailActivity.super.onBackPressed();
                        overridePendingTransition(0, 0);
                    }
                })
                .start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
