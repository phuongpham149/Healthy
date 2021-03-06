package com.example.phuong.healthy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.example.phuong.healthy.R;
import com.example.phuong.healthy.databases.SqlLiteDbHelper;
import com.example.phuong.healthy.models.Fav;
import com.example.phuong.healthy.utils.Constant;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by phuong on 06/01/2017.
 */

public class HomeFavAdapter extends RecyclerView.Adapter<HomeFavAdapter.MyViewHolder> {
    private List<Fav> mFavs;
    private Context mContext;
    private onItemFavClickListener mListener;
    private SqlLiteDbHelper mSqlLiteDbHelper;

    public HomeFavAdapter(List<Fav> mFavs, Context mContext, onItemFavClickListener listener) {
        this.mFavs = mFavs;
        this.mContext = mContext;
        mListener = listener;

        mSqlLiteDbHelper = new SqlLiteDbHelper(mContext);
        mSqlLiteDbHelper.openDataBase();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_fav, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Fav fav = mFavs.get(position);
        holder.mTvTitle.setText(fav.getName());
        Picasso.with(mContext)
                .load(fav.getImage())
                .placeholder(R.drawable.image_default)
                .error(R.drawable.image_not_found)
                .into(holder.mImgIcon);

        holder.mSwipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

        // Drag From Right
        holder.mSwipeLayout.addDrag(SwipeLayout.DragEdge.Right, holder.mSwipeLayout.findViewById(R.id.menu_wrapper));

        holder.mImgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Delete successful", Toast.LENGTH_SHORT).show();
                mSqlLiteDbHelper.delFav(fav.getIdItem(), fav.getType());
                if (fav.getType() == Constant.TYPE_DRUG) {
                    mSqlLiteDbHelper.updateItemDrugUnFav(fav.getIdItem());
                }
                if (fav.getType() == Constant.TYPE_HOSPITAL) {
                    mSqlLiteDbHelper.updateItemHospitalUnFav(fav.getIdItem());
                }
                if (fav.getType() == Constant.TYPE_PROVICE) {
                    mSqlLiteDbHelper.updateItemProviceUnFav(fav.getIdItem());
                }
                mFavs.remove(position);
                notifyDataSetChanged();
            }
        });

        holder.mRlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.itemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFavs.size();
    }

    public interface onItemFavClickListener {
        void itemClick(int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImgIcon;
        public TextView mTvTitle;
        public ImageView mImgDelete;
        public SwipeLayout mSwipeLayout;
        RelativeLayout mRlItem;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            mImgIcon = (ImageView) itemView.findViewById(R.id.imgIcon);
            mImgDelete = (ImageView) itemView.findViewById(R.id.imgDel);
            mSwipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            mRlItem = (RelativeLayout) itemView.findViewById(R.id.rl_item_content);
        }
    }
}
