package com.example.supabaseeee_crud;

import android.util.Log;

import java.io.InputStream;

public final class GridWorldData {

    private GridWorldData() {}

    /**
     * The width of the grid, in number of cells
     */
    private static int width;

    private void setWidth(int val) {width = val;}
    public int getWidth() {return width;}



    /**
     * The height of the grid, in number of cells
     */
    private static int height;

    private void setHeight(int val) {height = val;}
    public int getHeight() {return height;}



    private static List2D<StaticStructureID> gridStaticStructures = new List2D<StaticStructureID>(0, 0, StaticStructureID.VOID);


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

}
