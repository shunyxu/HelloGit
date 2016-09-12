package com.xu.hellogitdemo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.xu.hellogitdemo.R;
import com.xu.hellogitdemo.fragment.HouseFragment;
import com.xu.hellogitdemo.fragment.MeFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener {

    BottomNavigationBar bottomNavigationBar;

    private List<Fragment> fragments = new ArrayList<>();
    private int mCurrFragment = 0;
    private FragmentManager manager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.ic_menu_camera, "One"))//
                .addItem(new BottomNavigationItem(R.drawable.ic_menu_gallery, "Two"))
                .addItem(new BottomNavigationItem(R.drawable.ic_menu_send, "Three"))
                .addItem(new BottomNavigationItem(R.drawable.ic_menu_manage, "Four"))
                .initialise();

        bottomNavigationBar.setTabSelectedListener(this);

        manager = getSupportFragmentManager();
        fragments.add(new HouseFragment());
        fragments.add(new MeFragment());
        fragments.add(new MeFragment());
        fragments.add(new MeFragment());

        setDefaultFragment();
    }

    private void setDefaultFragment() {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fragment, fragments.get(mCurrFragment));
        transaction.commit();

        bottomNavigationBar.selectTab(mCurrFragment);
    }


    @Override
    public void onTabSelected(int position) {
        //开启事务
        FragmentTransaction transaction = fragmentAnimation(position);
        Fragment fragment = fragments.get(position);
        getcurrentFragment().onPause();
        if (fragment.isAdded()) {
            fragment.onResume();
        } else {
            transaction.add(R.id.fragment, fragment);
        }
        for (int j = 0; j < fragments.size(); j++) {
            if (j == position) {
                transaction.show(fragment);
            } else {
                transaction.hide(fragments.get(j));
            }
        }
        transaction.commit();
        mCurrFragment = position;
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    private Fragment getcurrentFragment() {
        return fragments.get(mCurrFragment);
    }


    // 为Fragment的切换设置动画
    public FragmentTransaction fragmentAnimation(int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (index < mCurrFragment) {
            transaction.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_right_out);
        } else if (index > mCurrFragment) {
            transaction.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out);
        }

        return transaction;
    }
}
