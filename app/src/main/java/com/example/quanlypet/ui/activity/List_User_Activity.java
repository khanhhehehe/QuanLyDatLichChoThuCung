package com.example.quanlypet.ui.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;

import com.example.quanlypet.R;
import com.example.quanlypet.adapter.ad_use.List_user_Adapter;
import com.example.quanlypet.database.UsersDB;
import com.example.quanlypet.model.UsersObj;

import java.util.ArrayList;
import java.util.List;

public class List_User_Activity extends AppCompatActivity {
    private static final int MY_REQUESTCODE = 111;
    RecyclerView recyclerView;
    List_user_Adapter adapter;
    private SearchView searchView;
    List<UsersObj> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);
        Toolbar toolbar = findViewById(R.id.toolbar_user);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Danh Sách Người Dùng");
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.recy_list_user);
        adapter = new List_user_Adapter(this, new List_user_Adapter.ClickItem() {
            @Override
            public void update(UsersObj obj) {
                updateUser(obj);
            }
        });
        list = new ArrayList<>();
        list = UsersDB.getInstance(this).Dao().getAllData();
        adapter.setData(list);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_users, menu);
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.seach_user).getActionView();
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

    private void updateUser(UsersObj obj) {
        Intent i = new Intent(this, DetailUsersActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_user", obj);
        i.putExtras(bundle);
        startActivityForResult(i, MY_REQUESTCODE);
    }

    public void loadDATA() {
        list = UsersDB.getInstance(this).Dao().getAllData();
        adapter.setData(list);
    }


    @Override
    protected void onResume() {
        super.onResume();
        loadDATA();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_REQUESTCODE && resultCode == RESULT_OK) ;
        loadDATA();
    }
}