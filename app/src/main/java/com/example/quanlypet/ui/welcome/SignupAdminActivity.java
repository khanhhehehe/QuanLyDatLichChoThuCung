package com.example.quanlypet.ui.welcome;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.quanlypet.R;
import com.example.quanlypet.database.AdminDB;
import com.example.quanlypet.model.AdminObj;
import com.google.android.material.textfield.TextInputEditText;

public class SignupAdminActivity extends AppCompatActivity {

    private TextInputEditText edUsername;
    private TextInputEditText edName;
    private TextInputEditText edEmail;
    private TextInputEditText edPassword;
    private TextInputEditText edRePassword;
    private Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_admin);

        edUsername = findViewById(R.id.ed_Username);
        edName = findViewById(R.id.ed_Name);
        edEmail = findViewById(R.id.ed_Email);
        edPassword = findViewById(R.id.ed_Password);
        edRePassword = findViewById(R.id.ed_RePassword);
        btnSignup = findViewById(R.id.btn_Signup);

        btnSignup.setOnClickListener(view -> {
            String importName = edUsername.getText().toString().trim();
            String fullName = edName.getText().toString().trim();
            String email = edEmail.getText().toString().trim();
            String password = edPassword.getText().toString().trim();
            String Repassword = edRePassword.getText().toString().trim();
            if (importName.isEmpty() || fullName.isEmpty() || email.isEmpty() || password.isEmpty()||Repassword.isEmpty()) {
                if (Repassword.equals(password)){
                    Toast.makeText(getApplicationContext(), "Không Khớp", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(getApplicationContext(), "Không được để trống!", Toast.LENGTH_SHORT).show();
            } else {
                AdminObj adminObj = new AdminObj(importName, fullName, email, password);
                AdminDB.getInstance(getApplicationContext()).Dao().insert(adminObj);
                Toast.makeText(getApplicationContext(), "Thêm thành công!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}