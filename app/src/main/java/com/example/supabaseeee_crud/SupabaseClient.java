package com.example.supabaseeee_crud;

import org.json.JSONObject;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class SupabaseClient {
    private static final String SUPABASE_URL = "https://ssdacwlhutmamdmoyvqc.supabase.co";
    private static final String API_KEY = "sb_publishable_2n6G8xjPykpriI6wjloqzA_x2ghR8tI";
    private static final String BASE_URL = SUPABASE_URL + "/rest/v1/scores";
    private static final OkHttpClient client = new OkHttpClient();

    // Token de l'utilisateur connecté
    private static String userToken = "";
    private static String userName = "";

    public static void setUserToken(String token) {
        userToken = token;
    }

    public static String getUserToken() {return userToken;}

    public static void setUserName(String name) {userName = name;}

    private static Request.Builder base(String url){
        Request.Builder builder = new  Request.Builder()
                .url(url)
                .addHeader("apikey", API_KEY)
                .addHeader("Content-Type", "application/json");

        // Si on a un token utilisateur, on l'utilise, sinon on utilise la clé API (anonyme)
        String authHeader = (userToken != null) ? "Bearer " + userToken : "Bearer " + API_KEY;
        builder.addHeader("Authorization", authHeader);

        return builder;
    }

    // --- AUTHENTIFICATION ---

    public static void signUp(String email, String password, Callback callback) {
        try {
            JSONObject json = new JSONObject();
            json.put("email", email);
            json.put("password", password);
            RequestBody body = RequestBody.create(json.toString(), MediaType.parse("application/json"));
            Request request = base(SUPABASE_URL + "/auth/v1/signup").post(body).build();
            client.newCall(request).enqueue(callback);
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void signIn(String email, String password, Callback callback) {
        try {
            JSONObject json = new JSONObject();
            json.put("email", email);
            json.put("password", password);
            RequestBody body = RequestBody.create(json.toString(), MediaType.parse("application/json"));
            Request request = base(SUPABASE_URL + "/auth/v1/token?grant_type=password").post(body).build();
            client.newCall(request).enqueue(callback);
        } catch (Exception e) { e.printStackTrace(); }
    }

    // --- CRUD PRODUITS ---

    public static void getAll(Callback callback){
        Request request = base(BASE_URL).get().build();
        client.newCall(request).enqueue(callback);
    }

    public static void insert(String name, double price, Callback callback) {
        try {
            JSONObject json = new JSONObject();
            json.put("name", name);
            json.put("price", price);
            RequestBody body = RequestBody.create(json.toString(), MediaType.parse("application/json"));
            Request request = base(BASE_URL).post(body).build();
            client.newCall(request).enqueue(callback);
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void insertScore(long time_ms, Callback callback) {
        if (userToken.isEmpty()) return;
        try {
            JSONObject json = new JSONObject();
            json.put("displayName", userName);
            json.put("time", time_ms);
            RequestBody body = RequestBody.create(json.toString(), MediaType.parse("application/json"));
            Request request = base(BASE_URL).post(body).build();
            client.newCall(request).enqueue(callback);
        } catch (Exception e) { e.printStackTrace(); }
    }

    // ... autres méthodes update/delete utilisent déjà base(url) donc elles sont OK
    public static void update(int id, String name, double price, Callback callback) {
        try {
            JSONObject json = new JSONObject();
            json.put("name", name);
            json.put("price", price);
            RequestBody body = RequestBody.create(json.toString(), MediaType.parse("application/json"));
            Request request = base(BASE_URL + "?id=eq." + id).patch(body).build();
            client.newCall(request).enqueue(callback);
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void delete(int id, Callback callback) {
        Request request = base(BASE_URL + "?id=eq." + id).delete().build();
        client.newCall(request).enqueue(callback);
    }
}