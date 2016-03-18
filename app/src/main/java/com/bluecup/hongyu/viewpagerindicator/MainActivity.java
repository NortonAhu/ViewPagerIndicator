package com.bluecup.hongyu.viewpagerindicator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "yhtest";

    private static final List<String> mTitles = Arrays.asList("头条1", "阅读2", "热点3","头条4", "阅读5", "热点6","头条7", "阅读8", "热点9");

    private List<Fragment> mFragments = new ArrayList<>();
    private ViewPagerIndicator indicatorTitle;
    private ViewPager contentViewpager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();
    }

    private void initData() {
        for (String title: mTitles) {
            ViewPagerFragment fragment = ViewPagerFragment.newInstance(title);
            mFragments.add(fragment);
        }
    }

    private void initView() {
        indicatorTitle = (ViewPagerIndicator) findViewById(R.id.indicator_title_bar);
        contentViewpager = (ViewPager) findViewById(R.id.viewpager_content);

        indicatorTitle.setTabTitles(mTitles);
        contentViewpager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        indicatorTitle.setViewPager(contentViewpager);

    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }
}
