package com.example.phuong.healthy.fragments;

import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.phuong.healthy.R;
import com.example.phuong.healthy.eventBus.BusProvider;
import com.example.phuong.healthy.eventBus.MessageRemindHealthy;
import com.example.phuong.healthy.models.RemindDrug;
import com.example.phuong.healthy.utils.Constant;

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
    @ViewById(R.id.btnSend)
    TextView mTvSend;
    @ViewById(R.id.edtName)
    EditText mEdtName;
    @ViewById(R.id.edtEmail)
    EditText mEdtEmail;
    @ViewById(R.id.edtFeedback)
    EditText mEdtFeedback;
    @ViewById(R.id.checkbox_turn_on_healthy_setting)
    CheckBox mChbRemindHealthy;

    private Calendar mCurrentTime;
    private String mHourSelect = "";
    private String mMinSelect = "";
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private String mStatus = "";

    @Override
    void inits() {
        mCurrentTime = Calendar.getInstance();
        mSharedPreferences = getActivity().getSharedPreferences(Constant.NAME_SHAREPREFERENCES, 0);
        mEditor = mSharedPreferences.edit();
        if (mSharedPreferences.getString(Constant.HOUR_SHAREPREFERENCES, "").length() > 0) {

            mTvHour.setText(mSharedPreferences.getString(Constant.HOUR_SHAREPREFERENCES, ""));
            mTvMin.setText(mSharedPreferences.getString(Constant.MIN_SHAREPREFERENCES, ""));
        } else {
            mTvHour.setText(String.valueOf(mCurrentTime.get(Calendar.HOUR_OF_DAY)));
            mTvMin.setText(String.valueOf(mCurrentTime.get(Calendar.MINUTE)));
        }

        if (mSharedPreferences.getBoolean(Constant.STATUS_SHAREPREFERENCES, false)) {
            mChbRemindDrug.setChecked(true);
            mRlSetTimeSetting.setVisibility(View.VISIBLE);
            mRlRemindSetting.setVisibility(View.VISIBLE);
        }

        if(mSharedPreferences.getBoolean(Constant.STATE_REMIND_HEALTHY,false)){
            mChbRemindHealthy.setChecked(true);
        }
        BusProvider.getInstance().register(this);
    }

    @CheckedChange(R.id.checkbox_turn_on_remind_drug)
    void check() {
        if (mChbRemindDrug.isChecked()) {
            mRlSetTimeSetting.setVisibility(View.VISIBLE);
        } else {
            mRlSetTimeSetting.setVisibility(View.GONE);
        }
    }

    @CheckedChange(R.id.checkbox_turn_on_healthy_setting)
    void checkRemindHealthy(){
        if(mChbRemindHealthy.isChecked()){
            mEditor.putBoolean(Constant.STATE_REMIND_HEALTHY, true);
            MessageRemindHealthy messageRemindHealthy = new MessageRemindHealthy(true);
            BusProvider.getInstance().post(messageRemindHealthy);
        }
        else{
            mEditor.putBoolean(Constant.STATE_REMIND_HEALTHY, false);
            MessageRemindHealthy messageRemindHealthy = new MessageRemindHealthy(false);
            BusProvider.getInstance().post(messageRemindHealthy);
        }
        mEditor.commit();
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
        int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mCurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                mHourSelect = String.valueOf(selectedHour);
                mMinSelect = String.valueOf(selectedMinute);
                mTvHour.setText(mHourSelect);
                mTvMin.setText(mMinSelect);

                mEditor.putString(Constant.HOUR_SHAREPREFERENCES, mHourSelect);
                mEditor.putString(Constant.MIN_SHAREPREFERENCES, mMinSelect);
                mEditor.commit();

                RemindDrug remindDrug = new RemindDrug();
                remindDrug.setHour(mHourSelect);
                remindDrug.setMin(mMinSelect);
                BusProvider.getInstance().post(remindDrug);

            }
        }, hour, minute, true);
        mTimePicker.setTitle(getResources().getString(R.string.textview_set_time));
        mTimePicker.show();
    }

    @Click(R.id.checkbox_turn_on_remind_drug)
    void ControlRemindDrug() {
        mEditor.putString(Constant.HOUR_SHAREPREFERENCES, mTvHour.getText().toString());
        mEditor.putString(Constant.MIN_SHAREPREFERENCES, mTvMin.getText().toString());
        if (mChbRemindDrug.isChecked()) {
            mEditor.putBoolean(Constant.STATUS_SHAREPREFERENCES, true);
            mStatus = getActivity().getString(R.string.message_true);

            mEditor.putString(Constant.HOUR_SHAREPREFERENCES, mTvHour.getText().toString());
            mEditor.putString(Constant.MIN_SHAREPREFERENCES, mTvMin.getText().toString());
            mEditor.commit();

            RemindDrug remindDrug = new RemindDrug();
            remindDrug.setHour(mTvHour.getText().toString());
            remindDrug.setMin(mTvMin.getText().toString());

            BusProvider.getInstance().post(remindDrug);
            BusProvider.getInstance().post(mStatus);
        } else {
            mEditor.putBoolean(Constant.STATUS_SHAREPREFERENCES, false);
            mStatus = getActivity().getString(R.string.message_false);
            BusProvider.getInstance().post(mStatus);
        }
        mEditor.commit();
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

    @Click(R.id.btnSend)
    void SendFeedback() {
        String phoneNumber = getResources().getString(R.string.number_phone_receive_feedback);
        StringBuilder msg = new StringBuilder();
        msg.append("From : ");
        msg.append(mEdtName.getText());
        msg.append("\n Email: ");
        msg.append(mEdtEmail.getText());
        msg.append("\n Feedback: ");
        msg.append(mEdtFeedback.getText());

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, msg.toString(), null, null);
        Toast.makeText(getActivity().getApplication(), getResources().getString(R.string.message_send_feedback_success), Toast.LENGTH_SHORT).show();
        resetForm();
    }

    public void resetForm() {
        mEdtName.setText("");
        mEdtEmail.setText("");
        mEdtFeedback.setText("");
    }
}
