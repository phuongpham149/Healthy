package com.example.phuong.healthy.fragments;

import android.support.v4.app.Fragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

/**
 * Created by phuong on 05/01/2017.
 */
@EFragment
public abstract class BaseFragment extends Fragment {
    @AfterViews
    abstract void inits();
}
