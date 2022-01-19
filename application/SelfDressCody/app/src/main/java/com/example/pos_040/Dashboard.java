package com.example.pos_040;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.pos_040.Retrofit.APIClient;
import com.example.pos_040.Retrofit.ApiInterface;
import com.example.pos_040.Retrofit.Example;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


// 왼쪽 탭
public class Dashboard extends AppCompatActivity {
    ArrayList<cardModel> cardmodels;
//    ArrayList<allele> dblist;
//    ArrayList<allele> dblist2;
//    GridView aigridview;
//    cardAdapter cardAdapter = null;
    //ArrayList<cardModel> ailist_a, ailist_b, ailist_c, ailist_d;
    //public ArrayList<cardModel> ailist_random;
    private static int[][] arr = new int[13][13];
    private static boolean[] visited;

    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    double lat, longi;
    TextView temp, tempdes;
    ImageView imageIcon;

    String temp_weather;
    public int search;

    public static Context context_dashboard;

    public static SQLiteHelper sqLiteHelper;
     TextView dash_descrip;

    ArrayList<allele> dblist = new ArrayList<>();
    ArrayList<allele> dblist2 = new ArrayList<>();
    ArrayList<allele> dblist3 = new ArrayList<>();
    ArrayList<cardModel> ailist_a = new ArrayList<>();
    ArrayList<cardModel> ailist_b = new ArrayList<>();
    ArrayList<cardModel> ailist_c = new ArrayList<>();
    ArrayList<cardModel> ailist_d = new ArrayList<>();
    ArrayList<cardModel> ailist_random = new ArrayList<>();
    ImageView imageView_ava;
    ImageView imageView_up;
    ImageView imageView_down;
    ImageView imageView_out;
    ImageView main_up;
    ImageView main_down;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.dashboard);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuitem) {
                switch (menuitem.getItemId()) {
                    case R.id.dashboard: {
                        return true;
                    }

                    case R.id.home: {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    }

                    case R.id.about: {
                        startActivity(new Intent(getApplicationContext(), About.class));
                        overridePendingTransition(0, 0);
                        return true;
                    }

                }
                return  false;
            }
        });

        // sqlite
        // memo : 1(상의), 2(하의), image : 이미지 url, color : 이미지의 컬러, sort : 계절, feel : ai를 통해 나온 값, hv : h(1~12),v(0~4),무채/색(0,1)
        sqLiteHelper = new SQLiteHelper(this, "AlbumDB.sqlite", null, 1);
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS FOOD(Id INTEGER PRIMARY KEY AUTOINCREMENT, memo INTEGER, image VARCHAR, color INTEGER, sort INTEGER, feel INTEGER, hv INTEGER)");

    //    aigridview = (GridView) findViewById(R.id.aigridView);
        cardmodels = new ArrayList<>();
        // cardAdapter = new cardAdapter(this, R.layout.card_item, cardmodels);
//        dash_descrip = (TextView) findViewById(R.id.dash_descrip);
        imageView_ava = (ImageView) findViewById(R.id.mainava);
        imageView_up = (ImageView) findViewById(R.id.imgUp_ran);
        imageView_down = (ImageView) findViewById(R.id.imgDown_ran);
        imageView_out = (ImageView) findViewById(R.id.imgOut_ran);
        main_up = (ImageView) findViewById(R.id.mainup);
        main_down = (ImageView) findViewById(R.id.maindown);
        imageView_ava.bringToFront();

        // 배경화면 낮과 밤
//        RelativeLayout cons_dash = (RelativeLayout) findViewById(R.id.rellayout);
//        Calendar c = Calendar.getInstance();
//        int timeofday = c.get(Calendar.HOUR_OF_DAY);
//        if(timeofday >= 6 && timeofday <=18) {
//            cons_dash.setBackgroundResource(R.drawable.dashboard_main_am);
//        } else {
//            cons_dash.setBackgroundResource(R.drawable.dashboard_main_pm);
//
//        }

       // aigridview.setAdapter(cardAdapter);

        // 날씨
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        temp = (TextView) findViewById(R.id.temp);
        tempdes = (TextView) findViewById(R.id.tempdes);


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            OnGPS();
        } else {
            getLocation();
            getWeatherData(lat, longi);
        }

        context_dashboard = this;

        // showdetail 로 이동
        Button btnshowdetail = (Button) findViewById(R.id.btnshow);
        btnshowdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Dashboard_show.class);
                intent.putExtra("ailist_a", ailist_a);
                startActivity(intent);
            }
        });

    }

    private String getURLForResource(int resId) {
        return Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + resId).toString();
    }


    //GPS 및 날씨
    private void getWeatherData(double lat, double lon) {
        ApiInterface apiInterface = APIClient.getRetrofit().create(ApiInterface.class);

        Call<Example> call = apiInterface.getWeatherData(lat, lon);

        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                String str = "http://openweathermap.org/img/wn/"+response.body().getWeatherList().get(0).getIcon()+"@2x.png";
                String str1 =  "http://openweathermap.org/img/wn/01d@2x.png";

                //Glide.with(MainActivity.this).load(str1).into(imageIcon);

               // Log.e("TAG", str);
                //temp.setText(response.body().getWeatherList().get(0).getIcon());
                String time_real = response.body().getMain().getFeels_like();
                temp.setText(response.body().getMain().getTemp()+"°");
                temp_weather = response.body().getWeatherList().get(0).getIcon();
                tempdes.setText("| "+response.body().getWeatherList().get(0).getIcon());
                String weather_um = "";
                if(temp_weather.equals("Drizzle") || temp_weather.equals("Rain")) {
                    weather_um = " # 우산을 챙기세요";
                }
//                String dash_descrip1 = String.valueOf(dash_descrip.getText())+weather_um;
//                dash_descrip.setText(dash_descrip1);

                Log.e("Text==", String.valueOf(temp.getText()));

                Cursor cardcursor = sqLiteHelper.getData("SELECT * FROM FOOD WHERE memo=1");
                dblist.clear();
                while(cardcursor.moveToNext()) {
                    int id = cardcursor.getInt(0);
                    int memo = cardcursor.getInt(1);
                    String image = cardcursor.getString(2);
                    int color = cardcursor.getInt(3);
                    int sort = cardcursor.getInt(4);
                    int feel = cardcursor.getInt(5);
                    int hv = cardcursor.getInt(6);

                    dblist.add(new allele(memo, image, color, sort, feel, hv, id));
                }

                Cursor cardcursor2 = sqLiteHelper.getData("SELECT * FROM FOOD WHERE memo=2");
                dblist2.clear();
                while(cardcursor2.moveToNext()) {
                    int id = cardcursor2.getInt(0);
                    int memo = cardcursor2.getInt(1);
                    String image = cardcursor2.getString(2);
                    int color = cardcursor2.getInt(3);
                    int sort = cardcursor2.getInt(4);
                    int feel = cardcursor2.getInt(5);
                    int hv = cardcursor2.getInt(6);

                    dblist2.add(new allele(memo, image, color, sort, feel, hv, id));
                }

                Cursor cardcursor3 = sqLiteHelper.getData("SELECT * FROM FOOD WHERE memo=3");
                dblist3.clear();
                while(cardcursor3.moveToNext()) {
                    int id = cardcursor3.getInt(0);
                    int memo = cardcursor3.getInt(1);
                    String image = cardcursor3.getString(2);
                    int color = cardcursor3.getInt(3);
                    int sort = cardcursor3.getInt(4);
                    int feel = cardcursor3.getInt(5);
                    int hv = cardcursor3.getInt(6);

                    dblist3.add(new allele(memo, image, color, sort, feel, hv, id));
                }

                if(dblist.size()==0 || dblist2.size()==0 || dblist3.size()==0) {
                    Glide.with(Dashboard.this).load(R.drawable.none).circleCrop().into(imageView_up);
                    Glide.with(Dashboard.this).load(R.drawable.none).into(imageView_down);

                }

                Grade grade = new Grade(dblist, dblist2, dblist3);
                ArrayList<cardModel> output = grade.calculate(time_real);
                if(output!=null) {
                    ailist_a = output;
                }

//                int[] searcharr = new int[12];
//                Arrays.fill(searcharr, 0);
//                search = 0;
//                if(dblist.size() >= dblist2.size()) {
//                    search += 1;
//                } else {
//                    search += 2;
//                }
//
//                if(search==1) {
//                    for(int i=0; i<dblist.size(); i++) {
//                        int j= dblist.get(i).getHv()/100;
//                        searcharr[j]++;
//                    }
//                    for(int m = 0; m<13; m++) {
//
//                    }
//                } else {
//                    for(int i=0; i<dblist2.size(); i++) {
//                        int j= dblist2.get(i).getHv()/100;
//                        searcharr[j]++;
//                    }
//                }
//                search = Math.
//
//
//                Log.e("ailist_a", String.valueOf(ailist_a.size()));


                // 가장 높은 등급에서 랜덤으로 보여주기
                ailist_random.clear();
                Random ran = new Random();
                if(ailist_a.size()!=0) {
                    int random = ran.nextInt(ailist_a.size());
                    ailist_random.add(new cardModel(ailist_a.get(random).getImage(), ailist_a.get(random).getImage2(), ailist_a.get(random).getImage3(), ailist_a.get(random).getId1(), ailist_a.get(random).getId2(), ailist_a.get(random).getId3(), ailist_a.get(random).getColor1(), ailist_a.get(random).getColor2(), ailist_a.get(random).getColor3(), ailist_a.get(random).getDescrip(), 1));
                }

                //Uri resUri = Uri.parse("android.resource://your.package.here/drawable/none");
                String resUri = getURLForResource(R.drawable.none);

                if(ailist_a.size()==0) {
                    ailist_a.add(new cardModel(resUri, resUri, null, 1, 2, 0, 0,0,0, "", 1));
                }


                if (ailist_random.size() != 0) {
                    //   Log.e("TAG-------","====");
                    RequestOptions option1 = new RequestOptions().circleCrop();
                    Glide.with(Dashboard.this).load(ailist_random.get(0).getImage()).apply(option1).into(imageView_up);
                    Glide.with(Dashboard.this).load(ailist_random.get(0).getImage2()).apply(option1).into(imageView_down);
                    Glide.with(Dashboard.this).load(ailist_random.get(0).getImage3()).apply(option1).into(imageView_out);


//                    dash_descrip.setText(ailist_random.get(0).getDescrip());

                    main_up.setColorFilter(ailist_random.get(0).getColor1());
                    main_down.setColorFilter(ailist_random.get(0).getColor2());
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });
    }
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(Dashboard.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Dashboard.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location LocationGps = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location LocationNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location LocationPassive = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if(LocationGps!= null) {
                lat = LocationGps.getLatitude();
                longi = LocationGps.getLongitude();

            } else if(LocationNetwork!= null) {
                lat = LocationNetwork.getLatitude();
                longi = LocationNetwork.getLongitude();

            } else if(LocationPassive!= null) {
                lat = LocationPassive.getLatitude();
                longi = LocationPassive.getLongitude();

            } else {
                lat = 37.57;
                longi = 126.98;
                Toast.makeText(this, "Can't your Location", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
