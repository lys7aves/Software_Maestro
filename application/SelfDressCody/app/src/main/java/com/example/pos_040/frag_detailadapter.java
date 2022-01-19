package com.example.pos_040;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class frag_detailadapter extends RecyclerView.Adapter<frag_detailadapter.ViewHolder> {

    private Context context;
    private int layout;
    private ArrayList<cardModel> dashboardList;

    public frag_detailadapter(Context context, ArrayList<cardModel> dashboardList) {
        this.context = context;
        this.dashboardList = dashboardList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(context).inflate(R.layout.album_detail_detail, parent, false);

        return new ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        cardModel cardModel = dashboardList.get(position);
        Glide.with(context).load(cardModel.getImage2()).into(holder.imageView1);
    }

    @Override
    public int getItemCount() {
        return dashboardList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView1, imageView2, imagegrade;
        ImageView upava, downava;

        ViewHolder(final View itemView) {
            super(itemView);
            imageView1 = (ImageView) itemView.findViewById(R.id.image_detail_match);
        }
    }
}
