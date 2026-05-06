package com.example.supabaseeee_crud;

public class GridEntityPlayer extends GridEntity
{
    GridWorldActivity activity;

    public GridEntityPlayer(int x, int y, String spritePath, GridWorldActivity activity_)
    {
        super(x, y, spritePath);
        speed = 2;
        faction = GridEntityFaction.player;
        activity = activity_;
    }

    @Override
    public void afterUpdate()
    {
        super.afterUpdate();

        for (int i = 0; i < GridWorldData.entities.size(); i++)
        {
            GridEntity entity = GridWorldData.entities.get(i);
            if (entity.faction == GridEntityFaction.hostile)
            {
                if (entity.current_transform.x == current_transform.x && entity.current_transform.y == current_transform.y)
                {
                    activity.switchToCombat();
                }
            }
        }
    }
}