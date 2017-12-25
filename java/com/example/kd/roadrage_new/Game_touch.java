package com.example.kd.roadrage_new;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;

import java.util.Random;

import static java.lang.Integer.parseInt;

/*
    Module developed by Keertan Dakarapu - kxd160830 and Dhawal Parmar - ddp160330
*/
public class Game_touch extends AppCompatActivity implements SensorEventListener {

    /*public static final long DEFAULT_ANIMATION_DURATION = 2500L;
    protected View mRoad;
    protected View mRoad1;
    protected View mFrameLayout;
    protected float mScreenHeight;
*/
    private float xPos, xAccel, xVel = 0.0f;
    private float yPos, yAccel, yVel = 0.0f;
    private float truckX,truckY = 0.0f;
    private float xMax, yMax;
    private float xPosSand1, yPosSand1, xPosSand2, yPosSand2 = 0.0f;
    private Bitmap car;
    private Bitmap truck;
    private Bitmap Bg1,Bg2;
    private int score;
    private Bitmap sand1,sand2;
    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        CarView carView = new CarView(this);

        setContentView(carView);
        score = 0;
        xPos = 800;
        yPos = 1000;
        xPosSand1 = 0.0f;
        yPosSand1 = 0.0f;
        xPosSand2 = 0.0f;
        yPosSand2 = -1920.0f;

        Point size = new Point();
        Display display = getWindowManager().getDefaultDisplay();
        display.getSize(size);
        xMax = (float) size.x - 250;
        yMax = (float) size.y - 100;

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);


    }

    @Override
    protected void onStart() {
        super.onStart();
        sensorManager.registerListener((SensorEventListener) this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onStop() {
        sensorManager.unregisterListener((SensorEventListener) this);
        super.onStop();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            xAccel = sensorEvent.values[0];
            yAccel = -sensorEvent.values[1];
            updateBall();
        }
    }

    private void updateBall() {

        float frameTime = 0.966f;

        if(yPosSand1 < yMax )
            yPosSand1 = yPosSand1 + 30.0f;
        else
            yPosSand1 = -1920.0f;

        if(yPosSand2 < yMax)
            yPosSand2 = yPosSand2 + 30.0f;
        else
            yPosSand2 = -1920.0f;


        if(!(xPos+150<truckX-10) && !(xPos>truckX+190) && !(yPos+280<truckY) && !(yPos>truckY+480))
        {
            sensorManager.unregisterListener((SensorEventListener) this);
            gothit(score);
        }
        else
        {
            score = score + 3;
        }



        xVel = (xAccel * frameTime);

        float xS = (xVel*2);

        xPos -= xS;


        if (xPos > xMax) {
            xPos = xMax;
        } else if (xPos < 250) {
            xPos = 250;
        }


        if(truckY>yMax+100) {
            truckY = -200;

            Random rand = new Random();

            truckX = rand.nextInt((int)xMax)+250;

        }
        else
        {
            truckY = truckY+5;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private class CarView extends View {

        public CarView(Context context) {
            super(context);

            Bitmap TruckSrc = BitmapFactory.decodeResource(getResources(), R.drawable.truck);



        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawBitmap(Bg1, 0, 0, null);
            canvas.drawBitmap(sand1, xPosSand1, yPosSand1, null);
            canvas.drawBitmap(sand2, xPosSand2, yPosSand2, null);

            canvas.drawBitmap(car, xPos, 1300, null);
            canvas.drawBitmap(truck, truckX, truckY, null);

            Typeface stylish = Typeface.createFromAsset(getAssets(),"fonts/ARDESTINE.ttf");

            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            paint.setTextSize(80);

            paint.setTypeface(stylish);

            canvas.drawText("Score:"+score,250,50,paint);
            invalidate();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    public void gothit(int endscore)
    {
        PackageManager m = getPackageManager();
        String s = getPackageName();

        try {
            PackageInfo p = m.getPackageInfo(s, 0);
            s = p.applicationInfo.dataDir;
        } catch (PackageManager.NameNotFoundException e) {
            Log.w("Sorry,", "Error Package name not found ", e);
        }

        Scoregetter sg = new Scoregetter(s,"db2.txt");

        boolean high = false;
        if(sg.highscorecount>=10) {
            for (int i = 0; i < sg.player_scores.length; i++) {
                if (parseInt(sg.player_scores[i]) < endscore) {
                    high = true;
                    break;
                }
            }
        }
        else
        {
            high = true;
        }

        if(high==true)
        {
            Intent intent = new Intent(Game_touch.this, HighScore_Input.class);
            String[] strarr = {""};
            strarr[0] = "" + endscore;
            intent.putExtra("string-array", strarr);
            startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(Game_touch.this, Game_end.class);
            String[] strarr = {""};
            strarr[0] = "" + endscore;
            intent.putExtra("string-array", strarr);
            startActivity(intent);
        }

    }


}