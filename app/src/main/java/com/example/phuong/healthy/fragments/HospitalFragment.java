package com.example.phuong.healthy.fragments;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.example.phuong.healthy.R;
import com.example.phuong.healthy.adapters.HomeInfoHospitalAdapter;
import com.example.phuong.healthy.databases.SqlLiteDbHelper;
import com.example.phuong.healthy.models.Hospital;
import com.example.phuong.healthy.utils.Constant;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.TextChange;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phuong on 06/01/2017.
 */
@EFragment(R.layout.fragment_hospital)
public class HospitalFragment extends BaseFragment implements HomeInfoHospitalAdapter.onItemDetailClick {
    @FragmentArg
    public int provice;
    @ViewById(R.id.recyclerViewHospital)
    RecyclerView mRecyclerViewHospital;
    @ViewById(R.id.edtSearch)
    EditText mEdtSearch;
    private HomeInfoHospitalAdapter mHospitalAdapter;
    private List<Hospital> mHospitals;
    private SqlLiteDbHelper mSqlLiteDbHelper;

    @Override
    void inits() {
        mSqlLiteDbHelper = new SqlLiteDbHelper(getActivity());
        mSqlLiteDbHelper.openDataBase();
        initData();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        mRecyclerViewHospital.setLayoutManager(layoutManager);
        mHospitalAdapter = new HomeInfoHospitalAdapter(mHospitals, getContext(), this);
        mRecyclerViewHospital.setAdapter(mHospitalAdapter);
    }

    public void initData() {
        mHospitals = new ArrayList<>();
        mHospitals = mSqlLiteDbHelper.GetHospitalByIdProvice(provice);
    }

    @Override
    public void itemDetailClick(int id) {
        HospitalDetailFragment hospitalDetailFragment = HospitalDetailFragment_.builder().build();
        hospitalDetailFragment.idHospital = id;
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frContainInfor, hospitalDetailFragment).addToBackStack(getClass().getName()).commit();
    }

    @TextChange(R.id.edtSearch)
    void onTextChangesSearch(CharSequence query) {
        query = query.toString().toLowerCase();
        final List<Hospital> filteredList = new ArrayList<>();

        for (int i = 0; i < mHospitals.size(); i++) {

            final String text = mHospitals.get(i).getName().toLowerCase();

            if (Constant.unAccent(text).contains(Constant.unAccent(query.toString()))) {
                filteredList.add(mHospitals.get(i));
            }
        }

        mRecyclerViewHospital.setLayoutManager(new LinearLayoutManager(getActivity()));
        mHospitalAdapter = new HomeInfoHospitalAdapter(filteredList, getContext(), this);
        mRecyclerViewHospital.setAdapter(mHospitalAdapter);
        mHospitalAdapter.notifyDataSetChanged();
    }
}
