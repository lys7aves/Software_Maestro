package com.example.pos_040;

import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Arrays;


public class fragment_tab2 extends Fragment {

    albumListAdapter mAdapter;
    ArrayList<allele> list, list2;
    ImageView imageViewDetail, imageViewMatch;
    ArrayList<cardModel> ailist_a, ailist_b, ailist_c, ailist_random;
    private static int[][] arr = new int[18][18];
    private static boolean[] visited;

    public fragment_tab2() {
        // Required empty public constructor
    }

    public static fragment_tab2 newInstance() {
        fragment_tab2 fragment = new fragment_tab2();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab2, container, false);
        GridView gridView = (GridView) view.findViewById(R.id.gridView);
        list = new ArrayList<>();
        list2 = new ArrayList<>();
        ailist_a = new ArrayList<>();
        ailist_b = new ArrayList<>();
        ailist_c = new ArrayList<>();
        ailist_random = new ArrayList<>();
        mAdapter =  new albumListAdapter(getActivity(), R.layout.album_items,list);
        gridView.setAdapter(mAdapter);

        Cursor cursor = Dashboard.sqLiteHelper.getData("SELECT * FROM FOOD WHERE memo=2");
        list.clear();

        while(cursor.moveToNext()) {
            int id = cursor.getInt(0);
            int memo = cursor.getInt(1);
            String image = cursor.getString(2);
            int color = cursor.getInt(3);
            int sort = cursor.getInt(4);
            int feel = cursor.getInt(5);
            int hv = cursor.getInt(6);

            list.add(new allele(memo, image, color, sort, feel, hv, id));
        }
        mAdapter.notifyDataSetChanged();

        Cursor cardcursor2 = Dashboard.sqLiteHelper.getData("SELECT * FROM FOOD WHERE memo=1");
        list2.clear();
        while(cardcursor2.moveToNext()) {
            int id = cardcursor2.getInt(0);
            int memo = cardcursor2.getInt(1);
            String image = cardcursor2.getString(2);
            int color = cardcursor2.getInt(3);
            int sort = cardcursor2.getInt(4);
            int feel = cardcursor2.getInt(5);
            int hv = cardcursor2.getInt(6);

            list2.add(new allele(memo, image, color, sort, feel, hv, id));
        }

        final String pathURL = getURLForResource(R.drawable.none);
        if(list.size()==0) {
            Log.e("TAG____","sdf");
            list.add(new allele(1, pathURL ,0, 0,0,0, 0));

            gridView.setEnabled(false);
            mAdapter.notifyDataSetChanged();
            Log.e("TAG:SIZE:", pathURL);
        }
        for(int a[]: arr) {
            Arrays.fill(a, 0);
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.album_detail);

                imageViewDetail = (ImageView) dialog.findViewById(R.id.image_detail);
                imageViewMatch = (ImageView) dialog.findViewById(R.id.image_detail_match);
                final TextView text_vivid = (TextView) dialog.findViewById(R.id.text_vivid);
                final ImageView image_close = (ImageView) dialog.findViewById(R.id.image_close);
                final RelativeLayout layout_detail = (RelativeLayout) dialog.findViewById(R.id.layout_detail);
                final Button rel_spr = (Button) dialog.findViewById(R.id.rel_spr);
                final Button rel_sum = (Button) dialog.findViewById(R.id.rel_sum);
                final Button rel_aut = (Button) dialog.findViewById(R.id.rel_aut);
                final Button rel_win = (Button) dialog.findViewById(R.id.rel_win);

                frag_detailadapter detailViewAdapter;
                RecyclerView viewpager = (RecyclerView) dialog.findViewById(R.id.viewPager);
                int width = (int) (getActivity().getResources().getDisplayMetrics().widthPixels*0.5);
                int height = (int) (getActivity().getResources().getDisplayMetrics().heightPixels*1);
                dialog.getWindow().setLayout(width, height);
                dialog.show();

                String tone1 = "톤온톤 코디";
                String tone2 = "톤인톤 코디";

                ailist_a.clear();
                Grade_frag grade_frag = new Grade_frag(list, list2);
                ArrayList<cardModel> output = grade_frag.calcu(position);
                if(output!=null) {
                    ailist_a = output;
                }

//                Glide.with(view).load(ailist_random.get(0).getImage2()).into(imageViewMatch);
                Glide.with(view).load(list.get(position).getImage()).into(imageViewDetail);
                layout_detail.setBackgroundColor(list.get(position).getColor());

                String[] toneon = new String[]{"# Red", "# Red-Orange", "# Orange", "# Yellow-Orange", "# Yellow", "# Yellow-Green", "# Green", "# Blue-Green", "# Blue", "Blue-Violet", "#Violet", "Red-Violet"};
                String[] tonein = new String[]{"# Vivid톤", "# Bright톤", "# Strong톤", "# Deep톤", "# Light톤", "# Soft톤", "# Dull톤", "# Dark톤", "# Pale톤","# LightGray톤","# Gray톤","DarkGray톤"};

                String str="  ";

                int abcsort = list.get(position).getSort();
                String difsort = "";
                if (abcsort / 1000 == 1) {
                    rel_spr.setBackground(getActivity().getDrawable(R.drawable.button_background_dashboard3));
                }
                if ((abcsort % 1000) / 100 == 1) {
                    rel_sum.setBackground(getActivity().getDrawable(R.drawable.button_background_dashboard3));

                }
                if ((abcsort % 100) / 10 == 1) {
                    rel_aut.setBackground(getActivity().getDrawable(R.drawable.button_background_dashboard3));

                }
                if ((abcsort % 10 == 1)) {
                    rel_win.setBackground(getActivity().getDrawable(R.drawable.button_background_dashboard3));

                }

                int abcfeel = list.get(position).getFeel();
                int abchv = list.get(position).getHv();
                Log.e("feelabcabcabc :", String.valueOf(abchv));
                if(abchv%10 == 1) {                               // 색
                    difsort += toneon[abchv/1000-1];
                } else {
                    difsort += "# 무채색";
                }
                if(abcfeel!=12) {
                    difsort += "\n"+tonein[abcfeel-1];
                }

                text_vivid.setText(difsort);

                if(ailist_a.size()!=0) {
                    detailViewAdapter = new frag_detailadapter(getActivity(), ailist_a);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
                    gridLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
                    viewpager.setLayoutManager(gridLayoutManager);
                    viewpager.setAdapter(detailViewAdapter);
                } else {
                    ailist_a.add(new cardModel(pathURL, pathURL, null, 0, 0, 1,1,1,1,"",1));
                    detailViewAdapter = new frag_detailadapter(getActivity(), ailist_a);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
                    gridLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
                    viewpager.setLayoutManager(gridLayoutManager);
                    viewpager.setAdapter(detailViewAdapter);
                }

                image_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                CharSequence[] items = {"Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());

                dialog.setTitle("Choose an action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if(item == 0) {
                            // Delete
                            Cursor c = Dashboard.sqLiteHelper.getData("SELECT id FROM FOOD WHERE memo=2");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while(c.moveToNext()) {
                                arrID.add(c.getInt(0));
                            }
                            showDialogDelete(arrID.get(position));
                        }
                    }
                });
                dialog.show();
                return true;
            }
        });

        return view;
    }

    private void showDialogDelete(final int idDog) {
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(getActivity());

        dialogDelete.setTitle("Warning!");
        dialogDelete.setMessage("Are you sure you want too this delete?");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    Dashboard.sqLiteHelper.deleteData(idDog);
                    Toast.makeText(getActivity(), "Delete successfully!!!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.e("error",e.getMessage());
                }
                updatealbumList();
            }
        });

        dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });
        dialogDelete.show();
    }

    public void updatealbumList() {
        Cursor cursor = Dashboard.sqLiteHelper.getData("SELECT * FROM FOOD WHERE memo=2");
        list.clear();
        while(cursor.moveToNext()) {
            int id = cursor.getInt(0);
            int memo = cursor.getInt(1);
            String image = cursor.getString(2);
            int color = cursor.getInt(3);
            int sort = cursor.getInt(4);
            int feel = cursor.getInt(5);
            int hv = cursor.getInt(6);

            list.add(new allele(memo, image, color, sort, feel, hv, id));
        }
        mAdapter.notifyDataSetChanged();
    }

    private String getURLForResource(int resId) {
        return Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + resId).toString();
    }
}