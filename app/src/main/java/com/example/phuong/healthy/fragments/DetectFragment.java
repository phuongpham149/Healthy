package com.example.phuong.healthy.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.phuong.healthy.R;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by phuong on 06/01/2017.
 */
@EFragment(R.layout.fragment_detect)
public class DetectFragment extends BaseFragment {
    private static final int REQUEST_CAMERA = 1000;
    private static final int REQUEST_GALERY = 2000;
    private static final String KEY_CAMERA = "data";
    @ViewById(R.id.imageview_chose_pic_down_item_setting)
    ImageView mImgChoseDown;
    @ViewById(R.id.imageview_chose_pic_up_item_setting)
    ImageView mImgChoseUp;
    @ViewById(R.id.rl_info_chose_photo_item_detect)
    RelativeLayout mRlChosePhoto;
    @ViewById(R.id.rl_galery)
    RelativeLayout mRlGalery;
    @ViewById(R.id.rl_content)
    RelativeLayout mRlContent;
    @ViewById(R.id.imgPhoto)
    ImageView mImgPhoto;

    @Override
    void inits() {
    }

    @Click(R.id.rl_galery)
    void getPicFromGalery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_GALERY);
    }

    @Click(R.id.rl_camera)
    void getPicFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_CAMERA);
        }
    }

    @Click(R.id.imageview_chose_pic_down_item_setting)
    void ChosePicDown() {
        mRlChosePhoto.setVisibility(View.VISIBLE);
        mImgChoseDown.setVisibility(View.GONE);
        mImgChoseUp.setVisibility(View.VISIBLE);
    }

    @Click(R.id.imageview_chose_pic_up_item_setting)
    void ChosePicUp() {
        mRlChosePhoto.setVisibility(View.GONE);
        mImgChoseDown.setVisibility(View.VISIBLE);
        mImgChoseUp.setVisibility(View.GONE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAMERA && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get(KEY_CAMERA);
            mImgPhoto.setImageBitmap(imageBitmap);
        }
        if (requestCode == REQUEST_GALERY) {
            Uri selectedImage = data.getData();
            InputStream imageStream = null;
            try {
                imageStream = getActivity().getContentResolver().openInputStream(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
            mImgPhoto.setImageBitmap(bitmap);
        }
    }
}
