package com.example.quanlypet.adapter.Spinner;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quanlypet.R;
import com.example.quanlypet.model.AnimalObj;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SpinnerAnimal extends BaseAdapter {
    private CircleImageView imgAnimal;
    private TextView tvNameAnimal;
    private TextView tvTypeAnimal;
    List<AnimalObj> list;

    public void setData(List<AnimalObj> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        AnimalObj obj = list.get(position);
        return obj;
    }

    @Override
    public long getItemId(int position) {
        AnimalObj obj = list.get(position);
        return obj.getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = View.inflate(parent.getContext(), R.layout.spinner_animal, null);
        } else {
            view = convertView;
        }


        imgAnimal = (CircleImageView) view.findViewById(R.id.img_animal);
        tvNameAnimal = (TextView) view.findViewById(R.id.tv_nameAnimal);
        tvTypeAnimal = (TextView) view.findViewById(R.id.tv_typeAnimal);
        AnimalObj animalObj = list.get(position);
        tvNameAnimal.setText("Tên: "+animalObj.getName());
        tvTypeAnimal.setText("Giống: "+animalObj.getSpecies());
        byte[] anh = animalObj.getAvatar();
        Bitmap bitmap = BitmapFactory.decodeByteArray(anh,0, anh.length);
        imgAnimal.setImageBitmap(bitmap);


        return view;
    }
}
