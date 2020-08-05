package com.example.a0924_;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    public MyView vw;
    public Button bt_up,bt_down,bt_left,bt_right,bt_center ;
    Paint Pnt_Green, Pnt_Black;
    int mColor;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout layout0 = new LinearLayout(this); //제일 바탕에 있는 layout
        layout0.setOrientation( LinearLayout.VERTICAL ); //내부에서 vertical 배치 (↕)
        // ↓ layout0을 width도 부모와 같게, heightㄷ 부모와 같게 설정하는 코드 (부모: Constraint Layout)
        layout0.setLayoutParams(new ViewGroup.LayoutParams (ViewGroup.LayoutParams.FILL_PARENT , ViewGroup.LayoutParams.FILL_PARENT ));
        layout0.setGravity(0x01);
        // ↓ 펜을 정의
        Pnt_Green = new Paint();
        Pnt_Green.setColor(Color.GREEN);
        Pnt_Green.setAntiAlias (true);
        Pnt_Black = new Paint();
        Pnt_Black.setColor(Color.BLACK);
        Pnt_Black.setAntiAlias (true);
        // ↓ view 캔버스를 정의
        vw = new MyView(this);
        vw.setLayoutParams(new ViewGroup.LayoutParams (ViewGroup.LayoutParams.WRAP_CONTENT , 460));

        String TAG = "MyMSG"; //tag를 지정
        Log.i(TAG, "hello everyone"); //지정된 태그와 함께 log에 찍을 메시지를 입력
        //logcat에 지정한 tag에 해당하는 메시지만을 띄울 수 있다
        int a = 3; //a = 3이라고 선언
        Log.i(TAG, "a = " + a); //a의 값을 출력할 수 있다

        /*
        //새로운 java 파일에서 클래스 정의
        package com.example.a0924_;

        public class test{
                public int adder(int a, int b){ //test 클래스 내의 adder 함수 정의
                return a + b;
                }
                public int multiplier(int a, int b){ //test 클래스 내의 multiplier 함수 정의
                return a * b;
                }
        }
        */
        test t = new test(); //test 클래스에 해당하는 객체 t를 생성
        int b = t.adder(1,4); //t 안에 들어있는 adder 함수를 사용
        int c = t.multiplier(4, 5); //t 안에 들어있는 Multiplier 함수를 사용
        Log.i(TAG, "adder = " + b + " multiplier = " + c); //Logcat에 위의 값들을 출력하기

//----------------------------------- Button의 정의
        Button bt_up = new Button(this); //버튼 정의
        bt_up.setLayoutParams(new ViewGroup.LayoutParams (120, ViewGroup.LayoutParams.WRAP_CONTENT )); //버튼의 크기 정의
        bt_up.setText("UP"); //버튼에 텍스트 적기
        bt_up.setId((int)1); //버튼의 ID를 정의 (해당 버튼에 이벤트를 넣을 때 사용)
//----------------------------------- Layout 1 정의
        LinearLayout layout1 = new LinearLayout(this);
        layout1.setOrientation( LinearLayout.HORIZONTAL ); //내부에서 horizontal 배치 (↔)
        layout1.setLayoutParams(new ViewGroup.LayoutParams (ViewGroup.LayoutParams.FILL_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT ));
        layout1.setGravity(0x01); //바꾸면 어떻게 될까?
//----------------------------------- Layout 2 정의
        LinearLayout layout2 = new LinearLayout(this);
        layout2.setOrientation( LinearLayout.HORIZONTAL );
        layout2.setLayoutParams(new ViewGroup.LayoutParams (ViewGroup.LayoutParams.FILL_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT ));
        layout2.setGravity(0x01);
//----------------------------------- Layout 3 정의
        LinearLayout layout3 = new LinearLayout(this);
        layout3.setOrientation( LinearLayout.HORIZONTAL );
        layout3.setLayoutParams(new ViewGroup.LayoutParams (ViewGroup.LayoutParams.FILL_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT ));
        layout3.setGravity(0x01);
//-----------------------------------
        Button bt_left = new Button(this);
        bt_left.setLayoutParams (new ViewGroup.LayoutParams (120, ViewGroup.LayoutParams.WRAP_CONTENT ));
        bt_left.setText("LEFT");
        bt_left.setId((int)2);
//-----------------------------------
        Button bt_center = new Button(this);
        bt_center.setLayoutParams (new ViewGroup.LayoutParams (120, ViewGroup.LayoutParams.WRAP_CONTENT ));
        bt_center.setText("CENTER");
        bt_center.setId((int)3);
//-----------------------------------
        Button bt_right = new Button(this);
        bt_right.setLayoutParams (new ViewGroup.LayoutParams (120, ViewGroup.LayoutParams.WRAP_CONTENT ));
        bt_right.setText("RIGHT");
        bt_right.setId((int)4);
//-----------------------------------
        Button bt_down = new Button(this);
        bt_down.setLayoutParams (new ViewGroup.LayoutParams (120, ViewGroup.LayoutParams.WRAP_CONTENT ));
        bt_down.setText("DOWN");
        bt_down.setId((int)5);
//-----------------------------------
        Button bt_one = new Button(this);
        bt_one.setLayoutParams (new ViewGroup.LayoutParams (120, ViewGroup.LayoutParams.WRAP_CONTENT ));
        bt_one.setText("1");
        bt_one.setId((int)6);
//-----------------------------------
        Button bt_two = new Button(this);
        bt_two.setLayoutParams (new ViewGroup.LayoutParams (120, ViewGroup.LayoutParams.WRAP_CONTENT ));
        bt_two.setText("2");
        bt_two.setId((int)7);
//-----------------------------------
        Button bt_three = new Button(this);
        bt_three.setLayoutParams (new ViewGroup.LayoutParams (120, ViewGroup.LayoutParams.WRAP_CONTENT ));
        bt_three.setText("3");
        bt_three.setId((int)8);
//-----------------------------------
        Button bt_four = new Button(this);
        bt_four.setLayoutParams (new ViewGroup.LayoutParams (120, ViewGroup.LayoutParams.WRAP_CONTENT ));
        bt_four.setText("4");
        bt_four.setId((int)9);
//-----------------------------------
        // ↓ layout1 위에 특정 요소들을 좌에서 우로 올리는 코드
        layout1.addView(bt_left);
        layout1.addView(bt_center);
        layout1.addView(bt_right);
        // ↓ layout2 위에 특정 요소들을 좌에서 우로 올리는 코드
        layout2.addView(bt_one);
        layout2.addView(bt_up);
        layout2.addView(bt_two);
        // ↓ layout3 위에 특정 요소들을 좌에서 우로 올리는 코드
        layout3.addView(bt_three);
        layout3.addView(bt_down);
        layout3.addView(bt_four);
        // ↓ layout0 위에 특정 요소들을 위에서 아래로 올리는 코드
        layout0.addView(vw); //vw : 내가 만든 view (캔버스)
        layout0.addView(layout2); //버튼 레이아웃 상단
        layout0.addView(layout1); //버튼 레이아웃 중앙
        layout0.addView(layout3); //버튼 레이아웃 하단
        vw.setFocusable(true);
        vw.setFocusableInTouchMode (true);
        setContentView(layout0); //layout0을 보이도록 하는 코드 -> xml을 사용하지 않음
    }
    protected class MyView extends View { //extends: MyView는 View를 상속받으라
    //부모 (View) 의 특징을 자손 (MyView) 가 그대로 물려받으므로 View의 모든 특징을 갖고 있으면서 추가로 특징을 더할 수 있다
        public MyView(Context context) {
            super(context);
            mX = 250;
            mY = 250;
            mColor = Color.BLUE; }
        public void onDraw(Canvas canvas) { //onDraw는 View라는 부모가 원래부터 갖고있던 함수
        //터치, 홈버튼, 키보드 동작 등으로 화면상의 변화가 생기면 onDraw 함수는 자동으로 수행된다
        //원래 부모가 가지고 있던 onDraw 함수의 특징을 그대로 가져온 채로, 
        //onDraw 함수가 자동으로 수행될 때 내가 취할 행동을 함수 내부에 추가로 기술
            canvas.drawColor(Color.WHITE);
            Paint Pnt = new Paint();
            canvas.drawRect(50,20,100,70,Pnt_Green);
            canvas.drawRect(120,20,170,70,Pnt_Black); //사각형을 그림 (검은색과 초록색) -> 좌상단 X, Y 좌표, 우하단 X, Y 좌표, 색깔 
            Pnt.setColor(mColor);
            Pnt.setAntiAlias(true);
            canvas.drawCircle(mX,mY,16,Pnt); } } }