package com.example.pos_040;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class cardAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<cardModel> dashboardList;

    public cardAdapter(Context context, int layout, ArrayList<cardModel> dashboardList) {
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
        ImageView imageView1, imageView2, imagegrade;
        ImageView upava, downava;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View row = view;
        cardAdapter.ViewHolder holder = new cardAdapter.ViewHolder();

        if(row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.imageView1 = (ImageView) row.findViewById(R.id.imgUp);
            holder.imageView2 = (ImageView) row.findViewById(R.id.imgDown);
            //holder.imagegrade = (ImageView) row.findViewById(R.id.imggrade);
            holder.upava = (ImageView) row.findViewById(R.id.upava);
            holder.downava = (ImageView) row.findViewById(R.id.downava);

            row.setTag(holder);
        }
        else {
            holder = (cardAdapter.ViewHolder) row.getTag();
        }

        cardModel cardModel = dashboardList.get(position);
        Glide.with(context).load(cardModel.getImage()).into(holder.imageView1);
        Glide.with(context).load(cardModel.getImage2()).into(holder.imageView2);
        if(cardModel.getStar()==1) {
            Glide.with(context).load(R.drawable.gre).into(holder.imagegrade);
        }else if(cardModel.getStar()==2) {
            Glide.with(context).load(R.drawable.yel).into(holder.imagegrade);
        }else if(cardModel.getStar()==3) {
            Glide.with(context).load(R.drawable.red).into(holder.imagegrade);
        }else {
            Glide.with(context).load(R.drawable.d).into(holder.imagegrade);
        }
        holder.upava.setColorFilter(cardModel.getColor1());
        holder.downava.setColorFilter(cardModel.getColor2());

        return row;
    }
}