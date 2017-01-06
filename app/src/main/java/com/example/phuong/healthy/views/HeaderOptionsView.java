package com.example.phuong.healthy.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.phuong.healthy.R;

/**
 * Created by phuong on 05/01/2017.
 */

public class HeaderOptionsView extends RelativeLayout {
    public HeaderOptionsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HeaderOptionsView, 0, 0);
        String mTitle = typedArray.getString(R.styleable.HeaderOptionsView_title);
        typedArray.recycle();

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.activity_header, this, true);
        TextView mTvTitle = (TextView) findViewById(R.id.textview_header_main_title);

        mTvTitle.setText(mTitle);
    }

    public HeaderOptionsView(Context context) {
        super(context, null);
    }
}
