package com.example.supabaseeee_crud;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.supabaseeee_crud.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding binding;

    private ArrayList<Product> productList = new ArrayList<>();

    private ArrayAdapter<Product> adapter;
    // Definition of characters :

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());




        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAnchorView(R.id.fab)
                        .setAction("Action", null).show();
            }
        });
// Test Fighting system
        Personnage Hero = new Personnage("Hero", 65, 80);
        Personnage Enemy = new Personnage( "Enemy", 28, 15);
        ArrayList<Personnage> Combat_character_list = new ArrayList<Personnage>();
        Combat_character_list.add(Hero);
        Combat_character_list.add(Enemy);
        Fighting_system.Combat(Combat_character_list);

        Button fighting_button = findViewById(R.id.fighting_button);

        fighting_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getApplicationContext(), Fighting_interface_activity.class);
                startActivity(myIntent);
            }
        });
    }




}