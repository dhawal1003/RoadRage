package com.example.kd.roadrage_new;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


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
    Module developed by Keertan Dakarapu (kxd160830)
*/
public class Game_end extends AppCompatActivity {

    public static Activity gameEndPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_end);

        Intent intent = getIntent();
        final String [] player_score = intent.getStringArrayExtra("string-array");
        Typeface stylish = Typeface.createFromAsset(getAssets(),"fonts/ARDESTINE.ttf");

        TextView t1 = (TextView)findViewById(R.id.Game_over_textView);
        TextView t2 = (TextView)findViewById(R.id.gameover_score_textView2);

        Button menu = (Button) findViewById(R.id.tomenu_gameover_button);
        Button highscores = (Button) findViewById(R.id.tohighscore_gameover_button);

        t1.setTypeface(stylish);
        t2.setText(player_score[0]);
        t2.setTypeface(stylish);

        menu.setTypeface(stylish);
        highscores.setTypeface(stylish);

        // Redirects to the home screen
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Game_end.this, MainActivity.class);
                startActivity(intent);

            }
        });

        // Redirects to the high scores page
        highscores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Game_end.this, Highscores.class);
                startActivity(intent);

            }
        });

    }
}
