package com.example.supabaseeee_crud;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class GridWorldLoop  extends Thread
{
	private volatile boolean isRunning = false;
	private volatile boolean isDone = false;

	GridWorld world;
	SurfaceHolder surfaceHolder;

	int targetFPS = 30;

	double averageFPS;

	public GridWorldLoop(GridWorld _world, SurfaceHolder _surfaceHolder)
	{
		world = _world;
		surfaceHolder = _surfaceHolder;
		Log.d("thread", "new thread");
	}

	public double getAverageFPS()
	{
		return averageFPS;
	}

	public void startLoop()
	{
		isRunning = true;
		start();
	}

	public void stopLoop()
	{
		isRunning = false;
		Log.d("thread", "stopped");
	}

	public boolean isDone() {return isDone;}

	@Override
	public void run()
	{
		super.run();

		int updateCount = 0;
		long lastAverageCalc = 0;

		Canvas canvas;

		while (isRunning)
		{
			long timeLoopStart = System.currentTimeMillis();

			try
			{
				canvas = surfaceHolder.lockCanvas();
				world.update();
				updateCount ++;
				world.draw(canvas);
				surfaceHolder.unlockCanvasAndPost(canvas);
			}
			catch (IllegalArgumentException e)
			{
				e.printStackTrace();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

			long sinceLastAverageCalc = System.currentTimeMillis() - lastAverageCalc;
			if (sinceLastAverageCalc > 1000)
			{
				averageFPS = updateCount / (1E-3 * sinceLastAverageCalc);
				updateCount = 0;
				lastAverageCalc = System.currentTimeMillis();
			}

			long timeLoopEnd = System.currentTimeMillis();

			long sleepTime = (1000 / targetFPS) - (timeLoopEnd - timeLoopStart);

			if (sleepTime > 0)
			{
				try
				{
					Log.d("thread", "slep");
					sleep(sleepTime);
					Log.d("thread", "unslep");
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}

		Log.d("thread", "stopped");

		isDone = true;
	}
}
