package com.crcodings.samplezeal.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.crcodings.samplezeal.EditCaseActivity;
import com.crcodings.samplezeal.R;
import com.crcodings.samplezeal.model.ReportsModel;

import java.util.ArrayList;

public class ReportsAdapter extends RecyclerView.Adapter<ReportsAdapter.MyViewHolder>  {

    private ArrayList<ReportsModel> reportsModelArrayList;
    Context context;
    public ReportsAdapter(Context context, ArrayList<ReportsModel> reportsModelArrayList) {
        this.context = context;
        this.reportsModelArrayList = reportsModelArrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_reports, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final ReportsModel reportsModel = reportsModelArrayList.get(position);

        holder.textPatientName.setText(reportsModel.getName());
        holder.textCondition.setText(reportsModel.getConditionpatient());
        holder.textWoundType.setText(reportsModel.getWoundtype());
        holder.textDoctor.setText(reportsModel.getDoctorname());
        holder.textHospital.setText(reportsModel.getHospital());
        holder.textDate.setText(reportsModel.getDate());
        holder.textStatus.setText(reportsModel.getStatus());

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, EditCaseActivity.class);
                intent.putExtra("id",reportsModel.getId());
                intent.putExtra("Name",reportsModel.getName());
                intent.putExtra("Age",reportsModel.getAge());
                intent.putExtra("Conditionpatient",reportsModel.getConditionpatient());
                intent.putExtra("Woundtype",reportsModel.getWoundtype());
                intent.putExtra("History1",reportsModel.getHistory1());
                intent.putExtra("History2",reportsModel.getHistory2());
                intent.putExtra("History3",reportsModel.getHistory3());
                intent.putExtra("Doctorname",reportsModel.getDoctorname());
                intent.putExtra("Speciality",reportsModel.getSpeciality());
                intent.putExtra("Hospital",reportsModel.getHospital());
                intent.putExtra("Comments",reportsModel.getComments());
                intent.putExtra("Date",reportsModel.getDate());
                intent.putExtra("Dressingby",reportsModel.getDressingby());
                intent.putExtra("Status",reportsModel.getStatus());
                intent.putExtra("imagefolder",reportsModel.getImagefolder());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return reportsModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textPatientName;
        public TextView textCondition;
        public TextView textWoundType;
        public TextView textDoctor;
        public TextView textHospital;
        public TextView textDate;
        public TextView textStatus;
        ImageView imgEdit;

        public MyViewHolder(View view) {
            super(view);
            textPatientName = view.findViewById(R.id.textPatientName);
            textCondition = view.findViewById(R.id.textCondition);
            textWoundType = view.findViewById(R.id.textWoundType);
            textDoctor = view.findViewById(R.id.textDoctor);
            textHospital = view.findViewById(R.id.textHospital);
            textDate = view.findViewById(R.id.textDate);
            textStatus = view.findViewById(R.id.textStatus);
            imgEdit = view.findViewById(R.id.imgEdit);

        }
    }


}
