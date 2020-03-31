package com.foxy.recyclerviewcard;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.foxy.recyclerviewcard.adapters.ImageCardAdapter;
import com.foxy.recyclerviewcard.bases.BaseActivity;
import com.foxy.recyclerviewcard.customs.FoxyRecyclerView;
import com.foxy.recyclerviewcard.customs.FoxyScaleHelper;
import com.foxy.recyclerviewcard.customs.FoxySwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    private FoxySwipeRefreshLayout foxySwipeRefreshLayout;
    private FoxyRecyclerView foxyRecyclerView;
    private List<Integer> mList;
    private FoxyScaleHelper foxyScaleHelper;
    private ImageCardAdapter adapter;

    public static String flag = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar();
        setContentView(R.layout.activity_main);

        initViews();

        initData();

        initRecyclerView();
    }

    private void initViews() {
        mList = new ArrayList<>();
        foxyRecyclerView = findViewById(R.id.rvList);
        foxySwipeRefreshLayout = findViewById(R.id.swipeLayout);
        foxySwipeRefreshLayout.setOnRefreshListener(this);
        foxySwipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

    }

    private void initData() {
        for(int i = 0; i < 6; i++) {
            mList.add(R.drawable.img01);
            mList.add(R.drawable.img02);
            mList.add(R.drawable.img03);
            mList.add(R.drawable.img01);
            mList.add(R.drawable.img02);
            mList.add(R.drawable.img03);
        }
    }

    private void initRecyclerView() {
        Context context;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        foxyRecyclerView.setLayoutManager(layoutManager);
        foxyRecyclerView.setHasFixedSize(true);
        adapter = new ImageCardAdapter(mList);
        foxyRecyclerView.setAdapter(adapter);
        foxyScaleHelper = new FoxyScaleHelper();
        foxyScaleHelper.setFirstItemPos(1000);
        foxyScaleHelper.attachToRecyclerView(foxyRecyclerView);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                foxySwipeRefreshLayout.setRefreshing(false);
                mList.clear();
                for(int i = 0; i < 3; i++) {
                    mList.add(R.drawable.img05);
                    mList.add(R.drawable.img06);
                    mList.add(R.drawable.img07);
                }
                foxyScaleHelper.setCurrentItem(foxyScaleHelper.getCurrentItem(), true);
                adapter.setList(mList);
                adapter.notifyDataSetChanged();
            }
        }, 1500);
    }
}
