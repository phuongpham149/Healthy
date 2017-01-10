package com.example.phuong.healthy.fragments;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.example.phuong.healthy.R;
import com.example.phuong.healthy.adapters.HomeInfoHospitalAdapter;
import com.example.phuong.healthy.models.Hospital;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.TextChange;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phuong on 06/01/2017.
 */
@EFragment(R.layout.fragment_hospital)
public class HospitalFragment extends BaseFragment implements HomeInfoHospitalAdapter.onItemDetailClick {
    @ViewById(R.id.recyclerViewHospital)
    RecyclerView mRecyclerViewHospital;
    @ViewById(R.id.edtSearch)
    EditText mEdtSearch;

    private HomeInfoHospitalAdapter mHospitalAdapter;
    private List<Hospital> mHospitals;

    @Override
    void inits() {
        initData();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        mRecyclerViewHospital.setLayoutManager(layoutManager);
        mHospitalAdapter = new HomeInfoHospitalAdapter(mHospitals, getContext(), this);
        mRecyclerViewHospital.setAdapter(mHospitalAdapter);
    }

    public void initData() {
        mHospitals = new ArrayList<>();
        //mProvices = Provices.listAll(Provices.class);
        Hospital hospital = new Hospital();
        hospital.setName("Bạch Mai");
        hospital.setAddress("78 Ngô Quyền, Hà Nội, Việt Nam");
        hospital.setImage("https://upload.wikimedia.org/wikipedia/vi/thumb/1/13/Hanoi_Logo.svg/1021px-Hanoi_Logo.svg.png");

        Hospital hospital1 = new Hospital();
        hospital1.setName("Bạch Mai");
        hospital1.setAddress("78 Ngô Quyền, Hà Nội, Việt Nam");
        hospital1.setImage("https://travel.com.vn/images/destination/Large/dg_150722_1384161092_13179561885287du-lich-ha-noi.jpg");

        Hospital hospital2 = new Hospital();
        hospital2.setName("Bạch Mai");
        hospital2.setAddress("78 Ngô Quyền, Hà Nội, Việt Nam");
        hospital2.setImage("https://upload.wikimedia.org/wikipedia/vi/thumb/1/13/Hanoi_Logo.svg/1021px-Hanoi_Logo.svg.png");

        Hospital hospital3 = new Hospital();
        hospital3.setName("Bạch Mai");
        hospital3.setAddress("78 Ngô Quyền, Hà Nội, Việt Nam");
        hospital3.setImage("https://travel.com.vn/images/destination/Large/dg_150722_1384161092_13179561885287du-lich-ha-noi.jpg");

        Hospital hospital4 = new Hospital();
        hospital4.setName("Bạch Mai");
        hospital4.setAddress("78 Ngô Quyền, Hà Nội, Việt Nam");
        hospital4.setImage("https://upload.wikimedia.org/wikipedia/vi/thumb/1/13/Hanoi_Logo.svg/1021px-Hanoi_Logo.svg.png");

        Hospital hospital5 = new Hospital();
        hospital5.setName("Bạch Mai");
        hospital5.setAddress("78 Ngô Quyền, Hà Nội, Việt Nam");
        hospital5.setImage("https://travel.com.vn/images/destination/Large/dg_150722_1384161092_13179561885287du-lich-ha-noi.jpg");

        Hospital hospital6 = new Hospital();
        hospital6.setName("Bạch Mai");
        hospital6.setAddress("78 Ngô Quyền, Hà Nội, Việt Nam");
        hospital6.setImage("https://upload.wikimedia.org/wikipedia/vi/thumb/1/13/Hanoi_Logo.svg/1021px-Hanoi_Logo.svg.png");

        mHospitals.add(hospital);
        mHospitals.add(hospital1);
        mHospitals.add(hospital2);
        mHospitals.add(hospital3);
        mHospitals.add(hospital4);
        mHospitals.add(hospital5);
        mHospitals.add(hospital6);
    }

    @Override
    public void itemDetailClick(int position) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frContainInfor, HospitalDetailFragment_.builder().build()).addToBackStack(getClass().getName()).commit();
    }

    @TextChange(R.id.edtSearch)
    void onTextChangesSearch(CharSequence query, int before, int start, int count) {
        query = query.toString().toLowerCase();
        final List<Hospital> filteredList = new ArrayList<>();

        for (int i = 0; i < mHospitals.size(); i++) {

            final String text = mHospitals.get(i).getName().toLowerCase();

            if (text.contains(query)) {
                filteredList.add(mHospitals.get(i));
            }
        }

        mRecyclerViewHospital.setLayoutManager(new LinearLayoutManager(getActivity()));
        mHospitalAdapter = new HomeInfoHospitalAdapter(filteredList, getContext(), this);
        mRecyclerViewHospital.setAdapter(mHospitalAdapter);
        mHospitalAdapter.notifyDataSetChanged();
    }
}
