package com.example.a1126;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public MyView titlevw;
    public ranking Ranking;
    static MediaPlayer player;
    int score = 0;
    int touchflag1 = 0, touchflag2 = 0;
    float BGY = -400;
    int width;
    int height;
    int DPI;
    int px_w;
    int howtoflag = 0, rankflag = 0;

    String name1, name4, name5, name2, name3;
    int score1, score2, score3, score4, score5;
    String name6, name7, name8, name9, name10;
    int score6, score7, score8, score9, score10;

    Bitmap background, title, touch, howto, easter;
    Handler TitleHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        DPI = metrics.densityDpi;
        px_w = DPI / 160;

        player = MediaPlayer.create(this, R.raw.flyaway_loop);
        player.setVolume(0.7f, 0.7f);
        player.setLooping(true);
        player.start();

        Ranking = new ranking();
        Ranking.load5();

        Resources res = getResources();
        background = BitmapFactory.decodeResource(res, R.drawable.hw_bg);
        Bitmap title_ = BitmapFactory.decodeResource(res, R.drawable.hw_title);
        title = Bitmap.createScaledBitmap(title_, width, 200 * (width / 480), true);
        Bitmap touch_ = BitmapFactory.decodeResource(res, R.drawable.hw_touch);
        touch = Bitmap.createScaledBitmap(touch_, width, 32 * (width / 400), true);
        Bitmap howto_ = BitmapFactory.decodeResource(res, R.drawable.hw_howto);
        howto = Bitmap.createScaledBitmap(howto_, width, 417*(width / 480), true);
        Bitmap easter_ = BitmapFactory.decodeResource(res, R.drawable.hw_easteregg);
        easter = Bitmap.createScaledBitmap(easter_, width, 417*(width / 480), true);

        titlevw = new MyView(this);
        setContentView(titlevw);

        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.activity_main, null);
        addContentView(v, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT));

        Button ranking_btn = v.findViewById(R.id.ranking_btn);
        Button howto_btn = v.findViewById(R.id.howto_btn);

        howto_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (howtoflag == 0) {
                    howtoflag = 1;
                }
                else{
                    howtoflag = 0;
                    rankflag = 0;
                }
            }
        });
        ranking_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (rankflag== 0) {
                    rankflag = 1;
                }
                else{
                    howtoflag = 0;
                    rankflag = 0;
                }
            }
        });

        TitleHandler = new Handler(){
            public void handleMessage(Message msg){
                if(msg.what == 1){
                    BGY += 2;
                    touchflag1++;
                    if(BGY >= 800 * px_w){
                        BGY = 0;
                    } // 배경

                    if(touchflag1 % 50 == 49){
                        if(touchflag2 == 1){
                            touchflag2 = 0;
                        }
                        else{
                            touchflag2 = 1;
                        }
                        touchflag1 = 0;
                    }

                    titlevw.invalidate();
                    TitleHandler.sendEmptyMessageDelayed(1, 10);
                }
            }
        };
        TitleHandler.sendEmptyMessage(1);

        titlevw.setOnClickListener(new MyView.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (howtoflag == 0 && rankflag == 0) {
                    Intent maintogame = new Intent(MainActivity.this, GameActivity_.class);
                    startActivityForResult(maintogame, 0);
                }
                else{
                    howtoflag = 0;
                    rankflag = 0;
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        if(resultCode == RESULT_OK) {
            score = data.getIntExtra("score", score);

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Your Score is : " + score)
                    .setMessage("Do you want to continue?")
                    .setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent maintogame = new Intent(MainActivity.this, GameActivity_.class);
                            startActivityForResult(maintogame, 0);
                        }
                    })
                    .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferences sharedPreferences = getSharedPreferences("highscore", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putInt("score", score);
                            editor.apply();
                            finishAffinity();
                            System.runFinalization();
                            System.exit(0);
                        }
                    })
                    .setNeutralButton("등록", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            final EditText name = new EditText(MainActivity.this);
                            AlertDialog.Builder ranking = new AlertDialog.Builder(MainActivity.this);
                            ranking.setTitle("Setting Score on Ranking")
                                    .setMessage("Enter Your Name :")
                                    .setView(name)
                                    .setPositiveButton("예", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            String value = name.getText().toString();
                                            Ranking.savehighscore(value, score);
                                            Ranking.save5();
                                            dialog.dismiss();
                                        }
                                    })
                                    .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).setCancelable(false);
                            AlertDialog alertDialog = ranking.create();
                            alertDialog.show();
                            ;
                        }
                    })
                    .setCancelable(false);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    class MyView extends View {
        public MyView(Context context) {
            super(context);
        }

        public void onDraw(Canvas canvas) { //onDraw 함수는 시스템에 변화가 생겼을 때만 리로드된다
            Paint text = new Paint();
            text.setAntiAlias(true);
            text.setTextSize(50);
            text.setColor(Color.BLACK);
            Typeface t = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL);
            text.setTypeface(t);

            canvas.drawColor(0xFFFF9C5A);
            canvas.drawBitmap(background, 0, BGY, null); //(0, Y)
            canvas.drawBitmap(background, 0, BGY - 1920 - 300, null); //(0, Y+800)
            if(howtoflag == 0 && rankflag == 0) {
                canvas.drawBitmap(title, 0, height / 4, null);
                canvas.drawText("Highscore : " + score1, 0, 50, text);

                if (touchflag2 == 0) {
                    canvas.drawBitmap(touch, 0, height * 4 / 5, null);
                }
            }
            else if(howtoflag == 1 && rankflag == 0){
                canvas.drawBitmap(howto, 0, height / 4, null);
            }
            else if(howtoflag == 0 && rankflag == 1){
                canvas.drawText("Rank 1 : " + name1 + ", Score : "+ score1, 0, 50, text);
                canvas.drawText("Rank 2 : " + name2 + ", Score : "+ score2, 0, 200, text);
                canvas.drawText("Rank 3 : " + name3 + ", Score : "+ score3, 0, 350, text);
                canvas.drawText("Rank 4 : " + name4 + ", Score : "+ score4, 0, 500, text);
                canvas.drawText("Rank 5 : " + name5 + ", Score : "+ score5, 0, 650, text);
                canvas.drawText("Rank 6 : " + name6 + ", Score : "+ score6, 0, 800, text);
                canvas.drawText("Rank 7 : " + name7 + ", Score : "+ score7, 0, 950, text);
                canvas.drawText("Rank 8 : " + name8 + ", Score : "+ score8, 0, 1100, text);
                canvas.drawText("Rank 9 : " + name9 + ", Score : "+ score9, 0, 1250, text);
                canvas.drawText("Rank 10 : " + name10 + ", Score : "+ score10, 0, 1400, text);
            }
            else{
                canvas.drawBitmap(easter, 0, height / 4, null);
                canvas.drawText("BGM by theFatRat", 0, height - 300, text);
                canvas.drawText("sfx by Game Freak", 0, height - 250, text);

            }
        }
    }

    class ranking{
        void save5(){
            SharedPreferences high = getSharedPreferences("highscore", MODE_PRIVATE);
            SharedPreferences.Editor editor = high.edit();
            editor.putString("no1_name", name1);
            editor.putString("no2_name", name2);
            editor.putString("no3_name", name3);
            editor.putString("no4_name", name4);
            editor.putString("no5_name", name5);
            editor.putString("no6_name", name6);
            editor.putString("no7_name", name7);
            editor.putString("no8_name", name8);
            editor.putString("no9_name", name9);
            editor.putString("no10_name", name10);
            editor.putInt("no1_score", score1);
            editor.putInt("no2_score", score2);
            editor.putInt("no3_score", score3);
            editor.putInt("no4_score", score4);
            editor.putInt("no5_score", score5);
            editor.putInt("no6_score", score6);
            editor.putInt("no7_score", score7);
            editor.putInt("no8_score", score8);
            editor.putInt("no9_score", score9);
            editor.putInt("no10_score", score10);
            editor.apply();
        }
        void load5(){
            SharedPreferences high = getSharedPreferences("highscore", MODE_PRIVATE);
            name1 = high.getString("no1_name", "EE");
            score1 = high.getInt("no1_score", 0);
            name2 = high.getString("no2_name", "EE");
            score2 = high.getInt("no2_score", 0);
            name3 = high.getString("no3_name", "EE");
            score3 = high.getInt("no3_score", 0);
            name4 = high.getString("no4_name", "EE");
            score4 = high.getInt("no4_score", 0);
            name5 = high.getString("no5_name", "EE");
            score5 = high.getInt("no5_score", 0);
            name6 = high.getString("no6_name", "EE");
            score6 = high.getInt("no6_score", 0);
            name7 = high.getString("no7_name", "EE");
            score7 = high.getInt("no7_score", 0);
            name8 = high.getString("no8_name", "EE");
            score8 = high.getInt("no8_score", 0);
            name9 = high.getString("no9_name", "EE");
            score9 = high.getInt("no9_score", 0);
            name10 = high.getString("no10_name", "EE");
            score10 = high.getInt("no10_score", 0);
        }
        void savehighscore(String value, int Score){
            if(Score >= score1){
                name10 = name9;
                name9 = name8;
                name8 = name7;
                name7 = name6;
                name6 = name5;
                name5 = name4;
                name4 = name3;
                name3 = name2;
                name2 = name1;
                name1 = value;
                score10 = score9;
                score9 = score8;
                score8 = score7;
                score7 = score6;
                score6 = score5;
                score5 = score4;
                score4 = score3;
                score3 = score2;
                score2 = score1;
                score1 = Score;
            }
            else if(Score < score1 && Score >= score2){
                name10 = name9;
                name9 = name8;
                name8 = name7;
                name7 = name6;
                name6 = name5;
                name5 = name4;
                name4 = name3;
                name3 = name2;
                name2 = value;
                score10 = score9;
                score9 = score8;
                score8 = score7;
                score7 = score6;
                score6 = score5;
                score5 = score4;
                score4 = score3;
                score3 = score2;
                score2 = Score;
            }
            else if(Score < score2 && Score>= score3){
                name10 = name9;
                name9 = name8;
                name8 = name7;
                name7 = name6;
                name6 = name5;
                name5 = name4;
                name4 = name3;
                name3 = value;
                score10 = score9;
                score9 = score8;
                score8 = score7;
                score7 = score6;
                score6 = score5;
                score5 = score4;
                score4 = score3;
                score3 = Score;
            }
            else if(Score < score3 && Score >= score4){
                name10 = name9;
                name9 = name8;
                name8 = name7;
                name7 = name6;
                name6 = name5;
                name5 = name4;
                name4 = value;
                score10 = score9;
                score9 = score8;
                score8 = score7;
                score7 = score6;
                score6 = score5;
                score5 = score4;
                score4 = Score;
            }
            else if(Score < score4 && Score >= score5){
                name10 = name9;
                name9 = name8;
                name8 = name7;
                name7 = name6;
                name6 = name5;
                name5 = value;
                score10 = score9;
                score9 = score8;
                score8 = score7;
                score7 = score6;
                score6 = score5;
                score5 = Score;
            }
            else if(Score < score5 && Score >= score6){
                name10 = name9;
                name9 = name8;
                name8 = name7;
                name7 = name6;
                name6 = value;
                score10 = score9;
                score9 = score8;
                score8 = score7;
                score7 = score6;
                score6 = Score;
            }
            else if(Score < score6 && Score >= score7){
                name10 = name9;
                name9 = name8;
                name8 = name7;
                name7 = value;
                score10 = score9;
                score9 = score8;
                score8 = score7;
                score7 = Score;
            }
            else if(Score < score7 && Score >= score8){
                name10 = name9;
                name9 = name8;
                name8 = value;
                score10 = score9;
                score9 = score8;
                score8 = Score;
            }
            else if(Score < score8 && Score >= score9){
                name10 = name9;
                name9 = value;
                score10 = score9;
                score9 = Score;
            }
            else if(Score < score9 && Score >= score10){
                name10 = value;
                score10 = Score;
            }
        }
    }
}