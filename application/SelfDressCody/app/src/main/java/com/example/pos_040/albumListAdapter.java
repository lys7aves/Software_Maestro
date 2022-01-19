package com.example.pos_040;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class albumListAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<allele> albumList;

    public albumListAdapter(Context context, int layout, ArrayList<allele> albumList) {
        this.context = context;
        this.layout = layout;
        this.albumList = albumList;
    }

    @Override
    public int getCount() {
        return albumList.size();
    }

    @Override
    public Object getItem(int position) {
        return albumList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        ImageView imageView;
    }

    GradientDrawable drawable;

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View row = view;
        ViewHolder holder = new ViewHolder();

        if(row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.imageView = (ImageView) row.findViewById(R.id.imgDog);
           // drawable = (GradientDrawable) context.getDrawable(R.drawable.background_rounding);
          //  holder.imageView.setBackground(drawable);
            holder.imageView.setClipToOutline(true);
            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }

        allele allele = albumList.get(position);

        Glide.with(context).load(allele.getImage()).into(holder.imageView);


        return row;
    }
}
