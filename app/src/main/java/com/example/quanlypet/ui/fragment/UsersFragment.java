package com.example.quanlypet.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.quanlypet.R;
import com.example.quanlypet.database.AdminDB;
import com.example.quanlypet.database.UsersDB;
import com.example.quanlypet.model.AdminObj;
import com.example.quanlypet.model.UsersObj;
import com.example.quanlypet.ui.activity.AnimalActivity;
import com.example.quanlypet.ui.activity.List_User_Activity;
import com.example.quanlypet.ui.activity.UsersInforActivity;
import com.example.quanlypet.ui.welcome.ChangePasswordActivity;
import com.example.quanlypet.ui.welcome.LoginActivity;
import com.example.quanlypet.ui.welcome.WelcomeActivity;

public class UsersFragment extends Fragment {
    private LinearLayout lnInforAccount;
    private LinearLayout lnAnimalManager;
    private LinearLayout lnUserManager;
    private LinearLayout lnChangePass;
    private LinearLayout lnLogOut;
    private TextView usersName;
    private Animation animation;
    public UsersFragment() {
    }

    public static UsersFragment newInstance() {
        UsersFragment fragment = new UsersFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_users, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        usersName = view.findViewById(R.id.usersName);
        lnInforAccount = view.findViewById(R.id.ln_inforAccount);
        lnAnimalManager = view.findViewById(R.id.ln_animalManager);
        lnUserManager = view.findViewById(R.id.ln_userManager);
        lnChangePass = view.findViewById(R.id.ln_changePass);
        lnLogOut = view.findViewById(R.id.ln_logOut);
        animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.slide_down);
        SharedPreferences preferences = requireActivity().getSharedPreferences("Users_info", Context.MODE_PRIVATE);
        String username = preferences.getString("Username", "");
        if (username.equals("Admin")) {
            lnUserManager.setVisibility(View.VISIBLE);
            AdminObj adminObj = AdminDB.getInstance(getContext()).Dao().getIdAdmin(username);
            usersName.setText(adminObj.getFull_name());
        } else {
            lnAnimalManager.setVisibility(View.VISIBLE);
            UsersObj usersObj = UsersDB.getInstance(getContext()).Dao().getIdUsers(username);
            usersName.setText(usersObj.getFull_name());
        }

        lnAnimalManager.setOnClickListener(view1 -> {
            startActivity(new Intent(getContext(), AnimalActivity.class));
        });
        lnInforAccount.setOnClickListener(view1 -> {
            startActivity(new Intent(getActivity(), UsersInforActivity.class));
        });
        lnUserManager.setOnClickListener(view1 -> {
            startActivity(new Intent(getActivity(), List_User_Activity.class));
        });
        lnChangePass.setOnClickListener(view1 -> {
            startActivity(new Intent(getContext(), ChangePasswordActivity.class));
        });
        lnLogOut.setOnClickListener(view1 -> {
            startActivity(new Intent(getContext(), LoginActivity.class));
            getActivity().finish();
        });
        lnLogOut.setAnimation(animation);
        lnChangePass.setAnimation(animation);
        lnUserManager.setAnimation(animation);
        lnInforAccount.setAnimation(animation);
        lnAnimalManager.setAnimation(animation);
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
        notifyAll();
    }

    public void replaceFragmet(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.ln_inforAccount, fragment);
        transaction.commit();
    }
}