package com.example.quanlypet.ui.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.quanlypet.ImageConverter;
import com.example.quanlypet.R;


public class DoctorInforActivity extends AppCompatActivity {
    private TextView tvSpecializeDocter;
    private TextView tvNameDocter;
    private TextView tvPhoneDocter;
    private TextView tvAddressDocter;
    private Toolbar Tbr;
    private ImageView imgDt;
    private Button btnCall;
    private CardView cardView;
    private Button btnCancel;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_infor);
        tvSpecializeDocter = (TextView) findViewById(R.id.tv_specializeDocter);
        tvNameDocter = (TextView) findViewById(R.id.tv_nameDocter);
        imgDt = findViewById(R.id.img_dt);
        tvPhoneDocter = (TextView) findViewById(R.id.tv_phoneDocter);
        tvAddressDocter = (TextView) findViewById(R.id.tv_addressDocter);
        cardView = findViewById(R.id.card_callPhone);
        Tbr = findViewById(R.id.id_tollBar);
        setSupportActionBar(Tbr);
        getSupportActionBar().setTitle("Thông tin chi tiết");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tbr.setTitleTextColor(Color.WHITE);


        Intent intent = getIntent();
        byte[] byteArray = getIntent().getByteArrayExtra("img");
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        Bitmap circularBitmap = ImageConverter.getRoundedCornerBitmap(bitmap, 100);
        String name =  intent.getStringExtra("name");
        String phone = intent.getStringExtra("phone");
        String specialize =  intent.getStringExtra("specialize");
        String address = intent.getStringExtra("address");
        tvNameDocter.setText(name);
        tvPhoneDocter.setText(phone);
        tvAddressDocter.setText(address);
        tvSpecializeDocter.setText(specialize);
        imgDt.setImageBitmap(circularBitmap);

        cardView.setOnClickListener(v->{
            card_callPhone();
        });
    }
    public void card_callPhone(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_call_phone);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.bg_dialog_call));
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        window.setAttributes(windowAttributes);
        windowAttributes.gravity = Gravity.BOTTOM;
        btnCall = (Button) dialog.findViewById(R.id.btn_call);
        btnCancel = (Button) dialog.findViewById(R.id.btn_cancel);
        Intent intent1 = getIntent();
        String phone1 = intent1.getStringExtra("phone");
        btnCall.setText(phone1);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_call = new Intent(Intent.ACTION_CALL, Uri.parse("tel: " +phone1));
                startActivity(intent_call);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }
}