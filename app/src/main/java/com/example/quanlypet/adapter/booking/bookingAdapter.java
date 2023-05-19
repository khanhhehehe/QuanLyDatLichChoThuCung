package com.example.quanlypet.adapter.booking;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlypet.R;
import com.example.quanlypet.database.AnimalDB;
import com.example.quanlypet.database.BookDB;
import com.example.quanlypet.database.DoctorDB;
import com.example.quanlypet.model.AnimalObj;
import com.example.quanlypet.model.BookObj;
import com.example.quanlypet.model.DoctorObj;

import java.util.List;

public class bookingAdapter extends RecyclerView.Adapter<bookingAdapter.ViewHolder> {
    List<BookObj> list;
    Context mContext;
    private Callback callback;

    public interface Callback {
        void update(BookObj bookObj, int index);
    }

    public bookingAdapter(Callback callback, Context mContext) {
        this.mContext = mContext;
        this.callback = callback;
    }

    public void setDATA(List<BookObj> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booking, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), android.R.anim.slide_in_left);

        BookObj obj = list.get(position);
        int index = position;
        holder.tvTime.setText(obj.getTime());
        holder.tvService.setText(obj.getService());
        DoctorObj doctorObj = DoctorDB.getInstance(mContext).Dao().getIdDoctor(obj.getId_doctor() + "");
        holder.nameDoctor.setText(doctorObj.getName());
        holder.tvAddress.setText(doctorObj.getAddress());
        AnimalObj animalObj = AnimalDB.getInstance(mContext).Dao().getIDAnimal(obj.getId_animal() + "");
        holder.tvNamePet.setText(animalObj.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.update(obj, index);
            }
        });
        if (obj.getObj_status() == 1) {
            holder.linner_status.setBackgroundColor(Color.YELLOW);
        } else if (obj.getObj_status() == 2) {
            holder.linner_status.setBackgroundColor(Color.GRAY);
            holder.img_more.setVisibility(View.INVISIBLE);
        } else if (obj.getObj_status() == 3) {
            holder.linner_status.setBackgroundColor(Color.GREEN);
        } else if (obj.getObj_status() == 4) {
            holder.linner_status.setBackgroundColor(Color.BLUE);
            holder.img_more.setVisibility(View.INVISIBLE);
        }
        holder.img_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDiaLogHuy(obj, index);
            }
        });
        holder.itemView.startAnimation(animation);

    }

    public void showDiaLogHuy(BookObj bookObj, int index) {
        Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.dialog_cancel_book);
        dialog.getWindow().setBackgroundDrawable(mContext.getDrawable(R.drawable.bg_huy_booking));
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        window.setAttributes(windowAttributes);
        windowAttributes.gravity = Gravity.BOTTOM;
        Button btn_huy = dialog.findViewById(R.id.btn_huylich);
        Button btn_cancle = dialog.findViewById(R.id.btn_cancel);

        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookObj.setObj_status(2);
                BookDB.getInstance(mContext).Dao().edit(bookObj);
                list.set(index, bookObj);
                notifyDataSetChanged();
                Toast.makeText(mContext, "Hủy Lịch Thành Công", Toast.LENGTH_SHORT).show();

                dialog.dismiss();
            }
        });
        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout linner_status;
        private TextView titleDoctor;
        private TextView nameDoctor;
        private TextView gachngang;
        private TextView tvAddress;
        private TextView tvNamePet;
        private TextView gachngang2;
        private TextView tvService;
        private TextView tvTime;
        private ImageView img_more;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleDoctor = (TextView) itemView.findViewById(R.id.titleDoctor);
            nameDoctor = (TextView) itemView.findViewById(R.id.nameDoctor);
            gachngang = (TextView) itemView.findViewById(R.id.gachngang);
            tvAddress = (TextView) itemView.findViewById(R.id.tv_address);
            tvNamePet = (TextView) itemView.findViewById(R.id.tv_namePet);
            gachngang2 = (TextView) itemView.findViewById(R.id.gachngang2);
            tvService = (TextView) itemView.findViewById(R.id.tv_service);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            linner_status = itemView.findViewById(R.id.linner_status);
            img_more = itemView.findViewById(R.id.img_more);
        }
    }
}
