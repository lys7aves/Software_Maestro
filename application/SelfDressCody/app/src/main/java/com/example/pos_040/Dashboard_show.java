package com.example.pos_040;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class Dashboard_show extends AppCompatActivity {

    cardAdapter2 cardviewAdapter;
    GridView viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showdetail);

        viewPager = findViewById(R.id.viewPager);

        final ArrayList<cardModel> ailist_a = (ArrayList<cardModel>)getIntent().getSerializableExtra("ailist_a");

        cardviewAdapter = new cardAdapter2(this, R.layout.card_item, ailist_a);
        viewPager.setAdapter(cardviewAdapter);

        viewPager.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final BottomSheetDialog dialog = new BottomSheetDialog(Dashboard_show.this);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.dashboard_detail);

                final ImageView image_detail1 = (ImageView) dialog.findViewById(R.id.dash_detail1);
                final ImageView image_detail2 = (ImageView) dialog.findViewById(R.id.dash_detail2);
                final TextView text_coady = (TextView) dialog.findViewById(R.id.text_coady);
                dialog.show();

                text_coady.setText(ailist_a.get(position).getDescrip());
                Glide.with(view).load(ailist_a.get(position).getImage()).into(image_detail1);
                Glide.with(view).load(ailist_a.get(position).getImage2()).into(image_detail2);

            }
        });
    }
}
