package com.example.supabaseeee_crud;

import static java.lang.Math.abs;
import static java.lang.Math.round;

public class Transform2D
{
	float x = 0;
	float y = 0;

	public Transform2D() {}

	public Transform2D(float x_, float y_)
	{
		x = x_;
		y = y_;
	}

	public TransformI2D toTransformI2D()
	{
		return new TransformI2D(round(x), round(y));
	}

	public void toTransformI2D(TransformI2D transform)
	{
		transform.x = round(x);
		transform.y = round(y);
	}

	public boolean isApproxEqual(TransformI2D transform)
	{
		return abs(x - transform.x) < 0.1f && abs(y - transform.y) < 0.1f;
	}

	public void copyTo(Transform2D transform)
	{
		transform.x = x;
		transform.y = y;
	}

	public static void Substract(Transform2D initial, Transform2D other, Transform2D output)
	{
		output.x = initial.x - other.x;
		output.y = initial.y - other.y;
	}
};