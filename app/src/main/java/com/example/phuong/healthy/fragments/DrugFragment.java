package com.example.phuong.healthy.fragments;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;

import com.example.phuong.healthy.R;
import com.example.phuong.healthy.adapters.HomeInfoDrugAdapter;
import com.example.phuong.healthy.databases.SqlLiteDbHelper;
import com.example.phuong.healthy.models.Drug;
import com.example.phuong.healthy.utils.Constant;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.TextChange;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phuong on 06/01/2017.
 */
@EFragment(R.layout.fragment_drug)
public class DrugFragment extends BaseFragment implements HomeInfoDrugAdapter.onItemDrugClick {
    @ViewById(R.id.recyclerViewDrug)
    RecyclerView mRecyclerViewDrug;
    @ViewById(R.id.edtSearch)
    EditText mEdtSearch;
    private SqlLiteDbHelper mSqlLiteDbHelper;

    private List<Drug> mDrugs;
    private HomeInfoDrugAdapter mAdapter;

    @Override
    void inits() {
        mSqlLiteDbHelper = new SqlLiteDbHelper(getActivity());
        mSqlLiteDbHelper.openDataBase();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewDrug.setLayoutManager(layoutManager);
        initsData();
        mAdapter = new HomeInfoDrugAdapter(mDrugs, getActivity(), this);
        mRecyclerViewDrug.setAdapter(mAdapter);
    }

    public void initsData() {
        mDrugs = new ArrayList<>();
        mDrugs = mSqlLiteDbHelper.GetDrugs();
    }

    @Override
    public void itemDrugClick(int position) {
        DrugDetailFragment drugDetailFragment = DrugDetailFragment_.builder().build();
        drugDetailFragment.idDrug = mDrugs.get(position).getId();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frContainInfor, drugDetailFragment).addToBackStack(getClass().getName()).commit();
    }

    @TextChange(R.id.edtSearch)
    void onTextChangesSearch(CharSequence query) {
        query = query.toString().toLowerCase();
        final List<Drug> filteredList = new ArrayList<>();

        for (int i = 0; i < mDrugs.size(); i++) {

            final String text = mDrugs.get(i).getName().toLowerCase();
            String indication = mDrugs.get(i).getIndication().toLowerCase();

            if (Constant.unAccent(text).contains(Constant.unAccent(query.toString())) ||
                    Constant.unAccent(indication).contains(Constant.unAccent(query.toString()))
                    ) {
                filteredList.add(mDrugs.get(i));
            }
        }

        mRecyclerViewDrug.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new HomeInfoDrugAdapter(filteredList, getActivity(), this);
        mRecyclerViewDrug.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}
