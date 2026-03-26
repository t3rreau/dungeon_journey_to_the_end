package com.example.supabaseeee_crud;

import java.util.ArrayList;

public class List2D<T> {



    private ArrayList<T> list = new ArrayList<T>();
    private int width, height;



    public List2D(int w, int h, T initialVal)
    {
        resize(w, h, initialVal);
    }



    public int getWidth() {return width;}
    public int getHeight() {return height;}



    public void resize(int w, int h, T val)
    {
        width = w;
        height = h;

        list.clear();
        list.ensureCapacity(w * h);
        for (int i = 0; i < w * h; i ++) list.add(val);
    }
}
