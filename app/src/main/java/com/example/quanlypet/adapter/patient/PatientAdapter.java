package com.example.quanlypet.adapter.patient;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlypet.R;
import com.example.quanlypet.database.AnimalDB;
import com.example.quanlypet.database.DoctorDB;
import com.example.quanlypet.database.PatientDB;
import com.example.quanlypet.model.AnimalObj;
import com.example.quanlypet.model.DoctorObj;
import com.example.quanlypet.model.PatientObj;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.PatientViewHolder> {
    private Context context;
    private ArrayList<PatientObj> list;
    private PatientObj  patientObjNew;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public void setDataPatient(ArrayList<PatientObj> list){
        this.list=list;
        notifyDataSetChanged();
    }

    public PatientAdapter(Context context) {
        this.context = context;
    }
    @NonNull
    @Override
    public PatientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_patient,parent,false);
        return new PatientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientViewHolder holder, int position) {
        PatientObj patientObj = list.get(position);
        if(patientObj==null)
            return;
        holder.tvId.setText(patientObj.getId()+"");
        holder.tvDateCreate.setText(patientObj.getDate_create());
        holder.tvDateUpdate.setText(patientObj.getDate_update());
        holder.tvSysptems.setText(patientObj.getSysptems());
        holder.tvInstructions.setText(patientObj.getInstructions());
        holder.tvDiagnostic.setText(patientObj.getDiagnostic());

        holder.idRelativeLayout.setOnClickListener(v->{
            Dialog dialog = new Dialog(v.getContext(), androidx.appcompat.R.style.ThemeOverlay_AppCompat_Dialog_Alert);
            dialog.setContentView(R.layout.activity_add_patient);
            dialog.setCancelable(false);

            TextInputEditText edDateCreate = dialog. findViewById(R.id.ed_dateCreate);
            TextInputEditText edDateUpdate= dialog. findViewById(R.id.ed_dateUpdate);
            Button btnAddPatient= dialog.findViewById(R.id.btn_addPatient);
            Button btnCanel= dialog.findViewById(R.id.btn_canel);
            btnAddPatient.setText("Sửa");
            btnAddPatient.setOnClickListener(v1->{

                String dateCreate = edDateCreate.getText().toString().trim();
                String dateUpdate = edDateUpdate.getText().toString().trim();

                if ( dateCreate.isEmpty() || dateUpdate.isEmpty()){
                    Toast.makeText(v1.getContext(), "Không được để trống!", Toast.LENGTH_SHORT).show();
                } else {
                    patientObjNew = patientObj;
                    patientObjNew.setDate_create(dateCreate);
                    patientObjNew.setDate_update(dateUpdate);
                    PatientDB.getInstance(v.getContext()).Dao().edit(patientObj);
                    list = (ArrayList<PatientObj>) PatientDB.getInstance(v.getContext()).Dao().getAllData();
                    setDataPatient(list);
                    Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
            btnCanel.setOnClickListener(v1->{
                dialog.cancel();
            });
            dialog.show();
        });
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    public class PatientViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout idRelativeLayout;
        private ImageView imgDocter;
        private TextView tvId;
        private TextView tvDateCreate;
        private TextView tvDateUpdate;
        private TextView tvSysptems;
        private TextView tvDiagnostic;
        private TextView tvInstructions;


        public PatientViewHolder(@NonNull View itemView) {
            super(itemView);

            idRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.id_relativeLayout);
            imgDocter = (ImageView) itemView.findViewById(R.id.img_doctor);
            tvId = (TextView) itemView.findViewById(R.id.tv_id);

            tvDateCreate = (TextView) itemView.findViewById(R.id.tv_dateCreate);
            tvDateUpdate = (TextView) itemView.findViewById(R.id.tv_dateUpdate);
            tvSysptems = (TextView) itemView.findViewById(R.id.tv_sysptems);
            tvDiagnostic = (TextView) itemView.findViewById(R.id.tv_diagnostic);
            tvInstructions = (TextView) itemView.findViewById(R.id.tv_instructions);

        }
    }
}
