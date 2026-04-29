package com.example.supabaseeee_crud;


import static java.lang.Math.ceil;
import static java.lang.Math.round;

import android.util.Log;

import java.util.ArrayList;

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

	TransformI2D current_transform = new TransformI2D();

	TransformI2D movement_initial_transform = new TransformI2D();
	TransformI2D movement_target_transform = new TransformI2D();
	long movementInitTime;

	int displayLayerY; // at which Y layer should the object be rendered

	// the position in terms of grid cells where the entity should be drawn
	public Transform2D display_transform = new Transform2D();

	ArrayList<TransformI2D> patrolPath = new ArrayList<>();
	int patrolIndex = 0;

	public GridEntity(int x_, int y_)
	{
		current_transform.x = x_;
		current_transform.y = y_;
		display_transform.x = x_;
		display_transform.y = y_;

		displayLayerY = y_;

		GridWorldData.entities.add(this);
	}

	public GridEntity()
	{
		GridWorldData.entities.add(this);
	}

	public void Destroy()
	{
		GridWorldData.entities.remove(this);
	}

	public boolean value_very_close(float value, float target) {return value > target - 0.1f && value < target + 0.1f;}

	public boolean isMoving()
	{
		return status == GridEntityStatus.MovementOngoing;
	}

	public boolean moveTo(TransformI2D position)
	{
		if (isMoving()) return false;

		position.copyTo(movement_target_transform);
		current_transform.copyTo(movement_initial_transform);

		status = GridEntityStatus.MovementOngoing;

		movementInitTime = System.nanoTime();

		Log.d("GridEntity", "Started movement");

		return true;
	}

	public void setPatrolPath(ArrayList<TransformI2D> path)
	{
		patrolPath = path;
		patrolIndex = patrolPath.size();
	}

	public void update()
	{
		if (status != GridEntityStatus.MovementOngoing)
		{
			updatePatrol();
		}
		else
		{
			updateMovement();
		}
	}

	protected void updateMovement()
	{
		long elapsed_time = System.nanoTime() - movementInitTime;
		TransformI2D diff = new TransformI2D();
		TransformI2D.Substract(movement_target_transform, movement_initial_transform, diff);

		float movement_length = (float)Math.sqrt(Math.pow(diff.x, 2)  + Math.pow(diff.y, 2));

		// 0 to 1 representing progress of the movement from initial to intended position
		double t = ((elapsed_time / 1000000000d) * speed) / movement_length;

		display_transform.x = movement_initial_transform.x + (float)((diff.x) * t);
		display_transform.y = movement_initial_transform.y + (float)((diff.y) * t);

		current_transform.x = round(display_transform.x);
		current_transform.y = round(display_transform.y); // important change to avoid getting hidden by the floor

		int newDisplayY = round(display_transform.y + 0.5f);

		if (displayLayerY != newDisplayY)
		{
			GridWorldData.shouldSortEntities = true;
		}

		displayLayerY = newDisplayY;

		if (t > 1)
		{
			Log.d("GridEntity", "Finished movement");
			movement_target_transform.toTransform2D(display_transform);
			status = GridEntityStatus.Idle;
			return;
		}
	}

	// if possible, update the patrol index
	protected void updatePatrol()
	{
		if (patrolPath.isEmpty()) return;

		patrolIndex ++;
		if (patrolIndex >= patrolPath.size()) patrolIndex = 0;

		moveTo(patrolPath.get(patrolIndex));
	}
}
