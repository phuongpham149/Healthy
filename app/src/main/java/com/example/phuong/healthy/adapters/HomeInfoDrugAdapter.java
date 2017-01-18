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
import com.example.phuong.healthy.models.Drug;
import com.example.phuong.healthy.utils.Constant;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by phuong on 06/01/2017.
 */

public class HomeInfoDrugAdapter extends RecyclerView.Adapter<HomeInfoDrugAdapter.MyViewHolder> {
    private List<Drug> mDrugs;
    private Context mContext;
    private onItemDrugClick mListener;
    private SqlLiteDbHelper mSqlLiteDbHelper;

    public HomeInfoDrugAdapter(List<Drug> drugs, Context mContext, onItemDrugClick listener) {
        this.mDrugs = drugs;
        this.mContext = mContext;
        mListener = listener;
        mSqlLiteDbHelper = new SqlLiteDbHelper(mContext);
        mSqlLiteDbHelper.openDataBase();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_drug, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Drug drug = mDrugs.get(position);
        holder.mTvTitle.setText(drug.getName());
        holder.mTvAddress.setText(drug.getManufacturer());
        if (drug.getFav() == 0) {
            holder.mImgFav.setChecked(false);
        } else {
            holder.mImgFav.setChecked(true);
        }
        Picasso.with(mContext)
                .load(drug.getImage())
                .placeholder(R.drawable.image_default)
                .error(R.drawable.image_not_found)
                .into(holder.mImgIcon);

        holder.mSwipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

        // Drag From Right
        holder.mSwipeLayout.addDrag(SwipeLayout.DragEdge.Right, holder.mSwipeLayout.findViewById(R.id.menu_wrapper));

        holder.mImgFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDrugs.get(position).getFav() == 0) {
                    Toast.makeText(mContext, "Add your favourite successful", Toast.LENGTH_SHORT).show();
                    mSqlLiteDbHelper.insertFav(mDrugs.get(position).getId(), Constant.TYPE_DRUG);
                    mSqlLiteDbHelper.updateItemDrugFav(mDrugs.get(position).getId());
                } else {
                    Toast.makeText(mContext, "Delete favourite successful", Toast.LENGTH_SHORT).show();
                    mSqlLiteDbHelper.updateItemDrugUnFav(mDrugs.get(position).getId());
                    mSqlLiteDbHelper.delFav(mDrugs.get(position).getId(), Constant.TYPE_DRUG);
                }
            }
        });
        holder.mRlContentItemDrug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.itemDrugClick(mDrugs.get(position).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDrugs.size();
    }

    public interface onItemDrugClick {
        void itemDrugClick(int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImgIcon;
        public TextView mTvTitle;
        public TextView mTvAddress;
        public CheckBox mImgFav;
        public SwipeLayout mSwipeLayout;
        public RelativeLayout mRlContentItemDrug;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            mTvAddress = (TextView) itemView.findViewById(R.id.tvAddress);
            mImgIcon = (ImageView) itemView.findViewById(R.id.imgIcon);
            mImgFav = (CheckBox) itemView.findViewById(R.id.imgFav);
            mSwipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            mRlContentItemDrug = (RelativeLayout) itemView.findViewById(R.id.rl_item_content_drug);
        }
    }
}
