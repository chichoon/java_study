package com.example.a1015;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MyView vw;
    ArrayList<Vertex> arVertex; //Array 이름과 함께 선언
    //For문을 이용하여 수행하기 위함
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate
                (savedInstanceState);
        vw = new MyView(this); //MyView 클래스의 객체 vw 생성
        setContentView(vw);
        arVertex = new ArrayList<Vertex>();
        //Array를 저장할 그릇을 생성함
        //이걸 빼먹으면 오류가 날것
    }
    // 정점 하나에 대한 정보를 가지는 클래스
    public class Vertex {
        Vertex(float ax, float ay, boolean ad) {
            x = ax; //Vertex의 생성자 함수, 클래스 생성시 한번만 불림
            y = ay; //생성자가 불릴 때마다 좌표를 가져옴
            Draw = ad;
        }
        float x;
        float y;
        boolean Draw; //점만 찍을 땐 False,
        //점을 찍은 채로 움직이면 선을 긋는다는 의미에서
        //Draw 변수가 True가 된다
    }
    protected class MyView extends View { //View를 상속받는 MyView
        Paint mPaint;
        public MyView(Context context) { //생성자 함수
            super(context); //상속받은 부모클래스의 생성자 함수 끌어옴
            // Paint 객체 미리 초기화
            mPaint = new Paint(); //Paint의 객체 생성
            mPaint.setColor(Color.BLACK); //점의 색은 Black
            mPaint.setStrokeWidth(3); //선의 두께는 3
            mPaint.setAntiAlias(true);
            //여기까지 연필을 설정하는 코드
        }
        public void onDraw(Canvas canvas) { //OnDraw함수
            canvas.drawColor(Color.LTGRAY); //배경색이 회색
            for (int i=0;i<arVertex.size();i++) { //★★★매우 중요한 For문
                //For문이 끝나면 해당 int i는 사라져버린다
                //arVertex.size() : 해당 Array의 객채의 갯수
                //int i = 1; i <= arVertex로 해도 값은 똑같지만, 가급적 0부터 하는 것이 좋다
                //객체의 Index는 0부터 시작하기 때문에
                if (arVertex.get(i).Draw) {
                    canvas.drawLine(arVertex.get(i-1).x, arVertex.get(i-1).y,
                            arVertex.get(i).x, arVertex.get(i).y, mPaint);
                } //점을 이어주는 역할
                //직전에 그려진 점과 현재 찍힌 점을 mPaint에 지정된 설정 (검은색, 3px) 대로 이으시오
                //Invalidate 때문에 해당 함수는 계속 불러와져 수행된다
            }
            // 정점을 순회하면서 선분으로 잇는다.
        }
        // 터치 이동시마다 정점들을 추가한다.
        public boolean onTouchEvent(MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                //ACTION_DOWN: 터치 발생
                arVertex.add(new Vertex(event.getX(), event.getY(), false));
                //점 객체를 생성 (X좌표와 Y좌표를 가져오고, 찍은 채로 움직이지 않으므로 False)
                return true;
            }
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                //ACTION_MOVE: 터치한채로 드래그
                arVertex.add(new Vertex(event.getX(), event.getY(), true));
                //점 객체를 생성 (X좌표와 Y좌표를 가져오고, 찍은 채로 Drag하므로
                //점을 이어 선으로 만들겠다는 의미에서 True)
                invalidate();
                //Invalidate: View에 있는 OnDraw 함수를 강제로 부른다
                //화면을 계속 업데이트해주는 함수이므로, 꼭 불러줘야 화면이 업데이트되면서
                //선이 그려지는 것이 보임
                return true;
            }
            return false;
        }
    }
}