package com.example.a1029;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import android.os.Bundle;
import android.widget.LinearLayout;

import org.w3c.dom.Attr;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public MyView vw; //**
    int lineColor;
    int eraseflag = 0;

    ArrayList<Vertex> arVertex;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        vw = new MyView(this);
        vw.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 640));
        lineColor = Color.BLUE;

        LinearLayout layout0 = new LinearLayout(this);
        layout0.setOrientation(LinearLayout.VERTICAL);
        layout0.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
        layout0.setGravity(0x01);
        LinearLayout layout1 = new LinearLayout(this);
        layout1.setOrientation(LinearLayout.HORIZONTAL);
        layout1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layout1.setGravity(0x01);
        LinearLayout layout2 = new LinearLayout(this);
        layout2.setOrientation(LinearLayout.HORIZONTAL);
        layout2.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layout2.setGravity(0x01);
        LinearLayout layout3 = new LinearLayout(this);
        layout3.setOrientation(LinearLayout.HORIZONTAL);
        layout3.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layout3.setGravity(0x01);

        Button bt_up = new Button(this);
        bt_up.setLayoutParams(new ViewGroup.LayoutParams(200, ViewGroup.LayoutParams.MATCH_PARENT));
        bt_up.setText("up");
        bt_up.setId((int) 1);

        Button bt_left = new Button(this);
        bt_left.setLayoutParams(new ViewGroup.LayoutParams(200, ViewGroup.LayoutParams.MATCH_PARENT));
        bt_left.setText("left");
        bt_left.setId((int) 2);

        Button bt_color = new Button(this);
        bt_color.setLayoutParams(new ViewGroup.LayoutParams(200, ViewGroup.LayoutParams.MATCH_PARENT));
        bt_color.setText("Color");
        bt_color.setId((int) 3);

        Button bt_right = new Button(this);
        bt_right.setLayoutParams(new ViewGroup.LayoutParams(200, ViewGroup.LayoutParams.MATCH_PARENT));
        bt_right.setText("right");
        bt_right.setId((int) 4);

        Button bt_down = new Button(this);
        bt_down.setLayoutParams(new ViewGroup.LayoutParams(200, ViewGroup.LayoutParams.MATCH_PARENT));
        bt_down.setText("down");
        bt_down.setId((int) 5);

        bt_up.setOnClickListener(this);
        bt_left.setOnClickListener(this);
        bt_color.setOnClickListener(this);
        bt_right.setOnClickListener(this);
        bt_down.setOnClickListener(this);

        layout0.addView(vw); //vw : 내가 만든 view (캔버스)
        layout0.addView(layout1); //버튼 레이아웃 상단
        layout0.addView(layout2); //버튼 레이아웃 중앙
        layout0.addView(layout3); //버튼 레이아웃 하단

        layout1.addView(bt_up);
        layout2.addView(bt_left);
        layout2.addView(bt_color);
        layout2.addView(bt_right);
        layout3.addView(bt_down);

        setContentView(layout0);

        arVertex = new ArrayList<Vertex>();
        arVertex.clear();
        arVertex.add(new Vertex(250, 250, false, lineColor)); //**

    }

    public class Vertex {
        Vertex(float ax, float ay, boolean ad, int color) {
            x = ax; //Vertex의 생성자 함수, 클래스 생성시 한번만 불림
            y = ay; //생성자가 불릴 때마다 좌표를 가져옴
            Draw = ad;
            Color = color;
        }
        float x;
        float y;
        boolean Draw; //점만 찍을 땐 False,
        int Color;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case 1:
                if (vw.mY > 25) vw.mY -= 5;
                arVertex.add(new Vertex(vw.mX, vw.mY, true, lineColor));
                eraseflag = 0;
                vw.invalidate();
                break;
            case 2:
                if (vw.mX > 25) vw.mX -= 5;
                arVertex.add(new Vertex(vw.mX, vw.mY, true, lineColor));
                eraseflag = 0;
                vw.invalidate();
                break;
            case 3:
                if (vw.cColor == Color.BLUE) {
                    vw.cColor = Color.RED;
                    lineColor = Color.RED;
                    eraseflag++;
                } else {
                    vw.cColor = Color.BLUE;
                    lineColor = Color.BLUE;
                    eraseflag++;
                }
                if(eraseflag == 2){
                    arVertex.clear();
                    arVertex.add(new Vertex(vw.mX, vw.mY, false, lineColor)); //**
                    eraseflag = 0;
                }
                vw.invalidate();
                break;
            case 4:
                if (vw.mX < 500 - 35) vw.mX += 5;
                arVertex.add(new Vertex(vw.mX, vw.mY, true, lineColor));
                eraseflag = 0;
                vw.invalidate();
                break;
            case 5:
                if (vw.mY < 500 - 25) vw.mY += 5;
                arVertex.add(new Vertex(vw.mX, vw.mY, true, lineColor));
                eraseflag = 0;
                vw.invalidate();
                break;
        }
    }

    class MyView extends View {
        float mX, mY;
        int cColor;
        Paint mPaint;

        public MyView(Context context) {
            super(context);
            mX = 250;
            mY = 250;
            cColor = Color.BLUE;
        }

        public void onDraw(Canvas canvas) {

            mPaint = new Paint();

            mPaint.setStrokeWidth(3);
            mPaint.setAntiAlias(true);

            canvas.drawColor(Color.WHITE);
            Paint Pnt = new Paint();
            Pnt.setColor(cColor);
            Pnt.setAntiAlias(true);
            canvas.drawCircle(mX, mY, 16, Pnt);
            for (int i = 0; i < arVertex.size(); i++) {
                if (arVertex.get(i).Draw) {
                    mPaint.setColor(arVertex.get(i).Color);
                    canvas.drawLine(arVertex.get(i - 1).x, arVertex.get(i - 1).y, arVertex.get(i).x, arVertex.get(i).y, mPaint);
                }
            }
        }
    }
}
