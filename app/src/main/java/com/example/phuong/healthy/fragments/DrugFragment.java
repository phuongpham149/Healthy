package com.example.phuong.healthy.fragments;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.example.phuong.healthy.R;
import com.example.phuong.healthy.adapters.HomeInfoDrugAdapter;
import com.example.phuong.healthy.models.Drug;

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

    private List<Drug> mDrugs;
    private HomeInfoDrugAdapter mAdapter;

    @Override
    void inits() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewDrug.setLayoutManager(layoutManager);
        initsData();
        mAdapter = new HomeInfoDrugAdapter(mDrugs, getActivity(), this);
        mRecyclerViewDrug.setAdapter(mAdapter);
    }

    public void initsData() {
        mDrugs = new ArrayList<>();

        Drug drug = new Drug();
        drug.setName("Pentaxin");
        drug.setIndication("Cảm cúm, tiêu chảy");
        drug.setImage("http://enbac10.vcmedia.vn/thumb_max/up_new/2014/09/29/item/880293/20140929191804.jpg");

        Drug drug1 = new Drug();
        drug1.setName("Tetraxim");
        drug1.setIndication("Cảm cúm, tiêu chảy");
        drug1.setImage("http://hinhanh.gamechocon.com/Thu_Vien_Anh/Anh_Lon/pills-icon.png");

        Drug drug2 = new Drug();
        drug2.setName("Pneumo 23");
        drug2.setIndication("Cảm cúm, tiêu chảy");
        drug2.setImage("http://enbac10.vcmedia.vn/thumb_max/up_new/2014/09/29/item/880293/20140929191804.jpg");

        Drug drug3 = new Drug();
        drug3.setName("Pentaxin");
        drug3.setIndication("Cảm cúm, tiêu chảy");
        drug3.setImage("http://hinhanh.gamechocon.com/Thu_Vien_Anh/Anh_Lon/pills-icon.png");

        Drug drug4 = new Drug();
        drug4.setName("Meningo A+C");
        drug4.setIndication("Cảm cúm, tiêu chảy");
        drug4.setImage("http://enbac10.vcmedia.vn/thumb_max/up_new/2014/09/29/item/880293/20140929191804.jpg");

        Drug drug5 = new Drug();
        drug5.setName("Pentaxin");
        drug5.setIndication("Cảm cúm, tiêu chảy");
        drug5.setImage("http://hinhanh.gamechocon.com/Thu_Vien_Anh/Anh_Lon/pills-icon.png");

        Drug drug7 = new Drug();
        drug7.setName("Influvac");
        drug7.setIndication("Cảm cúm, tiêu chảy");
        drug7.setImage("http://enbac10.vcmedia.vn/thumb_max/up_new/2014/09/29/item/880293/20140929191804.jpg");

        Drug drug6 = new Drug();
        drug6.setName("Pentaxin");
        drug6.setIndication("Cảm cúm, tiêu chảy");
        drug6.setImage("http://hinhanh.gamechocon.com/Thu_Vien_Anh/Anh_Lon/pills-icon.png");

        mDrugs.add(drug);
        mDrugs.add(drug1);
        mDrugs.add(drug2);
        mDrugs.add(drug3);
        mDrugs.add(drug4);
        mDrugs.add(drug5);
        mDrugs.add(drug6);
        mDrugs.add(drug7);
    }

    @Override
    public void itemDrugClick(int position) {
        DrugDetailFragment drugDetailFragment = DrugDetailFragment_.builder().build();
        drugDetailFragment.position = position;
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frContainInfor, drugDetailFragment).commit();
    }

    @TextChange(R.id.edtSearch)
    void onTextChangesSearch(CharSequence query, int before, int start, int count) {
        query = query.toString().toLowerCase();
        final List<Drug> filteredList = new ArrayList<>();

        for (int i = 0; i < mDrugs.size(); i++) {

            final String text = mDrugs.get(i).getName().toLowerCase();

            if (text.contains(query)) {
                filteredList.add(mDrugs.get(i));
            }
        }

        mRecyclerViewDrug.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new HomeInfoDrugAdapter(filteredList, getActivity(), this);
        mRecyclerViewDrug.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}
