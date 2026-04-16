package com.example.supabaseeee_crud;

public class GridEntityRoamer extends GridEntity
{
	public GridEntityRoamer(int x, int y)
	{
		super();

		current_transform.x = x;
		current_transform.y = y;
		display_transform.x = x;
		display_transform.y = y;
	}
}
