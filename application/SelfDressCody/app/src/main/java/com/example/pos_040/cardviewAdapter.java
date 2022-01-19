package com.example.pos_040;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class cardviewAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<cardModel> dashboardList;

    public cardviewAdapter(ArrayList<cardModel> dashboardList, Context context) {
        this.dashboardList = dashboardList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return dashboardList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.card_item, container, false);

        ImageView imageView1, imageView2, imagegrade;
        ImageView upava, downava;

        imageView1 = (ImageView) view.findViewById(R.id.imgUp);
        imageView2 = (ImageView) view.findViewById(R.id.imgDown);
        //imagegrade = (ImageView) view.findViewById(R.id.imggrade);
        upava = (ImageView) view.findViewById(R.id.upava);
        downava = (ImageView) view.findViewById(R.id.downava);

        cardModel cardModel = dashboardList.get(position);
        Glide.with(context).load(cardModel.getImage()).into(imageView1);
        Glide.with(context).load(cardModel.getImage2()).into(imageView2);
//        if(cardModel.getStar()==1) {
//            Glide.with(context).load(R.drawable.gre).into(imagegrade);
//        }else if(cardModel.getStar()==2) {
//            Glide.with(context).load(R.drawable.yel).into(imagegrade);
//        }else if(cardModel.getStar()==3) {
//            Glide.with(context).load(R.drawable.red).into(imagegrade);
//        }else {
//            Glide.with(context).load(R.drawable.d).into(imagegrade);
//        }
        upava.setColorFilter(cardModel.getColor1());
        downava.setColorFilter(cardModel.getColor2());


        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
