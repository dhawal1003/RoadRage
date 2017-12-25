package com.example.kd.roadrage_new;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.kd.roadrage_new.Game.gamePage;
import static com.example.kd.roadrage_new.Game_end.gameEndPage;
import static com.example.kd.roadrage_new.HighScore_Input.highScoresInputPage;
import static com.example.kd.roadrage_new.Highscores.highScoresPage;
import static com.example.kd.roadrage_new.Settings.settingsPage;
import static com.example.kd.roadrage_new.pausescreen.pausePage;

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
public class MainActivity extends AppCompatActivity {

    TextView t;
    Boolean motion_enabled;
    public int Car_selected;
    public int env_selected;
    public int sensitivity;
    Scoregetter sg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PackageManager m = getPackageManager();
        String s = getPackageName();

        try {
            PackageInfo p = m.getPackageInfo(s, 0);
            s = p.applicationInfo.dataDir;
        } catch (PackageManager.NameNotFoundException e) {
            Log.w("Sorry,", "Error Package name not found ", e);
        }


        motion_enabled = true;
        Car_selected = 3;
        env_selected = 0;

        sg = new Scoregetter(s,"settings2.txt",0);

        motion_enabled = sg.motion;
        Car_selected = sg.car_selected;
        env_selected = sg.env_selected;
        sensitivity = sg.sensitivity;

        Typeface stylish = Typeface.createFromAsset(getAssets(),"fonts/ARDESTINE.ttf");

        t = (TextView) findViewById(R.id.Game_heading);
        t.setTypeface(stylish);
        Button play_button = (Button)findViewById(R.id.play_button);
        play_button.setTypeface(stylish);
        Button settings_button = (Button)findViewById(R.id.settings_button);
        settings_button.setTypeface(stylish);
        Button highscore_button = (Button)findViewById(R.id.highscores_button);
        highscore_button.setTypeface(stylish);

        // Sets the button click listener for Settings Button
        settings_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Settings.class);
                startActivity(intent);
            }
        });

        // Sets the button click listener for HighScore Button
        highscore_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Highscores.class);
                startActivity(intent);}
        });

        // Sets the button click listener for Play Button
        play_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(motion_enabled=true)
                {
                    Intent intent = new Intent(MainActivity.this, Game.class);
                    int[] strarr = {0,0,0};
                    strarr[0] = Car_selected;
                    strarr[1] = sensitivity;
                    strarr[2] = env_selected;
                    intent.putExtra("integer-array", strarr);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(MainActivity.this, Game_touch.class);
                    int[] strarr = {0,0,0};
                    strarr[0] = Car_selected;
                    strarr[1] = sensitivity;
                    strarr[2] = env_selected;
                    intent.putExtra("string-array", strarr);
                    startActivity(intent);
                }
            }
        }

        );

    }
}
