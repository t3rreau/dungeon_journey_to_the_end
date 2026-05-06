package com.example.supabaseeee_crud;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

public class Fighting_interface_activity extends AppCompatActivity {

     ArrayList<SkillModel> SkillsModel = new ArrayList<>(); // arraylist for the Skill recyclerview
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fighting_interface);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;


        });
        RecyclerView recyclerView = findViewById(R.id.SkillsSelectionId);
        setUpSkillModel();
        Skills_RecyclerView_Adapter adapter = new Skills_RecyclerView_Adapter(getApplicationContext(), SkillsModel);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
// Ouverture menu skills
        Button skills = findViewById(R.id.idSkills);

        skills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View ActionSelection = findViewById(R.id.HLayoutActionsId);
                ActionSelection.setVisibility(INVISIBLE);
                View SkillsSelection = findViewById(R.id.SkillsSelectionId);
                SkillsSelection.setVisibility(VISIBLE);


            }
        });


        // Set health
        ArrayList<Integer> List_health_character_id = new ArrayList<>(Arrays.asList(R.id.HealthPlayer1Id, R.id.HealthPlayer2Id));
        for (int i = 0; i < Personnage.Combat_character_list.size(); i++) {
            Personnage Character = Personnage.Combat_character_list.get(i);
            int Health = Character.getHealth();
            TextView text = (TextView) findViewById(List_health_character_id.get(i));
            text.setText(String.valueOf(Health));
        }
    }
        private void setUpSkillModel(){
            String[] SkillNames = getResources().getStringArray(R.array.Skills_txt); // we may use the same method to create the detail button onclick
            for (int i = 0; i < SkillNames.length; i++) {
                SkillsModel.add(new SkillModel(SkillNames[i]));
            }
        }

}