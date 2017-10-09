package com.moon.larvademo;


import android.app.Fragment;
import android.os.Bundle;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public abstract class BaseLazeFragment extends android.support.v4.app.Fragment {

    // 懒加载
    protected boolean isVisible;

    protected boolean isPrepared;

    protected boolean loadFinish;


    public BaseLazeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    // 懒加载 方法
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible(){
        lazyLoad();
    }

    protected abstract void lazyLoad();

    protected void onInvisible(){}


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
