package com.example.supabaseeee_crud;

import java.util.ArrayList;

public class Personnage {


    public static ArrayList<Personnage> Combat_character_list = new ArrayList<Personnage>();
    public final String name;
    public final int speed;
    public int health;

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public int getHealth() {
        return health;
    }

    public Personnage(String name, int speed, int health) {
        this.name = name;
        this.speed = speed;
        this.health = health;
        Combat_character_list.add(this);
    }
    public void Delete(){
        Combat_character_list.remove(this);
    }
    public void skill(){

    }
}
