package com.example.supabaseeee_crud;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

public class GridPainter extends View {

    Paint basicPaint;

    // list of all bitmaps
    Bitmap imgMissing;
    Bitmap imgFloor;
    Bitmap imgWall;

    Rect cellRect;
    Rect destRect;

    int cellScreenSize;

    public int camera_x, camera_y;

    public GridPainter(Context context) {
        super(context);

        basicPaint = new Paint();
        basicPaint.setColor(Color.RED);

        imgFloor = GraphicsLoader.requestBitmap("floor.png", context.getAssets());
        imgWall = GraphicsLoader.requestBitmap("wall.png", context.getAssets());
        imgMissing = GraphicsLoader.requestBitmap("noimg.png", context.getAssets());

        // all images should have the same size (32x64)
        cellRect = new Rect(0, 0, imgMissing.getWidth(), imgMissing.getHeight());
        destRect = new Rect();
    }

	@Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(
                getLeft() + (float) (getRight() - getLeft()) / 3,
                getTop() + (float) (getBottom() - getTop()) / 3,
                getRight() - (float) (getRight() - getLeft()) / 3,
                getBottom() - (float) (getBottom() - getTop()) / 3, basicPaint);

        cellScreenSize = (getRight() - getLeft()) / 15;
        if (cellScreenSize % 2 == 1) cellScreenSize ++;

        for (int x = 0; x < GridWorldData.getWidth(); x++)
        {
            for (int y = 0; y < GridWorldData.getHeight(); y++)
            {
                StaticStructureID structureID = GridWorldData.getCell(x, y);

                Bitmap img;

                switch (structureID)
                {
                    case GROUND:
                        img = imgFloor;
                        break;

                    case BASIC_WALL:
                        img = imgWall;
                        break;

                    default:
                        img = imgMissing;
                        break;
                }

                destRect.bottom = getTop() + y * cellScreenSize + (cellScreenSize / 2) * 3 + camera_y;
                destRect.left = getLeft() + x * cellScreenSize - cellScreenSize / 2 + camera_x;
                destRect.top = getTop() + y * cellScreenSize - cellScreenSize / 2 + camera_y;
                destRect.right = getLeft() + x * cellScreenSize + cellScreenSize / 2 + camera_x;

                // Log.d("GridPainter", "draw at " + destRect.top + ":" + destRect.bottom + " " + destRect.left + ":" + destRect.right);

                canvas.drawBitmap(img, cellRect, destRect, null);
            }
        }

    }
}
