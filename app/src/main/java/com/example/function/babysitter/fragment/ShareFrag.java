package com.example.function.babysitter.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.function.babysitter.R;
import com.example.function.babysitter.adapter.ShareAdapter;
import com.example.function.babysitter.adapter.TimeTreeAdapter;
import com.example.function.babysitter.item.ShareItem;
import com.example.function.babysitter.item.TimeItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KongFanyang on 2015/11/20.
 */
public class ShareFrag extends Fragment {

    private RecyclerView shareRecycler;
    private ShareAdapter shareAdapter;
    private List<ShareItem> shareItems;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.timetree_fragment, null);
        initData();
        shareRecycler = (RecyclerView) view.findViewById(R.id.time_tree);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        shareRecycler.setLayoutManager(linearLayoutManager);
        shareRecycler.setItemAnimator(new DefaultItemAnimator());
        shareAdapter = new ShareAdapter(getActivity(), shareItems);
        shareRecycler.setAdapter(shareAdapter);

        return view;
    }

    private void initData() {
        shareItems = new ArrayList<>();
        for (int i=0; i<10; i++) {
            ShareItem shareItem = new ShareItem();
            shareItem.setShareContent("本宝宝今天出生了！");
            shareItem.setSharePhoto(getActivity().getResources().getDrawable(R.drawable.child));
            shareItems.add(shareItem);
        }
    }

}
