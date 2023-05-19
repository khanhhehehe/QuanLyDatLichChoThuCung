package com.example.quanlypet.adapter.bill;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlypet.R;
import com.example.quanlypet.database.UsersDB;
import com.example.quanlypet.model.AnimalObj;
import com.example.quanlypet.model.BillObj;
import com.example.quanlypet.model.UsersObj;

import java.util.ArrayList;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.BillViewHolder> implements Filterable {
    private Context context;
    private Callback callback;
    private ArrayList<BillObj> arrayList;
    private ArrayList<BillObj> listBill;
    public BillAdapter(Context context, Callback callback) {
        this.context = context;
        this.callback = callback;
    }
    public void setData(ArrayList<BillObj> arrayList){
        this.arrayList = arrayList;
        this.listBill = arrayList;
        notifyDataSetChanged();
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String search = constraint.toString();
                if (search.isEmpty()){
                    arrayList = listBill;
                }else{
                    ArrayList<BillObj> listdt = new ArrayList<>();
                    for (BillObj object: listBill){
                        if (object.getDate().toLowerCase().contains(search.toLowerCase())||
                                object.getTime().toLowerCase().contains(search.toLowerCase())){
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
                arrayList = (ArrayList<BillObj>) results.values;
                notifyDataSetChanged();
            }
        };
    }
    @NonNull
    @Override
    public BillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bill, parent, false);
        return new BillViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillViewHolder holder, int position) {
        Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.slide_up);
        BillObj object = arrayList.get(position);
        if (object == null)
            return;
        UsersObj usersObj = UsersDB.getInstance(context).Dao().getID(object.getId_users());
        holder.tvNameUsers.setText(usersObj.getFull_name());
        holder.tvDate.setText(object.getDate());
        holder.tvPriceBill.setText(object.getPrice()+"");
        holder.idItemBill.setOnLongClickListener(v -> {
            callback.Update(object);
            return false;
        });
        holder.itemView.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return arrayList == null?0:arrayList.size();
    }

    public class BillViewHolder extends RecyclerView.ViewHolder {
        private TextView tvPriceBill;
        private TextView tvDate;
        private CardView idItemBill;
        private TextView tvNameUsers;

        public BillViewHolder(@NonNull View itemView) {
            super(itemView);

            idItemBill = (CardView) itemView.findViewById(R.id.id_item_bill);
            tvNameUsers = (TextView) itemView.findViewById(R.id.tv_nameUsers);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
            tvPriceBill = (TextView) itemView.findViewById(R.id.tv_priceBill);
        }
    }
    public interface Callback{
        void Update(BillObj object);
    }
}