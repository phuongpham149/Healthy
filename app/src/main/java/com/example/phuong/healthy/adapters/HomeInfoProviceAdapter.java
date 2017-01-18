package com.example.phuong.healthy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
import com.example.phuong.healthy.listeners.OnClickItemDetailProviceListener;
import com.example.phuong.healthy.models.Provices;
import com.example.phuong.healthy.utils.Constant;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by phuong on 06/01/2017.
 */

public class HomeInfoProviceAdapter extends RecyclerView.Adapter<HomeInfoProviceAdapter.MyViewHolder> {
    private List<Provices> mProvices;
    private Context mContext;
    private OnClickItemDetailProviceListener mListener;
    private SqlLiteDbHelper mSqlLiteDbHelper;

    public HomeInfoProviceAdapter(List<Provices> mProvices, Context mContext, OnClickItemDetailProviceListener listener) {
        this.mProvices = mProvices;
        this.mContext = mContext;
        mListener = listener;
        mSqlLiteDbHelper = new SqlLiteDbHelper(mContext);
        mSqlLiteDbHelper.openDataBase();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_provice, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Provices provices = mProvices.get(position);
        holder.mTvTitle.setText(provices.getName());
        if (provices.getFav() == 0) {
            holder.mImgFav.setChecked(false);
        } else {
            holder.mImgFav.setChecked(true);
        }
        if (!"".equals(provices.getImage())) {
            Picasso.with(mContext)
                    .load(provices.getImage())
                    .placeholder(R.drawable.image_default)
                    .error(R.drawable.image_not_found)
                    .into(holder.mImgIcon);
        }

        holder.mSwipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

        // Drag From Right
        holder.mSwipeLayout.addDrag(SwipeLayout.DragEdge.Right, holder.mSwipeLayout.findViewById(R.id.menu_wrapper));

        holder.mImgFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mProvices.get(position).getFav() == 0) {
                    Toast.makeText(mContext, "Add your favourite successful", Toast.LENGTH_SHORT).show();
                    mSqlLiteDbHelper.updateItemProviceFav(mProvices.get(position).getId());
                    mSqlLiteDbHelper.insertFav(mProvices.get(position).getId(), Constant.TYPE_PROVICE);
                } else {
                    Toast.makeText(mContext, "Delete favourite successful", Toast.LENGTH_SHORT).show();
                    mSqlLiteDbHelper.delFav(mProvices.get(position).getId(), Constant.TYPE_PROVICE);
                    mSqlLiteDbHelper.updateItemProviceUnFav(mProvices.get(position).getId());
                }
            }
        });
        holder.mRlItemContentProvince.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.clickItemDetailProvice(mProvices.get(position).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProvices.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImgIcon;
        public TextView mTvTitle;
        public CheckBox mImgFav;
        public SwipeLayout mSwipeLayout;
        private RelativeLayout mRlItemContentProvince;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            mImgIcon = (ImageView) itemView.findViewById(R.id.imgIcon);
            mImgFav = (CheckBox) itemView.findViewById(R.id.imgFav);
            mSwipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            mRlItemContentProvince = (RelativeLayout) itemView.findViewById(R.id.rl_content_item_province);
        }
    }
}
