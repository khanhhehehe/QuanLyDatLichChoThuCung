package com.example.quanlypet.ui.welcome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.quanlypet.R;
import com.example.quanlypet.adapter.viewpager2.SlideAdapter;
import com.example.quanlypet.database.AdminDB;
import com.example.quanlypet.model.AdminObj;
import com.example.quanlypet.model.Photo;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import me.relex.circleindicator.CircleIndicator;

public class WelcomeActivity extends AppCompatActivity {
    private TextView tvNext;
    private ViewPager vpr;
    private Button btnSignin;
    private Button btnSignup;
    private CircleIndicator circleIndicator;
    private SlideAdapter slideAdapter;
    private List<Photo> photoList;
    private Timer timer;
    private List<Photo> getListPhoto(){
        List<Photo> list = new ArrayList<>();
        list.add(new Photo(R.drawable.users_well));
        list.add(new Photo(R.drawable.doctor_well));
        list.add(new Photo(R.drawable.hotel_well));
        list.add(new Photo(R.drawable.sevices_well));
        return list;
    }
//    private void tapSlide(){
//        if (photoList == null||photoList.isEmpty()||vpr == null){
//            return;
//        }
//        tvNext.setOnClickListener(view -> {
//            int curentItem = vpr.getCurrentItem();
//            int toltalItem = photoList.size() - 1;
//            if (curentItem < toltalItem){
//                curentItem++;
//                vpr.setCurrentItem(curentItem);
//            }else {
//                vpr.setCurrentItem(0);
//            }
//        });
//        tvPrev.setOnClickListener(view -> {
//            int curentItem = vpr.getCurrentItem();
//            int toltalItem = photoList.size()-6;
//            if (curentItem > toltalItem){
//                curentItem--;
//                vpr.setCurrentItem(curentItem);
//            }else {
//                vpr.setCurrentItem(6);
//            }
//        });
//    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null){
            timer.cancel();
            timer = null;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellcome);
        btnSignin = (Button) findViewById(R.id.btn_signin);
        btnSignup = (Button) findViewById(R.id.btn_signup);
        vpr = (ViewPager) findViewById(R.id.vpr);
        circleIndicator = (CircleIndicator) findViewById(R.id.circle_indicator);
        photoList = getListPhoto();
        slideAdapter = new SlideAdapter(this,photoList);
        vpr.setAdapter(slideAdapter);
        circleIndicator.setViewPager(vpr);
        slideAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
        if (AdminDB.getInstance(getApplicationContext()).Dao().getAllData().isEmpty()){
            AdminDB.getInstance(getApplicationContext()).Dao().
                    insert(new AdminObj("Admin","Account_QLPV","qlpvip@gmail.com","petvip"));
        }
        btnSignin.setOnClickListener(view -> {
          startActivity(new Intent(getApplicationContext(),LoginActivity.class));
          finish();
        });
        btnSignup.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(),SignupUsersActivity.class));
            finish();
        });
    }
}