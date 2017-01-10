package com.example.phuong.healthy.fragments;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.phuong.healthy.R;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by phuong on 06/01/2017.
 */
@EFragment(R.layout.fragment_detect)
public class DetectFragment extends BaseFragment {
    @ViewById(R.id.imageview_chose_pic_down_item_setting)
    ImageView mImgChoseDown;
    @ViewById(R.id.imageview_chose_pic_up_item_setting)
    ImageView mImgChoseUp;
    @ViewById(R.id.rl_info_chose_photo_item_detect)
    RelativeLayout mRlChosePhoto;


    @Override
    void inits() {

    }

    @Click(R.id.imageview_chose_pic_down_item_setting)
    void ChosePicDown(){
        mRlChosePhoto.setVisibility(View.VISIBLE);
        mImgChoseDown.setVisibility(View.GONE);
        mImgChoseUp.setVisibility(View.VISIBLE);
    }

    @Click(R.id.imageview_chose_pic_up_item_setting)
    void ChosePicUp(){
        mRlChosePhoto.setVisibility(View.GONE);
        mImgChoseDown.setVisibility(View.VISIBLE);
        mImgChoseUp.setVisibility(View.GONE);
    }
}
