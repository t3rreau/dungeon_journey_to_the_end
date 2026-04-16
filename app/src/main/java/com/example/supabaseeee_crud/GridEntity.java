package com.example.supabaseeee_crud;


enum GridEntityStatus
{
	Idle,
	MovementOngoing,
}

/**
 * Represent an entity that that can be drawn and can move on the grid
 */
public class GridEntity {

	GridEntityStatus status = GridEntityStatus.Idle;

	// how many tiles per second the entity travels
	float speed;

	TransformI2D current_transform;

	TransformI2D movement_initial_transform;
	TransformI2D movement_target_transform;
	long movementInitTime;

	// the position in terms of grid cells where the entity should be drawn
	Transform2D display_transform;



	public boolean value_very_close(float value, float target) {return value > target - 0.1f && value < target + 0.1f;}

	public boolean isMoving()
	{
		return ! display_transform.isApproxEqual(movement_target_transform);
	}

	public boolean moveTo(TransformI2D position)
	{
		if (isMoving()) return false;

		position.copyTo(movement_target_transform);
		current_transform.copyTo(movement_initial_transform);

		status = GridEntityStatus.MovementOngoing;

		movementInitTime = System.nanoTime();

		return true;
	}

	public void update()
	{
		if (status != GridEntityStatus.MovementOngoing) return;

		long elapsed_time = System.nanoTime() - movementInitTime;
		TransformI2D diff = new TransformI2D();
		TransformI2D.Substract(movement_target_transform, movement_initial_transform, diff);

		float movement_lenght = (float)Math.sqrt(Math.pow(diff.x, 2)  + Math.pow(diff.y, 2));

		// 0 to 1 representing progress of the movement from initial to intended position
		double t = ((elapsed_time / 1000000000d) * speed) / movement_lenght;

		if (t > 1)
		{
			movement_target_transform.toTransform2D(display_transform);
			status = GridEntityStatus.Idle;
			return;
		}

		display_transform.x = (float)((diff.x) * t);
		display_transform.y = (float)((diff.y) * t);
	}
}
