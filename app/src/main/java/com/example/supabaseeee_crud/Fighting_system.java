package com.example.supabaseeee_crud;

import android.util.Log;

import java.util.ArrayList;
import java.util.Scanner;  // Import the Scanner class
public class Fighting_system {
    public static void Combat(ArrayList<Personnage> liste_Personnages){
        int i=0;
        float temps_action = 50;
        ArrayList<Float> time_before_action = new ArrayList<>(); // Creation of Time left list
        for (int n=0; n < liste_Personnages.size(); n++) {
             time_before_action.add(100f);
        }
        int index_action_character = -1;
        while (index_action_character == -1 ) { // Répète jusqu'à ce que cela soit au tour de quelqu'un
            for (i = 0; i < liste_Personnages.size(); i++) { // itère à travers la liste des personnages du combat.
                time_before_action.set(i, time_before_action.get(i) - (liste_Personnages.get(i).speed/50)); // Fait décroître le temp avant action en fonction de la vitesse
                if (time_before_action.get(i) <= 0){
                    index_action_character = i;
                }
            }
        }


        String Test1 = "1";
        String Test2 = "2";
        String Test3 = "3";
        Log.d(Test1, "Le personnage "+ liste_Personnages.get(index_action_character).name + " empoigne sa détermination !(action)");
        time_before_action.set(index_action_character, temps_action + (-50/liste_Personnages.get(index_action_character).speed));
        Log.d(Test2, "Quel action va t-il décider de faire");
       // Scanner myObj = new Scanner(System.in);
        //String action = myObj.nextLine();  // Read user input       A REMPLACER PAR LA FUTUR CLASSE FIGHTING INTERFACE
        Log.d(Test3, liste_Personnages.get(index_action_character).name + " a bien agit son action.");

    }


}
