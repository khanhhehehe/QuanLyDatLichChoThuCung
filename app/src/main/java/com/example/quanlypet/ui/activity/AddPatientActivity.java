package com.example.quanlypet.ui.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.quanlypet.R;
import com.example.quanlypet.database.AnimalDB;
import com.example.quanlypet.database.DoctorDB;
import com.example.quanlypet.database.PatientDB;
import com.example.quanlypet.database.UsersDB;
import com.example.quanlypet.model.AnimalObj;
import com.example.quanlypet.model.DoctorObj;
import com.example.quanlypet.model.PatientObj;
import com.example.quanlypet.model.UsersObj;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class AddPatientActivity extends AppCompatActivity {
    private TextInputEditText edIdDocter;
    private TextInputEditText edIdAnimal;
    private TextInputEditText edDateCreate;
    private TextInputEditText edDateUpdate;
    private Button btnAddPatient;
    private TextInputEditText edSysptoms;
    private TextInputEditText edDiagnostic;
    private TextInputEditText edInstructions;
    private Button btnCanel;
    private Toolbar Tbr;
    private UsersObj usersObj;
    private AnimalObj animalObj;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    int mYear, mMonth, mDay;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);

        edDateCreate = (TextInputEditText) findViewById(R.id.ed_dateCreate);
        edDateUpdate = (TextInputEditText) findViewById(R.id.ed_dateUpdate);
        btnAddPatient = (Button) findViewById(R.id.btn_addPatient);
        edSysptoms = (TextInputEditText) findViewById(R.id.ed_Sysptoms);
        edDiagnostic = (TextInputEditText) findViewById(R.id.ed_Diagnostic);
        edInstructions = (TextInputEditText) findViewById(R.id.ed_Instructions);

        btnCanel = (Button) findViewById(R.id.btn_canel);
        Tbr = findViewById(R.id.id_tollBar);
        setSupportActionBar(Tbr);
        getSupportActionBar().setTitle("Thêm Bệnh án");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DatePickerDialog.OnDateSetListener date = (datePicker, year, monthOfYear, dayOfMonth) -> {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            GregorianCalendar c = new GregorianCalendar(mYear, mMonth, mDay);
            edDateCreate.setText(sdf.format(c.getTime()));
        };
        DatePickerDialog.OnDateSetListener date1 = (datePicker, year, monthOfYear, dayOfMonth) -> {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            GregorianCalendar c = new GregorianCalendar(mYear, mMonth, mDay);
            edDateUpdate.setText(sdf.format(c.getTime()));
        };
        dismissKeyboardShortcutsHelper();
        edDateCreate.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            mYear = calendar.get(Calendar.YEAR);
            mMonth = calendar.get(Calendar.MONTH);
            mDay = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    android.R.style.Theme_Holo_Light_Dialog_NoActionBar, date, mYear, mMonth, mDay);
            datePickerDialog.show();
        });
        edDateUpdate.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            mYear = calendar.get(Calendar.YEAR);
            mMonth = calendar.get(Calendar.MONTH);
            mDay = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    android.R.style.Theme_Holo_Light_Dialog_NoActionBar, date1, mYear, mMonth, mDay);
            datePickerDialog.show();
        });
        SharedPreferences sharedPreferences = getSharedPreferences("Animal_id_doctor_id", MODE_PRIVATE);
        int doctorid = sharedPreferences.getInt("doctorId",0);
        int animalid = sharedPreferences.getInt("animalId",0);

        DoctorObj doctorObj = (DoctorObj) DoctorDB.getInstance(getApplicationContext()).Dao().getIdDoctor(String.valueOf(doctorid));
        AnimalObj animalObj = (AnimalObj) AnimalDB.getInstance(getApplicationContext()).Dao().getIDAnimal(String.valueOf(animalid));

        btnAddPatient.setOnClickListener(v->{
            String dateCreate = edDateCreate.getText().toString();
            String dateUpdate = edDateUpdate.getText().toString();
            String sysptems = edSysptoms.getText().toString().trim();
            String diagnostic = edDiagnostic.getText().toString().trim();
            String instructions = edInstructions.getText().toString().trim();
            if( dateCreate.isEmpty() || dateUpdate.isEmpty() || sysptems.isEmpty() || diagnostic.isEmpty()){
                Toast.makeText(this, "Không được để trống", Toast.LENGTH_SHORT).show();
            }else{
                PatientObj patientObj = new PatientObj(doctorObj.getId(),animalObj.getId(),dateCreate,dateUpdate,sysptems,diagnostic,instructions);
                PatientDB.getInstance(getApplicationContext()).Dao().insert(patientObj);
                Toast.makeText(getApplicationContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}