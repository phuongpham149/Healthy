package com.example.phuong.healthy.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.example.phuong.healthy.R;
import com.example.phuong.healthy.adapters.MainTutorialAdapter;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    private static float mDensity;
    @ViewById(R.id.viewpagerTutorial)
    ViewPager mViewPager;
    @ViewById(R.id.indicator)
    CirclePageIndicator mIndicator;
    @ViewById(R.id.tvSkip)
    TextView mTvSkip;
    private Handler mHandler;
    private int mDelay = 5000;
    private MainTutorialAdapter mAdapter;
    private PageIndicator mPagerIndicator;

    @Override
    void inits() {
        mAdapter = new MainTutorialAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mPagerIndicator = mIndicator;
        mIndicator.setViewPager(mViewPager);
        mDensity = getResources().getDisplayMetrics().density;
        mIndicator.setBackgroundColor(Color.TRANSPARENT);
        mIndicator.setRadius(5 * mDensity);
        mIndicator.setFillColor(getResources().getColor(R.color.background));
        mIndicator.setStrokeColor(getResources().getColor(R.color.background));
        mIndicator.setStrokeWidth(mDensity);
    }

    @Click(R.id.tvSkip)
    void SkipAction() {
        Intent intent = new Intent(this, HomeActivity_.class);
        startActivity(intent);
    }
}
