package com.example.quanlypet.adapter.booking;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.quanlypet.ui.fragment.BookWaitFragment;
import com.example.quanlypet.ui.fragment.BookCancelFragment;
import com.example.quanlypet.ui.fragment.BookConfirmFragment;
import com.example.quanlypet.ui.fragment.BookDoneFragment;

public class ViewPager2_Booking_Adapter extends FragmentStateAdapter {
    public ViewPager2_Booking_Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment =null;
        switch (position) {
            case 0:
                fragment = BookWaitFragment.newInstance();
                break;
            case 1:
                fragment = BookConfirmFragment.newInstance();
                break;
            case 2:
                fragment = BookDoneFragment.newInstance();
                break;
            case 3:
                fragment = BookCancelFragment.newInstance();
                break;
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
