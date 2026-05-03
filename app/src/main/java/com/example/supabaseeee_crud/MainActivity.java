package com.example.supabaseeee_crud;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import com.example.supabaseeee_crud.databinding.ActivityMainBinding;

import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding binding;

    private ArrayList<Product> productList = new ArrayList<>();

    private ArrayAdapter<Product> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        /*
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAnchorView(R.id.fab)
                        .setAction("Action", null).show();
            }
        });

         */

        Button gotoCanva = findViewById(R.id.button_play);

        gotoCanva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getApplicationContext(), GridWorldActivity.class);
                startActivity(myIntent);
            }
        });

        Button button = findViewById(R.id.button_option);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getApplicationContext(), OptionActivity.class);
                startActivity(myIntent);
            }
        });

        button = findViewById(R.id.button_login);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(myIntent);
            }
        });

        button = findViewById(R.id.button_register);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(myIntent);
            }
        });

        button = findViewById(R.id.button_leaderboard);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getApplicationContext(), LeaderboardActivity.class);
                startActivity(myIntent);
            }
        });
    }


}