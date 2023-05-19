package com.example.quanlypet.ui.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlypet.R;
import com.example.quanlypet.adapter.animal.AnimalAdapter;
import com.example.quanlypet.adapter.patient.PatientAdapter;
import com.example.quanlypet.database.AnimalDB;
import com.example.quanlypet.database.PatientDB;
import com.example.quanlypet.model.AnimalObj;
import com.example.quanlypet.model.PatientObj;

import java.util.ArrayList;

public class PatientActivity extends AppCompatActivity {
    private Toolbar Tbr;
    private RecyclerView rcvPatient;
    private ArrayList<PatientObj> arrayList = new ArrayList<>();
    private ArrayList<AnimalObj> list = new ArrayList<>();
    private PatientAdapter adapter;
    private AnimalAdapter animalAdapter;
    private AnimalObj animalObj;
    private PatientObj patientObj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        Tbr = (Toolbar) findViewById(R.id.id_tollBar);
        rcvPatient = (RecyclerView) findViewById(R.id.rcv_patient);
        setSupportActionBar(Tbr);
        getSupportActionBar().setTitle("Xem bệnh án");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list =  (ArrayList<AnimalObj>) AnimalDB.getInstance(getApplicationContext()).Dao().getAllData();

        Bundle intent = getIntent().getExtras();
        animalObj = (AnimalObj) intent.getSerializable("obj");

        adapter = new PatientAdapter(getApplicationContext());
        LoadData();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rcvPatient.setLayoutManager(layoutManager);
        rcvPatient.setAdapter(adapter);

    }
    @Override
    public void onResume() {
        super.onResume();
        LoadData();
    }
    public void LoadData(){
            arrayList = (ArrayList<PatientObj>) PatientDB.getInstance(getApplicationContext()).Dao().getIDAnimal(String.valueOf(animalObj.getId()));
            adapter.setDataPatient(arrayList);
    }

}