package com.foxy.recyclerviewcard;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.foxy.recyclerviewcard.adapters.ImageCardAdapter;
import com.foxy.recyclerviewcard.bases.BaseActivity;
import com.foxy.recyclerviewcard.customs.FoxyRecyclerView;
import com.foxy.recyclerviewcard.customs.FoxyScaleHelper;
import com.foxy.recyclerviewcard.customs.FoxySwipeRefreshLayout;
import com.foxy.recyclerviewcard.models.FavoritePhoto;
import com.foxy.recyclerviewcard.models.Photo;
import java.util.ArrayList;
import java.util.List;
import static com.foxy.recyclerviewcard.utils.Constants.API_KEY;
import static com.foxy.recyclerviewcard.utils.Constants.FLICKR_DOMAIN;
import static com.foxy.recyclerviewcard.utils.Constants.FORMAT;
import static com.foxy.recyclerviewcard.utils.Constants.METHOD;
import static com.foxy.recyclerviewcard.utils.Constants.OPTION;
import static com.foxy.recyclerviewcard.utils.Constants.USER_ID;

public class MainActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    private ProgressDialog progressDialog;
    private FoxySwipeRefreshLayout foxySwipeRefreshLayout;
    private FoxyRecyclerView foxyRecyclerView;
    private List<Photo> mList;
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

//        initRecyclerView();
    }

    private void initProgressDialog(){
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Waiting");
        progressDialog.setMessage("Loading image from network ...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        //progressDialog.setMax(100);
        //progressDialog.setProgress(0);
        progressDialog.show();
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
        initProgressDialog();
        AndroidNetworking.post(FLICKR_DOMAIN)
                .addBodyParameter("method", METHOD)
                .addBodyParameter("api_key", API_KEY)
                .addBodyParameter("user_id",USER_ID)
                .addBodyParameter("format", FORMAT)
                .addBodyParameter("extras", OPTION)
                .addBodyParameter("nojsoncallback", "1")
                .addBodyParameter("per_page", "10")
                .addBodyParameter("page", "1")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsObject(FavoritePhoto.class, new ParsedRequestListener<Object>() {

                    @Override
                    public void onResponse(Object response) {
                        //swipeRefreshLayout.setRefreshing(false);
                        FavoritePhoto favouritePhoto = (FavoritePhoto) response;
                        mList = favouritePhoto.getPhotos().getPhoto();
                        initRecyclerView();
//                        MainActivity.this.mImageUrls.addAll(photos);
//                        staggeredRecyclerViewAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onError(ANError anError) {
                        // handle error
                        Toast.makeText(MainActivity.this, anError.toString(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
    }

    private void initRecyclerView() {
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
//                mList.clear();
//                for(int i = 0; i < 3; i++) {
//                    mList.add(R.drawable.img05);
//                    mList.add(R.drawable.img06);
//                    mList.add(R.drawable.img07);
//                }
//                foxyScaleHelper.setCurrentItem(foxyScaleHelper.getCurrentItem(), true);
//                adapter.setList(mList);
//                adapter.notifyDataSetChanged();
            }
        }, 1500);
    }
}
