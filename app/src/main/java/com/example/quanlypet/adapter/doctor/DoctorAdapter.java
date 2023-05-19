package com.example.quanlypet.adapter.doctor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlypet.R;
import com.example.quanlypet.model.DoctorObj;
import com.example.quanlypet.ui.activity.DoctorInforActivity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;


public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DocterViewHolder> implements Filterable {
    private Context context;
    private ArrayList<DoctorObj> list;
    private ArrayList<DoctorObj> listDotor;
    private int checkGender;
    private DoctorObj docterObjNew;
    private Callback callback;
    public void setDataDocter(ArrayList<DoctorObj> list){
        this.list=list;
        this.listDotor=list;
        notifyDataSetChanged();
    }

    public DoctorAdapter(Context context,Callback callback) {
        this.context = context;
        this.callback=callback;
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String search = constraint.toString();
                if (search.isEmpty()){
                    list = listDotor;
                }else{
                    ArrayList<DoctorObj> listdt = new ArrayList<>();
                    for (DoctorObj object: listDotor){
                        if (object.getName().toLowerCase().contains(search.toLowerCase())||
                                object.getEmail().toLowerCase().contains(search.toLowerCase())||
                        object.getPhone().toLowerCase().contains(search.toLowerCase())||
                        object.getSpecialize().toLowerCase().contains(search.toLowerCase())){
                            listdt.add(object);
                        }
                    }
                    list = listdt;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = list;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list = (ArrayList<DoctorObj>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    @NonNull
    @Override
    public DocterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_docter,parent,false);
        return new DocterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DocterViewHolder holder, int position) {
        Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.slide_up);
        DoctorObj docterObj = list.get(position);
        if(docterObj==null)
            return;
        holder.tv_Name.setText(docterObj.getName());
        holder.tv_Email.setText(docterObj.getEmail());
        byte[] hinhanh = docterObj.getImg();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhanh, 0, hinhanh.length);
        holder.img_Docter.setImageBitmap(bitmap);


        if(docterObj.getGender()==1){
            holder.tv_Gender.setText("Nam");
        }else{
            holder.tv_Gender.setText("Nữ");
        }
        holder.id_RelativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                callback.update(docterObj);
                return false;
            }
        });
        holder.img_Information.setOnClickListener(v->{
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] ByteArray = byteArrayOutputStream.toByteArray();
            Intent intent = new Intent(context, DoctorInforActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("name",docterObj.getName());
                intent.putExtra("phone",docterObj.getPhone());
                intent.putExtra("address",docterObj.getAddress());
                intent.putExtra("specialize",docterObj.getSpecialize());
                intent.putExtra("img",ByteArray);
                context.startActivity(intent);
        });
        holder.itemView.startAnimation(animation);
    }
    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    public class DocterViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_Docter;
        private TextView tv_Name;
        private TextView tv_Email;
        private TextView tv_Gender;
        private RelativeLayout id_RelativeLayout;
        private ImageView img_Information;

        public DocterViewHolder(@NonNull View itemView) {
            super(itemView);
            id_RelativeLayout = (RelativeLayout) itemView.findViewById(R.id.id_relativeLayout);
            img_Docter = (ImageView) itemView.findViewById(R.id.img_doctor);
            img_Information = (ImageView) itemView.findViewById(R.id.img_information);
            tv_Name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_Email = (TextView) itemView.findViewById(R.id.tv_email);
            tv_Gender = (TextView) itemView.findViewById(R.id.tv_gender);
        }
    }
    public interface Callback{
        void update(DoctorObj doctorObj);
    }
}
