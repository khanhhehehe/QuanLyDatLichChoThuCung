package com.example.quanlypet.ui.welcome;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlypet.MainActivity;
import com.example.quanlypet.R;
import com.example.quanlypet.database.AdminDB;
import com.example.quanlypet.database.UsersDB;
import com.example.quanlypet.model.AdminObj;
import com.example.quanlypet.model.UsersObj;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText edUsername;
    private TextInputEditText edPassword;
    private CheckBox ckbNhoMK;
    private TextView tvSignup;
    private Button btnLogin;
    private TextView tvResetPass;
    private TextView tvErrors;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edUsername = findViewById(R.id.ed_Username);
        edPassword = findViewById(R.id.ed_Password);
        ckbNhoMK = findViewById(R.id.ckb_nhoMK);
        tvResetPass = findViewById(R.id.tv_reset_pass);
        tvSignup = findViewById(R.id.tv_signup);
        btnLogin = findViewById(R.id.btn_login);
        tvErrors = findViewById(R.id.tv_errors);
        tvErrors.setText("");
        SharedPreferences preferences = getSharedPreferences("user_file", MODE_PRIVATE);
        edUsername.setText(preferences.getString("Username",""));
        edPassword.setText(preferences.getString("Password",""));
        ckbNhoMK.setChecked(preferences.getBoolean("Remember",false));
        tvSignup.setOnClickListener(view -> {
                startActivity(new Intent(getApplicationContext(), SignupUsersActivity.class));
        });
        btnLogin.setOnClickListener(view -> {
            CheckLogin();
            SharedPreferences sharedPreferences = getSharedPreferences("Users_info", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Username",edUsername.getText().toString());
            editor.commit();
        });
    }
    private AdminObj adminObj = new AdminObj();
    public void CheckLogin() {
        String str_user = edUsername.getText().toString().trim();
        String str_pass = edPassword.getText().toString().trim();
        if (str_user.isEmpty()||str_pass.isEmpty()) {
            tvErrors.setTextColor(Color.RED);
            tvErrors.setText("Không được bỏ trống !");
        }else {
            if (AdminDB.getInstance(getApplicationContext()).Dao().checkLogin(str_user,str_pass)>0||
                    UsersDB.getInstance(getApplicationContext()).Dao().checkLogin(str_user,str_pass)>0){
                RemeberUser(str_user,str_pass, ckbNhoMK.isChecked());
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                tvErrors.setTextColor(Color.GREEN);
                tvErrors.setText("Đăng nhập thành công.");
                finish();
            }else {
                tvErrors.setTextColor(Color.RED);
                tvErrors.setText("Tên đăng nhập hoặc mật khẩu không chính xác.");
            }
        }
    }
    public void RemeberUser(String R_user, String R_pass, boolean status) {
        SharedPreferences preferences = getSharedPreferences("user_file", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (!status) {
            editor.clear();
            editor.putString("Username",R_user);
        }else {
            editor.putString("Username",R_user);
            editor.putString("Password",R_pass);
            editor.putBoolean("Remember",status);
        }
        editor.commit();
    }
}