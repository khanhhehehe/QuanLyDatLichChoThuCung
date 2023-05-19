package com.example.quanlypet.ui.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlypet.adapter.bill.BillAdapter;
import com.example.quanlypet.R;
import com.example.quanlypet.database.BillDB;
import com.example.quanlypet.database.UsersDB;
import com.example.quanlypet.model.BillObj;
import com.example.quanlypet.model.UsersObj;
import com.example.quanlypet.ui.activity.AddBillActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class BillFragment extends Fragment implements BillAdapter.Callback {
    private TextInputEditText edUsername;
    private TextInputEditText edUserphone;
    private TextInputEditText edTime;
    private TextInputEditText edDate;
    private TextInputEditText edPrice;
    private TextInputEditText edNote;
    private Button btnUpdateBill;
    private Button btnCancel;
    private RecyclerView rcvBill;
    private ArrayList<BillObj> arrayList = new ArrayList<>();
    private BillAdapter adapterBill;
    private SimpleDateFormat sdftime = new SimpleDateFormat("HH:mm");
    private SimpleDateFormat sdfdate = new SimpleDateFormat("yyyy-MM-dd");
    private String username;
    private SearchView searchBill;

    public BillFragment() {
    }

    public static BillFragment newInstance() {
        BillFragment fragment = new BillFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bill, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("user_file", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("Username", "");
        rcvBill = (RecyclerView) view.findViewById(R.id.rcv_bill);
        searchBill = (SearchView) view.findViewById(R.id.search_bill);
        adapterBill = new BillAdapter(getContext(), this);
        LoadData();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rcvBill.setLayoutManager(layoutManager);
        rcvBill.setAdapter(adapterBill);

        searchBill.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapterBill.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterBill.getFilter().filter(newText);
                return false;
            }
        });
    }
    public void LoadData() {
        if (username.equals("Admin")) {
            arrayList = (ArrayList<BillObj>) BillDB.getInstance(getContext()).Dao().getAllData();
            adapterBill.setData(arrayList);
        } else {
            UsersObj userobj = UsersDB.getInstance(getContext()).Dao().getIdUsers(username);
            arrayList = (ArrayList<BillObj>) BillDB.getInstance(getContext()).Dao().getbyUsers(userobj.getId());
            adapterBill.setData(arrayList);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        LoadData();
    }

    @Override
    public void Update(BillObj object) {
        final Dialog dialog = new Dialog(getContext(), com.google.android.material.R.style.Widget_Material3_MaterialCalendar_Fullscreen);
        dialog.setContentView(R.layout.dialog_update_bill);
        dialog.setCancelable(false);
        dialog.show();

        edUsername = (TextInputEditText) dialog.findViewById(R.id.ed_Username);
        edUserphone = (TextInputEditText) dialog.findViewById(R.id.ed_Userphone);
        edTime = (TextInputEditText) dialog.findViewById(R.id.ed_time);
        edDate = (TextInputEditText) dialog.findViewById(R.id.ed_date);
        edPrice = (TextInputEditText) dialog.findViewById(R.id.ed_price);
        edNote = (TextInputEditText) dialog.findViewById(R.id.ed_note);
        btnUpdateBill = (Button) dialog.findViewById(R.id.btn_Update_bill);
        btnCancel = (Button) dialog.findViewById(R.id.btn_cancel);
        if (!username.equals("Admin")){
            btnUpdateBill.setVisibility(View.GONE);
        }
        edNote.setText(object.getNote());
        edPrice.setText(object.getPrice() + "");

        SharedPreferences sharedPreferences1 = requireActivity().getSharedPreferences("Users_info_id", Context.MODE_PRIVATE);
        int userid = sharedPreferences1.getInt("userId", 0);
        edDate.setText(sdfdate.format(new Date()));
        edTime.setText(sdftime.format(new Date()));
        UsersObj usersObj = UsersDB.getInstance(getActivity()).Dao().getID(userid);
        edUsername.setText(usersObj.getFull_name());
        edUserphone.setText(usersObj.getPhone());
        btnUpdateBill.setOnClickListener(v -> {
            if (edPrice.getText().toString().trim().isEmpty()||edNote.getText().toString().trim().isEmpty()) {
                Toast.makeText(getActivity(), "ko dc de trong", Toast.LENGTH_SHORT).show();
            } else {
                object.setTime(sdftime.format(new Date()));
                object.setDate(sdfdate.format(new Date()));
                object.setPrice(Double.parseDouble(edPrice.getText().toString().trim()));
                object.setNote(edNote.getText().toString().trim());
                BillDB.getInstance(getContext()).Dao().editBill(object);
                arrayList = (ArrayList<BillObj>) BillDB.getInstance(getContext()).Dao().getAllData();
                adapterBill.setData(arrayList);
                Toast.makeText(getActivity(), "sua thanh cong", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(v -> {
            dialog.cancel();
        });
    }
}