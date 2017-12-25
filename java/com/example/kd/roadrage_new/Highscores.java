package com.example.kd.roadrage_new;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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
public class Highscores extends AppCompatActivity {

    ListView list;
    public String[] playernames;
    public String[] playerscores;
    Scoregetter sg;
    public static Activity highScoresPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);

        Typeface stylish = Typeface.createFromAsset(getAssets(),"fonts/ARDESTINE.ttf");

        TextView t = (TextView) findViewById(R.id.highscores_textview_heading);
        t.setTypeface(stylish);
        Button back_button = (Button)findViewById(R.id.back_button_highscores);
        back_button.setTypeface(stylish);

        PackageManager m = getPackageManager();
        String s = getPackageName();

        try {
            PackageInfo p = m.getPackageInfo(s, 0);
            s = p.applicationInfo.dataDir;
        } catch (PackageManager.NameNotFoundException e) {
            Log.w("Sorry,", "Error Package name not found ", e);
        }

        sg = new Scoregetter(s,"db2.txt");

        playernames = sg.player_names;
        playerscores = sg.player_scores;

        int i,j;
        String temp;

        //Sorting All the players detail arrays, according to the scores, in Ascending order.
        for(i=1;i<playerscores.length;i++)
        {
            for(j=i;j<playerscores.length;j++) {

                if (Integer.parseInt(playerscores[i])< Integer.parseInt(playerscores[j]))
                {
                    temp = playernames[i];
                    playernames[i] = playernames[j];
                    playernames[j] = temp;
                    temp = playerscores[i];
                    playerscores[i] = playerscores[j];
                    playerscores[j] = temp;
                }
            }
        }


        list = (ListView) findViewById(R.id.highscores_list);

        // Calling the Adapter class object to set the players data as a customised list item.
        MyAdapter adapter = new MyAdapter(this,playernames,playerscores);
        list.setAdapter(adapter);

        // Redirect
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Highscores.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }

        );



    }



    // the adapter class for parsing the data into a custom list view .
    class MyAdapter extends ArrayAdapter {
        Context context;

        String [] playernames;
        String [] playerscores;


        MyAdapter(Context c,String[] playernames,String[] playerscores)
        {

            super(c,R.layout.list_item_design,playernames);

            // initializing the list view text fields to the Contact details data provided by the main Activity class.
            this.context = c;
            this.playernames= playernames;
            this.playerscores= playerscores;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row;

            if(playernames[position].equals("-"))
            {
                row = inflater.inflate(R.layout.empty_layout,parent,false);
            }
            else {
                row = inflater.inflate(R.layout.list_item_design, parent, false);


                Typeface stylish = Typeface.createFromAsset(getAssets(), "fonts/ARDESTINE.ttf");

                TextView name = (TextView) row.findViewById(R.id.player_name);
                TextView score = (TextView) row.findViewById(R.id.score);

                name.setText(playernames[position]);
                score.setText(playerscores[position]);

                name.setTypeface(stylish);
                score.setTypeface(stylish);
            }

            return row;
        }
    }
}
