package com.example.phuong.healthy.fragments;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.phuong.healthy.R;
import com.example.phuong.healthy.adapters.HomeFavAdapter;
import com.example.phuong.healthy.databases.SqlLiteDbHelper;
import com.example.phuong.healthy.models.Drug;
import com.example.phuong.healthy.models.Fav;
import com.example.phuong.healthy.models.Hospital;
import com.example.phuong.healthy.models.Provices;
import com.example.phuong.healthy.utils.Constant;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phuong on 08/01/2017.
 */
@EFragment(R.layout.fragment_fav_list)
public class FavListFragment extends BaseFragment implements HomeFavAdapter.onItemFavClickListener {
    @ViewById(R.id.recyclerViewFav)
    RecyclerView mRecyclerViewFav;
    @ViewById(R.id.tvTitleFav)
    TextView mTvTitle;

    private List<Fav> mFavs;
    private HomeFavAdapter mHomeFavAdapter;
    private SqlLiteDbHelper mSqlLiteDbHelper;

    @Override
    void inits() {
        mSqlLiteDbHelper = new SqlLiteDbHelper(getActivity());
        mSqlLiteDbHelper.openDataBase();
        initData();
        if (mFavs.size() > 0) {
            mTvTitle.setVisibility(View.GONE);
        }
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewFav.setLayoutManager(layoutManager);
        mHomeFavAdapter = new HomeFavAdapter(mFavs, getActivity(), this);
        mRecyclerViewFav.setAdapter(mHomeFavAdapter);
    }

    public void initData() {
        mFavs = new ArrayList<>();
        mFavs = mSqlLiteDbHelper.GetFavs();
        for (Fav fav : mFavs) {
            if (fav.getType() == Constant.TYPE_PROVICE) {
                Provices provices = mSqlLiteDbHelper.getProviceDetail(fav.getIdItem());
                fav.setName(provices.getName());
                fav.setImage(provices.getImage());
            }
            if (fav.getType() == Constant.TYPE_HOSPITAL) {
                Hospital hospital = mSqlLiteDbHelper.getHospitalDetail(fav.getIdItem());
                fav.setName(hospital.getName());
                fav.setImage(hospital.getImage());
            }
            if (fav.getType() == Constant.TYPE_DRUG) {
                Drug drug = mSqlLiteDbHelper.getDrugDetail(fav.getIdItem());
                fav.setName(drug.getName());
                fav.setImage(drug.getImage());
            }
        }
    }

    @Override
    public void itemClick(int position) {
        switch (mFavs.get(position).getType()) {
            case 1:
                HospitalFavFragment hospitalFavFragment = HospitalFavFragment_.builder().build();
                hospitalFavFragment.provice = mFavs.get(position).getIdItem();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frContainFav, hospitalFavFragment).addToBackStack(getClass().getName()).commit();
                break;
            case 2:
                HospitalDetailFragment hospitalDetailFragment = HospitalDetailFragment_.builder().build();
                hospitalDetailFragment.idHospital = mFavs.get(position).getIdItem();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frContainFav, hospitalDetailFragment).addToBackStack(getClass().getName()).commit();
                break;
            case 3:
                DrugDetailFragment drugDetailFragment = DrugDetailFragment_.builder().build();
                drugDetailFragment.idDrug = mFavs.get(position).getIdItem();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frContainFav, drugDetailFragment).addToBackStack(getClass().getName()).commit();
                break;
        }
    }
}
