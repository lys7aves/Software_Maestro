package com.example.pos_040;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;


// main 탭
public class MainActivity extends AppCompatActivity {

    private String path = null;

    fragmentAdapter fragmentAdapter;
    private ViewPager viewPager;

    public static TextView txtUp;
    public static TextView txtDown;

    public static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton btnSet = findViewById(R.id.btnSet);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);

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

        TabLayout tabLayout = findViewById(R.id.tab);
        tabLayout.addTab((tabLayout.newTab().setText("상의")));
        tabLayout.addTab((tabLayout.newTab().setText("하의")));
        tabLayout.addTab((tabLayout.newTab().setText("외투")));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        fragmentAdapter = new fragmentAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                fragmentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        txtUp = (TextView) findViewById(R.id.upcount);
        txtDown = (TextView) findViewById(R.id.downcount);
//        clothcount();

        mContext = this;

        TextView for_txt = (TextView) findViewById(R.id.for_lee);
        for_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ForLee.class);
                startActivity(intent);
            }
        });

        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SetActivity.class);
                startActivityForResult(intent,888);
            }
        });


//        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
//        temp = (TextView) findViewById(R.id.temp);
//        temp_low = (TextView) findViewById(R.id.temp_low);
//        temp_high = (TextView) findViewById(R.id.temp_high);
//        imageIcon = (ImageView) findViewById(R.id.weatherid);
//
//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
//        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//            OnGPS();
//        } else {
//            getLocation();
//            getWeatherData(lat, longi);
//
//        }

    }

    ImageView imageViewDog;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 888) {
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 888);
            }
            else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 888 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            Glide.with(this).load(uri).into(imageViewDog);

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public String getPathFromURI(Uri contentUri) {
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        cursor.moveToNext();
        String path = cursor.getString(cursor.getColumnIndex("_data"));
        cursor.close();;
        return path;
    }

//    public void clothcount() {
//        Cursor cursor = Dashboard.sqLiteHelper.getData("SELECT * FROM FOOD WHERE memo=1");
//        int upcnt = cursor.getCount();
//        txtUp.setText(String.valueOf(upcnt));
//        Cursor cursor2 = Dashboard.sqLiteHelper.getData("SELECT * FROM FOOD WHERE memo=2");
//        int downcnt = cursor2.getCount();
//        txtDown.setText(String.valueOf(downcnt));
//    }

}