package com.xu.hellogitdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xu.hellogitdemo.R;
import com.xu.hellogitdemo.adapter.MyAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Unbinder;

/**
 * Created by Administrator on 2016/9/12.
 */
public class HouseFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    //    @BindView(R.id.refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;


    //    @BindView(R.id.recycle)
    RecyclerView mRecyclerView;

    private Unbinder unbinder;
    private MyAdapter adapter;
    private Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_house, null);
        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.refresh);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recycle);
        toolbar = (Toolbar) mView.findViewById(R.id.toolbar);
        initView();
        return mView;

    }


    private void initView() {
        toolbar.setLogoDescription(R.string.app_name);
        mSwipeRefreshLayout.setColorSchemeColors(R.color.colorAccent, R.color.colorPrimary, R.color.yellow, R.color.blue);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
        mSwipeRefreshLayout.setRefreshing(true);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        //为RecyclerView指定布局管理器对象
        mRecyclerView.setLayoutManager(layoutManager);

        List<String> datas = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            datas.add("这是第" + i + "条数据");
        }
        adapter = new MyAdapter(datas);
        mRecyclerView.setAdapter(adapter);
//        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//            }
//        });
    }


    @Override
    public void onRefresh() {

    }
}
