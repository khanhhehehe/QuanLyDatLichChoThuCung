package com.example.quanlypet.ui.welcome;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlypet.R;
import com.example.quanlypet.database.UsersDB;
import com.example.quanlypet.model.UsersObj;
import com.google.android.material.textfield.TextInputEditText;

public class SignupUsersActivity extends AppCompatActivity {

    private TextView tvExit;
    private TextInputEditText edUsername;
    private TextInputEditText edName;
    private TextInputEditText edEmail;
    private TextInputEditText edPhone;
    private RadioButton rdoMale;
    private RadioButton rdoFemale;
    private TextInputEditText edPassword;
    private TextInputEditText edRePassword;
    private TextView tvErrors;
    private Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_users);

        tvExit = findViewById(R.id.tv_exit);
        edUsername = findViewById(R.id.ed_Username);
        edName = findViewById(R.id.ed_Name);
        edEmail = findViewById(R.id.ed_Email);
        edPhone = findViewById(R.id.ed_Phone);
        rdoMale = findViewById(R.id.rdo_Male);
        rdoFemale = findViewById(R.id.rdo_Female);
        edPassword = findViewById(R.id.ed_Password);
        edRePassword = findViewById(R.id.ed_RePassword);
        tvErrors = findViewById(R.id.tv_errors);
        btnSignup = findViewById(R.id.btn_Signup);
        rdoMale.setChecked(true);
        tvErrors.setText("");
        tvExit.setOnClickListener(view -> {
            finish();
        });
        btnSignup.setOnClickListener(view -> {
            if (Validate()>0){
                String importName = edUsername.getText().toString().trim();
                String fullName = edName.getText().toString().trim();
                String email = edEmail.getText().toString().trim();
                String phone = edPhone.getText().toString().trim();
                String password = edPassword.getText().toString().trim();
                int gender = 0;
                if (rdoMale.isChecked()){
                    gender = 0;
                } else if (rdoFemale.isChecked()){
                    gender = 1;
                }
                UsersObj usersObj = new UsersObj(importName,fullName,email,phone,gender,password);
                UsersDB.getInstance(getApplicationContext()).Dao().insert(usersObj);
                tvErrors.setText("Thêm thành công.");
                Toast.makeText(this, "Tạo tài khoản thành công.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
    public int Validate(){
        String importName = edUsername.getText().toString().trim();
        String fullName = edName.getText().toString().trim();
        String email = edEmail.getText().toString().trim();
        String phone = edPhone.getText().toString().trim();
        String password = edPassword.getText().toString().trim();
        String Repassword = edRePassword.getText().toString().trim();
        int check = 1;
        if (importName.isEmpty() || fullName.isEmpty() || email.isEmpty() ||
                phone.isEmpty() || password.isEmpty()||Repassword.isEmpty()) {
            tvErrors.setText("Không được để trống!");
            check = -1;
        }else {
            if (!Repassword.equals(password)){
                tvErrors.setText("Mật khẩu không khớp.");
                check = -1;
            }
        }
        return check;
    }
}