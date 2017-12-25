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
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.duration;


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
    Module developed by Keertan Dakarapu - kxd160830 and Dhawal Parmar - ddp160330
*/
public class Settings extends AppCompatActivity {

    String s;
    Scoregetter sg;
    Scoresetter st;
    SeekBar sk;
    RadioGroup radioButtonGroup1,radioButtonGroup2;
    public static Activity settingsPage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        Typeface stylish = Typeface.createFromAsset(getAssets(),"fonts/ARDESTINE.ttf");

        TextView t = (TextView) findViewById(R.id.settings_heading_textView);
        t.setTypeface(stylish);
        Button back_button = (Button)findViewById(R.id.back_button_settings);
        Button save_button = (Button)findViewById(R.id.save_button_settings);

        back_button.setTypeface(stylish);

        PackageManager m = getPackageManager();
        s = getPackageName();

        try {
            PackageInfo p = m.getPackageInfo(s, 0);
            s = p.applicationInfo.dataDir;
        } catch (PackageManager.NameNotFoundException e) {
            Log.w("Sorry,", "Error Package name not found ", e);
        }

        sg = new Scoregetter(s,"settings2.txt",0);


        sk = (SeekBar) findViewById(R.id.motion_seekBar);
        radioButtonGroup1 = (RadioGroup)findViewById(R.id.car_selection);
        radioButtonGroup2 = (RadioGroup)findViewById(R.id.env_selection);


  //      Toast.makeText(Settings.this, ""+sg.motion+"|"+sg.sensitivity+"|"+sg.car_selected, Toast.LENGTH_SHORT).show();

        sk.setProgress(sg.sensitivity);
        radioButtonGroup1.check(sg.car_selected);
        radioButtonGroup2.check(sg.env_selected);

        RadioButton car_selected = (RadioButton)findViewById(R.id.car_radio_1);
        RadioButton env_selected = (RadioButton)findViewById(R.id.env1);

        if(sg.car_selected==0)
        {
            car_selected = (RadioButton)findViewById(R.id.car_radio_1);
        }
        if(sg.car_selected==1)
        {
            car_selected = (RadioButton)findViewById(R.id.car_radio_2);
        }
        if(sg.car_selected==2)
        {
            car_selected = (RadioButton)findViewById(R.id.car_radio_3);
        }
        if(sg.car_selected==3)
        {
           car_selected = (RadioButton)findViewById(R.id.car_radio_4);
        }


        if(sg.env_selected==0)
        {
            env_selected = (RadioButton)findViewById(R.id.env1);
        }
        if(sg.env_selected==1)
        {
            env_selected = (RadioButton)findViewById(R.id.env2);
        }
        if(sg.env_selected==2)
        {
            env_selected = (RadioButton)findViewById(R.id.env3);
        }
        if(sg.env_selected==3)
        {
            env_selected = (RadioButton)findViewById(R.id.env4);
        }

        car_selected.setChecked(true);
        env_selected.setChecked(true);

        // redirects to the home page and settings are not saved
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        //        Toast.makeText(Settings.this, "Changes not saved.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Settings.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        // Saves the settings and redirects to the home screen
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, MainActivity.class);
                int radioButtonID1 = radioButtonGroup1.getCheckedRadioButtonId();
                View radioButton1 = radioButtonGroup1.findViewById(radioButtonID1);
                int radioButtonID2 = radioButtonGroup2.getCheckedRadioButtonId();
                View radioButton2 = radioButtonGroup2.findViewById(radioButtonID2);
                int car_selected = radioButtonGroup1.indexOfChild(radioButton1);
                int env_selected = radioButtonGroup2.indexOfChild(radioButton2);
                Boolean motion_enable = true;
                int sensitivity = sk.getProgress();

       //         Toast.makeText(Settings.this, "Changes Saved", Toast.LENGTH_SHORT).show();
                st = new Scoresetter(s,"settings2.txt",motion_enable,car_selected,env_selected,sensitivity);
                startActivity(intent);
                finish();
            }
        });
    }
}
