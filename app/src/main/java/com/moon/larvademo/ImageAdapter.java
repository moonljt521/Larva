package com.moon.larvademo;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.moon.larva.Larva;


/**
 * 购物车  adapter
 *
 * @author liangjt
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {


    private Activity mContext;
    private String[] mList;


    public ImageAdapter(Activity context, String[] list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public int getItemCount() {
        return mList == null || mList.length == 0 ? 0 : mList.length;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Larva.getInstance().load(mContext.getApplicationContext(),mList[position],holder.imageView,300,300);

//        Glide.with(mContext).load(mList[position]).into(holder.imageView);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int arg1) {
        return new MyViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.item_imageview, parent, false));
    }

    class MyViewHolder extends ViewHolder {

        ImageView imageView;
        public int id;

        public MyViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.item_imageview);

        }


    }
}
