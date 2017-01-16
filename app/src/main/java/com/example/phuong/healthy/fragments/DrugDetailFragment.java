package com.example.phuong.healthy.fragments;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.phuong.healthy.R;
import com.example.phuong.healthy.databases.SqlLiteDbHelper;
import com.example.phuong.healthy.models.Drug;
import com.example.phuong.healthy.views.CircleImage;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

/**
 * Created by phuong on 08/01/2017.
 */
@EFragment(R.layout.fragment_drug_detail)
public class DrugDetailFragment extends BaseFragment {
    @FragmentArg
    public int idDrug;
    @ViewById(R.id.imgDrug)
    CircleImage mImgDrug;
    @ViewById(R.id.imageview_info_down_item_drug_detail)
    ImageView mImgInfoDown;
    @ViewById(R.id.imageview_info_up_item_drug_detail)
    ImageView mImgInfoUp;
    @ViewById(R.id.tvName)
    TextView mTvName;
    @ViewById(R.id.tvManufacter)
    TextView mTvManufacter;
    @ViewById(R.id.tvIndication)
    TextView mTvIndication;
    @ViewById(R.id.rl_infor_side_effect_drug)
    RelativeLayout mRlInfoSideEffect;
    @ViewById(R.id.imageview_contraindication_down_item_drug_detail)
    ImageView mImgContraindicationDown;
    @ViewById(R.id.imageview_contraindication_up_item_drug_detail)
    ImageView mImgContraindicationUp;
    @ViewById(R.id.rl_contraindication_effect_item_drug)
    RelativeLayout mRlContraindication;
    @ViewById(R.id.tvContraindication)
    TextView mTvContraindication;
    @ViewById(R.id.imageview_usage_down_item_drug_detail)
    ImageView mImgUsageDown;
    @ViewById(R.id.imageview_usage_up_item_drug_detail)
    ImageView mImgUsageUp;
    @ViewById(R.id.rl_info_usage)
    RelativeLayout mRlUsage;
    @ViewById(R.id.tvUsage)
    TextView mTvUsage;
    @ViewById(R.id.rl_content)
    RelativeLayout mRlContent;
    private Drug mDrug;
    private SqlLiteDbHelper mSqlLiteDbHelper;

    @Override
    void inits() {
        mSqlLiteDbHelper = new SqlLiteDbHelper(getActivity());
        mSqlLiteDbHelper.openDataBase();
        initData();
        Picasso.with(getActivity())
                .load(mDrug.getImage())
                .placeholder(R.drawable.image_default)
                .error(R.drawable.image_not_found)
                .into(mImgDrug);
    }

    @Click(R.id.imageview_info_down_item_drug_detail)
    void InfoDownAction() {
        mImgInfoDown.setVisibility(View.GONE);
        mImgInfoUp.setVisibility(View.VISIBLE);
        mRlInfoSideEffect.setVisibility(View.VISIBLE);

    }

    @Click(R.id.imageview_info_up_item_drug_detail)
    void InfoUpAction() {
        mImgInfoUp.setVisibility(View.GONE);
        mImgInfoDown.setVisibility(View.VISIBLE);
        mRlInfoSideEffect.setVisibility(View.GONE);
    }

    @Click(R.id.imageview_contraindication_down_item_drug_detail)
    void ContraindicationDownAction() {
        mImgContraindicationDown.setVisibility(View.GONE);
        mImgContraindicationUp.setVisibility(View.VISIBLE);
        mRlContraindication.setVisibility(View.VISIBLE);

    }

    @Click(R.id.imageview_contraindication_up_item_drug_detail)
    void ContraindicationUpAction() {
        mImgContraindicationDown.setVisibility(View.VISIBLE);
        mImgContraindicationUp.setVisibility(View.GONE);
        mRlContraindication.setVisibility(View.GONE);
    }

    @Click(R.id.imageview_usage_down_item_drug_detail)
    void UsageDownAction() {
        mImgUsageDown.setVisibility(View.GONE);
        mImgUsageUp.setVisibility(View.VISIBLE);
        mRlUsage.setVisibility(View.VISIBLE);

    }

    @Click(R.id.imageview_usage_up_item_drug_detail)
    void UsageUpAction() {
        mImgUsageDown.setVisibility(View.VISIBLE);
        mImgUsageUp.setVisibility(View.GONE);
        mRlUsage.setVisibility(View.GONE);

    }

    public void initData() {
        mDrug = mSqlLiteDbHelper.getDrugDetail(idDrug);
        mTvName.setText(mDrug.getName());
        mTvManufacter.setText(mDrug.getManufacturer());
        mTvIndication.setText(mDrug.getIndication());
        mTvContraindication.setText(mDrug.getContraindication());
        mTvUsage.setText(mDrug.getDosage_and_usage());
    }
}
