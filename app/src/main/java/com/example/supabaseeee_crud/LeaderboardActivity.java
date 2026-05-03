package com.example.supabaseeee_crud;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LeaderboardActivity extends AppCompatActivity {

	TextView widgetLeaderboard;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EdgeToEdge.enable(this);
		setContentView(R.layout.activity_leaderboard);

		/*
		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
			Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
			return insets;
		});
		*/

		widgetLeaderboard = findViewById(R.id.textLeaderboard);

		SupabaseClient.getAllScores(new Callback() {
			@Override
			public void onFailure(@NonNull Call call, @NonNull IOException e) {

			}

			@Override
			public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
				String data = response.body().string();
				Log.d("Leadeboard", "obtained " + data);
				JSONArray scores = null;
                try {
                    scores = new JSONArray(data);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                Log.d("leaderboard", "got scores");

				ArrayList<JSONObject> rows = new ArrayList<>();
				StringBuilder boardText = new StringBuilder();
				for (int i = 0; i < scores.length(); i++)
				{
                    JSONObject score = null;
                    try {
                        score = scores.getJSONObject(i);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    rows.add(score);
				}

				rows.sort(new Comparator<JSONObject>() {
					@Override
					public int compare(JSONObject o1, JSONObject o2) {
						try {

							return o1.getInt("time") - o2.getInt("time");
						}
						catch (Exception e)
						{
							e.printStackTrace();
							return 0;
						}
					}
				});

				for (int i = 0; i < rows.size(); i++)
				{
					JSONObject row = rows.get(i);
                    try {
                        boardText.append(row.getString("displayName") + " : " + row.getString("time") + "ms\n");
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }

				updateDisplayedText(boardText.toString());
			}
		});
	}

	protected void updateDisplayedText(String text)
	{
		runOnUiThread(new Runnable() {

			@Override
			public void run() {

				// Stuff that updates the UI
				widgetLeaderboard.setText(text);
			}
		});
	}
}