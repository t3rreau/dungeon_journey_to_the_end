package com.example.supabaseeee_crud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

	private EditText widgetLogin;
	private EditText widgetPassword;

	private Button buttonSignUp;
	private Button buttonSignIn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EdgeToEdge.enable(this);
		setContentView(R.layout.activity_login);

		/*
		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ac), (v, insets) -> {
			Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
			return insets;
		});

		 */

		buttonSignIn = findViewById(R.id.buttonLogin);

		buttonSignIn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent myIntent = new Intent(getApplicationContext(), LoginActivity.class);
				startActivity(myIntent);
			}
		});

		buttonSignUp = findViewById(R.id.buttonRegister);

		buttonSignUp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent myIntent = new Intent(getApplicationContext(), LoginActivity.class);
				startActivity(myIntent);
			}
		});


	}
}