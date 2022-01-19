package com.example.pos_040;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class aboutListAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<aboutele> List;

    public aboutListAdapter(Context context, int layout, ArrayList<aboutele> List) {
        this.context = context;
        this.layout = layout;
        this.List = List;
    }


    @Override
    public int getCount() {
        return List.size();
    }

    @Override
    public Object getItem(int position) {
        return List.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView textView;
    }

    GradientDrawable drawable;

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View row = view;
        ViewHolder holder = new ViewHolder();

        if(row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.textView = (TextView) row.findViewById(R.id.textttt);
            holder.imageView = (ImageView) row.findViewById(R.id.image);
            drawable = (GradientDrawable) context.getDrawable(R.drawable.background_rounding);
            holder.imageView.setBackground(drawable);
            holder.imageView.setClipToOutline(true);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        aboutele ele = List.get(position);
        Glide.with(context).load(ele.getImage()).into(holder.imageView);
        holder.textView.setText(ele.getTitle());

        return row;
    }
}
