package com.example.quanlypet.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlypet.R;
import com.example.quanlypet.adapter.bill.BillAdapter;
import com.example.quanlypet.database.BillDB;
import com.example.quanlypet.database.UsersDB;
import com.example.quanlypet.model.BillObj;
import com.example.quanlypet.model.UsersObj;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddBillActivity extends AppCompatActivity {
    private Toolbar Tbr;
    private TextInputEditText edUsername;
    private TextInputEditText edUserphone;
    private TextInputEditText edTime;
    private TextInputEditText edDate;
    private TextInputEditText edPrice;
    private TextInputEditText edNote;
    private Button btnAddBill;

    private SimpleDateFormat sdftime = new SimpleDateFormat("HH:mm");
    private SimpleDateFormat sdfdate = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);
        Tbr = findViewById(R.id.id_tollBar_addBill);
        setSupportActionBar(Tbr);
        getSupportActionBar().setTitle("Thêm bill");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edUsername = (TextInputEditText) findViewById(R.id.ed_Username);
        edUserphone = (TextInputEditText) findViewById(R.id.ed_Userphone);
        edTime = (TextInputEditText) findViewById(R.id.ed_time);
        edDate = (TextInputEditText) findViewById(R.id.ed_date);
        edPrice = (TextInputEditText) findViewById(R.id.ed_price);
        edNote = (TextInputEditText) findViewById(R.id.ed_note);
        btnAddBill = (Button) findViewById(R.id.btn_Add_bill);
        SharedPreferences sharedPreferences = getSharedPreferences("Users_info_id", MODE_PRIVATE);
        int userid = sharedPreferences.getInt("userId", 0);
        String service = sharedPreferences.getString("obj_service", "");
        if (service.equals("Khám và Chữa")) {
            edPrice.setText("200000");
            edNote.setText("Khám và Chữa giá 200000");
        } else if (service.equalsIgnoreCase("Kiểm tra sức Khỏe")) {
            edPrice.setText("200000");
            edNote.setText("Kiểm tra sức Khỏe giá 200000");
        } else if (service.equalsIgnoreCase("Tiêm Phòng")) {
            edPrice.setText("150000");
            edNote.setText("Tiêm Phòng giá 150000");
        } else if (service.equalsIgnoreCase("Phẫu Thuật")) {
            edPrice.setText("350000");
            edNote.setText("Phẫu Thuật giá 350000");
        } else if (service.equalsIgnoreCase("Siêu âm")) {
            edPrice.setText("300000");
            edNote.setText("Siêu âm giá 300000");
        } else if (service.equalsIgnoreCase("Spa - Cắt & tỉa")) {
            edPrice.setText("200000");
            edNote.setText("Spa - Cắt & tỉa giá 200000");
        }
        edDate.setText(sdfdate.format(new Date()));
        edTime.setText(sdftime.format(new Date()));
        UsersObj usersObj = UsersDB.getInstance(getApplicationContext()).Dao().getID(userid);
        edUsername.setText(usersObj.getFull_name());
        edUserphone.setText(usersObj.getPhone());
        btnAddBill.setOnClickListener(v -> {

            double price = Double.parseDouble(edPrice.getText().toString().trim());
            String note = edNote.getText().toString().trim();
            if (edPrice.getText().toString().trim().isEmpty() || note.isEmpty()) {
                Toast.makeText(getApplicationContext(), "ban phai nhap het", Toast.LENGTH_SHORT).show();
            } else {
                String time = sdftime.format(new Date());
                String date = sdfdate.format(new Date());
                int users = usersObj.getId();
                BillObj object = new BillObj(users, time, date, price, note);
                BillDB.getInstance(getApplicationContext()).Dao().insertBill(object);
                Toast.makeText(getApplicationContext(), "them thanh cong", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}