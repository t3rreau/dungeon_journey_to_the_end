package com.example.supabaseeee_crud;

import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.EnumMap;

public class GridWorld extends SurfaceView implements SurfaceHolder.Callback
{

	GridWorldLoop loop = new GridWorldLoop(this, getHolder());

	Bitmap tilePlaceholder;
	Bitmap entityPlaceholder;

	Map<StaticStructureID, String> tileFilenames = new EnumMap<>(Map.ofEntries(
			Map.entry(StaticStructureID.VOID, "noimg.png"),
			Map.entry(StaticStructureID.GROUND, "floor.png"),
			Map.entry(StaticStructureID.BASIC_WALL, "wall.png")
	));

	Map<StaticStructureID, Bitmap> tileBitmaps = new EnumMap<>(StaticStructureID.class);

	// preallocated rects
	Rect destRect = new Rect();
	Rect tileRect = new Rect();

	/** Camera position, in pixels */
	int cameraX = 0, cameraY = 0;

	public GridWorld(Context context)
	{
		super(context);

		SurfaceHolder surfaceHolder = getHolder();
		surfaceHolder.addCallback(this);

		tilePlaceholder = getTileBitmap(StaticStructureID.VOID);
		entityPlaceholder = GraphicsLoader.requestBitmap("roamer_down.png", getContext().getAssets());

		/*
		setOnTouchListener(new View.OnTouchListener() {

			float lastTouchX, lastTouchY;

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

						cameraX += (int)deltaX;
						cameraY += (int)deltaY;

						lastTouchX = event.getX();
						lastTouchY = event.getY();


						return true;
					}
				}
				return false;
			}
		});
		*/


		init();

		loop.startLoop();
	}

	@Override
	public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

	}

	@Override
	public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {

	}

	@Override
	public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

	}

	@Override
	public void draw(Canvas canvas)
	{
		// super.draw(canvas);

		drawWorld(canvas);
		drawAverageFPS(canvas);
	}

	protected void init()
	{
		StringBuffer buf = new StringBuffer();
		try {
			InputStream istream = getContext().getAssets().open("test_floor.txt");
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
	}

	protected void drawWorld(Canvas canvas)
	{

		Paint backgroundPaint = new Paint();
		backgroundPaint.setColor(ContextCompat.getColor(getContext(), R.color.black));
		canvas.drawRect(new Rect(getLeft(), getTop(), getRight(), getBottom()), backgroundPaint);

		tileRect = new Rect(0, 0, tilePlaceholder.getWidth(), tilePlaceholder.getHeight());
		tileRect.top = 0; tileRect.bottom = tilePlaceholder.getHeight();
		tileRect.left = 0; tileRect.right = tilePlaceholder.getWidth();

		destRect = new Rect(); // allocate the destrect object

		int cellScreenSize = (getRight() - getLeft()) / 15;
		if (cellScreenSize % 2 == 1) cellScreenSize ++;

		for (int x = 0; x < GridWorldData.getWidth(); x++)
		{
			for (int y = 0; y < GridWorldData.getHeight(); y++)
			{
				StaticStructureID structureID = GridWorldData.getCell(x, y);

				Bitmap img = getTileBitmap(structureID);

				destRect.bottom = getTop() + y * cellScreenSize + (cellScreenSize / 2) * 3 + cameraY;
				destRect.left = getLeft() + x * cellScreenSize - cellScreenSize / 2 + cameraX;
				destRect.top = getTop() + y * cellScreenSize - cellScreenSize / 2 + cameraY;
				destRect.right = getLeft() + x * cellScreenSize + cellScreenSize / 2 + cameraX;

				// Log.d("GridWorld", "draw at " + destRect.top + ":" + destRect.bottom + " " + destRect.left + ":" + destRect.right);

				canvas.drawBitmap(img, tileRect, destRect, null);
			}
		}

		for (int i = 0; i < GridWorldData.entities.size(); i++)
		{
			float x = GridWorldData.entities.get(i).display_transform.x;
			float y = GridWorldData.entities.get(i).display_transform.y;

			destRect.bottom = (int)(getTop() + y * cellScreenSize + (cellScreenSize / 2f) * 3 + cameraY);
			destRect.left = (int)(getLeft() + x * cellScreenSize - cellScreenSize / 2f + cameraX);
			destRect.top = (int)(getTop() + y * cellScreenSize - cellScreenSize / 2f + cameraY);
			destRect.right = (int)(getLeft() + x * cellScreenSize + cellScreenSize / 2f + cameraX);

			canvas.drawBitmap(entityPlaceholder, tileRect, destRect, null);
		}

		// invalidate(); // DO NOT DO THIS YOU CANNOT DO THAT FROM A THREAD
	}

	protected Bitmap getTileBitmap(StaticStructureID id)
	{
		if (tileBitmaps.containsKey(id))
		{
			return tileBitmaps.get(id);
		}
		else
		{
			Bitmap bitmap = GraphicsLoader.requestBitmap(tileFilenames.get(id), getContext().getAssets());
			tileBitmaps.put(id, bitmap);
			return bitmap;
		}
	}



	protected void drawAverageFPS(Canvas canvas)
	{
		Paint paint = new Paint();
		paint.setTextSize(20);
		paint.setColor(ContextCompat.getColor(getContext(), R.color.magenta));
		canvas.drawText("FPS : " + loop.getAverageFPS(), 100, 20, paint);
	}


	public void update()
	{
		GridWorldData.update();
	}
}
