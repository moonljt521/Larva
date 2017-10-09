package com.moon.larvademo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.moon.larva.Larva;

/**
 * author: moon
 * created on: 17/10/1 下午8:03
 * description:
 */
public class ImageFragment extends BaseLazeFragment {

    ImageView imageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imageView = (ImageView) getView().findViewById(R.id.fragment_imageView);

        isPrepared = true;

        lazyLoad();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_imageview, container, false);

        return view;
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible || loadFinish) {

            return;
        }
        // do sth
        Larva.getInstance().load(getActivity().getApplicationContext(),"http://www.cs.com" +
                        ".cn/xwzx/201709/W020170927332375618264.jpg",
                imageView,300,300);



//        new Larva.Create().into(null).load("");
//
//        Glide.with(this).load("").into(imageView);

        loadFinish = true;

    }
}
