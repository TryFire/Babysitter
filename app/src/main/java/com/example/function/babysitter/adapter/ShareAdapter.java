package com.example.function.babysitter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.function.babysitter.R;
import com.example.function.babysitter.item.ShareItem;

import java.util.List;

/**
 * Created by KongFanyang on 2015/11/20.
 */
public class ShareAdapter extends RecyclerView.Adapter<ShareAdapter.ItemViewHolder> implements View.OnClickListener {

    private Context context;
    private List<ShareItem> shareItemList;

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
        itemViewHolder.contentTextView.setTag(i);
        itemViewHolder.contentImageView.setTag(i);
        itemViewHolder.likeBtn.setTag(i);
        itemViewHolder.commentBtn.setTag(i);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.like_btn:

                Toast.makeText(context, v.getTag()+" like", Toast.LENGTH_LONG).show();
                break;
            case R.id.comment_btn :
                Toast.makeText(context, v.getTag()+" comment", Toast.LENGTH_LONG).show();

                break;
            case R.id.content_tv :
                Toast.makeText(context, v.getTag()+" content", Toast.LENGTH_LONG).show();
                break;
            case R.id.content_photo :
                Toast.makeText(context, v.getTag()+" photo", Toast.LENGTH_LONG).show();
                break;

        }
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView contentImageView;
        TextView contentTextView;
        ImageView likeBtn;
        ImageView commentBtn;

        public ItemViewHolder(View itemView) {
            super(itemView);
            contentImageView = (ImageView) itemView.findViewById(R.id.content_photo);
            contentTextView = (TextView) itemView.findViewById(R.id.content_tv);
            likeBtn = (ImageView) itemView.findViewById(R.id.like_btn);
            commentBtn = (ImageView) itemView.findViewById(R.id.comment_btn);
        }
    }

}
