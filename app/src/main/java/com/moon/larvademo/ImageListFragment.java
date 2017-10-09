package com.moon.larvademo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * author: moon
 * created on: 17/10/1 下午8:03
 * description:  测试用列表加载图片
 */
public class ImageListFragment extends BaseLazeFragment {

    RecyclerView recyclerView;

    ImageAdapter adapter;

    String[] imgUrls = {
            "http://ww3.sinaimg.cn/large/7a8aed7bjw1f2p0v9vwr5j20b70gswfi.jpg",
            "http://ww1.sinaimg.cn/large/7a8aed7bjw1f2nxxvgz7xj20hs0qognd.jpg",
            "http://ww2.sinaimg.cn/large/7a8aed7bjw1f2mteyftqqj20jg0siq6g.jpg",
            "http://ww2.sinaimg.cn/large/7a8aed7bjw1f2lkx2lhgfj20f00f0dhm.jpg",
            "http://ww3.sinaimg.cn/large/7a8aed7bjw1f2h04lir85j20fa0mx784.jpg",
            "http://ww3.sinaimg.cn/large/7a8aed7bjw1f2fuecji0lj20f009oab3.jpg",
            "http://ww1.sinaimg.cn/large/610dc034jw1f2ewruruvij20d70miadg.jpg",
            "http://ww3.sinaimg.cn/large/7a8aed7bjw1f2cfxa9joaj20f00fzwg2.jpg",
            "http://ww1.sinaimg.cn/large/610dc034gw1f2cf4ulmpzj20dw0kugn0.jpg",
            "http://ww1.sinaimg.cn/large/7a8aed7bjw1f27uhoko12j20ez0miq4p.jpg",
            "http://ww1.sinaimg.cn/large/7a8aed7bjw1f27uhoko12j20ez0miq4p.jpg",
            "http://ww2.sinaimg.cn/large/610dc034jw1f27tuwswd3j20hs0qoq6q.jpg",
            "http://ww3.sinaimg.cn/large/7a8aed7bjw1f26lox908uj20u018waov.jpg",
            "http://ww2.sinaimg.cn/large/7a8aed7bjw1f25gtggxqjj20f00b9tb5.jpg",
            "http://ww1.sinaimg.cn/large/7a8aed7bjw1f249fugof8j20hn0qogo4.jpg",
            "http://ww1.sinaimg.cn/large/7a8aed7bjw1f20ruz456sj20go0p0wi3.jpg",
            "http://ww4.sinaimg.cn/large/7a8aed7bjw1f1yjc38i9oj20hs0qoq6k.jpg",
            "http://ww3.sinaimg.cn/large/610dc034gw1f1yj0vc3ntj20e60jc0ua.jpg",
            "http://ww4.sinaimg.cn/large/7a8aed7bjw1f1xad7meu2j20dw0ku0vj.jpg",
            "http://ww1.sinaimg.cn/large/7a8aed7bjw1f1w5m7c9knj20go0p0ae4.jpg",
            "http://ww4.sinaimg.cn/large/7a8aed7bjw1f1so7l2u60j20zk1cy7g9.jpg",
            "http://ww4.sinaimg.cn/large/7a8aed7bjw1f1rmqzruylj20hs0qon14.jpg",
            "http://ww2.sinaimg.cn/large/7a8aed7bjw1f1qed6rs61j20ss0zkgrt.jpg",
            "http://ww3.sinaimg.cn/large/7a8aed7bjw1f1p77v97xpj20k00zkgpw.jpg",
            "http://ww1.sinaimg.cn/large/7a8aed7bjw1f1o75j517xj20u018iqnf.jpg",
            "http://ww4.sinaimg.cn/large/7a8aed7bjw1f1klhuc8w5j20d30h9gn8.jpg",
            "http://ww4.sinaimg.cn/large/7a8aed7bjw1f1jionqvz6j20hs0qoq7p.jpg",
            "http://ww3.sinaimg.cn/large/7a8aed7bjw1f1ia8qj5qbj20nd0zkmzp.jpg",
            "http://ww3.sinaimg.cn/large/7a8aed7bjw1f1h4f51wbcj20f00lddih.jpg",
            "http://ww1.sinaimg.cn/large/7a8aed7bjw1f1g2xpx9ehj20ez0mi0vc.jpg"
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = (RecyclerView) getView().findViewById(R.id.fragment_recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));



        isPrepared = true;

        lazyLoad();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_imageview_list, container, false);

        return view;
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible || loadFinish) {

            return;
        }
        // do sth
        recyclerView.setAdapter(adapter = new ImageAdapter(getActivity(),imgUrls));

        loadFinish = true;

    }




}
