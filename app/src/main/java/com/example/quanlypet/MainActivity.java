package com.example.quanlypet;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;


import com.example.quanlypet.adapter.viewpager2.ViewPager2Adapter;

import com.example.quanlypet.effect.DepthPageTransformer;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.viewpager2.widget.ViewPager2;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager2;
    private BottomNavigationView bottomNavigationView;
    private Toolbar Tbr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences("user_file", MODE_PRIVATE);
        String username = sharedPreferences.getString("Username", "");

        viewPager2 = findViewById(R.id.view_pager2);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        Tbr = findViewById(R.id.id_tollBar);
        setSupportActionBar(Tbr);
        bottomNavigationView.setItemIconTintList(null);
        ViewPager2Adapter adapter = new ViewPager2Adapter(this);
        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager2.setAdapter(adapter);
        viewPager2.setUserInputEnabled(false);
        Tbr.setNavigationIcon(R.drawable.home_item);
        Tbr.setTitle("Home");
        Tbr.setTitleTextColor(Color.WHITE);
        if (username.equals("Admin")) {
            bottomNavigationView.getMenu().findItem(R.id.docter).setVisible(true);
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        viewPager2.setCurrentItem(0);
                        Tbr.setTitle("Home");
                        Tbr.setTitleTextColor(Color.WHITE);
                        Tbr.setNavigationIcon(R.drawable.home_item);
                        viewPager2.setPageTransformer(new DepthPageTransformer());
                        break;
                    case R.id.docter:
                        viewPager2.setCurrentItem(1);
                        Tbr.setTitle("Bác Sĩ");
                        Tbr.setTitleTextColor(Color.WHITE);
                        Tbr.setNavigationIcon(R.drawable.doctor_item);
                        viewPager2.setPageTransformer(new DepthPageTransformer());
                        break;
                    case R.id.book:
                        viewPager2.setCurrentItem(2);
                        Tbr.setTitle("Đặt Lịch");
                        Tbr.setTitleTextColor(Color.WHITE);
                        Tbr.setNavigationIcon(R.drawable.booking_item);
                        viewPager2.setPageTransformer(new DepthPageTransformer());
                        break;
                    case R.id.bill:
                        viewPager2.setCurrentItem(3);
                        Tbr.setTitle("Hóa Đơn");
                        Tbr.setTitleTextColor(Color.WHITE);
                        Tbr.setNavigationIcon(R.drawable.bill_item);
                        viewPager2.setPageTransformer(new DepthPageTransformer());
                        break;
                    case R.id.account:
                        viewPager2.setCurrentItem(4);
                        Tbr.setTitle("Tài Khoản");
                        Tbr.setTitleTextColor(Color.WHITE);
                        Tbr.setNavigationIcon(R.drawable.account_item);
                        viewPager2.setPageTransformer(new DepthPageTransformer());
                        break;
                }
                return true;
            }
        });
    }
}


