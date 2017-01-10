package com.example.phuong.healthy.fragments;

import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.example.phuong.healthy.R;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by phuong on 06/01/2017.
 */
@EFragment(R.layout.fragment_infor)
public class InforFragment extends BaseFragment {

    @ViewById(R.id.tabs)
    TabLayout mTabs;

    private String mTabTitles[] = new String[]{"HOSPITAL", "DRUG"};

    @Override
    void inits() {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frContainInfor, ProvicesFragment_.builder().build()).commit();

        initTextTab();
        mTabs.getTabAt(0).setIcon(R.drawable.ic_hospital);
        mTabs.getTabAt(1).setIcon(R.drawable.ic_pill);

        mTabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frContainInfor, ProvicesFragment_.builder().build()).commit();

                        break;
                    case 1:
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frContainInfor, DrugFragment_.builder().build()).commit();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void initTextTab() {

        TabLayout.Tab tab0 = mTabs.newTab();
        mTabs.addTab(tab0);
        TabLayout.Tab tab1 = mTabs.newTab();
        mTabs.addTab(tab1);

        TextView mTvTab1 = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.tab_infor_text_custom, null);
        mTvTab1.setText(mTabTitles[0]);
        tab0.setCustomView(mTvTab1);
        
        TextView mTvTab2 = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.tab_infor_text_custom, null);
        mTvTab2.setText(mTabTitles[1]);
        tab1.setCustomView(mTvTab2);

    }
}
