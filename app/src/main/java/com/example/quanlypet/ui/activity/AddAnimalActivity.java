package com.example.quanlypet.ui.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.quanlypet.R;
import com.example.quanlypet.adapter.animal.AnimalAdapter;
import com.example.quanlypet.database.AnimalDB;
import com.example.quanlypet.database.UsersDB;
import com.example.quanlypet.model.AnimalObj;
import com.example.quanlypet.model.UsersObj;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddAnimalActivity extends AppCompatActivity {
    private Toolbar idTollBar;
    private Bitmap bitmap;
    private Toolbar Tbr;
    private EditText edNameAnimal;
    private CircleImageView imgAnh;
    private ImageView btnAlbum;
    private EditText edAgeAnimal;
    private EditText edSpeciesAnimal;
    private Button btnAddAnimal;
    private UsersObj usersObj;
    private Button btnCancel;
    int REQUEST_CODE_ALBUM = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_animal);
        idTollBar = (Toolbar) findViewById(R.id.id_tollBar_addAnimal);
        Tbr = findViewById(R.id.id_tollBar_addAnimal);
        setSupportActionBar(Tbr);
        getSupportActionBar().setTitle("Add Animal");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tbr.setTitleTextColor(Color.WHITE);
        edNameAnimal = (EditText)findViewById(R.id.ed_nameAnimal);
        btnAlbum = findViewById(R.id.btn_album);
        SharedPreferences sharedPreferences = getSharedPreferences("Users_info",MODE_PRIVATE);
        String username = sharedPreferences.getString("Username","");
        usersObj = UsersDB.getInstance(getApplicationContext()).Dao().getIdUsers(username);
        imgAnh = (CircleImageView) findViewById(R.id.img_anh);
        btnAlbum.setOnClickListener(v ->{
            Intent i = new Intent(Intent.ACTION_PICK);
            i.setType("image/*");
            i.setAction(Intent.ACTION_GET_CONTENT);
            chooseImage.launch(i);
        });
        imgAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);
            }
        });
        edAgeAnimal = (EditText) findViewById(R.id.ed_ageAnimal);
        edSpeciesAnimal = (EditText) findViewById(R.id.ed_speciesAnimal);
        btnAddAnimal = (Button) findViewById(R.id.btn_addAnimal);
        btnAddAnimal.setOnClickListener(v -> {
            String namean = edNameAnimal.getText().toString().trim();
            BitmapDrawable bitmapDrawable = (BitmapDrawable) imgAnh.getDrawable();
            Bitmap bitmap = bitmapDrawable.getBitmap();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] anh = byteArrayOutputStream.toByteArray();
            int age = Integer.parseInt(edAgeAnimal.getText().toString().trim());
            String species = edSpeciesAnimal.getText().toString().trim();
            if (namean.isEmpty() || species.isEmpty()) {
                Toast.makeText(getApplicationContext(), "ko dc de trong", Toast.LENGTH_SHORT).show();
            } else {
                AnimalObj object = new AnimalObj(usersObj.getId(),namean, anh, age, species,1);
                AnimalDB.getInstance(getApplicationContext()).Dao().insert(object);
                Toast.makeText(getApplicationContext(), "them thanh cong", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK && data != null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                imgAnh.setImageBitmap(bitmap);
        }
    }
    ActivityResultLauncher<Intent> chooseImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Uri selectedImageUri = data.getData();
                        if (null != selectedImageUri) {
                            imgAnh.setImageURI(selectedImageUri);
                            BitmapDrawable bitmapDrawable = (BitmapDrawable) imgAnh.getDrawable();
                            bitmap = bitmapDrawable.getBitmap();
                        }
                    }
                }
            });
}