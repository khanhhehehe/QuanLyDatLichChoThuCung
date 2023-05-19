package com.example.quanlypet.adapter.Spinner;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quanlypet.R;
import com.example.quanlypet.model.DoctorObj;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SpinnerDoctor extends BaseAdapter {
    private CircleImageView imgAnimal;
    private TextView tvNameAnimal;
    private TextView tvTypeAnimal;
    List<DoctorObj> list;

    public void setDATA(List<DoctorObj> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        DoctorObj doctorObj = list.get(position);
        return doctorObj;
    }

    @Override
    public long getItemId(int position) {
        DoctorObj doctorObj = list.get(position);
        return doctorObj.getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = View.inflate(parent.getContext(), R.layout.spinner_doctor, null);
        } else {
            view = convertView;
        }
        DoctorObj doctorObj = list.get(position);

        imgAnimal = (CircleImageView) view.findViewById(R.id.img_animal);
        tvNameAnimal = (TextView) view.findViewById(R.id.tv_nameAnimal);
        tvTypeAnimal = (TextView) view.findViewById(R.id.tv_typeAnimal);
        tvNameAnimal.setText("Tên: "+doctorObj.getName());
        tvTypeAnimal.setText("Chuyên ngành: "+doctorObj.getSpecialize());
        byte[] anh = doctorObj.getImg();
        Bitmap bitmap = BitmapFactory.decodeByteArray(anh, 0, anh.length);
        imgAnimal.setImageBitmap(bitmap);


        return view;

    }
}
