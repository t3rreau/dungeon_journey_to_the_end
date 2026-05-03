package com.example.supabaseeee_crud;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Skills_RecyclerView_Adapter extends RecyclerView.Adapter<Skills_RecyclerView_Adapter.MyViewHolder> {
    ArrayList<SkillModel> skillsModelArrayList;
    Context context;

    public Skills_RecyclerView_Adapter(Context context, ArrayList<SkillModel> skillsModelArrayList) {
        this.skillsModelArrayList = skillsModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public Skills_RecyclerView_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.skills_recycler_view_row, parent, false);
        return new Skills_RecyclerView_Adapter.MyViewHolder(view); // allows to give the look of our rows
    }

    @Override
    public void onBindViewHolder(@NonNull Skills_RecyclerView_Adapter.MyViewHolder holder, int position) {
        // Assigning value to each of our rows
        holder.SkillName.setText(skillsModelArrayList.get(position).getSkillName());
    }

    @Override
    public int getItemCount() { // number of items in total
        return skillsModelArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // like an on create method grab all the views from our recycler_view_row layout file
        TextView SkillName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            SkillName = itemView.findViewById(R.id.idSkillName);

        }
    }
}
