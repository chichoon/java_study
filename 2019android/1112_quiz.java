package com.example.a1105;

        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Context;
        import android.graphics.Canvas;
        import android.graphics.Color;
        import android.graphics.Paint;
        import android.os.Bundle;
        import android.os.Handler;
        import android.os.Message;
        import android.view.Display;
        import android.view.View;
        import android.view.WindowManager;

        import java.util.ArrayList;
        import java.util.Random;


public class MainActivity extends AppCompatActivity {
    public MyView vw; //**
    ArrayList<Balls> Balls = new ArrayList<Balls>();
    Handler mHandler;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vw = new MyView(this);
        setContentView(vw);

        for(int i = 0; i < 10; i++){
            Balls.add(new Balls());
        }

        mHandler = new Handler(){
            public void handleMessage(Message msg){
                for(int i = 0; i < 10; i++){
                    Balls.get(i).x += Balls.get(i).Speed * Balls.get(i).Direction;
                    if(Balls.get(i).x >= 800){
                        Balls.get(i).Direction = -1;
                    }
                    else if(Balls.get(i).x < 0){
                        Balls.get(i).Direction = 1;
                    }
                }
                vw.invalidate();
                mHandler.sendEmptyMessageDelayed(0, 10);
            }
        };
        mHandler.sendEmptyMessage(0);
    }

    public class Balls {
        Balls() {
            int n = new Random().nextInt(1000000);
            x = n % 480; //Vertex의 생성자 함수, 클래스 생성시 한번만 불림
            y = n % 1000 + 30; //생성자가 불릴 때마다 좌표를 가져옴
            Color = new Random().nextInt(0xFFFFFF) + 0xFF000000;
            Speed = n % 30;
            Direction = 1;
            Radius = n % 30 + 5;
        }
        int x;
        int y;
        int Color;
        int Speed;
        int Direction;
        int Radius;
    }

    class MyView extends View {
        public MyView(Context context) {
            super(context);
        }

        public void onDraw(Canvas canvas) { //onDraw 함수는 시스템에 변화가 생겼을 때만 리로드된다
            canvas.drawColor(Color.WHITE); //따라서 invalidate 함수는 시스템에 임의로 변경점을 만들어 onDraw를 리로드하는 것이다
            ArrayList<Paint> paint = new ArrayList<Paint>();
            for(int i = 0; i < 10; i++){
                paint.add(new Paint());
                paint.get(i).setColor(Balls.get(i).Color);
                paint.get(i).setAntiAlias(true);
                canvas.drawCircle(Balls.get(i).x, Balls.get(i).y, Balls.get(i).Radius, paint.get(i));
            }
        }
    }
}
