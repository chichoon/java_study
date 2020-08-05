package com.example.a1119;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public MyView vw;
    ArrayList<Ball4> Ball4 = new ArrayList<Ball4>();
    Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vw = new MyView(this);
        setContentView(vw);
        mHandler = new Handler(){
            public void handleMessage(Message msg){
                if(Ball4.size() != 0){
                    for(int i = 0; i <  Ball4.size(); i++){
                        Ball4.get(i).X1 += Ball4.get(i).Speed;
                        Ball4.get(i).Y1 += Ball4.get(i).Speed;
                        Ball4.get(i).X2 -= Ball4.get(i).Speed;
                        Ball4.get(i).Y2 += Ball4.get(i).Speed;
                        Ball4.get(i).X3 -= Ball4.get(i).Speed;
                        Ball4.get(i).Y3 -= Ball4.get(i).Speed;
                        Ball4.get(i).X4 += Ball4.get(i).Speed;
                        Ball4.get(i).Y4 -= Ball4.get(i).Speed;
                        if(Ball4.get(i).X1 >= 1200 && Ball4.get(i).X3 < 0 && Ball4. get(i).Y1 < 0 && Ball4.get(i).Y3 >= 2000){
                            Ball4.remove(i);
                            break;
                        }
                    }
                }
                vw.invalidate();
                mHandler.sendEmptyMessageDelayed(0, 10);
            }
        };
        mHandler.sendEmptyMessage(0);
    }
    public class Ball4 {
        Ball4(float x, float y) {
            X1 = x;
            X2 = x;
            X3 = x;
            X4 = x;
            Y1 = y;
            Y2 = y;
            Y3 = y;
            Y4 = y;
            Color = new Random().nextInt(0xFFFFFF) + 0xFF000000;
            Speed = new Random().nextInt(1000) % 30 + 1;
            Radius = new Random().nextInt(1000) % 50 + 10;
            paint = new Paint();
            paint.setColor(Color);
        }
        float X1, Y1, X2, Y2, X3, Y3, X4, Y4;
        int Color;
        int Speed;
        int Radius;
        Paint paint;
    }

    class MyView extends View {
        public MyView(Context context) {
            super(context);
        }
        public void onDraw(Canvas canvas) { //onDraw 함수는 시스템에 변화가 생겼을 때만 리로드된다
            canvas.drawColor(Color.WHITE); //따라서 invalidate 함수는 시스템에 임의로 변경점을 만들어 onDraw를 리로드하는 것이다
            if(Ball4.size() != 0){
                for(int i = 0; i <  Ball4.size(); i++) {
                    canvas.drawCircle(Ball4.get(i).X1, Ball4.get(i).Y1, Ball4.get(i).Radius, Ball4.get(i).paint);
                    canvas.drawCircle(Ball4.get(i).X2, Ball4.get(i).Y2, Ball4.get(i).Radius, Ball4.get(i).paint);
                    canvas.drawCircle(Ball4.get(i).X3, Ball4.get(i).Y3, Ball4.get(i).Radius, Ball4.get(i).paint);
                    canvas.drawCircle(Ball4.get(i).X4, Ball4.get(i).Y4, Ball4.get(i).Radius, Ball4.get(i).paint);
                }
            }
        }

        public boolean onTouchEvent(MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                Ball4.add(new Ball4(event.getX(), event.getY()));
                invalidate();
                return true;
            }
            return false;
        }
    }
}
