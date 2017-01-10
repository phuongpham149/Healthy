package com.example.phuong.healthy.fragments;

import com.example.phuong.healthy.R;

import org.androidannotations.annotations.EFragment;

/**
 * Created by phuong on 06/01/2017.
 */
@EFragment(R.layout.fragment_fav)
public class FavFragment extends BaseFragment {


    @Override
    void inits() {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frContainFav, FavListFragment_.builder().build()).commit();
    }


}
