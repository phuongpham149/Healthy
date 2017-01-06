package com.example.phuong.healthy.fragments;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phuong.healthy.R;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

/**
 * Created by phuong on 05/01/2017.
 */
@EFragment(R.layout.item_tutorial)
public class MainTutorialFragment extends BaseFragment {
    private static final String KEY_BUNDLE = "MainTutorialFragment";
    @FragmentArg
    public int mImgResource;

    @FragmentArg
    public String mContent;

    @ViewById(R.id.image_tutorial)
    ImageView mImgTutorial;

    @ViewById(R.id.tvContent)
    TextView mTvContent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_BUNDLE)) {
            mImgResource = savedInstanceState.getInt(KEY_BUNDLE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_BUNDLE, mImgResource);
    }

    @Override
    public void inits() {
        mImgTutorial.setImageResource(mImgResource);
        mTvContent.setText(mContent);
        setRetainInstance(true);
    }
}
