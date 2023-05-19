package com.example.quanlypet.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.quanlypet.R;
import com.example.quanlypet.adapter.Spinner.SpinnerAnimal;
import com.example.quanlypet.adapter.Spinner.SpinnerDoctor;
import com.example.quanlypet.adapter.booking.bookingAdapter;
import com.example.quanlypet.adapter.booking.booking_admin_Adapter;
import com.example.quanlypet.database.AnimalDB;
import com.example.quanlypet.database.BookDB;
import com.example.quanlypet.database.DoctorDB;
import com.example.quanlypet.database.UsersDB;
import com.example.quanlypet.model.AnimalObj;
import com.example.quanlypet.model.BookObj;
import com.example.quanlypet.model.DoctorObj;
import com.example.quanlypet.model.UsersObj;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class BookWaitFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener  {
    private  SwipeRefreshLayout swipeRefreshLayout;



    private int REQUEST_CAMERA = 111;
    private Bitmap bitmap;
    private ImageView imgClose;
    private Button btnKhamvachua;
    private Button btnKiemtrasuckhoe;
    private Button btnTiemphong;
    private Button btnPhauthuat;
    private Button btnSieuam;
    private Button btnSpa;
    List<BookObj> list;
    List<BookObj> list2;
    bookingAdapter adapter;
    booking_admin_Adapter adapterAdmin;
    RecyclerView reCy_booking;
    UsersObj usersObj = new UsersObj();
    private Spinner spnDoctor;
    private TextInputLayout TIPNameDoctor;
    private TextInputEditText TIEDNameDoctor;
    private TextInputLayout TIPPhoneNumber;
    private TextInputEditText TIEDPhoneNumber;
    private Spinner spnPet;
    private TextInputLayout TIPNamePet;
    private TextInputEditText TIEDNamePet;
    private TextInputLayout TIPTypePet;
    private TextInputEditText TIEDTypePet;
    private TextInputLayout TIPStatus;
    private TextInputEditText TIEDStatus;
    private ImageView imgPicture;
    private Button btnCamera;
    private Button btnAlbum;
    private RadioGroup rdogr;
    private RadioButton rdoPhongkham;
    private RadioButton rdoTainha;
    private TextInputLayout TIPTime;
    private TextInputEditText TIEDTime;
    private TextInputLayout TIPAddress;
    private TextInputEditText TIEDAddress;
    private TextInputLayout TIPService;
    private TextInputEditText TIEDService;
    private Button btnUpdate;
    private Button btnHuy;
    private String noikham;
    private int idPet;
    SpinnerAnimal adapterSPNAnimal;
    SpinnerDoctor adapterSPNDoctor;
    List<AnimalObj> listAnimal;
    List<DoctorObj> listDoctor;
    private int idDoctor;
    private List<BookObj> list3;


    public static BookWaitFragment newInstance() {
        BookWaitFragment fragment = new BookWaitFragment();

        return fragment;
    }

    public BookWaitFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_book_wait, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reCy_booking = view.findViewById(R.id.recy_cho);
        swipeRefreshLayout = view.findViewById(R.id.SwipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        list = new ArrayList<>();
        list2 = new ArrayList<>();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_file", Context.MODE_PRIVATE);
        String user = sharedPreferences.getString("Username", "");
        if (user.equalsIgnoreCase("Admin")) {
            list2 = BookDB.getInstance(getActivity()).Dao().getStatus(1);
            adapterAdmin = new booking_admin_Adapter(list2, getActivity(), new booking_admin_Adapter.Callback() {
                @Override
                public void updateAdmin(BookObj bookObj, int index) {
                    UpDateAdmin(bookObj, index);
                }
            });
            adapterAdmin.setDATA(list2);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            reCy_booking.setAdapter(adapterAdmin);
            reCy_booking.setLayoutManager(linearLayoutManager);
            loadDATA2();
        } else {
            usersObj = UsersDB.getInstance(getActivity()).Dao().getIdUsers(user);
            int id = usersObj.getId();
            list = BookDB.getInstance(getActivity()).Dao().getStatus2(1,id);
            adapter = new bookingAdapter(new bookingAdapter.Callback() {
                @Override
                public void update(BookObj bookObj, int index) {
                    updateBooking(bookObj, index);
                }
            }, getActivity());
            adapter.setDATA(list);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            reCy_booking.setAdapter(adapter);
            reCy_booking.setLayoutManager(linearLayoutManager);
            loadDATA();
        }

    }

    private void UpDateAdmin(BookObj bookObj, int index) {
        final Dialog dialog = new Dialog(getActivity(), com.google.android.material.R.style.Widget_Material3_MaterialCalendar_Fullscreen);

        dialog.setContentView(R.layout.update_booking);
        Toolbar toolbar = dialog.findViewById(R.id.tbl_booking);
        toolbar.setTitle("Chi tiết Lịch Đặt");
        spnDoctor = (Spinner) dialog.findViewById(R.id.spn_doctor);
        TIPNameDoctor = (TextInputLayout) dialog.findViewById(R.id.TIP_NameDoctor);
        TIEDNameDoctor = (TextInputEditText) dialog.findViewById(R.id.TIED_NameDoctor);
        TIPPhoneNumber = (TextInputLayout) dialog.findViewById(R.id.TIP_PhoneNumber);
        TIEDPhoneNumber = (TextInputEditText) dialog.findViewById(R.id.TIED_PhoneNumber);
        spnPet = (Spinner) dialog.findViewById(R.id.spn_Pet);
        TIPNamePet = (TextInputLayout) dialog.findViewById(R.id.TIP_NamePet);
        TIEDNamePet = (TextInputEditText) dialog.findViewById(R.id.TIED_NamePet);
        TIPTypePet = (TextInputLayout) dialog.findViewById(R.id.TIP_TypePet);
        TIEDTypePet = (TextInputEditText) dialog.findViewById(R.id.TIED_TypePet);
        TIPStatus = (TextInputLayout) dialog.findViewById(R.id.TIP_Status);
        TIEDStatus = (TextInputEditText) dialog.findViewById(R.id.TIED_Status);
        imgPicture = (ImageView) dialog.findViewById(R.id.img_picture);
        btnCamera = (Button) dialog.findViewById(R.id.btn_camera);
        btnAlbum = (Button) dialog.findViewById(R.id.btn_album);
        rdogr = (RadioGroup) dialog.findViewById(R.id.rdogr);
        rdoPhongkham = (RadioButton) dialog.findViewById(R.id.rdo_phongkham);
        rdoTainha = (RadioButton) dialog.findViewById(R.id.rdo_tainha);
        TIPTime = (TextInputLayout) dialog.findViewById(R.id.TIP_Time);
        TIEDTime = (TextInputEditText) dialog.findViewById(R.id.TIED_Time);
        TIPAddress = (TextInputLayout) dialog.findViewById(R.id.TIP_Address);
        TIEDAddress = (TextInputEditText) dialog.findViewById(R.id.TIED_Address);
        TIPService = (TextInputLayout) dialog.findViewById(R.id.TIP_Service);
        TIEDService = (TextInputEditText) dialog.findViewById(R.id.TIED_Service);
        btnUpdate = (Button) dialog.findViewById(R.id.btn_update);
        btnHuy = (Button) dialog.findViewById(R.id.btn_huy);
        SlectedSpinner();
        TIEDStatus.setText(bookObj.getStatus());
        TIEDAddress.setText(bookObj.getAddress());
        TIEDTime.setText(bookObj.getTime());
        TIEDService.setText(bookObj.getService());
        TIEDService.setFocusable(false);
        TIEDService.setFocusableInTouchMode(false);
        TIEDNameDoctor.setFocusable(false);
        TIEDNameDoctor.setFocusableInTouchMode(false);
        TIEDNamePet.setFocusable(false);
        TIEDNamePet.setFocusableInTouchMode(false);
        TIEDPhoneNumber.setFocusable(false);
        TIEDPhoneNumber.setFocusableInTouchMode(false);
        TIEDTypePet.setFocusable(false);
        TIEDTypePet.setFocusableInTouchMode(false);
        btnUpdate.setVisibility(View.INVISIBLE);
        btnHuy.setVisibility(View.INVISIBLE);
        rdogr.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rdo_phongkham:
                        TIPAddress.setEnabled(false);
                        TIEDAddress.setText("");
                        break;
                    case R.id.rdo_tainha:
                        TIPAddress.setEnabled(true);
                        break;
                }
            }
        });
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(intent, REQUEST_CAMERA);
            }
        });
        btnAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                chooseImg.launch(i);
            }
        });
        byte[] hinhanh = bookObj.getPhoto_status();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhanh, 0, hinhanh.length);
        imgPicture.setImageBitmap(bitmap);
        if (bookObj.getLocation().equals("Phòng Khám")) {
            rdoPhongkham.setChecked(true);
        } else {
            rdoPhongkham.setChecked(false);
        }
        if (bookObj.getLocation().equals("Tại nhà")) {
            rdoTainha.setChecked(true);
        } else {
            rdoTainha.setChecked(false);
        }
        adapterSPNAnimal = new SpinnerAnimal();
        listAnimal = AnimalDB.getInstance(getActivity()).Dao().getAllData();
        adapterSPNAnimal.setData(listAnimal);
        spnPet.setAdapter(adapterSPNAnimal);

        adapterSPNDoctor = new SpinnerDoctor();
        listDoctor = DoctorDB.getInstance(getActivity()).Dao().getAllData();
        adapterSPNDoctor.setDATA(listDoctor);
        spnDoctor.setAdapter(adapterSPNDoctor);

        for (int i = 0; i < spnDoctor.getCount(); i++) {
            if (list2.get(index).getId_doctor() == listDoctor.get(i).getId()) {
                spnDoctor.setSelection(i);
                spnDoctor.setSelected(true);
            }
        }
        for (int j = 0; j < spnPet.getCount(); j++) {
            if (list2.get(index).getId_animal() == listAnimal.get(j).getId()) {
                spnPet.setSelection(j);
                spnPet.setSelected(true);
            }
        }

        if (rdoPhongkham.isChecked()) {
            noikham = "Phòng Khám";
        } else if (rdoTainha.isChecked()) {
            noikham = "Tại nhà";
        }
        TIEDService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDiaLogSerVice();
            }
        });

        dialog.show();
    }




    private void updateBooking(BookObj bookObj, int index) {
        final Dialog dialog = new Dialog(getActivity(), com.google.android.material.R.style.Widget_Material3_MaterialCalendar_Fullscreen);

        dialog.setContentView(R.layout.update_booking);
        Toolbar toolbar = dialog.findViewById(R.id.tbl_booking);
        toolbar.setTitle("Chi tiết Lịch Đặt");
        spnDoctor = (Spinner) dialog.findViewById(R.id.spn_doctor);
        TIPNameDoctor = (TextInputLayout) dialog.findViewById(R.id.TIP_NameDoctor);
        TIEDNameDoctor = (TextInputEditText) dialog.findViewById(R.id.TIED_NameDoctor);
        TIPPhoneNumber = (TextInputLayout) dialog.findViewById(R.id.TIP_PhoneNumber);
        TIEDPhoneNumber = (TextInputEditText) dialog.findViewById(R.id.TIED_PhoneNumber);
        spnPet = (Spinner) dialog.findViewById(R.id.spn_Pet);
        TIPNamePet = (TextInputLayout) dialog.findViewById(R.id.TIP_NamePet);
        TIEDNamePet = (TextInputEditText) dialog.findViewById(R.id.TIED_NamePet);
        TIPTypePet = (TextInputLayout) dialog.findViewById(R.id.TIP_TypePet);
        TIEDTypePet = (TextInputEditText) dialog.findViewById(R.id.TIED_TypePet);
        TIPStatus = (TextInputLayout) dialog.findViewById(R.id.TIP_Status);
        TIEDStatus = (TextInputEditText) dialog.findViewById(R.id.TIED_Status);
        imgPicture = (ImageView) dialog.findViewById(R.id.img_picture);
        btnCamera = (Button) dialog.findViewById(R.id.btn_camera);
        btnAlbum = (Button) dialog.findViewById(R.id.btn_album);
        rdogr = (RadioGroup) dialog.findViewById(R.id.rdogr);
        rdoPhongkham = (RadioButton) dialog.findViewById(R.id.rdo_phongkham);
        rdoTainha = (RadioButton) dialog.findViewById(R.id.rdo_tainha);
        TIPTime = (TextInputLayout) dialog.findViewById(R.id.TIP_Time);
        TIEDTime = (TextInputEditText) dialog.findViewById(R.id.TIED_Time);
        TIPAddress = (TextInputLayout) dialog.findViewById(R.id.TIP_Address);
        TIEDAddress = (TextInputEditText) dialog.findViewById(R.id.TIED_Address);
        TIPService = (TextInputLayout) dialog.findViewById(R.id.TIP_Service);
        TIEDService = (TextInputEditText) dialog.findViewById(R.id.TIED_Service);
        btnUpdate = (Button) dialog.findViewById(R.id.btn_update);
        btnHuy = (Button) dialog.findViewById(R.id.btn_huy);
        SlectedSpinner();
        TIEDStatus.setText(bookObj.getStatus());
        TIEDAddress.setText(bookObj.getAddress());
        TIEDTime.setText(bookObj.getTime());
        TIEDService.setText(bookObj.getService());
        TIEDService.setFocusable(false);
        TIEDService.setFocusableInTouchMode(false);
        TIEDNameDoctor.setFocusable(false);
        TIEDNameDoctor.setFocusableInTouchMode(false);
        TIEDNamePet.setFocusable(false);
        TIEDNamePet.setFocusableInTouchMode(false);
        TIEDPhoneNumber.setFocusable(false);
        TIEDPhoneNumber.setFocusableInTouchMode(false);
        TIEDTypePet.setFocusable(false);
        TIEDTypePet.setFocusableInTouchMode(false);
        rdogr.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rdo_phongkham:
                        TIPAddress.setEnabled(false);
                        TIEDAddress.setText("");
                        break;
                    case R.id.rdo_tainha:
                        TIPAddress.setEnabled(true);
                        break;
                }
            }
        });
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(intent, REQUEST_CAMERA);
            }
        });
        btnAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                chooseImg.launch(i);
            }
        });
        byte[] hinhanh = bookObj.getPhoto_status();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhanh, 0, hinhanh.length);
        imgPicture.setImageBitmap(bitmap);
        if (bookObj.getLocation().equals("Phòng Khám")) {
            rdoPhongkham.setChecked(true);
        } else {
            rdoPhongkham.setChecked(false);
        }
        if (bookObj.getLocation().equals("Tại nhà")) {
            rdoTainha.setChecked(true);
        } else {
            rdoTainha.setChecked(false);
        }
        adapterSPNAnimal = new SpinnerAnimal();
        listAnimal = AnimalDB.getInstance(getActivity()).Dao().getAllData();
        adapterSPNAnimal.setData(listAnimal);
        spnPet.setAdapter(adapterSPNAnimal);

        adapterSPNDoctor = new SpinnerDoctor();
        listDoctor = DoctorDB.getInstance(getActivity()).Dao().getAllData();
        adapterSPNDoctor.setDATA(listDoctor);
        spnDoctor.setAdapter(adapterSPNDoctor);

        for (int i = 0; i < spnDoctor.getCount(); i++) {
            if (list.get(index).getId_doctor() == listDoctor.get(i).getId()) {
                spnDoctor.setSelection(i);
                spnDoctor.setSelected(true);
            }
        }
        for (int j = 0; j < spnPet.getCount(); j++) {
            if (list.get(index).getId_animal() == listAnimal.get(j).getId()) {
                spnPet.setSelection(j);
                spnPet.setSelected(true);
            }
        }

        if (rdoPhongkham.isChecked()) {
            noikham = "Phòng Khám";
        } else if (rdoTainha.isChecked()) {
            noikham = "Tại nhà";
        }
        TIEDService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDiaLogSerVice();
            }
        });


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_file", Context.MODE_PRIVATE);
        String user = sharedPreferences.getString("Username", "");
        usersObj = UsersDB.getInstance(getActivity()).Dao().getIdUsers(user);
        int id = usersObj.getId();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strTime = TIEDTime.getText().toString();
                String strDiaChi = TIEDAddress.getText().toString();
                String strDichVU = TIEDService.getText().toString();
                String strTT = TIEDStatus.getText().toString();
                if (rdoPhongkham.isChecked()) {
                    noikham = "Phòng Khám";
                } else if (rdoTainha.isChecked()) {
                    noikham = "Tại nhà";
                }
                BitmapDrawable bitmapDrawable = (BitmapDrawable) imgPicture.getDrawable();
                Bitmap bitmap2 = bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] anh = byteArrayOutputStream.toByteArray();
                bookObj.setId_user(id);
                bookObj.setId_doctor(idDoctor);
                bookObj.setId_animal(idPet);
                bookObj.setStatus(strTT);
                bookObj.setPhoto_status(anh);
                bookObj.setLocation(noikham);
                bookObj.setTime(strTime);
                bookObj.setAddress(strDiaChi);
                bookObj.setService(strDichVU);
                BookDB.getInstance(getActivity()).Dao().edit(bookObj);
                loadDATA();
                Toast.makeText(getActivity(), "Đã sửa", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }


    ActivityResultLauncher<Intent> chooseImg = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Uri selectImgUri = data.getData();
                        if (selectImgUri != null) {
                            imgPicture.setImageURI(selectImgUri);
                        }
                        BitmapDrawable bitmapDrawable = (BitmapDrawable) imgPicture.getDrawable();
                        bitmap = bitmapDrawable.getBitmap();
                    }
                }
            }
    );

    public void showDiaLogSerVice() {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_choose_service);
        dialog.getWindow().setBackgroundDrawable(getActivity().getDrawable(R.drawable.bg_dialog_dichvu));
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        window.setAttributes(windowAttributes);
        windowAttributes.gravity = Gravity.BOTTOM;
        imgClose = (ImageView) dialog.findViewById(R.id.img_close);
        btnKhamvachua = (Button) dialog.findViewById(R.id.btn_khamvachua);
        btnKiemtrasuckhoe = (Button) dialog.findViewById(R.id.btn_kiemtrasuckhoe);
        btnTiemphong = (Button) dialog.findViewById(R.id.btn_tiemphong);
        btnPhauthuat = (Button) dialog.findViewById(R.id.btn_phauthuat);
        btnSieuam = (Button) dialog.findViewById(R.id.btn_sieuam);
        btnSpa = (Button) dialog.findViewById(R.id.btn_spa);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnKhamvachua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TIEDService.setText("Khám và Chữa");
                dialog.dismiss();
            }
        });
        btnKiemtrasuckhoe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TIEDService.setText("Kiểm tra sức khỏe");
                dialog.dismiss();
            }
        });
        btnTiemphong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TIEDService.setText("Tiêm Phòng");
                dialog.dismiss();
            }
        });
        btnPhauthuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TIEDService.setText("Phẫu Thuật");
                dialog.dismiss();
            }
        });

        btnSieuam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TIEDService.setText("Siêu Âm");
                dialog.dismiss();
            }
        });
        btnSpa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TIEDService.setText("Spa - Cắt & Tỉa");
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void SlectedSpinner() {
        spnDoctor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                listDoctor = DoctorDB.getInstance(getActivity()).Dao().getAllData();
                idDoctor = listDoctor.get(position).getId();

                TIEDNameDoctor.setText(listDoctor.get(position).getName());
                TIEDPhoneNumber.setText(listDoctor.get(position).getPhone());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spnPet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                listAnimal = AnimalDB.getInstance(getActivity()).Dao().getAllData();
                idPet = listAnimal.get(position).getId();
                TIEDNamePet.setText(listAnimal.get(position).getName());
                TIEDTypePet.setText(listAnimal.get(position).getSpecies());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == getActivity().RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgPicture.setImageBitmap(bitmap);

        }
    }

    public void loadDATA() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_file", Context.MODE_PRIVATE);
        String user = sharedPreferences.getString("Username", "");
        usersObj = UsersDB.getInstance(getActivity()).Dao().getIdUsers(user);
        int id = usersObj.getId();
        list = BookDB.getInstance(getActivity()).Dao().getStatus2(1,id);
        adapter.setDATA(list);
    }
    public void loadDATA2() {
        list2 = BookDB.getInstance(getActivity()).Dao().getStatus(1);
        adapterAdmin.setDATA(list2);
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_file", Context.MODE_PRIVATE);
        String user = sharedPreferences.getString("Username", "");
        if (user.equalsIgnoreCase("Admin")) {
            list2 = BookDB.getInstance(getActivity()).Dao().getStatus(1);
            adapterAdmin.setDATA(list2);
        } else {
            usersObj = UsersDB.getInstance(getActivity()).Dao().getIdUsers(user);
            int id = usersObj.getId();
            list = BookDB.getInstance(getActivity()).Dao().getStatus2(1,id);
            adapter.setDATA(list);

        }
    }

    @Override
    public void onRefresh() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_file", Context.MODE_PRIVATE);
        String user = sharedPreferences.getString("Username", "");
        if (user.equalsIgnoreCase("Admin")) {
            list2 = BookDB.getInstance(getActivity()).Dao().getStatus(1);
            adapterAdmin.setDATA(list2);
        } else {
            usersObj = UsersDB.getInstance(getActivity()).Dao().getIdUsers(user);
            int id = usersObj.getId();
            list = BookDB.getInstance(getActivity()).Dao().getStatus2(1,id);
            adapter.setDATA(list);

        }
        Handler handler  = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        },1000);
    }
}