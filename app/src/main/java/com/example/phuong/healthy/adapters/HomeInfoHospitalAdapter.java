package com.example.phuong.healthy.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.example.phuong.healthy.R;
import com.example.phuong.healthy.databases.SqlLiteDbHelper;
import com.example.phuong.healthy.models.Hospital;
import com.example.phuong.healthy.utils.Constant;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by phuong on 06/01/2017.
 */

public class HomeInfoHospitalAdapter extends RecyclerView.Adapter<HomeInfoHospitalAdapter.MyViewHolder> {
    private List<Hospital> mHospitals;
    private Context mContext;
    private onItemDetailClick mListener;
    private SqlLiteDbHelper mSqlLiteDbHelper;

    public HomeInfoHospitalAdapter(List<Hospital> mHospitals, Context mContext, onItemDetailClick listener) {
        this.mHospitals = mHospitals;
        this.mContext = mContext;
        mListener = listener;

        mSqlLiteDbHelper = new SqlLiteDbHelper(mContext);
        mSqlLiteDbHelper.openDataBase();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_hospital, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Hospital hospital = mHospitals.get(position);
        holder.mTvTitle.setText(hospital.getName());
        holder.mTvAddress.setText(hospital.getAddress());
        Picasso.with(mContext)
                .load(hospital.getImage())
                .placeholder(R.drawable.image_default)
                .error(R.drawable.image_not_found)
                .into(holder.mImgIcon);
        if (hospital.getFav() == 0) {
            holder.mImgFav.setChecked(false);
        } else {
            holder.mImgFav.setChecked(true);
        }

        holder.mSwipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

        // Drag From Right
        holder.mSwipeLayout.addDrag(SwipeLayout.DragEdge.Right, holder.mSwipeLayout.findViewById(R.id.menu_wrapper));

        holder.mImgFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mHospitals.get(position).getFav() == 0) {
                    Toast.makeText(mContext, "Add your favourite successful", Toast.LENGTH_SHORT).show();
                    mSqlLiteDbHelper.updateItemHospitalFav(mHospitals.get(position).getId());
                    mSqlLiteDbHelper.insertFav(mHospitals.get(position).getId(), Constant.TYPE_HOSPITAL);
                } else {
                    Toast.makeText(mContext, "Delete favourite successful", Toast.LENGTH_SHORT).show();
                    mSqlLiteDbHelper.updateItemHospitalUnFav(mHospitals.get(position).getId());
                    mSqlLiteDbHelper.delFav(mHospitals.get(position).getId(), Constant.TYPE_HOSPITAL);
                }
            }
        });


        holder.mRlContentItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.itemDetailClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mHospitals.size();
    }

    public interface onItemDetailClick {
        void itemDetailClick(int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImgIcon;
        public TextView mTvTitle;
        public TextView mTvAddress;
        public CheckBox mImgFav;
        public SwipeLayout mSwipeLayout;
        public RelativeLayout mRlContentItem;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            mTvAddress = (TextView) itemView.findViewById(R.id.tvAddress);
            mImgIcon = (ImageView) itemView.findViewById(R.id.imgIcon);
            mImgFav = (CheckBox) itemView.findViewById(R.id.imgFav);
            mSwipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            mRlContentItem = (RelativeLayout) itemView.findViewById(R.id.rlContentItemHospital);
        }
    }
}
