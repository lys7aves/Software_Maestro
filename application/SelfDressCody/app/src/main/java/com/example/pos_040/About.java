package com.example.pos_040;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

// 오른쪽 탭
public class About extends AppCompatActivity {

    TextView text2;
    EditText text1;
    Button btn;
    aboutListAdapter listAdapter;
    ArrayList<aboutele> list;
    GridView gridView;
    static String[] title = null;
    static String[] link = null;
    static String[] description = null;
    int k = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.about);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuitem) {
                switch (menuitem.getItemId()) {
                    case R.id.dashboard: {
                        startActivity(new Intent(getApplicationContext(), Dashboard.class));
                        overridePendingTransition(0, 0);
                        return true;
                    }

                    case R.id.home: {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    }

                    case R.id.about: {
                        return true;
                    }

                }
                return  false;
            }
        });

        text2 = findViewById(R.id.text2);

        gridView = (GridView) findViewById(R.id.gridView);
        list = new ArrayList<>();
        listAdapter = new aboutListAdapter(About.this, R.layout.about_items, list);
        gridView.setAdapter(listAdapter);

        searchNaver("남자흰색니트");


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final Dialog dialog = new Dialog(About.this);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.about_detail);

                final TextView textView = (TextView) dialog.findViewById(R.id.content1);

                textView.setText(list.get(position).getLink());

                dialog.show();
            }
        });


    }

    public void searchNaver(final String searchObject) {
        final String clientId = "2OOSvzshH2uYJmCCDBr5";
        final String clientSecret = "ipRxaUOPMo";
        final int display = 10;

        new Thread() {
            @Override
            public void run() {
                try {
                    String text = URLEncoder.encode(searchObject, "UTF-8");
                    String apiURL = "https://openapi.naver.com/v1/search/shop?query=" + text + "&display=" + display + "&"; // json 결과
                    // Json 형태로 결과값을 받아옴.
                    URL url = new URL(apiURL);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    con.setRequestProperty("X-Naver-Client-Id", clientId);
                    con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
                    con.connect();

                    int responseCode = con.getResponseCode();


                    BufferedReader br;
                    if(responseCode==200) { // 정상 호출
                        br = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    } else {  // 에러 발생
                        br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                    }

                    StringBuilder searchResult = new StringBuilder();
                    String inputLine;
                    while ((inputLine = br.readLine()) != null) {
                        searchResult.append(inputLine + "\n");

                    }
                    br.close();
                    con.disconnect();

                    String data = searchResult.toString();
                    String[] array;
                    array = data.split("\"");
                    title = new String[display];
                    link = new String[display];
                    description = new String[display];

                    for (int i = 0; i < array.length; i++) {
                        if (array[i].equals("title"))
                            title[k] = array[i+2];
                        if (array[i].equals("link"))
                            link[k] = array[i + 2];
                        if (array[i].equals("image")) {
                            description[k] = array[i + 2];
                            k++;
                        }
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ViewDataAdd();

                        }
                    });

                } catch (Exception e) {
                    Log.e("TAG", "error : " + e);
                }

            }
        }.start();

    }

    public void ViewDataAdd() {
        for(int i=0; i<k; i++) {
            String str = title[i].replace("<b>","");
            String ti = str.replace("</b>","");
            String li = link[i];
            String ima = description[i];
            Log.e("TAG",title[i]+link[i]+description[i]);

            list.add(new aboutele(ti, li, ima));
        }
        listAdapter.notifyDataSetChanged();
    }



}
