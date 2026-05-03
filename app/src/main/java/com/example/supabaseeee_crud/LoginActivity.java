package com.example.supabaseeee_crud;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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

		widgetLogin = findViewById(R.id.editTextUsername);
		widgetPassword = findViewById(R.id.editTextPassword);

		buttonSignIn = findViewById(R.id.buttonLogin);

		buttonSignIn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				SupabaseClient.signIn(widgetLogin.getText().toString(), widgetPassword.getText().toString(), new Callback() {
					@Override
					public void onFailure(@NonNull Call call, @NonNull IOException e) {

					}

					@Override
					public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
						String data = response.body().string();
						Log.d("Account", "received data : " + data);

						try {
							JSONObject json = new JSONObject(data);

							if (json.has("access_token")) {
								String token = json.getString("access_token");
								SupabaseClient.setUserToken(token);
							} else {
								if (json.has("code")) {
									String code = json.getString("code");
									Log.d("Account", "cannot register user");
								} else {
									Log.d("Account", "cannot register user");
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});

		buttonSignUp = findViewById(R.id.buttonRegister);

		buttonSignUp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				SupabaseClient.signUp(widgetLogin.getText().toString(), widgetPassword.getText().toString(), new Callback() {
					@Override
					public void onFailure(@NonNull Call call, @NonNull IOException e) {

					}

					@Override
					public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException
					{
						String data = response.body().string();
						Log.d("Account", "received data : " + data);

						try
						{
							JSONObject json = new JSONObject(data);

							if (json.has("access_token"))
							{
								String token = json.getString("access_token");
								SupabaseClient.setUserToken(token);
							}
							else
							{
								if (json.has("code"))
								{
									String code = json.getString("code");
									if (code.equals("422"))
									{
										Log.d("Account", "user already registered");
									}
								}
								else
								{
									Log.d("Account", "cannot register user");
								}
							}
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
				});

			}
		});


	}
}