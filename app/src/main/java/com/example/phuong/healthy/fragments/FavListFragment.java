package com.example.phuong.healthy.fragments;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.phuong.healthy.R;
import com.example.phuong.healthy.adapters.HomeFavAdapter;
import com.example.phuong.healthy.models.Fav;

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

    @Override
    void inits() {
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
        Fav fav = new Fav();
        fav.setId(1);
        fav.setImage("http://enbac10.vcmedia.vn/thumb_max/up_new/2014/09/29/item/880293/20140929191804.jpg");
        fav.setName("Pentaxin");
        fav.setType(3);
        mFavs.add(fav);
    }

    @Override
    public void itemClick(int position) {
        switch (mFavs.get(position).getType()) {
            case 1:
                //provice;
                break;
            case 2:
                //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frContainInfor,HospitalFragment_.builder().build()).commit();
                //hospital
                break;
            case 3:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frContainFav, DrugDetailFragment_.builder().build()).addToBackStack(getClass().getName()).commit();
                //drug
                break;
        }
    }
}
