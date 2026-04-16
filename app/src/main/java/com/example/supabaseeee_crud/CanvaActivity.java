package com.example.supabaseeee_crud;

import android.os.Bundle;
import android.os.CountDownTimer;
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

    GridPainter paintView;

    boolean canRepaint = true;

    CountDownTimer fpsLimiter = new CountDownTimer(30, 10) {

        @Override
        public void onTick(long l) {

        }

        @Override
        public void onFinish() {
            canRepaint = true;
        }
    };

    CountDownTimer updateCaller = new CountDownTimer(30, 10) {
        @Override
        public void onFinish() {
            update();
        }

        @Override
        public void onTick(long l) {

        }
    };

    protected void update()
    {
        GridWorldData.update();
        updateCaller.start();

        if (canRepaint)
        {
            // tell the view to redraw itself
            paintView.invalidate();

            canRepaint = false;
            fpsLimiter.start();
        }
    }

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

        paintView = new GridPainter(this);

        view.setOnTouchListener(new View.OnTouchListener() {

            final GridPainter painter = paintView;

            @Override
            public boolean onTouch(View v, MotionEvent event) {


                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN: {
                        lastTouchX = event.getX();
                        lastTouchY = event.getY();

                        return true;
                    }

                    case MotionEvent.ACTION_MOVE: {
                        float deltaX = event.getX() - lastTouchX;
                        float deltaY = event.getY() - lastTouchY;

                        painter.cameraX += (int)deltaX;
                        painter.cameraY += (int)deltaY;

                        lastTouchX = event.getX();
                        lastTouchY = event.getY();


                        if (canRepaint)
                        {
                            // tell the view to redraw itself
                            painter.invalidate();

                            canRepaint = false;
                            fpsLimiter.start();
                        }

                        return true;
                    }
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

        GridEntityRoamer roamer = new GridEntityRoamer(1, 2);
        roamer.speed = 1f;
        roamer.moveTo(new TransformI2D(4, 4));

        relativeLayout.addView(paintView);

        updateCaller.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        updateCaller.cancel();
        fpsLimiter.cancel();
    }
}