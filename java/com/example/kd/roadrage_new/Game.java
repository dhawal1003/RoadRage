package com.example.kd.roadrage_new;

import android.app.Activity;
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
import android.graphics.RectF;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import static java.lang.Integer.parseInt;

/*
 *
 *Human Computer Interaction - CS6326.001
 *under prof. John Cole
 *Project title: Roadrage
 *Description: The gameplay includes steering the car using the accelerometer and dodging the incoming trucks to achieve a highest score.
 * As you achieve a particular score, you will get a nitrous option which you can use to boost the speed of the car as well as the score.
 * Different cars and locations can be selected using the Settings Option.
 *Team:
 *Keertan Dakarapu (kxd160830)
 *Dhawal Parmar (ddp160330)
 *
*/


/*
    Module developed by Keertan Dakarapu - kxd160830 and Dhawal Parmar - ddp160330
*/
public class Game extends AppCompatActivity implements SensorEventListener {

    private float xPos, xAccel, xVel = 0.0f;
    private float yPos, yAccel, yVel = 0.0f;
    private float truckX,truckY = 0.0f;
    private float xMax, yMax;
    private boolean policeflag;
    private float screenX, screenY;
    private float xPosSand1, yPosSand1, xPosSand2, yPosSand2 = 0.0f;
    private Bitmap car;
    private Bitmap truck;
    private Bitmap Bg1,Bg2,pause,Police,flash,tail1,tail2,tail3;
    private int score;
    private int default_score;
    private Bitmap sand1,sand2;
    private SensorManager sensorManager;
    private int speed;
    public int cartype;
    public int truck_type;
    public int env_type;
    private String carSrc;
    private int speedinc;
    private double nitrous;
    private double nitrous_reserve;
    private int sensitivity;
    private int default_sensitivity;
    Boolean boost;
    Vehicle_properties player_car;
    Vehicle_properties villian_truck;
    private float policeX;
    boolean hit;
    Boolean pause_flag;
    public static Activity gamePage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gamePage = this;

        // Sets the screen orientation to potrait
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // Retrieve the settings stored during the last time the game was played
        Intent intent = getIntent();
        final int [] data = intent.getIntArrayExtra("integer-array");

        cartype = data[0];
        env_type = data[2];
        hit = false;
        if(env_type == 3) {
            Random rand = new Random();
            env_type = rand.nextInt(2);
        }
        truck_type = 11;
        policeflag = false;

        CarView carView = new CarView(this,cartype);
        setContentView(carView);

        // Set the default car, truck and other parameters
        score = 0;
        default_score = score;
        speed = 3;
        speedinc = 1;
        nitrous = 1.0;
        xPos = 440.0f;
        yPos = 1300.0f;
        xPosSand1 = 0.0f;
        yPosSand1 = 0.0f;
        xPosSand2 = 0.0f;
        yPosSand2 = -1920.0f;
        truckX = 240.0f;
        truckY = 0.0f;
        nitrous_reserve = 0;
        sensitivity = 3;
        sensitivity = (data[1] + 1)*3;
        default_sensitivity = sensitivity;
        boost = false;
        Point size = new Point();
        Display display = getWindowManager().getDefaultDisplay();
        display.getSize(size);
        screenX =  size.x;
        screenY =  size.y;
        pause_flag = false;

        xMax =  screenX - (((float)200/(float)1080)*screenX);
        yMax = screenX*(float)1.777;
        yPos = (float)(1300.0/1920.0)*screenY;

        player_car = new Vehicle_properties(cartype,screenX);
//        Toast.makeText(Game.this, ""+screenX, Toast.LENGTH_SHORT).show();
        villian_truck = new Vehicle_properties(truck_type,screenX);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        Bitmap t1 = BitmapFactory.decodeResource(getResources(), R.drawable.tail1);
        Bitmap t2 = BitmapFactory.decodeResource(getResources(), R.drawable.tail2);
        Bitmap t3 = BitmapFactory.decodeResource(getResources(), R.drawable.tail3);
        Bitmap road = BitmapFactory.decodeResource(getResources(), R.drawable.road1);
        Bitmap sand1Src  = BitmapFactory.decodeResource(getResources(), R.drawable.sand1);
        Bitmap sand2Src =  BitmapFactory.decodeResource(getResources(), R.drawable.sand2);
        final Bitmap pause_src = BitmapFactory.decodeResource(getResources(), R.drawable.pause_button);

        if(env_type==0) {
            road = BitmapFactory.decodeResource(getResources(), R.drawable.road1);
            sand1Src = BitmapFactory.decodeResource(getResources(), R.drawable.sand1);
            sand2Src = BitmapFactory.decodeResource(getResources(), R.drawable.sand2);
        }
       else if(env_type==1) {
            road = BitmapFactory.decodeResource(getResources(), R.drawable.road2);
            sand1Src = BitmapFactory.decodeResource(getResources(), R.drawable.grass1);
            sand2Src = BitmapFactory.decodeResource(getResources(), R.drawable.grass2);
        }
        else if(env_type==2) {
            road = BitmapFactory.decodeResource(getResources(), R.drawable.road3);
            sand1Src = BitmapFactory.decodeResource(getResources(), R.drawable.water1);
            sand2Src = BitmapFactory.decodeResource(getResources(), R.drawable.water2);
        }

        Bg1 = Bitmap.createScaledBitmap(road,(int)screenX,(int)(screenX*(float)1.777),true);
        sand1 = Bitmap.createScaledBitmap(sand1Src,(int)screenX,(int)(screenX*(float)1.777), true);
        sand2 = Bitmap.createScaledBitmap(sand2Src, (int)screenX,(int)(screenX*(float)1.777) , true);
        pause = Bitmap.createScaledBitmap(pause_src, (int)(((float)150/(float)1080)*screenX) ,(int)(((float)150/(float)1080)*screenX) , true);
        tail1 = Bitmap.createScaledBitmap(t1,  (int)player_car.width-50 , 2*(int)player_car.width-100 , true);
        tail2 = Bitmap.createScaledBitmap(t2,  (int)player_car.width-50 , 2*(int)player_car.width-100 , true);
        tail3 = Bitmap.createScaledBitmap(t3,  (int)player_car.width-50 , 2*(int)player_car.width-100 , true);

        Bitmap carSrc = BitmapFactory.decodeResource(getResources(), R.drawable.car);

        if(cartype==0) carSrc = BitmapFactory.decodeResource(getResources(), R.drawable.car);
        else if(cartype==1) carSrc = BitmapFactory.decodeResource(getResources(), R.drawable.car2);
        else if(cartype==2) carSrc = BitmapFactory.decodeResource(getResources(), R.drawable.car3);
        else carSrc = BitmapFactory.decodeResource(getResources(), R.drawable.car4);
        car = Bitmap.createScaledBitmap(carSrc, (int)player_car.width, (int)player_car.height, true);

        Bitmap TruckSrc = BitmapFactory.decodeResource(getResources(), R.drawable.truck);

        if(truck_type==11) {
            TruckSrc = BitmapFactory.decodeResource(getResources(), R.drawable.truck);
        }
        else if(truck_type==12)
        {
            TruckSrc = BitmapFactory.decodeResource(getResources(), R.drawable.truck2);
        }
        else if(truck_type==13)
        {
            TruckSrc = BitmapFactory.decodeResource(getResources(), R.drawable.policecar);
        }

        Bitmap flashsrc = BitmapFactory.decodeResource(getResources(), R.drawable.flash);
        flash = Bitmap.createScaledBitmap(flashsrc,100,100,true);

        truck = Bitmap.createScaledBitmap(TruckSrc, (int)villian_truck.width, (int)villian_truck.height, true);

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(!hit && !pause_flag) {
                    score = score + (int) (3 * nitrous);
                    default_score = default_score + 1;
                }
            }
        }, 0, 100);
    }

    // This event is used to pause the games as well as use the nitrous feature.
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.

        float x = e.getX();
        float y = e.getY();


        if(x>0 && x<200 && y>screenY-280 && y<screenY && !pause_flag)
        {
            pause_flag = true;
            Intent intent = new Intent(Game.this, pausescreen.class);
            startActivity(intent);
        }

        if(nitrous_reserve>=1000) {
            boost = true;
        }

        if(e.getAction()==MotionEvent.ACTION_UP)
        {
            boost = false;
            nitrous = 1.0;
            sensitivity = default_sensitivity;
        }
        return true;
    }

    // Registers the sensor listener on Start
        @Override
    protected void onStart() {
        super.onStart();
        sensorManager.registerListener((SensorEventListener) this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
    }


    // UnRegisters the sensor listener on Stop
    @Override
    protected void onStop() {
        sensorManager.unregisterListener((SensorEventListener) this);
        super.onStop();
    }

    // This method is used to capture the accelerometer values and accordingly move the car
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            xAccel = sensorEvent.values[0];
            yAccel = -sensorEvent.values[1];
            updateCar();
        }
    }

    // This method is used to change the car position, speed, truck position, road movements.
    private void updateCar() {
        float frameTime = 0.966f;

        if(!pause_flag){
            if (yPosSand1 < yMax)
                yPosSand1 = yPosSand1 + (10.0f * speed * (float) nitrous);
            else
                yPosSand1 = -screenX * (float) 1.777;

            if (yPosSand2 < yMax)
                yPosSand2 = yPosSand2 + (10.0f * speed * (float) nitrous);
            else
                yPosSand2 = -screenX * (float) 1.777;

            int w1 = (int) player_car.width;
            int w2 = (int) villian_truck.width;
            int h1 = (int) player_car.height;
            int h2 = (int) villian_truck.height;

            if ((truckX > xPos && yPos > truckY && truckX - xPos < w1 - 5 && yPos - truckY < h2 - 5) || (truckX < xPos && yPos > truckY && xPos - truckX < w2 - 10 && yPos - truckY < h2 - 5) || (truckX < xPos && yPos < truckY && xPos - truckX < w2 - 30 && truckY - yPos < h1 - 50) || (truckX > xPos && yPos < truckY && truckX - xPos < w1 - 5 && truckY - yPos < h1 - 50)) {
                sensorManager.unregisterListener((SensorEventListener) this);
                hit = true;
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        gothit(score);
                    }
                }, 1000);

            } else {
                if (nitrous_reserve < 1000 && !boost)
                    nitrous_reserve += 1;

                if ((default_score) > (100 * speedinc)) {
                    if (nitrous > 1.0) speed += (default_score / (100 * speedinc)) * nitrous;
                    else speed += (score / (100 * speedinc)) * nitrous;
                    speedinc++;
                }
            }

            xVel = (xAccel * frameTime);
            float xS = (xVel * sensitivity);
            xPos -= xS;

            if (xPos > (xMax - ((float) 150 / (float) 1080) * screenX)) {
                xPos = xMax - ((float) 150 / (float) 1080) * screenX;
            } else if (xPos < ((float) 200 / (float) 1080) * screenX) {
                xPos = ((float) 200 / (float) 1080) * screenX;
            }

            if (truckY > yMax + 100) {
                policeflag = false;
                truckY = -250;
                Random rand = new Random();
                truckX = rand.nextFloat() * (xMax - ((float) 400 / (float) 1080) * screenX) + ((float) 200 / (float) 1080) * screenX;
            } else {
                truckY = truckY + (speed * (float) nitrous);
            }

            if (boost) {
                if (nitrous_reserve > 0) {
                    nitrous_reserve = nitrous_reserve - 3;
                    nitrous = 2.0;
                    sensitivity = 15;
                } else {
                    nitrous = 1.0;
                    boost = false;
                    sensitivity = default_sensitivity;
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    // This view is used to draw the car, road, trucks and invalidate the views continuously.
    private class CarView extends View {

        public CarView(Context context,int cartypet) {
            super(context);
            Bitmap nit =BitmapFactory.decodeResource(getResources(), R.drawable.nitrous);
            // Bitmap score_bg = BitmapFactory.decodeResource(getResources(), R.drawable.custom_textbg);
            Bg2 = Bitmap.createScaledBitmap(nit,150,227,true);
        }

        // This function is called to generate the view continuously.
        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawBitmap(Bg1, 0, 0, null);
            canvas.drawBitmap(sand1, xPosSand1, yPosSand1, null);
            canvas.drawBitmap(sand2, xPosSand2, yPosSand2, null);

            if(boost)
            {
                switch(default_score%3) {
                    case 0:
                        canvas.drawBitmap(tail1, xPos+25, yPos + (int)player_car.height-50, null);
                     break;
                    case 1:
                        canvas.drawBitmap(tail2, xPos+25, yPos + (int)player_car.height-50, null);
                    break;
                    case 2:
                        canvas.drawBitmap(tail3, xPos+25, yPos + (int)player_car.height-50, null);
                        break;
                    default:
                        //canvas.drawBitmap(tail2, xPos, yPos + 300, null);
                }
            }

            canvas.drawBitmap(car, xPos, yPos, null);
            canvas.drawBitmap(truck, truckX, truckY, null);

            Typeface stylish = Typeface.createFromAsset(getAssets(),"fonts/ARDESTINE.ttf");

            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            paint.setTextSize(60);
            paint.setTypeface(stylish);

            //for nitrous
            Paint myPaint = new Paint();
            Paint toppaint = new Paint();

            myPaint.setColor(Color.rgb(200, 200, 200));
            toppaint.setColor(Color.rgb(0, 0, 0));

            Paint bor = new Paint();
            bor.setColor(Color.rgb(0, 0, 0));
            bor.setStyle(Paint.Style.STROKE);
            bor.setStrokeWidth(5);

            Paint myPaint2 = new Paint();
            myPaint2.setColor(Color.rgb((int)(1000-nitrous_reserve/1000*200),(int)(nitrous_reserve/1000*200), 0));

            RectF nav = new RectF((((float)200/(float)1080)*screenX),0,screenX - (((float)200/(float)1080)*screenX),55);
            canvas.drawRect(nav, toppaint);
            canvas.drawText("Score:"+score,(int)(screenX/2)-220,50,paint);

            RectF rec_bg = new RectF(screenX-120,screenY-620,screenX-60,screenY-120);
            RectF rec_fill = new RectF(screenX-120,(int)(((1000-nitrous_reserve)/1000)*500+screenY-620),screenX-60,screenY-120);
            RectF rec_border = new RectF(screenX-120,screenY-620,screenX-60,screenY-120);

            canvas.drawRect(rec_bg, myPaint);
            canvas.drawRect(rec_fill, myPaint2);
            canvas.drawRect(rec_border, bor);
            canvas.drawBitmap(Bg2, screenX-170, screenY-320, null);

            if(nitrous_reserve>=1000 && default_score%2==0) {
               canvas.drawBitmap(flash, screenX - 120, screenY - 720, null);
               paint.setTextSize(40);
               canvas.drawText("Touch screen for Nitrous",(int)(screenX/2)-220,90,paint);
            }

            if(hit)
            {
                Bitmap hit_src =BitmapFactory.decodeResource(getResources(), R.drawable.crash);
                Bitmap crash = Bitmap.createScaledBitmap(hit_src,300,232,true);
                canvas.drawBitmap(crash, xPos,  yPos, null);
            }
            canvas.drawBitmap(pause, 30, screenY-250, null);
            invalidate();
        }
    }

    // Generates a delay of 2 secs to resume the gameplay
    @Override
    protected void onResume() {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                pause_flag = false;
            }
        }, 2000);
        super.onResume();
   }

   // This function is called when the car crashes and it updates the highscores accordingly.
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
            Intent intent = new Intent(Game.this, HighScore_Input.class);
            String[] strarr = {""};
            strarr[0] = "" + endscore;
            intent.putExtra("string-array", strarr);
            startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(Game.this, Game_end.class);
            String[] strarr = {""};
            strarr[0] = "" + endscore;
            intent.putExtra("string-array", strarr);
            startActivity(intent);
        }
        finish();
    }

}