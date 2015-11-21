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

import com.example.function.babysitter.activity.CardDetailActivity;
import com.example.function.babysitter.item.TimeItem;
import com.example.function.babysitter.R;
import com.example.function.babysitter.adapter.TimeTreeAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KongFanyang on 2015/11/20.
 */
public class TimeTreeFrag extends Fragment implements TimeTreeAdapter.OnCardItemClickListener{

    private RecyclerView timeTreeRecycler;
    private TimeTreeAdapter timeLineAdapter;
    private List<TimeItem> timeItems;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.timetree_fragment, null);
        initData();
        timeTreeRecycler = (RecyclerView) view.findViewById(R.id.time_tree);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        timeTreeRecycler.setLayoutManager(linearLayoutManager);
        timeTreeRecycler.setItemAnimator(new DefaultItemAnimator());
        timeLineAdapter = new TimeTreeAdapter(getActivity(), timeItems);
        timeLineAdapter.setOnCardItemClickListener(this);
        timeTreeRecycler.setAdapter(timeLineAdapter);
        return view;
    }

    private void initData() {
        timeItems = new ArrayList<>();
        for (int i=0; i<15; i++) {
            TimeItem timeItem = new TimeItem();
            timeItem.setDate("11-" + (21 - i));
            if(i % 5 == 0) {
                timeItem.setTimeLineTitle("curious baby");
                timeItem.setId(i % 5);
                timeItem.setTimeLinePhoto(getContext().getResources().getDrawable(R.drawable.one));
            } else if (i % 5 == 1) {
                timeItem.setTimeLineTitle("Little Business man");
                timeItem.setId(i % 5);
                timeItem.setTimeLinePhoto(getContext().getResources().getDrawable(R.drawable.two));
            } else if (i % 5 == 2) {
                timeItem.setTimeLineTitle("Little Beckham");
                timeItem.setId(i % 5);
                timeItem.setTimeLinePhoto(getContext().getResources().getDrawable(R.drawable.three));
            } else if (i % 5 == 3) {
                timeItem.setTimeLineTitle("A~~~");
                timeItem.setId(i % 5);
                timeItem.setTimeLinePhoto(getContext().getResources().getDrawable(R.drawable.four));
            } else {
                timeItem.setTimeLineTitle("The Future");
                timeItem.setId(i % 5);
                timeItem.setTimeLinePhoto(getContext().getResources().getDrawable(R.drawable.five));
            }

            timeItems.add(timeItem);
        }
    }

    @Override
    public void onCardsClick(View v, int position) {
        Intent intent = new Intent(getActivity(), CardDetailActivity.class);
        int[] startAnimationLocation = new int[2];
        v.getLocationOnScreen(startAnimationLocation);
        intent.putExtra(CardDetailActivity.ARG_DRAWING_START_LOCATION, startAnimationLocation[1]);
        intent.putExtra(CardDetailActivity.TIME_ITEM, position);
        startActivity(intent);
        getActivity().overridePendingTransition(0, 0);
    }
}
