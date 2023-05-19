package com.example.quanlypet.adapter.booking;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlypet.R;
import com.example.quanlypet.database.BookDB;
import com.example.quanlypet.database.UsersDB;
import com.example.quanlypet.model.BookObj;
import com.example.quanlypet.model.UsersObj;
import com.example.quanlypet.ui.activity.AddBillActivity;
import com.example.quanlypet.ui.activity.AddPatientActivity;

import java.util.List;

public class booking_admin_Adapter extends RecyclerView.Adapter<booking_admin_Adapter.ViewHolder> {
    List<BookObj> list;
    Context mContext;
    private Callback callback;

    public interface Callback {
        void updateAdmin(BookObj bookObj, int index);
    }

    public booking_admin_Adapter(List<BookObj> list, Context mContext, Callback callback) {
        this.list = list;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_admin, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.slide_up);

        BookObj obj = list.get(position);
        int index = position;
        UsersObj usersObj = UsersDB.getInstance(mContext).Dao().getID(obj.getId_user());
        holder.nameDoctor.setText(usersObj.getFull_name());
        holder.tvService.setText(obj.getService());
        holder.tvTime.setText(obj.getTime());
        if (obj.getObj_status() == 1) {
            holder.linnerStatus.setBackgroundColor(Color.YELLOW);
        } else if (obj.getObj_status() == 2) {
            holder.linnerStatus.setBackgroundColor(Color.GRAY);
            holder.imgMore.setVisibility(View.INVISIBLE);
        } else if (obj.getObj_status() == 3) {
            holder.linnerStatus.setBackgroundColor(Color.GREEN);

        } else if (obj.getObj_status() == 4) {
            holder.linnerStatus.setBackgroundColor(Color.BLUE);
            holder.imgMore.setVisibility(View.INVISIBLE);

        }
        holder.imgMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDiaLogHuy(obj, index);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.updateAdmin(obj, index);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Dialog dialog = new Dialog(mContext);
                dialog.setContentView(R.layout.dialog_create_bill);
                dialog.getWindow().setBackgroundDrawable(mContext.getDrawable(R.drawable.bg_huy_booking));
                Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                WindowManager.LayoutParams windowAttributes = window.getAttributes();
                window.setAttributes(windowAttributes);
                windowAttributes.gravity = Gravity.BOTTOM;
                CardView CVTaobill;
                Button btnTaobill;
                Button btnCancel;
                CVTaobill = (CardView) dialog.findViewById(R.id.CV_taobill);
                btnTaobill = (Button) dialog.findViewById(R.id.btn_taobill);
                btnCancel = (Button) dialog.findViewById(R.id.btn_cancel);
                Button btnTaopatient;
                btnTaopatient = (Button) dialog.findViewById(R.id.btn_taopatient);
                btnTaobill.setOnClickListener(view -> {
                    Intent intent = new Intent(mContext, AddBillActivity.class);
                    SharedPreferences sharedPreferences = mContext.getSharedPreferences("Users_info_id", mContext.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("userId", obj.getId_user());
                    editor.putString("obj_service", obj.getService());
                    editor.commit();
                    mContext.startActivity(intent);
                    dialog.dismiss();
                });
                btnTaopatient.setOnClickListener(view->{
                    Intent intent = new Intent(mContext, AddPatientActivity.class);
                    SharedPreferences sharedPreferences = mContext.getSharedPreferences("Animal_id_doctor_id", mContext.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("doctorId",obj.getId_doctor());
                    editor.putInt("animalId",obj.getId_animal());
                    editor.commit();
                    mContext.startActivity(intent);
                    dialog.dismiss();
                });
                btnCancel.setOnClickListener(view -> {
                    dialog.cancel();
                });

                if (obj.getObj_status() == 4) {
                    dialog.show();
                }
                return false;
            }
        });
        holder.itemView.startAnimation(animation);
    }


    public void showDiaLogHuy(BookObj bookObj, int index) {
        Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.dialog_book_confirm_done);
        dialog.getWindow().setBackgroundDrawable(mContext.getDrawable(R.drawable.bg_huy_booking));
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        window.setAttributes(windowAttributes);
        windowAttributes.gravity = Gravity.BOTTOM;
        CardView cv_xacnhan = dialog.findViewById(R.id.CV_xacnhan);
        CardView cv_hoanthanh = dialog.findViewById(R.id.CV_hoanthanh);
        Button btn_xacnhan = dialog.findViewById(R.id.btn_xacnhan);
        Button btn_hoanthanh = dialog.findViewById(R.id.btn_hoanthanh);
        Button btn_cancle = dialog.findViewById(R.id.btn_cancel);
        if (bookObj.getObj_status() == 3) {
            btn_xacnhan.setVisibility(View.GONE);
            cv_xacnhan.setVisibility(View.GONE);
        }
        if (bookObj.getObj_status() == 1) {
            btn_hoanthanh.setVisibility(View.GONE);
            cv_hoanthanh.setVisibility(View.GONE);
        }
        btn_xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookObj.setObj_status(3);
                BookDB.getInstance(mContext).Dao().edit(bookObj);
                list.set(index, bookObj);
                notifyDataSetChanged();
                Toast.makeText(mContext, "Xác nhận Thành Công", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        btn_hoanthanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookObj.setObj_status(4);
                BookDB.getInstance(mContext).Dao().edit(bookObj);
                list.set(index, bookObj);
                notifyDataSetChanged();
                Toast.makeText(mContext, "Lịch Đặt Đã Được Hoàn Thành", Toast.LENGTH_SHORT).show();
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
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout linnerStatus;
        private TextView titleDoctor;
        private TextView nameDoctor;
        private TextView tvService;
        private TextView tvTime;
        private ImageView imgMore;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linnerStatus = (LinearLayout) itemView.findViewById(R.id.linner_status);
            titleDoctor = (TextView) itemView.findViewById(R.id.titleDoctor);
            nameDoctor = (TextView) itemView.findViewById(R.id.nameDoctor);
            tvService = (TextView) itemView.findViewById(R.id.tv_service);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            imgMore = (ImageView) itemView.findViewById(R.id.img_more);
        }
    }
}
