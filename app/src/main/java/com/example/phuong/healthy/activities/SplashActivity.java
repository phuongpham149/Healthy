package com.example.phuong.healthy.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.view.Window;
import android.view.WindowManager;

import com.example.phuong.healthy.R;

import org.androidannotations.annotations.EActivity;

/**
 * Created by phuong on 05/01/2017.
 */
@EActivity(R.layout.activity_splash)
public class SplashActivity extends BaseActivity {
    @Override
    void inits() {
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.background));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,MainActivity_.class);
                startActivity(intent);
            }
        },3000);
    }
}
