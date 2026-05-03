package com.example.supabaseeee_crud;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

	private EditText widgetLogin;
	private EditText widgetPassword;

	private Button buttonSignIn;
	private TextView widgetMsg;

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

		widgetLogin = findViewById(R.id.editTextEmail);
		widgetPassword = findViewById(R.id.editTextPassword);

		buttonSignIn = findViewById(R.id.buttonLogin);

		widgetMsg = findViewById(R.id.msgLoginText);
		updateDisplayedText("");

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

								JSONObject user = new JSONObject(json.getString("user"));
								JSONObject userdata = new JSONObject(user.getString("user_metadata"));

								String pseudo = userdata.getString("pseudo");
								Log.d("Account", "got pseudo " + pseudo);
								SupabaseClient.setUserPseudo(pseudo);



								updateDisplayedText("logged in successfully");
							} else {
								if (json.has("code")) {
									String code = json.getString("code");
									Log.d("Account", "cannot register user");
									updateDisplayedText("failed to login : " + code);
								} else {
									Log.d("Account", "cannot register user");
									updateDisplayedText("failed to login");
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});




	}


	protected void updateDisplayedText(String text)
	{
		runOnUiThread(new Runnable() {

			@Override
			public void run() {

				// Stuff that updates the UI
				widgetMsg.setText(text);
			}
		});
	}
}