package com.example.function.babysitter.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.function.babysitter.R;
import com.example.function.babysitter.item.ShareItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by KongFanyang on 2015/11/20.
 */
public class ShareAdapter extends RecyclerView.Adapter<ShareAdapter.ItemViewHolder> implements View.OnClickListener {

    private Context context;
    private List<ShareItem> shareItemList;
    private int licks = 6;
    private int count;

    private boolean added = false;

    private static final DecelerateInterpolator DECCELERATE_INTERPOLATOR = new DecelerateInterpolator();
    private static final AccelerateInterpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();
    private static final OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(4);

    private final Map<Integer, Integer> likesCount = new HashMap<>();
    private final Map<RecyclerView.ViewHolder, AnimatorSet> likeAnimations = new HashMap<>();
    private final ArrayList<Integer> likedPositions = new ArrayList<>();

    OnShareCommentItemClickListener shareCommentItemClickListener;

    public ShareAdapter(Context context, List<ShareItem> shareItemList) {
        this.context = context;
        this.shareItemList = shareItemList;
    }

    @Override
    public int getItemCount() {
        return shareItemList.size();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(context).inflate(R.layout.share_item, viewGroup, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        itemViewHolder.likeBtn.setOnClickListener(this);
        itemViewHolder.commentBtn.setOnClickListener(this);
        itemViewHolder.contentImageView.setOnClickListener(this);
        itemViewHolder.contentTextView.setOnClickListener(this);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder itemViewHolder, int i) {
        itemViewHolder.contentTextView.setText(shareItemList.get(i).getShareContent());
        itemViewHolder.contentImageView.setImageDrawable(shareItemList.get(i).getSharePhoto());
        itemViewHolder.shareAvatar.setImageDrawable(shareItemList.get(i).getShareAvatar());

        count = licks * i - (i % licks) * 2 + 3;
        if(count > 30) {
            count = count - 7;
        }

        itemViewHolder.tvLicks.setText( count + " licks");
        itemViewHolder.number = count;
        itemViewHolder.contentTextView.setTag(i);
        itemViewHolder.contentImageView.setTag(itemViewHolder);
        itemViewHolder.likeBtn.setTag(itemViewHolder);
        itemViewHolder.commentBtn.setTag(i);

        /*if(!added) {
            for (int t = 0; t < getItemCount(); t++) {
                likesCount.put(t, new Random().nextInt(30));
            }
            updateLikesCounter(itemViewHolder, true);
            added = true;
        }*/
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.like_btn:
                ItemViewHolder holder = (ItemViewHolder) v.getTag();
                if (!likedPositions.contains(holder.getPosition())) {
                    //likedPositions.add(holder.getPosition());
                   // updateLikesCounter(holder, true);
                    updateHeartButton(holder, true);
                }
                break;
            case R.id.comment_btn :
                if(shareCommentItemClickListener != null) {
                    shareCommentItemClickListener.onCommentClick(v, (Integer)v.getTag());
                }
                break;
            case R.id.content_tv :
                break;
            case R.id.content_photo :
                /*ItemViewHolder itemViewHolder = (ItemViewHolder) v.getTag();
                animatePhotoLike(itemViewHolder);
                updateHeartButton(itemViewHolder, true);*/
                break;

        }
    }

    private void updateHeartButton(final ItemViewHolder holder, boolean animated) {
        if (animated) {
            if (!likeAnimations.containsKey(holder)) {
                AnimatorSet animatorSet = new AnimatorSet();
                likeAnimations.put(holder, animatorSet);

                ObjectAnimator rotationAnim = ObjectAnimator.ofFloat(holder.likeBtn, "rotation", 0f, 360f);
                rotationAnim.setDuration(300);
                rotationAnim.setInterpolator(ACCELERATE_INTERPOLATOR);

                ObjectAnimator bounceAnimX = ObjectAnimator.ofFloat(holder.likeBtn, "scaleX", 0.2f, 1f);
                bounceAnimX.setDuration(300);
                bounceAnimX.setInterpolator(OVERSHOOT_INTERPOLATOR);

                ObjectAnimator bounceAnimY = ObjectAnimator.ofFloat(holder.likeBtn, "scaleY", 0.2f, 1f);
                bounceAnimY.setDuration(300);
                bounceAnimY.setInterpolator(OVERSHOOT_INTERPOLATOR);
                bounceAnimY.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        holder.likeBtn.setImageResource(R.drawable.ic_heart_outline_red);
                        holder.tvLicks.setText(holder.number + 1 + " licks");
                       // holder.likeBtn.setBackgroundResource(R.drawable.ic_heart_red);
                    }
                });

                animatorSet.play(rotationAnim);
                animatorSet.play(bounceAnimX).with(bounceAnimY).after(rotationAnim);

                animatorSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        //resetLikeAnimationState(holder);
                    }
                });

                animatorSet.start();
            }
        } else {
            if (likedPositions.contains(holder.getPosition())) {
                holder.likeBtn.setImageResource(R.drawable.ic_heart_outline_red);
                holder.tvLicks.setText(holder.number + 1 + " licks");
                //holder.likeBtn.setBackgroundResource(R.drawable.ic_heart_red);
            } else {
                holder.likeBtn.setImageResource(R.drawable.ic_heart_outline_grey);
            }
        }
    }

    /*private void (ItemViewHolder holder, boolean animated) {
        int currentLikesCount = likesCount.get(holder.getPosition()) + 1;
        String likesCountText = currentLikesCount + " likes";

        if (animated) {
            holder.tvLicks.setText(likesCountText);
        } else {
          //  holder.tvLicks.setCurrentText(likesCountText);
        }

        likesCount.put(holder.getPosition(), currentLikesCount);
    }*/

    private void animatePhotoLike(final ItemViewHolder holder) {
        if (!likeAnimations.containsKey(holder)) {
            holder.viewBackgroundLick.setVisibility(View.VISIBLE);
            holder.imageViewLick.setVisibility(View.VISIBLE);

            holder.viewBackgroundLick.setScaleY(0.1f);
            holder.viewBackgroundLick.setScaleX(0.1f);
            holder.viewBackgroundLick.setAlpha(1f);
            holder.imageViewLick.setScaleY(0.1f);
            holder.imageViewLick.setScaleX(0.1f);

            AnimatorSet animatorSet = new AnimatorSet();
            likeAnimations.put(holder, animatorSet);

            ObjectAnimator bgScaleYAnim = ObjectAnimator.ofFloat(holder.viewBackgroundLick, "scaleY", 0.1f, 1f);
            bgScaleYAnim.setDuration(200);
            bgScaleYAnim.setInterpolator(DECCELERATE_INTERPOLATOR);
            ObjectAnimator bgScaleXAnim = ObjectAnimator.ofFloat(holder.viewBackgroundLick, "scaleX", 0.1f, 1f);
            bgScaleXAnim.setDuration(200);
            bgScaleXAnim.setInterpolator(DECCELERATE_INTERPOLATOR);
            ObjectAnimator bgAlphaAnim = ObjectAnimator.ofFloat(holder.viewBackgroundLick, "alpha", 1f, 0f);
            bgAlphaAnim.setDuration(200);
            bgAlphaAnim.setStartDelay(150);
            bgAlphaAnim.setInterpolator(DECCELERATE_INTERPOLATOR);

            ObjectAnimator imgScaleUpYAnim = ObjectAnimator.ofFloat(holder.imageViewLick, "scaleY", 0.1f, 1f);
            imgScaleUpYAnim.setDuration(300);
            imgScaleUpYAnim.setInterpolator(DECCELERATE_INTERPOLATOR);
            ObjectAnimator imgScaleUpXAnim = ObjectAnimator.ofFloat(holder.imageViewLick, "scaleX", 0.1f, 1f);
            imgScaleUpXAnim.setDuration(300);
            imgScaleUpXAnim.setInterpolator(DECCELERATE_INTERPOLATOR);

            ObjectAnimator imgScaleDownYAnim = ObjectAnimator.ofFloat(holder.imageViewLick, "scaleY", 1f, 0f);
            imgScaleDownYAnim.setDuration(300);
            imgScaleDownYAnim.setInterpolator(ACCELERATE_INTERPOLATOR);
            ObjectAnimator imgScaleDownXAnim = ObjectAnimator.ofFloat(holder.imageViewLick, "scaleX", 1f, 0f);
            imgScaleDownXAnim.setDuration(300);
            imgScaleDownXAnim.setInterpolator(ACCELERATE_INTERPOLATOR);

            animatorSet.playTogether(bgScaleYAnim, bgScaleXAnim, bgAlphaAnim, imgScaleUpYAnim, imgScaleUpXAnim);
            animatorSet.play(imgScaleDownYAnim).with(imgScaleDownXAnim).after(imgScaleUpYAnim);

            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    resetLikeAnimationState(holder);
                }
            });
            animatorSet.start();
        }
    }

    private void resetLikeAnimationState(ItemViewHolder holder) {
        likeAnimations.remove(holder);
        holder.viewBackgroundLick.setVisibility(View.GONE);
        holder.imageViewLick.setVisibility(View.GONE);
    }

    public interface OnShareCommentItemClickListener {
        void onCommentClick(View v, int position);
    }

    public void setOnShareCommentItemClickListener(OnShareCommentItemClickListener listener) {
        this.shareCommentItemClickListener = listener;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView contentImageView;
        TextView contentTextView;
        ImageButton likeBtn;
        ImageView commentBtn;
        ImageView shareAvatar;
        TextView tvLicks;
        View viewBackgroundLick;
        ImageView imageViewLick;
        int number;

        public ItemViewHolder(View itemView) {
            super(itemView);
            contentImageView = (ImageView) itemView.findViewById(R.id.content_photo);
            contentTextView = (TextView) itemView.findViewById(R.id.content_tv);
            likeBtn = (ImageButton) itemView.findViewById(R.id.like_btn);
            commentBtn = (ImageView) itemView.findViewById(R.id.comment_btn);
            shareAvatar = (ImageView) itemView.findViewById(R.id.share_avatar);
            tvLicks = (TextView) itemView.findViewById(R.id.tv_licks);
            viewBackgroundLick = itemView.findViewById(R.id.viewBackgroundLike);
            imageViewLick = (ImageView) itemView.findViewById(R.id.imageViewLike);
        }
    }

}
