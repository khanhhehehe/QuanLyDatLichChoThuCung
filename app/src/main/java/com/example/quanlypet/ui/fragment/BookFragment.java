package com.example.quanlypet.ui.fragment;


import android.graphics.Bitmap;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.quanlypet.R;
import com.example.quanlypet.adapter.Spinner.SpinnerAnimal;
import com.example.quanlypet.adapter.Spinner.SpinnerDoctor;
import com.example.quanlypet.adapter.booking.ViewPager2_Booking_Adapter;
import com.example.quanlypet.adapter.booking.bookingAdapter;
import com.example.quanlypet.adapter.booking.booking_admin_Adapter;

import com.example.quanlypet.model.AnimalObj;
import com.example.quanlypet.model.BookObj;
import com.example.quanlypet.model.DoctorObj;
import com.example.quanlypet.model.UsersObj;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class BookFragment extends Fragment {

    private TabLayout tablayout;
    private TabItem tab1;
    private TabItem tab2;
    private TabItem tab3;
    private TabItem tab4;
    private ViewPager2 viewpagerTablayout;


    public BookFragment() {
    }

    public static BookFragment newInstance() {
        BookFragment fragment = new BookFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_book, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tablayout = (TabLayout) view.findViewById(R.id.tablayout);
        tab1 = (TabItem) view.findViewById(R.id.tab1);
        tab2 = (TabItem) view.findViewById(R.id.tab2);
        tab3 = (TabItem) view.findViewById(R.id.tab3);
        tab4 = (TabItem) view.findViewById(R.id.tab4);
        viewpagerTablayout = (ViewPager2) view.findViewById(R.id.viewpager_tablayout);
        ViewPager2_Booking_Adapter viewPager2_booking_adapter = new ViewPager2_Booking_Adapter(getActivity());
        viewpagerTablayout.setAdapter(viewPager2_booking_adapter);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tablayout, viewpagerTablayout, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("Lịch Chờ");
                        break;
                    case 1:
                        tab.setText("Lịch Đã Xác Nhận");
                        break;
                    case 2:
                        tab.setText("Lịch Đã Hoàn Thành");
                        break;
                    case 3:
                        tab.setText("Lịch Đã Hủy ");
                        break;
                }
            }
        });
        tabLayoutMediator.attach();
    }
}
