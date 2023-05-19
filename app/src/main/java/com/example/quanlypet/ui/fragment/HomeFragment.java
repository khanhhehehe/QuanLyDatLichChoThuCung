package com.example.quanlypet.ui.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.quanlypet.R;
import com.example.quanlypet.adapter.viewpager2.SlideAdapterHome;

import com.example.quanlypet.adapter.booking.bookingAdapter;
import com.example.quanlypet.adapter.booking.booking_admin_Adapter;

import com.example.quanlypet.database.BillDB;
import com.example.quanlypet.database.BookDB;
import com.example.quanlypet.database.UsersDB;
import com.example.quanlypet.model.BookObj;
import com.example.quanlypet.model.Photo;
import com.example.quanlypet.model.UsersObj;
import com.example.quanlypet.ui.activity.AddBookingActivity;
import com.example.quanlypet.ui.activity.ListDoctorActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class HomeFragment extends Fragment {
    private LinearLayout linerBooking;
    private LinearLayout linerAmbulance;
    private LinearLayout linerMess;
    private ViewPager vpr;
    private CircleIndicator circleIndicator;
    private SlideAdapterHome slideAdapter;
    private List<Photo> photoList;
    private Timer timer;
    private ImageView imgAddAnimal;
    private ImageView imgMap;
    private BarChart bcThongketuan;
    private Animation animation;
    RecyclerView id_recyNear;
    List<BookObj> list;
    private String user;
    bookingAdapter adapter;
    UsersObj usersObj = new UsersObj();
    TextView titleNear;
    booking_admin_Adapter adapter2;
    List<BookObj> list2 = new ArrayList<>();

    private BarChart barChart;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linerBooking = view.findViewById(R.id.liner_booking);
        linerAmbulance = view.findViewById(R.id.liner_ambulance);
        linerMess = view.findViewById(R.id.liner_mess);
        animation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_up);

        linerMess.setAnimation(animation);
        linerBooking.setAnimation(animation);
        linerAmbulance.setAnimation(animation);
        titleNear = view.findViewById(R.id.titleNear);
        list = new ArrayList<>();
        id_recyNear = view.findViewById(R.id.recy_bookingNear);
        adapter = new bookingAdapter(new bookingAdapter.Callback() {
            @Override
            public void update(BookObj bookObj, int index) {
            }
        }, getActivity());
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_file", Context.MODE_PRIVATE);
        user = sharedPreferences.getString("Username", "");
        if (user.equalsIgnoreCase("Admin")) {
            titleNear.setText("Lịch Đang Chờ Gần Đây");
            list2 = BookDB.getInstance(getActivity()).Dao().getStatus4(1);
            adapter2 = new booking_admin_Adapter(list2, getActivity(), new booking_admin_Adapter.Callback() {
                @Override
                public void updateAdmin(BookObj bookObj, int index) {
                }
            });
            adapter2.setDATA(list2);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            id_recyNear.setAdapter(adapter2);
            id_recyNear.setLayoutManager(linearLayoutManager);
        } else {
            usersObj = UsersDB.getInstance(getActivity()).Dao().getIdUsers(user);
            int id = usersObj.getId();
            list = BookDB.getInstance(getActivity()).Dao().getStatus3(4, id);
            adapter = new bookingAdapter(new bookingAdapter.Callback() {
                @Override
                public void update(BookObj bookObj, int index) {
                }
            }, getActivity());
            adapter.setDATA(list);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            id_recyNear.setAdapter(adapter);
            id_recyNear.setLayoutManager(linearLayoutManager);
        }
        if (user.equals("Admin")){
            linerBooking.setVisibility(View.GONE);
            linerAmbulance.setVisibility(View.GONE);
            linerMess.setVisibility(View.GONE);
        }
        linerBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AddBookingActivity.class);
                startActivity(i);
            }
        });
        linerMess.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Uri uri = Uri.parse("http://m.me/100088046954126");
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }
        });
        linerAmbulance.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), ListDoctorActivity.class));
        });
        vpr = (ViewPager) view.findViewById(R.id.vpr);
        circleIndicator = (CircleIndicator) view.findViewById(R.id.circle_indicator);
        barChart = (BarChart) view.findViewById(R.id.barChart);
        photoList = getListPhoto();
        slideAdapter = new SlideAdapterHome(getContext(), photoList);
        vpr.setAdapter(slideAdapter);
        circleIndicator.setViewPager(vpr);
        slideAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
        autoSlide();
        ArrayList<BarEntry> visitor = new ArrayList<>();

        float dt1 = BillDB.getInstance(getActivity()).Dao().getPriceDT("2022-01-01","2022-01-31");
        float dt2 = BillDB.getInstance(getActivity()).Dao().getPriceDT("2022-02-01","2022-02-29");
        float dt3 = BillDB.getInstance(getActivity()).Dao().getPriceDT("2022-03-01","2022-03-31");
        float dt4 = BillDB.getInstance(getActivity()).Dao().getPriceDT("2022-04-01","2022-04-30");
        float dt5 = BillDB.getInstance(getActivity()).Dao().getPriceDT("2022-05-01","2022-05-31");
        float dt6 = BillDB.getInstance(getActivity()).Dao().getPriceDT("2022-06-01","2022-06-30");
        float dt7 = BillDB.getInstance(getActivity()).Dao().getPriceDT("2022-07-01","2022-07-31");
        float dt8 = BillDB.getInstance(getActivity()).Dao().getPriceDT("2022-08-01","2022-08-31");
        float dt9 = BillDB.getInstance(getActivity()).Dao().getPriceDT("2022-09-01","2022-09-30");
        float dt10 = BillDB.getInstance(getActivity()).Dao().getPriceDT("2022-10-01","2022-10-31");
        float dt11 = BillDB.getInstance(getActivity()).Dao().getPriceDT("2022-11-01","2022-11-30");
        float dt12 = BillDB.getInstance(getActivity()).Dao().getPriceDT("2022-12-01","2022-12-31");

        visitor.add(new BarEntry(1,dt1));
        visitor.add(new BarEntry(2,dt2));
        visitor.add(new BarEntry(3,dt3));
        visitor.add(new BarEntry(4,dt4));
        visitor.add(new BarEntry(5,dt5));
        visitor.add(new BarEntry(6,dt6));
        visitor.add(new BarEntry(7,dt7));
        visitor.add(new BarEntry(8,dt8));
        visitor.add(new BarEntry(9,dt9));
        visitor.add(new BarEntry(10,dt10));
        visitor.add(new BarEntry(11,dt11));
        visitor.add(new BarEntry(12,dt12));

        BarDataSet barDataSet = new BarDataSet(visitor,"Thống kê doanh thu theo tháng");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(14f);
        BarData barData = new BarData( barDataSet);
        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("");
        barChart.getDescription().setTextSize(10f);
        barChart.animateY(2000);

        XAxis xAxis = barChart.getXAxis();

        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                //CHO NAY DUNG CO XOA CUA TAO
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_thongketuan);
                Window window = dialog.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                if (dialog!= null && dialog.getWindow()!= null){
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    bcThongketuan = (BarChart) dialog.findViewById(R.id.bc_thongketuan);
                    ArrayList<BarEntry> visitor = new ArrayList<>();

                    float dtt1 = BillDB.getInstance(getActivity()).Dao().getPriceTuan("01","07");
                    float dtt2 = BillDB.getInstance(getActivity()).Dao().getPriceTuan("08","15");
                    float dtt3 = BillDB.getInstance(getActivity()).Dao().getPriceTuan("16","23");
                    float dtt4 = BillDB.getInstance(getActivity()).Dao().getPriceTuan("24","31");

                    visitor.add(new BarEntry(1,dtt1));
                    visitor.add(new BarEntry(2,dtt2));
                    visitor.add(new BarEntry(3,dtt3));
                    visitor.add(new BarEntry(4,dtt4));

                    BarDataSet barDataSet = new BarDataSet(visitor,"Thống kê doanh thu theo tuần");
                    barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                    barDataSet.setValueTextColor(Color.BLACK);
                    barDataSet.setValueTextSize(14f);
                    BarData barData = new BarData( barDataSet);
                    bcThongketuan.setFitBars(true);
                    bcThongketuan.setData(barData);
                    bcThongketuan.invalidate();
                    bcThongketuan.getDescription().setText("Thống kê doanh thu theo tuần");
                    bcThongketuan.getDescription().setTextSize(20f);
                    bcThongketuan.animateY(5000);
                    dialog.show();
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });
        if (user.equals("Admin")){
            barChart.setVisibility(View.VISIBLE);
        } else {
            barChart.setVisibility(View.GONE);
        }
    }
    private List<Photo> getListPhoto() {
        List<Photo> list = new ArrayList<>();
        list.add(new Photo(R.drawable.one));
        list.add(new Photo(R.drawable.two));
        list.add(new Photo(R.drawable.three));
        list.add(new Photo(R.drawable.dichvu));
        return list;
    }
    private void autoSlide() {
        if (photoList == null || photoList.isEmpty() || vpr == null) {
            return;
        }
        if (timer == null) {
            timer = new Timer();
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int curentItem = vpr.getCurrentItem();
                        int toltalItem = photoList.size() - 1;
                        if (curentItem < toltalItem) {
                            curentItem++;
                            vpr.setCurrentItem(curentItem);
                        } else {
                            vpr.setCurrentItem(0);
                        }
                    }
                });
            }
        }, 5000, 6000);
    }
    @Override
    public void onResume() {
        super.onResume();
        LoadData();
    }
    public void LoadData(){
        if (user.equalsIgnoreCase("Admin")) {
            list2 = BookDB.getInstance(getActivity()).Dao().getStatus4(1);
            adapter2.setDATA(list2);
        } else {
            usersObj = UsersDB.getInstance(getActivity()).Dao().getIdUsers(user);
            int id = usersObj.getId();
            list = BookDB.getInstance(getActivity()).Dao().getStatus3(4, id);
            adapter.setDATA(list);
        }
    }
}
