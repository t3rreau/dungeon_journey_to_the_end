package com.example.supabaseeee_crud;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class TimeRecorder
{
	private static long time_mark;
	private static long accumulated_time;
	private static boolean paused;

	// at the start of a run, launch the timer
	public static void initRun()
	{
		time_mark = System.currentTimeMillis();
		accumulated_time = 0;
		paused = false;
	}

	public static void setPaused(boolean paused_)
	{
		if (paused_ == paused) return;

		if (paused_)
		{
			accumulated_time += System.currentTimeMillis() - time_mark;
		}
		else
		{
			time_mark = System.currentTimeMillis();
		}
		paused = paused_;
	}

	public static void publishTime()
	{
		setPaused(true);
		if (!SupabaseClient.getUserToken().isEmpty())
		{
			SupabaseClient.insertScore(accumulated_time, new Callback() {
				@Override
				public void onFailure(@NonNull Call call, @NonNull IOException e) {

				}

				@Override
				public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
					Log.d("TimeRecorder", "published time");
				}
			});
		}
		else
		{
			Log.e("TimeRecorder", "User is not logged in");
		}
	}
}
