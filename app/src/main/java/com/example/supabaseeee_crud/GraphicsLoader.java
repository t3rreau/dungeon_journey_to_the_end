package com.example.supabaseeee_crud;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.InputStream;
import java.util.HashMap;

/**
 * Class responsible for storing bitmaps in memory and pre-resizing them
 */
public final class GraphicsLoader
{
	/** Contains the width and height of the game display area, in pixels */
	private static TransformI2D displaySize;
	public void setDisplaySize(int w, int h) {displaySize.x = w; displaySize.y = w;}



	/**
	 * Represents all the images already loaded.
	 * The key is a string "relativeimagepath(width,height)"
	 * relativeimagepath is relative to the assets/graphics folder
	 * width and height are the pixel size of the loaded image
	 */
	private static HashMap<String, Bitmap> bitmaps = new HashMap<String, Bitmap>();


	private GraphicsLoader() {}

	private static String buildKeyString(String imageRelativePath, int w, int h)
	{
		return imageRelativePath + "(" + w + "," + h + ")";
	}

	public static Bitmap requestBitmap(String imagePath, int w, int h, AssetManager assetManager)
	{
		String key = buildKeyString(imagePath, w, h);

		if (bitmaps.containsKey(key))
		{
			// the bitmap was already loaded
			return bitmaps.get(key);
		}
		else
		{
			// the bitmap needs to be loaded
			loadImageFromResources(imagePath, w, h, assetManager);
			// tries one last time to return the bitmap
			if (bitmaps.containsKey(key))
			{
				return bitmaps.get(key);
			}
			else
			{
				Log.e("GraphicsLoader", "Couldn't load bitmap " + key);
				return null;
			}
		}
	}

	private static void loadImageFromResources(String imageName, int w, int h, AssetManager assetManager)
	{
		Log.d("GraphicsLoader", "Loading a new bitmap : " + imageName);
		try
		{

			InputStream istr = assetManager.open("graphics/" + imageName);
			Bitmap bitmap = BitmapFactory.decodeStream(istr);
			Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, w, h, false); // no bilinear filter for pixel art
			istr.close();

			bitmaps.put(buildKeyString(imageName, w, h), scaledBitmap);
			Log.d("GraphicsLoader", "Loaded a new bitmap");
		}
		catch (Exception e)
		{
			try
			{
				// tries to load a placeholder bitmap
				InputStream istr = assetManager.open("graphics/" + "noimg.png");
				Bitmap bitmap = BitmapFactory.decodeStream(istr);
				Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, w, h, false); // no bilinear filter for pixel art
				istr.close();

				bitmaps.put(buildKeyString(imageName, w, h), scaledBitmap);
				Log.d("GraphicsLoader", "Loaded a new placeholder bitmap");
			}
			catch (Exception e2)
			{
				e2.printStackTrace();
			}
			e.printStackTrace();
		}
	}
}
