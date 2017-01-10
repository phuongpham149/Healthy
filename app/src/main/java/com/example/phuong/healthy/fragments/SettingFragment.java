package com.example.phuong.healthy.fragments;

import android.app.TimePickerDialog;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.phuong.healthy.R;

import org.androidannotations.annotations.CheckedChange;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.Calendar;

/**
 * Created by phuong on 06/01/2017.
 */
@EFragment(R.layout.fragment_setting)
public class SettingFragment extends BaseFragment {
    @ViewById(R.id.imageview_remind_down_item_setting)
    ImageView mImgRemindDown;
    @ViewById(R.id.imageview_remind_up_item_setting)
    ImageView mImgRemindUp;
    @ViewById(R.id.rl_remind_setting)
    RelativeLayout mRlRemindSetting;
    @ViewById(R.id.rl_healthy_setting)
    RelativeLayout mRlHealthySetting;
    @ViewById(R.id.checkbox_turn_on_remind_drug)
    CheckBox mChbRemindDrug;
    @ViewById(R.id.rl_set_time_item_setting)
    RelativeLayout mRlSetTimeSetting;
    @ViewById(R.id.tvHour)
    TextView mTvHour;
    @ViewById(R.id.tvMin)
    TextView mTvMin;
    @ViewById(R.id.imageview_feedback_down_item_drug_detail)
    ImageView mImgFeedbackDown;
    @ViewById(R.id.imageview_feedback_up_item_drug_detail)
    ImageView mImgFeedbackUp;
    @ViewById(R.id.rl_infor_feedback_item_setting)
    RelativeLayout mRlInfoFeedback;

    private Calendar mcurrentTime;
    private String mHourSelect = "";
    private String mMinSelect = "";

    @Override
    void inits() {
        mcurrentTime = Calendar.getInstance();
        mHourSelect = String.valueOf(mcurrentTime.get(Calendar.HOUR_OF_DAY));
        mMinSelect = String.valueOf(mcurrentTime.get(Calendar.MINUTE));

        mTvHour.setText(mHourSelect);
        mTvMin.setText(mMinSelect);
    }
    @CheckedChange(R.id.checkbox_turn_on_remind_drug)
    void check(){
        if (mChbRemindDrug.isChecked()) {
            mRlSetTimeSetting.setVisibility(View.VISIBLE);
        } else {
            mRlSetTimeSetting.setVisibility(View.GONE);
        }
    }
    @Click(R.id.imageview_remind_down_item_setting)
    void RemindDownAction() {
        mImgRemindDown.setVisibility(View.GONE);
        mImgRemindUp.setVisibility(View.VISIBLE);
        mRlHealthySetting.setVisibility(View.VISIBLE);
        mRlRemindSetting.setVisibility(View.VISIBLE);
        if (mChbRemindDrug.isChecked()) {
            mRlSetTimeSetting.setVisibility(View.VISIBLE);
        } else {
            mRlSetTimeSetting.setVisibility(View.GONE);
        }
    }

    @Click(R.id.imageview_remind_up_item_setting)
    void RemindUpAction() {
        mImgRemindDown.setVisibility(View.VISIBLE);
        mImgRemindUp.setVisibility(View.GONE);
        mRlHealthySetting.setVisibility(View.GONE);
        mRlRemindSetting.setVisibility(View.GONE);
        mRlSetTimeSetting.setVisibility(View.GONE);
    }

    @Click(R.id.rl_set_time_item_setting)
    void SetTimeAction() {
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                mHourSelect = String.valueOf(selectedHour);
                mMinSelect = String.valueOf(selectedMinute);
                mTvHour.setText(mHourSelect);
                mTvMin.setText(mMinSelect);
            }
        }, hour, minute, true);
        mTimePicker.setTitle(getResources().getString(R.string.textview_set_time));
        mTimePicker.show();
    }

    @Click(R.id.imageview_feedback_down_item_drug_detail)
    void FeedbackDownAction() {
        mImgFeedbackDown.setVisibility(View.GONE);
        mImgFeedbackUp.setVisibility(View.VISIBLE);
        mRlInfoFeedback.setVisibility(View.VISIBLE);
    }

    @Click(R.id.imageview_feedback_up_item_drug_detail)
    void FeedbackUpAction() {
        mImgFeedbackDown.setVisibility(View.VISIBLE);
        mImgFeedbackUp.setVisibility(View.GONE);
        mRlInfoFeedback.setVisibility(View.GONE);
    }
}
