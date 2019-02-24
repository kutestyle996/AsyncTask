package com.example.asynctask;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private LayoutInflater mLayoutInflater;
    private List<String> mPaths;

    public ImageAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    @NonNull
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                              int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_image, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        holder.bindData(mPaths.get(position));
    }

    @Override
    public int getItemCount() {
        return mPaths != null ? mPaths.size() : 0;
    }

    public void setData(List<String> paths) {
        mPaths = paths;
        notifyDataSetChanged();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;

        public ImageViewHolder(View view) {
            super(view);
            mImageView = view.findViewById(R.id.image);
        }

        public void bindData(String path) {
            Picasso.get().load(path).into(mImageView);
        }
    }
}
