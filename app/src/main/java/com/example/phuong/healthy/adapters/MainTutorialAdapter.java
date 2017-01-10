package com.example.phuong.healthy.adapters;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.phuong.healthy.R;
import com.example.phuong.healthy.fragments.MainTutorialFragment;
import com.example.phuong.healthy.fragments.MainTutorialFragment_;

/**
 * Created by phuong on 05/01/2017.
 */

public class MainTutorialAdapter extends FragmentPagerAdapter {
    private int[] mImages = {
            R.drawable.tutorial1, R.drawable.tutorial3, R.drawable.tutorial2
    };
    private String[] mContent = {"Detect Drug",
            "Hospital",
            "Drug"};
    private Handler mHandler = new Handler();
    private int mCount = mImages.length;

    public MainTutorialAdapter(FragmentManager fm) {

        super(fm);
    }

    @Override
    public int getCount() {
        return mCount;
    }

    public void setCount(int count) {
        if (count > 0 && count <= 10) {
            mCount = count;
            notifyDataSetChanged();
        }
    }

    @Override
    public Fragment getItem(final int position) {
        final MainTutorialFragment mainTutorialFragment = MainTutorialFragment_.builder().build();
        mainTutorialFragment.mImgResource = mImages[position];
        mainTutorialFragment.mContent = mContent[position];
        return mainTutorialFragment;
    }
}
