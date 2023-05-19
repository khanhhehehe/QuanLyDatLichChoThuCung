package com.example.quanlypet.adapter.animal;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlypet.R;
import com.example.quanlypet.model.AnimalObj;
import com.example.quanlypet.model.UsersObj;
import com.example.quanlypet.ui.activity.DoctorInforActivity;
import com.example.quanlypet.ui.activity.PatientActivity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder> implements Filterable {
    private Context context;
    private Callback callback;
    private ArrayList<AnimalObj> arrayList;
    private ArrayList<AnimalObj> listAnimal;


    public AnimalAdapter(Context context, Callback callback) {
        this.context = context;
        this.callback = callback;
    }
    public void setData(ArrayList<AnimalObj> arrayList){
        this.arrayList = arrayList;
        this.listAnimal = arrayList;
        notifyDataSetChanged();
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String search = constraint.toString();
                if (search.isEmpty()){
                    arrayList = listAnimal;
                }else{
                    ArrayList<AnimalObj> listdt = new ArrayList<>();
                    for (AnimalObj object: listAnimal){
                        if (object.getName().toLowerCase().contains(search.toLowerCase())||
                                object.getSpecies().toLowerCase().contains(search.toLowerCase())){
                            listdt.add(object);
                        }
                    }
                    arrayList = listdt;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = arrayList;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                arrayList = (ArrayList<AnimalObj>) results.values;
                notifyDataSetChanged();
            }
        };
    }
    @NonNull
    @Override
    public AnimalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_animal, parent, false);
        return new AnimalViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull AnimalViewHolder holder, int position) {
        Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), android.R.anim.slide_in_left);
        AnimalObj object = arrayList.get(position);
        if (object == null)
            return;
        holder.tvNameAnimal.setText(object.getName());
        byte[] anh = object.getAvatar();
        Bitmap bitmap = BitmapFactory.decodeByteArray(anh, 0, anh.length);
        holder.imgAnhItem.setImageBitmap(bitmap);
        holder.tvAge.setText(object.getAge()+"");
        holder.tvLoai.setText(object.getSpecies());
        holder.relyAnimal.setOnLongClickListener(v ->{
            callback.Update(object);
            return false;
        });
        holder.imgInformation.setOnClickListener(v ->{
            Dialog dialog = new Dialog(v.getContext());
            dialog.setContentView(R.layout.dialog_benh_an);
            dialog.getWindow().setBackgroundDrawable(context.getDrawable(R.drawable.bg_huy_booking));
            Window window = dialog.getWindow();
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            WindowManager.LayoutParams windowAttributes = window.getAttributes();
            window.setAttributes(windowAttributes);
            windowAttributes.gravity = Gravity.BOTTOM;
            CardView CVTaobenhan;
            Button btnTaoBenhAn;
            CardView CVXembenhan;
            Button btnXemBenhAn = dialog.findViewById(R.id.btn_xemBenhAn);
            Button btnCancel  = dialog.findViewById(R.id.btn_cancel);
            btnXemBenhAn.setOnClickListener(view->{
                Intent intent = new Intent(v.getContext(), PatientActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("obj", object);
                intent.putExtras(bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            });
            btnCancel.setOnClickListener(view->{
                dialog.dismiss();
            });
            dialog.show();
        });
        holder.itemView.startAnimation(animation);
    }
    @Override
    public int getItemCount() {
        return arrayList == null ? 0: arrayList.size();
    }

    public class AnimalViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout relyAnimal;
        private TextView tvNameAnimal;
        private ImageView imgAnhItem;
        private TextView tvAge;
        private TextView tvLoai;
        private ImageView imgInformation;
        public AnimalViewHolder(@NonNull View itemView) {
            super(itemView);
            relyAnimal = (RelativeLayout) itemView.findViewById(R.id.rely_animal);
            tvNameAnimal = (TextView) itemView.findViewById(R.id.tv_nameAnimal);
            imgAnhItem = (ImageView) itemView.findViewById(R.id.img_anh_item);
            tvAge = (TextView) itemView.findViewById(R.id.tv_age);
            tvLoai = (TextView) itemView.findViewById(R.id.tv_loai);
            imgInformation = (ImageView) itemView.findViewById(R.id.img_information);
        }
    }
    public interface Callback{
        void Update(AnimalObj object);
    }
}
