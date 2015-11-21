package com.example.function.babysitter.util;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;

import com.example.function.babysitter.R;


/**
 * Created by K on 2015/8/19.
 */
public class ArcMenu extends ViewGroup implements View.OnClickListener {

    private int mRadius;
    private int marginRight;
    private int marginBottom;
    private int centerX;
    private int centerY;
    private int childCount;
    private double belta;
    private final Drawable background = super.getBackground();
    /**
     * 菜单的状态
     */
    private Status mCurrentStatus = Status.CLOSE;

    /**
     * 菜单的主按钮
     */
    private View mCButton;
    private OnMenuItemClickListener mMenuItemClickListener;

    public void setOnMenuItemClickListener(OnMenuItemClickListener mMenuItemClickListener) {
        this.mMenuItemClickListener = mMenuItemClickListener;
    }

    public interface OnMenuItemClickListener {
        void onClick(View view, int pos);
    }

    public enum Status {
        OPEN, CLOSE
    }

    public ArcMenu(Context context) {
        this(context, null);
    }

    public ArcMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ArcMenu, defStyleAttr, 0);

        mRadius = (int) a.getDimension(R.styleable.ArcMenu_radius,
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics()));
        marginRight = (int) a.getDimension(R.styleable.ArcMenu_margin_right,
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, getResources().getDisplayMetrics()));
        marginBottom = (int) a.getDimension(R.styleable.ArcMenu_margin_bottom,
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, getResources().getDisplayMetrics()));
        a.recycle();
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        childCount = getChildCount();
        if (changed && childCount > 0) {

            layoutCButton();//主菜单的位置
            belta = Math.PI / 2 / (childCount - 2);
            for (int i = 0; i < childCount - 1; i++) {
                View child = getChildAt(i);
                child.setVisibility(View.GONE);
                int cx = (int) (mRadius * Math.cos(belta * i));
                int cy = (int) (mRadius * Math.sin(belta * i));
                int cWidth = child.getMeasuredWidth();
                int cHeight = child.getMeasuredHeight();

                child.layout(centerX - cWidth / 2 - cx, centerY - cHeight / 2 - cy,
                        centerX + cWidth / 2 - cx, centerY + cHeight / 2 - cy);
            }
        }
    }

    /**
     * 定位主菜单按钮
     */
    private void layoutCButton() {
        mCButton = getChildAt(childCount - 1);
        mCButton.setOnClickListener(this);

        int width = mCButton.getMeasuredWidth();
        int height = mCButton.getMeasuredHeight();
        centerX = getMeasuredWidth() - marginRight - width / 2;
        centerY = getMeasuredHeight() - marginBottom - height / 2;
        int l = centerX - width / 2;
        int t = centerY - height / 2;
        mCButton.layout(l, t, l + width, t + height);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            //测量Child
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 中心按钮点击事件及动画
     *
     * @param v
     */

    @Override
    public void onClick(View v) {
        v.setClickable(false);
        rotateCButton(v, 0f, 360f, 280);
        toggleMenu(280);
        v.setClickable(true);

    }

    private void rotateCButton(View v, float start, float end, int duration) {

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(v, "rotation", start, end);
        objectAnimator.setDuration(duration);
        objectAnimator.start();
    }

    /**
     * 切换菜单
     */
    public void toggleMenu(int duration) {
        /**
         * 为menuItem添加平移动画和旋转动画
         */
        AnimatorSet animSet = new AnimatorSet();
        for (int i = 0; i < childCount - 1; i++) {
            final View childView = getChildAt(i);

            childView.setVisibility(View.VISIBLE);

            ObjectAnimator animatorX;
            ObjectAnimator animatorY;
            if (mCurrentStatus == Status.CLOSE) {

                super.setBackgroundColor(getResources().getColor(R.color.background_gray));

                animatorX = ObjectAnimator.ofFloat(childView, "translationX" , (float)(mRadius * Math.cos(belta * i)), 0f);
                animatorY = ObjectAnimator.ofFloat(childView, "translationY", (float)(mRadius * Math.sin(belta * i)), 0f);
                animSet.setDuration(duration);
                animSet.setInterpolator(new OvershootInterpolator());
                animSet.play(animatorX).with(animatorY);
                childView.setClickable(true);
                childView.setFocusable(true);
                animSet.start();
            } else { //to close
                super.setBackground(background);
                childView.setClickable(false);
                childView.setFocusable(false);
                animatorX = ObjectAnimator.ofFloat(childView, "translationX", 0f ,(float)(mRadius * Math.cos(belta * i)));
                animatorY = ObjectAnimator.ofFloat(childView, "translationY", 0f, (float)(mRadius * Math.sin(belta * i)));
                animSet.setDuration(duration);
                animSet.play(animatorX).with(animatorY);
                animSet.start();
            }




           /* tranAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (mCurrentStatus == Status.CLOSE) {
                        childView.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            RotateAnimation rotateAnim = new RotateAnimation(0, 720, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnim.setDuration(duration);
            rotateAnim.setFillAfter(true);

*/
            final int pos = i + 1;
            childView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mMenuItemClickListener != null) {
                        mMenuItemClickListener.onClick(childView, pos);
                    }
                    //changeStatus();
                    toggleMenu(120);
                }
            });
        }
        //切换菜单状态
        changeStatus();
    }

    public Status getStatus() {
        return this.mCurrentStatus;
    }

    /**
     * 添加menuItem的点击动画
     */
    private void menuItemAnim(int pos) {
        for (int i = 0; i < getChildCount() - 1; i++) {
            View childView = getChildAt(i);
            if (pos == i) {
                childView.startAnimation(scaleBigAnim(300));
            } else {
                childView.startAnimation(scaleSmallAnim(300));
            }
            childView.setVisibility(INVISIBLE);
            childView.setClickable(false);
            childView.setFocusable(false);
            ObjectAnimator animatorX = ObjectAnimator.ofFloat(childView, "translationX" , (float)(mRadius * Math.cos(belta * i)), 0f);
            ObjectAnimator animatorY = ObjectAnimator.ofFloat(childView, "translationY", (float)(mRadius * Math.sin(belta * i)), 0f);
            animatorX.start();
            animatorY.start();
            childView.setClickable(true);
            childView.setFocusable(true);
        }

    }

    /**
     * 为当前点击的item设置缩小动画
     */
    private Animation scaleSmallAnim(int duration) {
        AnimationSet animationSet = new AnimationSet(true);
        ScaleAnimation scaleAnim = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        AlphaAnimation alphaAnim = new AlphaAnimation(1f, 0.0f);
        animationSet.addAnimation(scaleAnim);
        animationSet.addAnimation(alphaAnim);

        animationSet.setDuration(duration);
        animationSet.setFillAfter(true);

        return animationSet;
    }

    /**
     * 为当前点击的item设置放大动画
     */
    private Animation scaleBigAnim(int duration) {
        AnimationSet animationSet = new AnimationSet(true);
        ScaleAnimation scaleAnim = new ScaleAnimation(1.0f, 3.0f, 1.0f, 4.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        AlphaAnimation alphaAnim = new AlphaAnimation(1f, 0.0f);
        animationSet.addAnimation(scaleAnim);
        animationSet.addAnimation(alphaAnim);

        animationSet.setDuration(duration);
        animationSet.setFillAfter(true);

        return animationSet;
    }

    private void changeStatus() {
        mCurrentStatus = (mCurrentStatus == Status.CLOSE ? Status.OPEN : Status.CLOSE);
    }


}
