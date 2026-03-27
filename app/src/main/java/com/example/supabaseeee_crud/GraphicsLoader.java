package com.example.supabaseeee_crud;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.InputStream;
import java.util.HashMap;

public final class GraphicsLoader
{
	private GraphicsLoader() {}

	/**
	 * Represents all the images already
	 */
	private static HashMap<String, Bitmap> bitmaps = new HashMap<String, Bitmap>();

	public static Bitmap requestBitmap(String imageName, AssetManager assetManager)
	{
		if (bitmaps.containsKey(imageName))
		{
			Log.d("GraphicsLoader", "Got the bitmap already");
			return bitmaps.get(imageName);
		}
		else
		{
			loadImageFromResources(imageName, assetManager);
			return bitmaps.get(imageName);
		}
	}

	private static void loadImageFromResources(String imageName, AssetManager assetManager)
	{
		Log.d("GraphicsLoader", "Loading a new bitmap");
		try
		{

			InputStream istr = assetManager.open("graphics/" + imageName);
			Bitmap bitmap = BitmapFactory.decodeStream(istr);
			istr.close();

			bitmaps.put(imageName, bitmap);
			Log.d("GraphicsLoader", "Loaded a new bitmap");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
