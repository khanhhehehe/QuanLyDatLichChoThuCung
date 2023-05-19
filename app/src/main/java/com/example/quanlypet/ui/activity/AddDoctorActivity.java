package com.example.quanlypet.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.example.quanlypet.R;
import com.example.quanlypet.database.DoctorDB;
import com.example.quanlypet.model.DoctorObj;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;


public class AddDoctorActivity extends AppCompatActivity{
    private TextInputEditText edNameDocter;
    private TextInputEditText edPhoneDocter;
    private RadioButton rdoBoy;
    private RadioButton rdoGirl;
    private TextInputEditText edEmailDocter;
    private TextInputEditText edAddressDocter;
    private TextInputEditText edSpecializeDocter;
    private Button btnAddDocter;
    private int checkGender;
    private Toolbar Tbr;
    private CircleImageView imgPicture;
    private ImageView btnAlbum;
    int REQUES_CODE_CAMERA = 111;
    int REQUEST_CODE_ALBUM = 123;
    Bitmap bitmap;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_docter);
        phanQuyen();
        edNameDocter = (TextInputEditText) findViewById(R.id.ed_nameDocter);
        edPhoneDocter = (TextInputEditText) findViewById(R.id.ed_phoneDocter);
        rdoBoy = (RadioButton) findViewById(R.id.rdo_boy);
        rdoGirl = (RadioButton) findViewById(R.id.rdo_girl);
        edEmailDocter = (TextInputEditText) findViewById(R.id.ed_emailDocter);
        edAddressDocter = (TextInputEditText) findViewById(R.id.ed_addressDocter);
        edSpecializeDocter = (TextInputEditText) findViewById(R.id.ed_specializeDocter);
        btnAddDocter = (Button) findViewById(R.id.btn_addDocter);
        imgPicture = (CircleImageView) findViewById(R.id.img_picture);
        btnAlbum = (ImageView) findViewById(R.id.btn_album);
        Tbr = findViewById(R.id.id_tollBar);
        setSupportActionBar(Tbr);
        getSupportActionBar().setTitle("Thêm Bác sĩ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tbr.setTitleTextColor(Color.WHITE);
        imgPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i, REQUES_CODE_CAMERA);
            }
        });
        btnAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i, REQUEST_CODE_ALBUM);
            }
        });

        btnAddDocter.setOnClickListener(v-> {
            String regexPhoneNumber = "(09|03|07|08|05)+([0-9]{8})";
            String name = edNameDocter.getText().toString().trim();
            String phone = edPhoneDocter.getText().toString().trim();

            String email = edEmailDocter.getText().toString().trim();
            String address = edAddressDocter.getText().toString().trim();
            String specialize = edSpecializeDocter.getText().toString().trim();
            checkGender = rdoBoy.isChecked() ? 1 : 0;

            BitmapDrawable bitmapDrawable = (BitmapDrawable) imgPicture.getDrawable();
            bitmap = bitmapDrawable.getBitmap();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] hinhanh = byteArrayOutputStream.toByteArray();

            if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || address.isEmpty() || specialize.isEmpty()) {
                Toast.makeText(this, "Không được để trống", Toast.LENGTH_SHORT).show();
            }else if(phone.matches(regexPhoneNumber) == false){
                Toast.makeText(this, "Số điện thoại không đúng định dạng", Toast.LENGTH_SHORT).show();
            }else{
                DoctorObj docterObj = new DoctorObj(name,hinhanh,phone,email,address,checkGender,specialize);
                DoctorDB.getInstance(getApplicationContext()).Dao().insert(docterObj);
                Toast.makeText(getApplicationContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUES_CODE_CAMERA && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgPicture.setImageBitmap(bitmap);
        }
        if (requestCode == REQUEST_CODE_ALBUM && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgPicture.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    public boolean phanQuyen() {
        if (Build.VERSION.SDK_INT > 23) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                return true;
            }else {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.INTERNET,
                        Manifest.permission.CALL_PHONE
                }, 1);
                return false;
            }
        } else {
            return true;
        }
    }
}