package com.example.supabaseeee_crud;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class Painter extends View {

    Paint basicPaint;

    public Painter(Context context) {
        super(context);

        basicPaint = new Paint();
        basicPaint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(
                getLeft() + (getRight() - getLeft()) / 3,
                getTop() + (getBottom() - getTop()) / 3,
                getRight() - (getRight() - getLeft()) / 3,
                getBottom() - (getBottom() - getTop()) / 3, basicPaint);
    }
}
