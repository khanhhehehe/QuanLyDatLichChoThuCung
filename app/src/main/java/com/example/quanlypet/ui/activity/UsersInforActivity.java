package com.example.quanlypet.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlypet.R;
import com.example.quanlypet.database.AdminDB;
import com.example.quanlypet.database.UsersDB;
import com.example.quanlypet.model.AdminObj;
import com.example.quanlypet.model.UsersObj;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class UsersInforActivity extends AppCompatActivity {
    private Toolbar idTollBar;
    private TextInputEditText edUsername;
    private TextInputEditText edFullnameUser;
    private TextInputEditText edEmailUsers;
    private TextInputEditText edPhoneUsers;
    private TextInputEditText edGenderUsers;
    private TextInputLayout TIPPhone;
    private TextInputLayout TIPGender;
    private Button btnUpdateUser;
    private TextView tvThongbao;
    private Button btnCancelUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infor_users);

        SharedPreferences sharedPreferences = getSharedPreferences("Users_info",MODE_PRIVATE);
        String username = sharedPreferences.getString("Username","");

        idTollBar = findViewById(R.id.id_tollBar);
        edUsername = findViewById(R.id.ed_username);
        edFullnameUser = findViewById(R.id.ed_fullnameUser);
        edEmailUsers = findViewById(R.id.ed_emailUsers);
        edPhoneUsers = findViewById(R.id.ed_phoneUsers);
        edGenderUsers = findViewById(R.id.ed_genderUsers);
        TIPPhone = findViewById(R.id.TIP_phone);
        TIPGender = findViewById(R.id.TIP_gender);
        btnUpdateUser = findViewById(R.id.btn_updateUser);
        tvThongbao = findViewById(R.id.tv_thongbao);
        btnCancelUser = findViewById(R.id.btn_cancelUser);

        tvThongbao.setVisibility(View.INVISIBLE);

        setSupportActionBar(idTollBar);
        getSupportActionBar().setTitle("Personal Information");
        idTollBar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (username.equals("Admin")){
            AdminObj adminObj = AdminDB.getInstance(this).Dao().getIdAdmin(username);
            edUsername.setText(username);
            edFullnameUser.setText(adminObj.getFull_name());
            TIPGender.setVisibility(View.GONE);
            edEmailUsers.setText(adminObj.getEmail());
            TIPPhone.setVisibility(View.GONE);

            btnUpdateUser.setOnClickListener(v->{
                String usernamenew = edUsername.getText().toString();
                String fullname = edFullnameUser.getText().toString().trim();
                String email = edEmailUsers.getText().toString().trim();
                if (usernamenew.isEmpty()||fullname.isEmpty()||email.isEmpty()){
                    tvThongbao.setVisibility(View.VISIBLE);
                } else {
                    adminObj.setImport_name(usernamenew);
                    adminObj.setFull_name(fullname);
                    adminObj.setEmail(email);
                    AdminDB.getInstance(this).Dao().edit(adminObj);
                    tvThongbao.setText("Update successfully");
                    Toast.makeText(this, "Update successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
            btnCancelUser.setOnClickListener(view -> {
                finish();
            });
        }else {
            UsersObj usersObj = UsersDB.getInstance(this).Dao().getIdUsers(username);
            edUsername.setText(username);
            edFullnameUser.setText(usersObj.getFull_name());
            edPhoneUsers.setText(usersObj.getPhone());
            edEmailUsers.setText(usersObj.getEmail());
            if (usersObj.getGender() == 0){
                edGenderUsers.setText("Male");
            } else {
                edGenderUsers.setText("Female");
            }

            btnUpdateUser.setOnClickListener(v->{
                String usernamenew = edUsername.getText().toString();
                String fullname = edFullnameUser.getText().toString().trim();
                String email = edEmailUsers.getText().toString().trim();
                String phone = edPhoneUsers.getText().toString().trim();
                int gender = edGenderUsers.getText().toString().trim().equals("Male")?0:1;
                if (usernamenew.isEmpty()||fullname.isEmpty()||email.isEmpty()||phone.isEmpty()){
                    tvThongbao.setVisibility(View.VISIBLE);
                } else {
                    usersObj.setImport_name(usernamenew);
                    usersObj.setFull_name(fullname);
                    usersObj.setEmail(email);
                    usersObj.setPhone(phone);
                    usersObj.setGender(gender);
                    UsersDB.getInstance(this).Dao().edit(usersObj);
                    tvThongbao.setText("Update successfully");
                    Toast.makeText(this, "Update successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
            btnCancelUser.setOnClickListener(view -> {
                finish();
            });
        }
    }
}