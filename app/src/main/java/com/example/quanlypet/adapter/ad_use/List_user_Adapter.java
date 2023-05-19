package com.example.quanlypet.adapter.ad_use;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlypet.R;
import com.example.quanlypet.adapter.booking.booking_admin_Adapter;
import com.example.quanlypet.model.BookObj;
import com.example.quanlypet.model.DoctorObj;
import com.example.quanlypet.model.UsersObj;
import com.example.quanlypet.ui.activity.DetailUsersActivity;

import java.util.ArrayList;
import java.util.List;

public class List_user_Adapter extends RecyclerView.Adapter<List_user_Adapter.ViewHolder> implements Filterable {
    List<UsersObj> list;
    List<UsersObj> listUsers;
    Context mContext;
    ClickItem clickItem;

    public interface ClickItem {
        void update(UsersObj obj);
    }

    public List_user_Adapter(Context mContext, ClickItem clickItem) {
        this.mContext = mContext;
        this.clickItem = clickItem;
    }

    public void setData(List<UsersObj> list) {
        this.list = list;
        this.listUsers = list;
        notifyDataSetChanged();
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String search = constraint.toString();
                if (search.isEmpty()){
                    list = listUsers;
                }else{
                    ArrayList<UsersObj> listdt = new ArrayList<>();
                    for (UsersObj object: listUsers){
                        if (object.getFull_name().toLowerCase().contains(search.toLowerCase())||
                                object.getEmail().toLowerCase().contains(search.toLowerCase())||
                                object.getPhone().toLowerCase().contains(search.toLowerCase())){
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
                list = (ArrayList<UsersObj>) results.values;
                notifyDataSetChanged();
            }
        };
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_users, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), android.R.anim.slide_in_left);
        UsersObj obj = list.get(position);
        holder.tvFullnameUsers.setText(obj.getFull_name());
        holder.tvPhoneUsers.setText(obj.getPhone());
        holder.itemView.setOnLongClickListener(view -> {
            clickItem.update(obj);
            return false;
        });
        holder.itemView.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0: list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvFullnameUsers;
        private TextView tvPhoneUsers;
        private CardView cardViewItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFullnameUsers = (TextView) itemView.findViewById(R.id.tv_fullnameUsers);
            tvPhoneUsers = (TextView) itemView.findViewById(R.id.tv_phoneUsers);
            cardViewItem = (CardView) itemView.findViewById(R.id.card_view_item);

        }
    }
}
