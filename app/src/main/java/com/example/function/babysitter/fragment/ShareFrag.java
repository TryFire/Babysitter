package com.example.function.babysitter.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.function.babysitter.R;
import com.example.function.babysitter.activity.CommentsActivity;
import com.example.function.babysitter.adapter.ShareAdapter;
import com.example.function.babysitter.item.ShareItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KongFanyang on 2015/11/20.
 */
public class ShareFrag extends Fragment implements ShareAdapter.OnShareCommentItemClickListener {

    private RecyclerView shareRecycler;
    private ShareAdapter shareAdapter;
    private List<ShareItem> shareItems;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.share_fragment, null);
        initData();
        shareRecycler = (RecyclerView) view.findViewById(R.id.share_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        shareRecycler.setLayoutManager(linearLayoutManager);
        shareRecycler.setItemAnimator(new DefaultItemAnimator());
        shareAdapter = new ShareAdapter(getActivity(), shareItems);
        shareAdapter.setOnShareCommentItemClickListener(this);
        shareRecycler.setAdapter(shareAdapter);

        return view;
    }

    private void initData() {
        shareItems = new ArrayList<>();
        for (int i=0; i<15; i++) {
            ShareItem shareItem = new ShareItem();
            if(i % 5 == 0) {
                shareItem.setShareContent(getString(R.string.baby_two));
                shareItem.setSharePhoto(getActivity().getResources().getDrawable(R.drawable.two));
                shareItem.setShareAvatar(getActivity().getResources().getDrawable(R.drawable.avatar_two));
            } else if (i % 5 == 1) {
                shareItem.setShareContent(getString(R.string.baby_four));
                shareItem.setSharePhoto(getActivity().getResources().getDrawable(R.drawable.four));
                shareItem.setShareAvatar(getActivity().getResources().getDrawable(R.drawable.avatar_four));
            } else if (i % 5 == 2) {
                shareItem.setShareContent(getString(R.string.baby_one));
                shareItem.setSharePhoto(getActivity().getResources().getDrawable(R.drawable.one));
                shareItem.setShareAvatar(getActivity().getResources().getDrawable(R.drawable.avatar_one));
            } else if (i % 5 == 3) {
                shareItem.setShareContent(getString(R.string.baby_five));
                shareItem.setSharePhoto(getActivity().getResources().getDrawable(R.drawable.five));
                shareItem.setShareAvatar(getActivity().getResources().getDrawable(R.drawable.avatar_two));
            } else {
                shareItem.setShareContent(getString(R.string.baby_three));
                shareItem.setSharePhoto(getActivity().getResources().getDrawable(R.drawable.three));
                shareItem.setShareAvatar(getActivity().getResources().getDrawable(R.drawable.avatar_three));
            }
            shareItems.add(shareItem);
        }
    }

    @Override
    public void onCommentClick(View v, int position) {
        Intent intent = new Intent(getActivity(), CommentsActivity.class);
        int[] startingLocation = new int[2];
        v.getLocationOnScreen(startingLocation);
        intent.putExtra(CommentsActivity.ARG_DRAWING_START_LOCATION, startingLocation[1]);
        startActivity(intent);
        getActivity().overridePendingTransition(0, 0);
    }
}
