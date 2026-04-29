package com.example.supabaseeee_crud;

import android.util.Log;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class GridWorldData {

    private GridWorldData() {}

    /**
     * The width of the grid, in number of cells
     */
    private static int width;

    private void setWidth(int val) {width = val;}
    public static int getWidth() {return gridStaticStructures.getWidth();}



    /**
     * The height of the grid, in number of cells
     */
    private static int height;

    private void setHeight(int val) {height = val;}
    public static int getHeight() {return gridStaticStructures.getHeight();}



    private static List2D<StaticStructureID> gridStaticStructures = new List2D<StaticStructureID>(0, 0, StaticStructureID.VOID);

    public static ArrayList<GridEntity> entities = new ArrayList<GridEntity>();
    static boolean shouldSortEntities = true;


    public static void gridGenFromString(String data)
    {
        String[] lines = data.split("\n");

        Log.d("GridWorldData", data);
        gridStaticStructures.resize(lines[0].length(), lines.length, StaticStructureID.VOID);

        Log.d("GridWorldData", String.valueOf((gridStaticStructures.getWidth() * gridStaticStructures.getHeight())));
        Log.d("GridWorldData", String.valueOf(gridStaticStructures.list.size()));

        StaticStructureID[] enumValues = StaticStructureID.values();

        for (int x = 0; x < lines[0].length(); x++)
        {
            for (int y = 0; y < lines.length; y++)
            {
                gridStaticStructures.set(x, y, enumValues[(int) (lines[y].charAt(x) - '0')]);
            }
        }

        Log.d("GridWorldData", gridStaticStructures.toString());
    }

    public static StaticStructureID getCell(int x, int y)
    {
        return gridStaticStructures.get(x, y);
    }

    public static void update()
    {
        for (int i = 0; i < entities.size(); i++)
        {
            entities.get(i).update();
        }

        if (shouldSortEntities) {
            entities.sort(new Comparator<GridEntity>() {
                @Override
                public int compare(GridEntity o1, GridEntity o2) {
                    return o1.current_transform.y - o2.current_transform.y;
                }
            });
        }
    }

}
