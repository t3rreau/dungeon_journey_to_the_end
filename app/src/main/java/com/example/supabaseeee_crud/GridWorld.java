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
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.EnumMap;

public class GridWorld extends SurfaceView implements SurfaceHolder.Callback
{

	GridWorldLoop loop;

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

	/** Player sprite */
	GridEntityPlayer playerGridEntity = null;

	GridWorldActivity activity;

	public GridWorld(Context context, GridWorldActivity activity_)
	{
		super(context);

		SurfaceHolder surfaceHolder = getHolder();
		surfaceHolder.addCallback(this);

		loop = new GridWorldLoop(this, getHolder());


		setOnTouchListener(new View.OnTouchListener() {

			float lastTouchX, lastTouchY;

			@Override
			public boolean onTouch(View v, MotionEvent event) {


				switch (event.getActionMasked()) {
					case MotionEvent.ACTION_DOWN: {
						lastTouchX = event.getX();
						lastTouchY = event.getY();

						uiFingerDown(new TransformI2D((int)lastTouchX, (int)lastTouchY));

						return true;
					}

					case MotionEvent.ACTION_MOVE: {
						float deltaX = event.getX() - lastTouchX;
						float deltaY = event.getY() - lastTouchY;

						// cameraX += (int)deltaX;
						// cameraY += (int)deltaY;

						lastTouchX = event.getX();
						lastTouchY = event.getY();


						return true;
					}
				}
				return false;
			}
		});

		activity = activity_;

		init();

		loop.startLoop();
	}

	public void stop()
	{

		loop.stopLoop();
		/*
		try {
			loop.join(5000);
		} catch (InterruptedException e) {
			Log.e("GridWorld", "thread didn't join");
		}
		loop = null;
		*/

		GridWorldData.entities.clear();
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

		if (playerGridEntity != null)
		{
			// center the camera on the player
			cameraX = - (int)(playerGridEntity.display_transform.x * getTileScreenPixelSize().x) + ((getRight() - getLeft()) / 2);
			cameraY = - (int)(playerGridEntity.display_transform.y * getTileScreenPixelSize().x) + ((getBottom() - getTop()) / 2);
		}

		drawWorld(canvas);
		drawUI(canvas);
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



		respawnPlayer();
	}

	// Player-related funcs

	protected void respawnPlayer()
	{
		if (playerGridEntity != null) playerGridEntity.destroy();
		playerGridEntity = new GridEntityPlayer(2, 3, "player_down.png", activity);
	}



	// ui-related functions

	protected int getUiSizeRef() {return (getRight() - getLeft()) / 10;}
	protected void uiFingerDown(TransformI2D pos)
	{
		int uiSizeRef = getUiSizeRef();

		TransformI2D dirButtonPos = new TransformI2D(round(getRight() - uiSizeRef * 1.5f), round(getTop() + uiSizeRef * 2f));

		Log.d("FingerDown", "finger down at " + pos.toString() + " checking with " + dirButtonPos.toString());

		// circle quadrants check to detect which direction button is pressed
		if (TransformI2D.getDistance(dirButtonPos, pos) < uiSizeRef)
		{
			if (Math.abs(dirButtonPos.x - pos.x) > Math.abs(dirButtonPos.y - pos.y))
			{
				if (dirButtonPos.x - pos.x < 0)
				{
					// right button pressed
					Log.d("FingerDown", "going right" + playerGridEntity.toString());
					if (playerGridEntity != null) playerGridEntity.moveToRelative(new TransformI2D(1, 0));
				}
				else
				{
					// left button pressed
					if (playerGridEntity != null) playerGridEntity.moveToRelative(new TransformI2D(-1, 0));
				}
			}
			else
			{
				if (dirButtonPos.y - pos.y < 0)
				{
					// down button pressed
					if (playerGridEntity != null) playerGridEntity.moveToRelative(new TransformI2D(0, 1));
				}
				else
				{
					// up button pressed
					if (playerGridEntity != null) playerGridEntity.moveToRelative(new TransformI2D(0, -1));
				}
			}
		}

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

		// invalidate(); // DO NOT DO THIS YOU CANNOT DO THAT FROM A THREAD
	}

	protected void drawUI(Canvas canvas)
	{
		int uiRef = (getRight() - getLeft()) / 10;

		Bitmap dirButton = GraphicsLoader.requestBitmap("dirbutton_up.png", uiRef, uiRef, getContext().getAssets());
		if (dirButton != null) canvas.drawBitmap(dirButton, getRight() - uiRef * 2, getTop() + uiRef, null);
		dirButton = GraphicsLoader.requestBitmap("dirbutton_right.png", uiRef, uiRef, getContext().getAssets());
		if (dirButton != null) canvas.drawBitmap(dirButton, getRight() - uiRef * 1.5f, getTop() + uiRef * 1.5f, null);
		dirButton = GraphicsLoader.requestBitmap("dirbutton_down.png", uiRef, uiRef, getContext().getAssets());
		if (dirButton != null) canvas.drawBitmap(dirButton, getRight() - uiRef * 2, getTop() + uiRef * 2, null);
		dirButton = GraphicsLoader.requestBitmap("dirbutton_left.png", uiRef, uiRef, getContext().getAssets());
		if (dirButton != null) canvas.drawBitmap(dirButton, getRight() - uiRef * 2.5f, getTop() + uiRef * 1.5f, null);

	}

	protected String getTileSpritePath(StaticStructureID id)
	{
		switch (id)
		{
			case VOID:
				return "void.png";

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
