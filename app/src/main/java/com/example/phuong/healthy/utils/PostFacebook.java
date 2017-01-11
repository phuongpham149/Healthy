package com.example.phuong.healthy.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by phuong on 11/01/2017.
 */

public class PostFacebook {
    private static PostFacebook mPostFacebook;

    public static PostFacebook getNewInstances() {
        if (mPostFacebook == null) {
            mPostFacebook = new PostFacebook();
            return mPostFacebook;
        }
        return null;
    }

    public Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);
        return b;
    }

    public void saveBitmap(Bitmap bitmap, String nameImage) {
        File imagePath = new File("/sdcard/Testing/" + nameImage + ".jpg");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
        }
    }
}
