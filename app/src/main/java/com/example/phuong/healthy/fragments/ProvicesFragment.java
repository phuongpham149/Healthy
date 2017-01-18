package com.example.phuong.healthy.fragments;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.example.phuong.healthy.R;
import com.example.phuong.healthy.adapters.HomeInfoProviceAdapter;
import com.example.phuong.healthy.databases.SqlLiteDbHelper;
import com.example.phuong.healthy.listeners.OnClickItemDetailProviceListener;
import com.example.phuong.healthy.models.Provices;
import com.example.phuong.healthy.utils.Constant;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.TextChange;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phuong on 06/01/2017.
 */
@EFragment(R.layout.fragment_provice)
public class ProvicesFragment extends BaseFragment implements OnClickItemDetailProviceListener {
    @ViewById(R.id.recyclerViewProvices)
    RecyclerView mRecyclerViewProvices;
    @ViewById(R.id.edtSearch)
    EditText mEdtSearch;
    @ViewById(R.id.btnSuggest)
    FloatingActionButton mBtnSuggest;

    private double mLongitude;
    private double mLatitude;
    private int mIdProvice = 0;

    private HomeInfoProviceAdapter mProvicesAdapter;
    private List<Provices> mProvices;
    private SqlLiteDbHelper mSqlLiteDbHelper;

    @Override
    void inits() {
        mSqlLiteDbHelper = new SqlLiteDbHelper(getActivity());
        mSqlLiteDbHelper.openDataBase();
        initData();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        mRecyclerViewProvices.setLayoutManager(layoutManager);
        mProvicesAdapter = new HomeInfoProviceAdapter(mProvices, getContext(), this);
        mRecyclerViewProvices.setAdapter(mProvicesAdapter);
    }

    public void initData() {
        mProvices = new ArrayList<>();
        mProvices = mSqlLiteDbHelper.GetProvices();
    }

    @Override
    public void clickItemDetailProvice(int id) {
        HospitalFragment hospitalFragment = HospitalFragment_.builder().build();
        hospitalFragment.provice = id;
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frContainInfor, hospitalFragment).addToBackStack(getClass().getName()).commit();
    }

    @TextChange(R.id.edtSearch)
    void onTextChangesSearch(CharSequence query) {
        query = query.toString().toLowerCase();
        final List<Provices> filteredList = new ArrayList<>();

        for (int i = 0; i < mProvices.size(); i++) {

            final String text = mProvices.get(i).getName().toLowerCase();

            if (Constant.unAccent(text).contains(Constant.unAccent(query.toString()))) {
                filteredList.add(mProvices.get(i));
            }
        }

        mRecyclerViewProvices.setLayoutManager(new LinearLayoutManager(getActivity()));
        mProvicesAdapter = new HomeInfoProviceAdapter(filteredList, getActivity(), this);
        mRecyclerViewProvices.setAdapter(mProvicesAdapter);
        mProvicesAdapter.notifyDataSetChanged();
    }

    @Click(R.id.btnSuggest)
    void SuggestAction() {
        String city = Constant.getLocationAddress(getActivity());
        //tim id
        for (int i = 0; i < mProvices.size(); i++) {

            final String text = mProvices.get(i).getName().toLowerCase();
            if (Constant.unAccent(text).equals(Constant.unAccent(city.toLowerCase()))) {
                mIdProvice = mProvices.get(i).getId();
            }
        }
        HospitalFragment hospitalFragment = HospitalFragment_.builder().build();
        hospitalFragment.provice = mIdProvice;
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frContainInfor, hospitalFragment).addToBackStack(getClass().getName()).commit();
    }

}
