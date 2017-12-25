package com.example.kd.roadrage_new;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static com.example.kd.roadrage_new.Game.gamePage;


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
public class pausescreen extends AppCompatActivity {

    public static Activity pausePage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pausescreen);
        Button resume_btn = (Button)findViewById(R.id.resume1);
        Button end_btn = (Button)findViewById(R.id.pausescreen_end);

        Typeface stylish = Typeface.createFromAsset(getAssets(),"fonts/ARDESTINE.ttf");

        resume_btn.setTypeface(stylish);
        end_btn.setTypeface(stylish);

        // Redirects back to the game page and resumes the game
        resume_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        }
        );

        // Ridirects to the home screen page and the game is finished in background
        end_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gamePage.finish();
                Intent intent = new Intent(pausescreen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }







}
