package com.example.function.babysitter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.function.babysitter.R;

import com.example.function.babysitter.Utils;
import com.example.function.babysitter.item.TimeItem;

import java.util.List;

/**
 * Created by KongFanyang on 2015/11/20.
 */
public class TimeTreeAdapter extends RecyclerView.Adapter<TimeTreeAdapter.ItemViewHolder> implements View.OnClickListener {

    private Context context;
    private List<TimeItem> timeItemsList;

    public static final int TIME_ITEM_CASH = 1;
    public static final int POSITION_CASH = 2;

    private boolean animateItems = false;
    private int lastAnimatedPosition = -1;
    private static final int ANIMATED_ITEMS_COUNT = 2;

    private OnCardItemClickListener onCardItemClickListener;

    public TimeTreeAdapter(Context context, List<TimeItem> timeItemsList) {
        this.context = context;
        this.timeItemsList = timeItemsList;
    }

    @Override
    public int getItemCount() {
        return timeItemsList.size();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(context).inflate(R.layout.timeline_item, viewGroup, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder itemViewHolder, int i) {
        runEnterAnimation(itemViewHolder.babyCard, i);
        itemViewHolder.babyCard.setTag(i);

        itemViewHolder.textView.setText(timeItemsList.get(i).getTimeLineTitle());
        itemViewHolder.imageView.setImageDrawable(timeItemsList.get(i).getTimeLinePhoto());

        itemViewHolder.babyCard.setOnClickListener(this);
        Log.d("recycler log", timeItemsList.get(i).getTimeLineTitle());
    }

    private void runEnterAnimation(View view, int position) {
        if(position > ANIMATED_ITEMS_COUNT) {
            return;
        }
        if(position > lastAnimatedPosition) {
            lastAnimatedPosition = position;
            view.setTranslationX(Utils.getScreenWidth(context));
            view.animate()
                    .setDuration(500)
                    .setStartDelay(200*position + 100)
                    .translationX(0)
                    .setInterpolator(new DecelerateInterpolator(1.f))
                    .start();
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.baby_card) {
            if(onCardItemClickListener != null) {
                onCardItemClickListener.onCardsClick(v, (Integer)v.getTag());
            }
        }
    }


    public interface OnCardItemClickListener {
        void onCardsClick(View v, int position);
    }

    public void setOnCardItemClickListener(OnCardItemClickListener listener) {
        this.onCardItemClickListener = listener;
    }


    class ItemViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        View babyCard;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.timeLinePhoto);
            textView = (TextView) itemView.findViewById(R.id.timeLineTitle);
            babyCard = itemView.findViewById(R.id.baby_card);
        }
    }
}
