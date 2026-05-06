package com.example.supabaseeee_crud;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

public class Skills_RecyclerView_Adapter extends RecyclerView.Adapter<Skills_RecyclerView_Adapter.MyViewHolder> {
    private Fighting_interface_activity activity;
    ArrayList<SkillModel> skillsModelArrayList;
    Context context;

    public Skills_RecyclerView_Adapter(Context context, ArrayList<SkillModel> skillsModelArrayList, Fighting_interface_activity activity) {
        this.skillsModelArrayList = skillsModelArrayList;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public Skills_RecyclerView_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.skills_recycler_view_row, parent, false);
        return new Skills_RecyclerView_Adapter.MyViewHolder(view, activity); // allows to give the look of our rows
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

    // https://gist.github.com/grantland/cd70814fe4ac369e3e92
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // like an on create method grab all the views from our recycler_view_row layout file
        TextView SkillName;
        private String content;

        private Fighting_interface_activity activity;
        public MyViewHolder(@NonNull View itemView, Fighting_interface_activity activity) {
            super(itemView);
            itemView.setOnClickListener(this);
            SkillName = itemView.findViewById(R.id.idSkillName);
            content = SkillName.getText().toString();
            this.activity = activity;
        }

        @Override
        public void onClick(View view) {
            Log.d("Adapter", "onClick " + getLayoutPosition() + " " + content); // getLayoutPosition returns the index of the clicked button
            switch (getLayoutPosition()) {
                case 0:
                    Personnage enemy = Personnage.Combat_character_list.get(1);
                    enemy.health -= SkillModel.Skills_list.get(0).getSkillDamage();
                    Log.d("Adapter", "Slice");
                    break;
                case 1:
                    Personnage Hero = Personnage.Combat_character_list.get(0);
                    Hero.health += 10;
                    Log.d("Adapter", "Heal");
                    break;
                case 2:
                    Personnage Enemy = Personnage.Combat_character_list.get(1);
                    Enemy.health -= SkillModel.Skills_list.get(2).getSkillDamage();
                    Log.d("Adapter", "SUMMON DIVINE JUDGEMENT!!!");
                    break;
                case 3:
                    Enemy = Personnage.Combat_character_list.get(1);
                    Enemy.health -= SkillModel.Skills_list.get(3).getSkillDamage();
                    Log.d("Adapter", "Stare at him");
                    break;
                case 4:
                    Enemy = Personnage.Combat_character_list.get(1);
                    Enemy.health -= SkillModel.Skills_list.get(4).getSkillDamage();
                    Log.d("Adapter", "Make a joke");
                    break;
                case 5:
                    Enemy = Personnage.Combat_character_list.get(1);
                    Enemy.health -= SkillModel.Skills_list.get(5).getSkillDamage();
                    Log.d("Adapter", "Call 911");
                    break;

            }
            ArrayList<Integer> List_health_character_id = new ArrayList<>(Arrays.asList(R.id.HealthPlayer1Id, R.id.HealthPlayer2Id));
            for (int i = 0; i < Personnage.Combat_character_list.size(); i++) {
                Personnage Character = Personnage.Combat_character_list.get(i);
                int Health = Character.getHealth();
                TextView text = (TextView) activity.findViewById(List_health_character_id.get(i));
                text.setText(String.valueOf(Health));
            }

            // temporary check for enemy health
            if (Personnage.Combat_character_list.get(1).getHealth() <= 0)
            {
                activity.switchToGameWin();
            }
        }
    }
}