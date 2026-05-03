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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText widgetLogin;
    private EditText widgetPassword;
    private Button buttonSignUp;
    private EditText widgetPseudo;
    private TextView widgetMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        /*
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

         */

        widgetLogin = findViewById(R.id.editTextRegEmail);
        widgetPseudo = findViewById(R.id.editTextRegPseudo);
        widgetPassword = findViewById(R.id.editTextRegPassword);
        buttonSignUp = findViewById(R.id.buttonCreateAccount);
        widgetMsg = findViewById(R.id.msgRegisterText);
        updateDisplayedText("");

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (widgetPseudo.getText().toString().isEmpty())
                {
                    updateDisplayedText("choose a pseudo");
                    return;
                }
                if (widgetLogin.getText().toString().isEmpty())
                {
                    updateDisplayedText("put an email");
                    return;
                }
                if (widgetPassword.getText().toString().isEmpty())
                {
                    updateDisplayedText("a password is needed");
                    return;
                }

                SupabaseClient.signUp(widgetLogin.getText().toString(), widgetPassword.getText().toString(), widgetPseudo.getText().toString(), new Callback() {
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

                                String userData = json.getString("user");
                                JSONObject userJson = new JSONObject(userData);
                                String userId = userJson.getString("id");
                                SupabaseClient.setUserId(userId);
                                SupabaseClient.setUserPseudo(widgetPseudo.getText().toString());

                                updateDisplayedText("signed up successfully");
                                Log.d("Account", "logged in");
                            }
                            else
                            {
                                if (json.has("code"))
                                {
                                    String code = json.getString("code");
                                    if (code.equals("422"))
                                    {
                                        Log.d("Account", "user already registered");
                                        updateDisplayedText("this user already exists");
                                    }
                                }
                                else
                                {
                                    Log.d("Account", "cannot register user");
                                    updateDisplayedText("cannot register this user");
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