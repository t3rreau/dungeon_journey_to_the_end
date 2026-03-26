package com.example.supabaseeee_crud;

import android.util.Log;

import java.util.ArrayList;

public class List2D<T> {



    public ArrayList<T> list = new ArrayList<T>();
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



    public T get(int x, int y)
    {
        return list.get(x + y * width);
    }

    public void set(int x, int y, T val)
    {
        list.set(x + y * width, val);
    }
}
