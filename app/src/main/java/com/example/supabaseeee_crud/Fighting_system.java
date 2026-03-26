package com.example.supabaseeee_crud;

import java.util.ArrayList;
import java.util.Scanner;  // Import the Scanner class
public class Fighting_system {
    public static Personnage Combat(ArrayList<Personnage> liste_Personnages){
        int i=0;
        float temps_action = 50;
        ArrayList<Float> time_before_action = new ArrayList<>(); // Creation of Time left list
        for (int n=0; n < liste_Personnages.size(); n++) {
             time_before_action.add(100f);
        }
        while  (time_before_action.get(i) > 0) { // 1 tour
            for (i = 0; i < liste_Personnages.size(); i++) {
                time_before_action.set(i, time_before_action.get(i) - (liste_Personnages.get(i).speed/50));
            }
        }

        System.out.print(("Le personnage "+ liste_Personnages.get(i).name + " empoigne sa détermination !(action)"));

        time_before_action.set(i, temps_action + (50/liste_Personnages.get(i).speed));
        System.out.print(("Quel action va t-il décider de faire"));
        Scanner myObj = new Scanner(System.in);
        String action = myObj.nextLine();  // Read user input
        System.out.print((liste_Personnages.get(i).name + " a bien agit son action."));
        return null;
    }


}
