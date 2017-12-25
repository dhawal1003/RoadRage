package com.example.kd.roadrage_new;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 *
 *Human Computer Interaction - CS6326.001
 *under prof. John Cole
 *Project title: Roadrage
 *
 *Team:
 *Keertan Dakarapu (kxd160830)
 *Dhawal Parmar (ddp160330)
 *
*/

/*
    Module developed by Keertan Dakarapu - kxd160830
*/
public class Scoregetter {

    public String[] player_names;
    public String[] player_scores;

    boolean motion;
    int car_selected;
    int env_selected;
    int sensitivity;

    int highscorecount;

    String dbname;
    int lastid;

    // Retreives the player names and scores from the text file and updates it in the game.
    public Scoregetter(String s,String db) {

        dbname = db;
        highscorecount = 0;
        File varTmpDir = new File(s+"/"+dbname);
        Environment.getExternalStorageState();  // If the app is stored on an external storage, then to make it work.

        if(varTmpDir.exists())
        {
            // the file already exists, so not creating again.
        }
        else
        {
            try {
                File gpxfile = new File(s, dbname);
                FileWriter writer = new FileWriter(gpxfile);
                writer.append("-\t0");
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        int count = 0;

        //the Main arrayList to store the parsed scores.
        ArrayList<ArrayList<String>> ult = new ArrayList<ArrayList<String>>();


        try(BufferedReader br = new BufferedReader(new FileReader(varTmpDir))) {
            for(String line; (line = br.readLine()) != null; ) {
                // process the text file line by line.

                String[] arr = line.split("\t");
                ArrayList<String> temp_ult = new ArrayList<>();

                temp_ult.add(arr[0]);
                temp_ult.add(arr[1]);

                //load the arrays from the text files onto this ArrayList.
                ult.add(temp_ult);

                count++;
                highscorecount++;

            }
            // line is not visible here.
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        List<String> temp_ult1 = new ArrayList<>();
        List<String> temp_ult2 = new ArrayList<>();

        int i,j;


        for(i=0;i<ult.size();i++)
        {
            temp_ult1.add(ult.get(i).get(0));
            temp_ult2.add(ult.get(i).get(1));
        }

        this.player_names = temp_ult1.toArray(new String[0]);
        this.player_scores = temp_ult2.toArray(new String[0]);
    }

    // Retrives the game settings from the text file and set the default setting to these settings
    public Scoregetter(String s,String db,int k) {

        //for settings.
        dbname = db;
        highscorecount = 0;
        File varTmpDir = new File(s+"/"+dbname);
        Environment.getExternalStorageState();  // If the app is stored on an external storage, then to make it work.

        if(varTmpDir.exists())
        {
            // the file already exists, so not creating again.
        }
        else
        {
            try {
                File gpxfile = new File(s, dbname);
                FileWriter writer = new FileWriter(gpxfile);
                writer.append("true\t0\t0\t1");
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        int count = 0;


        try(BufferedReader br = new BufferedReader(new FileReader(varTmpDir))) {
            for(String line; (line = br.readLine()) != null; ) {
                // process the text file line by line.

                String[] arr = line.split("\t");
                //this.motion = Boolean.getBoolean(arr[0]);
                this.motion = Boolean.parseBoolean(arr[0]);
                this.car_selected = Integer.parseInt(arr[1]);
                this.env_selected = Integer.parseInt(arr[2]);
                this.sensitivity = Integer.parseInt(arr[3]);


                //load the arrays from the text files onto this ArrayList.
           }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
