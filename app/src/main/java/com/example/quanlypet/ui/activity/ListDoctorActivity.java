package com.example.quanlypet.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlypet.R;
import com.example.quanlypet.adapter.doctor.ListDoctorAdapter;
import com.example.quanlypet.adapter.doctor.DoctorAdapter;
import com.example.quanlypet.database.DoctorDB;
import com.example.quanlypet.model.DoctorObj;
import com.example.quanlypet.model.ListDoctorObj;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ListDoctorActivity extends AppCompatActivity implements DoctorAdapter.Callback{
    private RecyclerView rcvDanhsachDoctor;
    private RecyclerView rcvDoctor;
    private ArrayList<DoctorObj> list = new ArrayList<>();
    private ArrayList<ListDoctorObj> list1 = new ArrayList<>();
    private ListDoctorAdapter listDoctorAdapter;
    private Toolbar idTollBar;
    private DoctorAdapter adapter;
    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_doctor);
        phanQuyen();
        rcvDoctor = (RecyclerView) findViewById(R.id.rcv_Doctor);
        idTollBar = (Toolbar) findViewById(R.id.id_tollBar);
        setSupportActionBar(idTollBar);
        getSupportActionBar().setTitle("Thông tin bác sĩ");
        adapter = new DoctorAdapter(getBaseContext(), this);
        list = (ArrayList<DoctorObj>) DoctorDB.getInstance(getBaseContext()).Dao().getAllData();
        adapter.setDataDocter(list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
        rcvDoctor.setLayoutManager(layoutManager);
        rcvDoctor.setAdapter(adapter);

        rcvDanhsachDoctor = (RecyclerView) findViewById(R.id.rcv_danhsachDoctor);
        getDS();
        listDoctorAdapter = new ListDoctorAdapter(getBaseContext());
        listDoctorAdapter.setDataDanhSach(list1);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
        rcvDanhsachDoctor.setLayoutManager(layoutManager1);
        rcvDanhsachDoctor.setAdapter(listDoctorAdapter);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_huy_docter, menu);
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.error).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    public void getDS() {
        list1.add(new ListDoctorObj("Hệ thống hỗ trợ", R.drawable.doctor, "0961803120"));
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

    @Override
    public void update(DoctorObj doctorObj) {

    }
}