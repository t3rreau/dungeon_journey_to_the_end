package com.example.supabaseeee_crud;

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




}
