package com.example.supabaseeee_crud;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class SupabaseClient {
    private static final String BASE_URL = "https://vwfcbfgrsrkuihoyrvky.supabase.co/rest/v1/Products";
    private static final String API_KEY = "sb_publishable_Nf00CxWzFSNTHMDax8YewQ_HOzZpaik";
    private static final OkHttpClient client = new OkHttpClient();

    private static Request.Builder base(String url) {
        return new Request.Builder()
                .url(url)
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json");
    }

    //READ
    public static void getALL(Callback callback){
        Request request = base(BASE_URL).get().build();
        client.newCall(request).enqueue(callback);

    }
}
