package com.example.function.babysitter.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.function.babysitter.R;
import com.example.function.babysitter.util.RoundedTransformation;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by function on 2015/10/28.
 */
public class CommentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private int itemCount = 0;
    private int lastAnimationPosition = -1;
    private int avatarSize;

    private boolean animationsLocked = false;
    private boolean delayEnterAnimation = true;

    public CommentsAdapter(Context context) {
        this.context = context;
        avatarSize = context.getResources().getDimensionPixelSize(R.dimen.comment_avatar_size);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        runEnterAnimation(holder.itemView, position);
        switch (position % 3) {
            case 0:
                ((CommentViewHolder)holder).tvComment.setText("这小宝贝太可爱啦！");
                break;
            case 1:
                ((CommentViewHolder)holder).tvComment.setText("我家小宝贝也这样啊，他们真是我们的开心宝啊");
                break;
            case 2:
                ((CommentViewHolder)holder).tvComment.setText("什么？你家小孩6字就要开始学编程？你们在哪家机构学的？放心吗");
                break;
        }

        Picasso.with(context)
                .load(R.drawable.lolita_head)
                .centerCrop()
                .resize(avatarSize, avatarSize)
                .transform(new RoundedTransformation())
                .into(((CommentViewHolder)holder).ivUserAvatar);
    }

    private void runEnterAnimation(View view, int potion) {
        Log.e("animationLocked", animationsLocked + "");
        if (animationsLocked) {
            return;
        }

        if(potion > lastAnimationPosition) {
            lastAnimationPosition = potion;
            view.setTranslationY(100);
            view.setAlpha(0.f);
            view.animate()
                    .translationY(0)
                    .alpha(1.f)
                    .setStartDelay(delayEnterAnimation ? potion * 20 : 0)
                    .setInterpolator(new DecelerateInterpolator())
                    .setDuration(300)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            animationsLocked = true;
                            Log.e("what the matter", "animationsLocked");
                        }
                    })
                    .start();
        }



    }

    @Override
    public int getItemCount() {
        return itemCount;
    }

    public void addItem() {
        itemCount ++;
        notifyItemInserted(itemCount - 1);
    }

    public void setAnimationsLocked(boolean animationsLocked) {
        this.animationsLocked = animationsLocked;
    }

    public void setDelayEnterAnimation(boolean delayEnterAnimation) {
        this.delayEnterAnimation = delayEnterAnimation;
    }

    public void updateItem() {
        itemCount = 20;
        notifyDataSetChanged();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.ivUserAvatar)
        ImageView ivUserAvatar;
        @InjectView(R.id.tvComment)
        TextView tvComment;
        public CommentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
