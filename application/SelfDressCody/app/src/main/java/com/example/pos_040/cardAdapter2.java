package com.example.pos_040;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class cardAdapter2 extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<cardModel> dashboardList;

    public cardAdapter2(Context context, int layout, ArrayList<cardModel> dashboardList) {
        this.context = context;
        this.layout = layout;
        this.dashboardList = dashboardList;
    }

    @Override
    public int getCount() {
        return dashboardList.size();
    }

    @Override
    public Object getItem(int position) {
        return dashboardList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        ImageView imageView1, imageView2, imageView3, imagegrade;
        ImageView upava, downava;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View row = view;
        ViewHolder holder = new ViewHolder();

        if(row==null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.imageView1 = (ImageView) row.findViewById(R.id.imgUp);
            holder.imageView2 = (ImageView) row.findViewById(R.id.imgDown);
            holder.imageView3 = (ImageView) row.findViewById(R.id.imgOut);
            holder.upava = (ImageView) row.findViewById(R.id.upava);
            holder.downava = (ImageView) row.findViewById(R.id.downava);

            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        cardModel cardModel = dashboardList.get(position);
        Glide.with(context).load(cardModel.getImage()).into(holder.imageView1);
        Glide.with(context).load(cardModel.getImage2()).into(holder.imageView2);
        if(cardModel.getImage3()==null) {
            Glide.with(context).load(R.drawable.none).into(holder.imageView3);
        } else {
            Glide.with(context).load(cardModel.getImage3()).into(holder.imageView3);
        }
        holder.upava.setColorFilter(cardModel.getColor1());
        holder.downava.setColorFilter(cardModel.getColor2());

        return row;
    }
}