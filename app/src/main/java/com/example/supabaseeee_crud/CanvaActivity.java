package com.example.supabaseeee_crud;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CanvaActivity extends AppCompatActivity {

    private RelativeLayout relativeLayout;

    float lastTouchX, lastTouchY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_canva);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.canvaLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        View view = findViewById(R.id.canvaLayout);

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("TOUCHY", "sauce bolognaise");

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        lastTouchX = event.getX();
                        lastTouchY = event.getY();
                        break;
                    }

                    case MotionEvent.ACTION_MOVE: {
                        float deltaX = event.getX() - lastTouchX;
                        float deltaY = event.getY() - lastTouchY;

                        
                    }
                return false;
            }
        });

        relativeLayout = findViewById(R.id.canvaLayout);



        StringBuffer buf = new StringBuffer();
        try {
            InputStream istream = getAssets().open("test_floor.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(istream));
            String str;

            while ((str = reader.readLine()) != null) {
                buf.append(str + "\n");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        GridWorldData.gridGenFromString(buf.toString());

        GraphicsLoader.requestBitmap("floor.png", getAssets());

        GridPainter paintView = new GridPainter(this);
        relativeLayout.addView(paintView);
    }
}