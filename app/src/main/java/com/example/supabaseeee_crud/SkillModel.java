package com.example.supabaseeee_crud;

import java.util.ArrayList;

public class SkillModel {
    String SkillName ;
    Integer SkillDamage ;
    public static ArrayList<SkillModel> Skills_list = new ArrayList<SkillModel>();


    public SkillModel(String skillName, Integer skillDamage) {
        SkillName = skillName;
        SkillDamage = skillDamage;
        Skills_list.add(this);
    }

    public String getSkillName() {

        return SkillName;
    }
    public Integer getSkillDamage() {
        return SkillDamage;
    }
    public void Delete(){

        Skills_list.remove(this);
    }
}

