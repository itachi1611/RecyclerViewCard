package com.foxy.recyclerviewcard.holders;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.foxy.recyclerviewcard.R;

public class ImageCardViewHolder extends RecyclerView.ViewHolder {

    public final ImageView imageView;

    public ImageCardViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.ivItem);
    }

}
