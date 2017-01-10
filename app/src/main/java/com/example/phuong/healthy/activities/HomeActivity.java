package com.example.phuong.healthy.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.example.phuong.healthy.R;
import com.example.phuong.healthy.fragments.DetectFragment_;
import com.example.phuong.healthy.fragments.FavFragment_;
import com.example.phuong.healthy.fragments.InforFragment_;
import com.example.phuong.healthy.fragments.SettingFragment_;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by phuong on 05/01/2017.
 */
@EActivity(R.layout.activity_home)
public class HomeActivity extends BaseActivity {

    @ViewById(R.id.tabs)
    TabLayout mTabs;
    private FragmentManager mFragmentManager;

    @Override
    void inits() {
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction().replace(R.id.flContainer, DetectFragment_.builder().build()).commit();

        initTextTab();

        mTabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        mFragmentManager.beginTransaction().replace(R.id.flContainer, DetectFragment_.builder().build()).commit();

                        break;
                    case 1:
                        mFragmentManager.beginTransaction().replace(R.id.flContainer, InforFragment_.builder().build()).commit();
                        break;
                    case 2:
                        mFragmentManager.beginTransaction().replace(R.id.flContainer, FavFragment_.builder().build()).commit();
                        break;
                    case 3:
                        mFragmentManager.beginTransaction().replace(R.id.flContainer, SettingFragment_.builder().build()).commit();
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
        TabLayout.Tab tab2 = mTabs.newTab();
        mTabs.addTab(tab2);
        TabLayout.Tab tab3 = mTabs.newTab();
        mTabs.addTab(tab3);

        TextView mTvTab1 = (TextView) LayoutInflater.from(this).inflate(R.layout.tab_home_text_custom, null);
        mTvTab1.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_search, 0, 0);
        tab0.setCustomView(mTvTab1);

        TextView mTvTab2 = (TextView) LayoutInflater.from(this).inflate(R.layout.tab_home_text_custom, null);
        mTvTab2.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_info, 0, 0);
        tab1.setCustomView(mTvTab2);

        TextView mTvTab3 = (TextView) LayoutInflater.from(this).inflate(R.layout.tab_home_text_custom, null);
        mTvTab3.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_favorite, 0, 0);
        tab2.setCustomView(mTvTab3);

        TextView mTvTab4 = (TextView) LayoutInflater.from(this).inflate(R.layout.tab_home_text_custom, null);
        mTvTab4.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_settings, 0, 0);
        tab3.setCustomView(mTvTab4);
    }
}
