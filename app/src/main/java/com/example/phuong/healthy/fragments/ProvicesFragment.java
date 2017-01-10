package com.example.phuong.healthy.fragments;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.example.phuong.healthy.R;
import com.example.phuong.healthy.adapters.HomeInfoProviceAdapter;
import com.example.phuong.healthy.listeners.OnClickItemDetailProviceListener;
import com.example.phuong.healthy.models.Provices;

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

    private HomeInfoProviceAdapter mProvicesAdapter;
    private List<Provices> mProvices;

    @Override
    void inits() {
        initData();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        mRecyclerViewProvices.setLayoutManager(layoutManager);
        mProvicesAdapter = new HomeInfoProviceAdapter(mProvices, getContext(), this);
        mRecyclerViewProvices.setAdapter(mProvicesAdapter);
    }

    public void initData() {
        mProvices = new ArrayList<>();
        //mProvices = Provices.listAll(Provices.class);
        mProvices.add(new Provices("Ha Noi", "https://upload.wikimedia.org/wikipedia/vi/thumb/1/13/Hanoi_Logo.svg/1021px-Hanoi_Logo.svg.png"));
        mProvices.add(new Provices("Hai Phong", "https://upload.wikimedia.org/wikipedia/vi/thumb/1/13/Hanoi_Logo.svg/1021px-Hanoi_Logo.svg.png"));
        mProvices.add(new Provices("Bac Giang", "https://travel.com.vn/images/destination/Large/dg_150722_1384161092_13179561885287du-lich-ha-noi.jpg"));

        mProvices.add(new Provices("Hà Nội", "https://upload.wikimedia.org/wikipedia/vi/thumb/1/13/Hanoi_Logo.svg/1021px-Hanoi_Logo.svg.png"));
        mProvices.add(new Provices("Hải Phòng", "https://upload.wikimedia.org/wikipedia/vi/thumb/1/13/Hanoi_Logo.svg/1021px-Hanoi_Logo.svg.png"));
        mProvices.add(new Provices("Bắc Giang", "https://travel.com.vn/images/destination/Large/dg_150722_1384161092_13179561885287du-lich-ha-noi.jpg"));

        mProvices.add(new Provices("Hà Nội", "https://upload.wikimedia.org/wikipedia/vi/thumb/1/13/Hanoi_Logo.svg/1021px-Hanoi_Logo.svg.png"));
        mProvices.add(new Provices("Hải Phòng", "https://upload.wikimedia.org/wikipedia/vi/thumb/1/13/Hanoi_Logo.svg/1021px-Hanoi_Logo.svg.png"));
        mProvices.add(new Provices("Bắc Giang", "https://travel.com.vn/images/destination/Large/dg_150722_1384161092_13179561885287du-lich-ha-noi.jpg"));

        mProvices.add(new Provices("Hà Nội", "https://upload.wikimedia.org/wikipedia/vi/thumb/1/13/Hanoi_Logo.svg/1021px-Hanoi_Logo.svg.png"));
        mProvices.add(new Provices("Hải Phòng", "https://upload.wikimedia.org/wikipedia/vi/thumb/1/13/Hanoi_Logo.svg/1021px-Hanoi_Logo.svg.png"));
        mProvices.add(new Provices("Bắc Giang", "https://travel.com.vn/images/destination/Large/dg_150722_1384161092_13179561885287du-lich-ha-noi.jpg"));
    }

    @Override
    public void clickItemDetailProvice(int position) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frContainInfor, HospitalFragment_.builder().build()).addToBackStack(getClass().getName()).commit();
    }

    @TextChange(R.id.edtSearch)
    void onTextChangesSearch(CharSequence query, int before, int start, int count) {
        query = query.toString().toLowerCase();
        final List<Provices> filteredList = new ArrayList<>();

        for (int i = 0; i < mProvices.size(); i++) {

            final String text = mProvices.get(i).getName().toLowerCase();

            if (text.contains(query)) {
                filteredList.add(mProvices.get(i));
            }
        }

        mRecyclerViewProvices.setLayoutManager(new LinearLayoutManager(getActivity()));
        mProvicesAdapter = new HomeInfoProviceAdapter(filteredList, getActivity(), this);
        mRecyclerViewProvices.setAdapter(mProvicesAdapter);
        mProvicesAdapter.notifyDataSetChanged();
    }
}
