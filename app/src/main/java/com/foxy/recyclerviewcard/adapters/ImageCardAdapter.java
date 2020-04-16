package com.foxy.recyclerviewcard.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.foxy.recyclerviewcard.R;
import com.foxy.recyclerviewcard.customs.FoxyAdapterHelper;
import com.foxy.recyclerviewcard.holders.ImageCardViewHolder;
import com.foxy.recyclerviewcard.models.Photo;
import com.foxy.recyclerviewcard.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class ImageCardAdapter extends RecyclerView.Adapter<ImageCardViewHolder> {

    private List<Photo> mList = new ArrayList<>();
    private FoxyAdapterHelper mFoxyAdapterHelper = new FoxyAdapterHelper();

    public ImageCardAdapter(List<Photo> mList) {
        this.mList = mList;
    }

    public List<Photo> getList() {
        return mList;
    }

    public void setList(List<Photo> list) {
        mList = list;
    }

    @NonNull
    @Override
    public ImageCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        mFoxyAdapterHelper.onCreateViewHolder(parent, itemView);
        return new ImageCardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageCardViewHolder holder, final int position) {
        mFoxyAdapterHelper.onBindViewHolder(holder.itemView, position, getItemCount());
        Glide.with(holder.imageView)
                .load(mList.get(position).getUrlM())
                .into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show(holder.imageView.getContext(), "" + position);
                ((RecyclerView)holder.itemView.getParent()).smoothScrollToPosition(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (mList != null) ? mList.size() : 0;
    }

}
