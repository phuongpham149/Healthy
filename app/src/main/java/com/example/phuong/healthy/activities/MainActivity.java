package com.example.phuong.healthy.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.example.phuong.healthy.R;
import com.example.phuong.healthy.adapters.MainTutorialAdapter;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    private static float mDensity;
    @ViewById(R.id.viewpagerTutorial)
    ViewPager mViewPager;
    @ViewById(R.id.indicator)
    CirclePageIndicator mIndicator;
    private Handler mHandler;
    private int mDelay = 5000;
    private MainTutorialAdapter mAdapter;
    private PageIndicator mPagerIndicator;
    private int mPage = 0;
    private boolean mTranfers = false;
    Runnable runnable = new Runnable() {
        public void run() {
            if (mAdapter.getCount() == mPage) {
                transferToHome();

            } else {
                mPage++;
            }
            mViewPager.setCurrentItem(mPage, true);
            mHandler.postDelayed(this, mDelay);
        }
    };

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
        mHandler = new Handler();
    }

    public void transferToHome() {
        Intent intent = new Intent(this, HomeActivity_.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHandler.postDelayed(runnable, mDelay);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(runnable);
    }
}
