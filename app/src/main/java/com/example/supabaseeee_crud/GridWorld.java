package com.example.supabaseeee_crud;

import static java.lang.Math.round;

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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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

		GridEntityRoamer roamer = new GridEntityRoamer(2, 2, "darkguy_down.png");
		roamer.speed = 1f;
		roamer.setPatrolPath(new ArrayList<>(Arrays.asList(new TransformI2D(2, 2), new TransformI2D(4, 2), new TransformI2D(4, 4), new TransformI2D(4, 2), new TransformI2D(2, 2))));
	}

	protected TransformI2D getTileScreenPixelSize()
	{
		int screenWidth = getRight() - getLeft();
		int screenHeight = getBottom() - getTop();

		int tileWidth = screenWidth / 16;
		if (tileWidth % 2 == 1) tileWidth ++;

		return new TransformI2D(tileWidth, tileWidth * 2);
	}

	protected void drawWorld(Canvas canvas)
	{

		// erase the background
		Paint backgroundPaint = new Paint();
		backgroundPaint.setColor(ContextCompat.getColor(getContext(), R.color.black));
		canvas.drawRect(new Rect(getLeft(), getTop(), getRight(), getBottom()), backgroundPaint);



		TransformI2D tileSize = getTileScreenPixelSize();

		int entitiesCounter = 0;
		TransformI2D drawPos = new TransformI2D();
		for (int y = 0; y < GridWorldData.getHeight(); y++)
		{
			for (int x = 0; x < GridWorldData.getWidth(); x++)
			{
				StaticStructureID structureID = GridWorldData.getCell(x, y);

				Bitmap img = GraphicsLoader.requestBitmap(getTileSpritePath(structureID), tileSize.x, tileSize.y, getContext().getAssets());
				drawPos.x = getLeft() + x * tileSize.x - tileSize.x / 2 + cameraX;
				drawPos.y = getTop() + y * tileSize.x - tileSize.x / 2 + cameraY;

				if (img != null) canvas.drawBitmap(img, drawPos.x, drawPos.y, null);
			}

			// draws all the entities that are at this y level
			for (; entitiesCounter < GridWorldData.entities.size(); entitiesCounter++)
			{
				GridEntity entity = GridWorldData.entities.get(entitiesCounter);

				if (entity.current_transform.y > y)
				{
					break; // the list is sorted in ascending y order, so if the entity has a greater y we should write it later.
				}

				float entityX = entity.display_transform.x;
				float entityY = entity.display_transform.y;

				Bitmap img = GraphicsLoader.requestBitmap(entity.getSpritePath(), tileSize.x, tileSize.y, getContext().getAssets());
				drawPos.x = round(getLeft() + entityX * tileSize.x - tileSize.x / 2 + cameraX);
				drawPos.y = round(getTop() + entityY * tileSize.x - tileSize.x / 2 + cameraY);

				if (img != null) canvas.drawBitmap(img, drawPos.x, drawPos.y, null);
			}

		}

		for (int i = 0; i < GridWorldData.entities.size(); i++)
		{

		}

		// invalidate(); // DO NOT DO THIS YOU CANNOT DO THAT FROM A THREAD
	}

	protected String getTileSpritePath(StaticStructureID id)
	{
		switch (id)
		{
			case VOID:
				return "noimg.png";

			case GROUND:
				return "floor.png";

			case BASIC_WALL:
				return "wall.png";

			default:
				return "noimg.png";
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
