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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.lang.Integer.parseInt;


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
    Module developed by Dhawal Parmar - ddp160330
*/
public class HighScore_Input extends AppCompatActivity {

    List<String> existing_names = new ArrayList<String>();
    List<String> existing_scores = new ArrayList<String>();
    public static Activity highScoresInputPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score__input);

        Intent intent = getIntent();
        final String [] player_score = intent.getStringArrayExtra("string-array");

        Typeface stylish = Typeface.createFromAsset(getAssets(),"fonts/ARDESTINE.ttf");


        TextView t1 = (TextView)findViewById(R.id.Highscore_input_textView);
        TextView t2 = (TextView)findViewById(R.id.score_textView);
        EditText player_name = (EditText) findViewById(R.id.ctl_highscore_player);
        Button save = (Button) findViewById(R.id.highscore_save_button);
        Button menu = (Button) findViewById(R.id.tomenu_button);

        t1.setTypeface(stylish);
        t2.setText(player_score[0]);

        t2.setTypeface(stylish);
        player_name.setTypeface(stylish);
        save.setTypeface(stylish);
        menu.setTypeface(stylish);

        PackageManager m = getPackageManager();
        String s = getPackageName();


        try {
            PackageInfo p = m.getPackageInfo(s, 0);
            s = p.applicationInfo.dataDir;
        } catch (PackageManager.NameNotFoundException e) {
            Log.w("Sorry,", "Error Package name not found ", e);
        }

        final String s2 = s;

        Scoregetter sg = new Scoregetter(s,"db2.txt");
          // existing_names = Arrays.asList(sg.player_names);
        //existing_scores = Arrays.asList(sg.player_scores);

        Collections.addAll(existing_names, sg.player_names);
        Collections.addAll(existing_scores, sg.player_scores);

        // Saves the player name and score in the high scores list on clicking the save button
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView t2 = (TextView)findViewById(R.id.score_textView);
                EditText player_name = (EditText) findViewById(R.id.ctl_highscore_player);

                String new_player = player_name.getText().toString();
                String new_score = t2.getText().toString();

                if(new_player.equals("")) new_player = "SecretPlayer";

                existing_names.add(new_player);
                existing_scores.add(new_score);

                String temp;

                for(int i=0;i<existing_scores.size();i++)
                {
                    for(int j=i;j<existing_scores.size();j++) {

                        if (Integer.parseInt(existing_scores.get(j)) > Integer.parseInt(existing_scores.get(i)))
                        {
                            temp = existing_names.get(i);
                            existing_names.set(i,existing_names.get(j));
                            existing_names.set(j,temp);

                            temp = existing_scores.get(i);
                            existing_scores.set(i,existing_scores.get(j));
                            existing_scores.set(j,temp);

                        }
                    }
                }

                if(existing_scores.size()>10) {
                    existing_names.remove(existing_names.size() - 1);
                    existing_scores.remove(existing_scores.size() - 1);
                }

                String[] updated_names = existing_names.toArray(new String[0]);
                String[] updated_scores = existing_scores.toArray(new String[0]);

                Scoresetter ss = new Scoresetter(s2,"db2.txt",updated_names,updated_scores);

                Intent intent = new Intent(HighScore_Input.this, Highscores.class);
                startActivity(intent);
                finish();
            }
        }

        );

        // Redirects to the home screen and does not save the score
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HighScore_Input.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
