package com.example.quanlypet.ui.welcome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlypet.R;
import com.example.quanlypet.database.AdminDB;
import com.example.quanlypet.database.UsersDB;
import com.google.android.material.textfield.TextInputEditText;

public class ChangePasswordActivity extends AppCompatActivity {
    private TextInputEditText NowPass;
    private TextInputEditText newPass;
    private TextInputEditText newPassAganin;
    private TextView tvErrors;
    private TextView tvExit;
    private TextView imgSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        NowPass = findViewById(R.id.ed_nowPass);
        newPass = findViewById(R.id.newPass);
        newPassAganin = findViewById(R.id.newPassAganin);
        tvErrors = findViewById(R.id.tv_errors);
        tvExit = findViewById(R.id.tv_exit);
        imgSave = findViewById(R.id.img_save);
        tvErrors.setText("");
        tvExit.setOnClickListener(view1 -> {
            finish();
        });
        imgSave.setOnClickListener(view1 -> {
            SharedPreferences preferences = getSharedPreferences("user_file", MODE_PRIVATE);
            String user = preferences.getString("Username", "");
            if (user.equals("Admin")){
                if (Validate() > 0) {
                    AdminDB.getInstance(getApplicationContext()).Dao().changePass(user, newPass.getText().toString());
                    tvErrors.setText("Đổi mật khẩu thành công.");
                    finish();
                }
            }else {
                if (Validate() > 0) {
                    UsersDB.getInstance(getApplicationContext()).Dao().changePass(user, newPass.getText().toString());
                    tvErrors.setText("Đổi mật khẩu thành công.");
                    finish();
                }
            }
        });
    }

    public int Validate(){
        int check = 1;
        if (NowPass.getText().toString().trim().isEmpty() ||
                newPass.getText().toString().trim().isEmpty() ||
                newPassAganin.getText().toString().isEmpty()){
            tvErrors.setText("Không để trống !");
            check = -1;
        }else {
            SharedPreferences preferences = getSharedPreferences("user_file",MODE_PRIVATE);
            String MKcu = preferences.getString("Password","");
            String MKmoi = newPass.getText().toString();
            String MKlai = newPassAganin.getText().toString();
            if (!MKcu.equals(NowPass.getText().toString())){
                tvErrors.setText("Mật khẩu hiện tại không đúng !");
                check = -1;
            }
            if (!MKmoi.equals(MKlai)){
                tvErrors.setText("Mật khẩu mới không trùng khớp !");
                check = -1;
            }
        }
        return check;
    }
}