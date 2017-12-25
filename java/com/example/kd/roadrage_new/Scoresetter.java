package com.example.kd.roadrage_new;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

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
public class Scoresetter {

    public String[] player_names;
    public String[] player_scores;

    String dbname;

    // Stores the scores and player names in the text file on the phone
    public Scoresetter(String s,String db, String[] pnames, String[] pscores) {

        dbname = db;
        this.player_names = pnames;
        this.player_scores = pscores;

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
                writer.append("-\t-");
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        String datatowrite = "";

        for(int i=0;i<pnames.length;i++)
        {
            datatowrite += player_names[i] + "\t" + player_scores[i] + "\n";
        }

        try {
            File gpxfile = new File(s, dbname);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(datatowrite);
            writer.flush();
            writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }


    // Stores the default game setting in the text file on the phone
    public Scoresetter(String s,String db, Boolean motion, int car_selected,int env_selected,int sensitivity) {

        dbname = db;

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
                writer.append("true\t0\t0\t0");
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        String datatowrite = "";
        datatowrite += motion + "\t" + car_selected + "\t"+ env_selected + "\t" + sensitivity +"\n";

        try {
            File gpxfile = new File(s, dbname);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(datatowrite);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
