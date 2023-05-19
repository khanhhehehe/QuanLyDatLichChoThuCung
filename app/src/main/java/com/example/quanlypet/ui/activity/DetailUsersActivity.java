package com.example.quanlypet.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlypet.R;
import com.example.quanlypet.adapter.ad_use.List_user_Adapter;
import com.example.quanlypet.database.UsersDB;
import com.example.quanlypet.model.UsersObj;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class DetailUsersActivity extends AppCompatActivity {
    private Toolbar idTollBar;
    private TextInputLayout TIPImportName;
    private TextInputEditText TIEDImportName;
    private TextInputLayout TIPPassword;
    private TextInputEditText TIEDPassword;
    private TextInputLayout TIPNameUser;
    private TextInputEditText TIEDNameUser;
    private TextInputLayout TIPEmail;
    private TextInputEditText TIEDEmail;
    private TextInputLayout TIPPhoneNumber;
    private TextInputEditText TIEDPhoneNumber;
    private TextInputLayout TIPGender;
    private TextInputEditText TIEDGender;
    private Button btnUpdate;
    private UsersObj obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_users);
        findID();
        setSupportActionBar(idTollBar);
        getSupportActionBar().setTitle("Personal Information");
        idTollBar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        obj = (UsersObj) getIntent().getExtras().get("object_user");
        TIEDImportName.setEnabled(false);
        TIEDNameUser.setEnabled(false);
        TIEDEmail.setEnabled(false);
        TIEDPhoneNumber.setEnabled(false);
        TIEDGender.setEnabled(false);

        TIEDPassword.setText(obj.getPassword());
        TIEDNameUser.setText(obj.getFull_name());
        TIEDImportName.setText(obj.getImport_name());
        TIEDEmail.setText(obj.getEmail());
        TIEDPhoneNumber.setText(obj.getPhone());
        if (obj.getGender() == 0) {
            TIEDGender.setText("Nam");
        } else {
            TIEDGender.setText("Nữ");
        }
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser();
            }
        });
    }

    private void updateUser() {
        obj.setPassword(TIEDPassword.getText().toString());
        UsersDB.getInstance(this).Dao().edit(obj);
        Toast.makeText(this, "Cập nhập thông tin người dùng thành công", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void findID() {
        idTollBar = (Toolbar) findViewById(R.id.id_tollBar);
        TIPImportName = (TextInputLayout) findViewById(R.id.TIP_ImportName);
        TIEDImportName = (TextInputEditText) findViewById(R.id.TIED_ImportName);
        TIPPassword = (TextInputLayout) findViewById(R.id.TIP_password);
        TIEDPassword = (TextInputEditText) findViewById(R.id.TIED_password);
        TIPNameUser = (TextInputLayout) findViewById(R.id.TIP_NameUser);
        TIEDNameUser = (TextInputEditText) findViewById(R.id.TIED_NameUser);
        TIPEmail = (TextInputLayout) findViewById(R.id.TIP_Email);
        TIEDEmail = (TextInputEditText) findViewById(R.id.TIED_Email);
        TIPPhoneNumber = (TextInputLayout) findViewById(R.id.TIP_PhoneNumber);
        TIEDPhoneNumber = (TextInputEditText) findViewById(R.id.TIED_PhoneNumber);
        TIPGender = (TextInputLayout) findViewById(R.id.TIP_Gender);
        TIEDGender = (TextInputEditText) findViewById(R.id.TIED_Gender);
        btnUpdate = (Button) findViewById(R.id.btn_update);

    }


    @Override
    protected void onResume() {
        super.onResume();


    }
}