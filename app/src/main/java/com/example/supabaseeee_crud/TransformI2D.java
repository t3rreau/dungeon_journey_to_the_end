package com.example.supabaseeee_crud;

import static java.lang.Math.round;

public class TransformI2D
{
	int x;
	int y;

	public TransformI2D() {}

	public TransformI2D(int x_, int y_)
	{
		x = x_;
		y = y_;
	}

	public Transform2D toTransform2D()
	{
		return new Transform2D(x, y);
	}

	public void toTransform2D(Transform2D transform)
	{
		transform.x = x;
		transform.y = y;
	}

	public void copyTo(TransformI2D transform)
	{
		transform.x = x;
		transform.y = y;
	}

	public static void Substract(TransformI2D initial, TransformI2D other, TransformI2D output)
	{
		output.x = initial.x - other.x;
		output.y = initial.y - other.y;
	}
};