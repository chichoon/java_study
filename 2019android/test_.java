package com.example.a0924_;

        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Context;
        import android.graphics.Canvas;
        import android.graphics.Color;
        import android.graphics.Paint;
        import android.os.Bundle;
        import android.text.Editable;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.LinearLayout;
        import android.widget.Toast;

public class MainActivity extends AppCompatActivity{
    public MyView vw;
    int button = 0;
    int ans;
    int numflag1 = 0;
    int numflag2 = 0;
    int flag = 0; //1, 2, 3, 4
    String a, b;
    int c, d;
    String viewtext = "";
    Paint Pnt_WHITE, Pnt_LTGRAY, Pnt_BLACK;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout layout0 = new LinearLayout(this); //제일 바탕에 있는 layout
        layout0.setOrientation( LinearLayout.VERTICAL ); //내부에서 vertical 배치 (↕)
        // ↓ layout0을 width도 부모와 같게, heightㄷ 부모와 같게 설정하는 코드 (부모: Constraint Layout)
        layout0.setLayoutParams(new ViewGroup.LayoutParams (ViewGroup.LayoutParams.FILL_PARENT , ViewGroup.LayoutParams.FILL_PARENT ));
        layout0.setGravity(0x01);
        // ↓ 펜을 정의
        Pnt_WHITE = new Paint();
        Pnt_WHITE.setColor(Color.WHITE);
        Pnt_WHITE.setAntiAlias (true);
        Pnt_LTGRAY = new Paint();
        Pnt_LTGRAY.setColor(Color.LTGRAY);
        Pnt_LTGRAY.setAntiAlias (true);
        Pnt_BLACK = new Paint();
        Pnt_BLACK.setColor(Color.BLACK);
        Pnt_BLACK.setAntiAlias (true);
        Pnt_BLACK.setTextSize(90);
        // ↓ view 캔버스를 정의
        vw = new MyView(this);
        vw.setLayoutParams(new ViewGroup.LayoutParams (ViewGroup.LayoutParams.WRAP_CONTENT , 640));
        Button bt_up = new Button(this); //버튼 정의
        bt_up.setLayoutParams(new ViewGroup.LayoutParams (120, ViewGroup.LayoutParams.WRAP_CONTENT )); //버튼의 크기 정의
        bt_up.setText("UP"); //버튼에 텍스트 적기
        bt_up.setTextSize(10);
        bt_up.setTextColor(Color.MAGENTA);
        bt_up.setId((int)1); //버튼의 ID를 정의 (해당 버튼에 이벤트를 넣을 때 사용)
        LinearLayout layout1 = new LinearLayout(this);
        layout1.setOrientation( LinearLayout.HORIZONTAL ); //내부에서 horizontal 배치 (↔)
        layout1.setLayoutParams(new ViewGroup.LayoutParams (ViewGroup.LayoutParams.FILL_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT ));
        layout1.setGravity(0x01); //바꾸면 어떻게 될까?
        LinearLayout layout2 = new LinearLayout(this);
        layout2.setOrientation( LinearLayout.HORIZONTAL );
        layout2.setLayoutParams(new ViewGroup.LayoutParams (ViewGroup.LayoutParams.FILL_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT ));
        layout2.setGravity(0x01);
        LinearLayout layout3 = new LinearLayout(this);
        layout3.setOrientation( LinearLayout.HORIZONTAL );
        layout3.setLayoutParams(new ViewGroup.LayoutParams (ViewGroup.LayoutParams.FILL_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT ));
        layout3.setGravity(0x01);
        LinearLayout layout4 = new LinearLayout(this);
        layout4.setOrientation(LinearLayout.HORIZONTAL);
        layout4.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT));
        layout4.setGravity(0x01);
        Button bt_1 = new Button(this);
        bt_1.setLayoutParams (new ViewGroup.LayoutParams (180, ViewGroup.LayoutParams.WRAP_CONTENT ));
        bt_1.setText("1");
        bt_1.setId((int)1);
        bt_1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(numflag1 == 0){
                    a += "1";
                }
                else if(numflag2 == 0){
                    b += "1";
                }
                button = 1;
                vw.invalidate();
            }
        });
        Button bt_2 = new Button(this);
        bt_2.setLayoutParams (new ViewGroup.LayoutParams (180, ViewGroup.LayoutParams.WRAP_CONTENT ));
        bt_2.setText("2");
        bt_2.setId((int)2);
        bt_2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(numflag1 == 0){
                    a += "2";
                }
                else if(numflag2 == 0){
                    b += "2";
                }
                button = 1;
                vw.invalidate();
            }
        });
        Button bt_3 = new Button(this);
        bt_3.setLayoutParams (new ViewGroup.LayoutParams (180, ViewGroup.LayoutParams.WRAP_CONTENT ));
        bt_3.setText("3");
        bt_3.setId((int)3);
        bt_3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(numflag1 == 0){
                    a += "3";
                }
                else if(numflag2 == 0){
                    b += "3";
                }
                button = 1;
                vw.invalidate();
            }
        });
        Button bt_4 = new Button(this);
        bt_4.setLayoutParams (new ViewGroup.LayoutParams (180, ViewGroup.LayoutParams.WRAP_CONTENT ));
        bt_4.setText("4");
        bt_4.setId((int)4);
        bt_4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(numflag1 == 0){
                    a += "4";
                }
                else if(numflag2 == 0){
                    b += "4";
                }
                button = 1;
                vw.invalidate();
            }
        });
        Button bt_5 = new Button(this);
        bt_5.setLayoutParams (new ViewGroup.LayoutParams (180, ViewGroup.LayoutParams.WRAP_CONTENT ));
        bt_5.setText("5");
        bt_5.setId((int)5);
        bt_5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(numflag1 == 0){
                    a += "5";
                }
                else if(numflag2 == 0){
                    b += "5";
                }
                button = 1;
                vw.invalidate();
            }
        });
        Button bt_6 = new Button(this);
        bt_6.setLayoutParams (new ViewGroup.LayoutParams (180, ViewGroup.LayoutParams.WRAP_CONTENT ));
        bt_6.setText("6");
        bt_6.setId((int)6);
        bt_6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(numflag1 == 0){
                    a += "6";
                }
                else if(numflag2 == 0){
                    b += "6";
                }
                button = 1;
                vw.invalidate();
            }
        });
        Button bt_7 = new Button(this);
        bt_7.setLayoutParams (new ViewGroup.LayoutParams (180, ViewGroup.LayoutParams.WRAP_CONTENT ));
        bt_7.setText("7");
        bt_7.setId((int)7);
        bt_7.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(numflag1 == 0){
                    a += "7";
                }
                else if(numflag2 == 0){
                    b += "7";
                }
                button = 1;
                vw.invalidate();
            }
        });
        Button bt_8 = new Button(this);
        bt_8.setLayoutParams (new ViewGroup.LayoutParams (180, ViewGroup.LayoutParams.WRAP_CONTENT ));
        bt_8.setText("8");
        bt_8.setId((int)8);
        bt_8.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(numflag1 == 0){
                    a += "8";
                }
                else if(numflag2 == 0){
                    b += "8";
                }
                button = 1;
                vw.invalidate();
            }
        });
        Button bt_9 = new Button(this);
        bt_9.setLayoutParams (new ViewGroup.LayoutParams (180, ViewGroup.LayoutParams.WRAP_CONTENT ));
        bt_9.setText("9");
        bt_9.setId((int)9);
        bt_3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(numflag1 == 0){
                    a += "9";
                }
                else if(numflag2 == 0){
                    b += "9";
                }
                button = 1;
                vw.invalidate();
            }
        });
        Button bt_0 = new Button(this);
        bt_0.setLayoutParams (new ViewGroup.LayoutParams (180, ViewGroup.LayoutParams.WRAP_CONTENT ));
        bt_0.setText("0");
        bt_0.setId((int)0);
        bt_3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(numflag1 == 0){
                    a += "0";
                }
                else if(numflag2 == 0){
                    b += "0";
                }
                button = 1;
                vw.invalidate();
            }
        });
        Button bt_div = new Button(this);
        bt_div.setLayoutParams (new ViewGroup.LayoutParams (180, ViewGroup.LayoutParams.WRAP_CONTENT ));
        bt_div.setText("/");
        bt_div.setId((int)10);
        bt_div.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(numflag1 == 1 && numflag2 == 0) {
                    c = Integer.parseInt(a);
                    flag = 4; //div
                    numflag1 = 1;
                    button = 1;
                    vw.invalidate();
                }
                else{
                    vw.invalidate();
                }
            }
        });
        Button bt_mul = new Button(this);
        bt_mul.setLayoutParams (new ViewGroup.LayoutParams (180, ViewGroup.LayoutParams.WRAP_CONTENT ));
        bt_mul.setText("*");
        bt_mul.setId((int)11);
        bt_mul.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(numflag1 == 1 && numflag2 == 0){
                    numflag1 = 1;
                    c = Integer.parseInt(a);
                    flag =3; //mul
                    button = 1;
                    vw.invalidate();
                }
                else{
                    vw.invalidate();
                }
            }
        });
        Button bt_add = new Button(this);
        bt_add.setLayoutParams (new ViewGroup.LayoutParams (180, ViewGroup.LayoutParams.WRAP_CONTENT ));
        bt_add.setText("+");
        bt_add.setId((int)12);
        bt_add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(numflag1 == 1 && numflag2 == 0){
                    numflag1 = 1;
                    c = Integer.parseInt(a);
                    flag =1;
                    button = 1;
                    vw.invalidate();
                }
                else{
                    vw.invalidate();
                }
            }
        });
        Button bt_sub = new Button(this);
        bt_sub.setLayoutParams (new ViewGroup.LayoutParams (180, ViewGroup.LayoutParams.WRAP_CONTENT ));
        bt_sub.setText("-");
        bt_sub.setId((int)13);
        bt_3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(numflag1 == 1 && numflag2 == 0){
                    c = Integer.parseInt(a);
                    flag = 2;
                    numflag1 = 1;
                    button = 1;
                    vw.invalidate();
                }
                else{
                    vw.invalidate();
                }

            }
        });
        Button bt_clr = new Button(this);
        bt_clr.setLayoutParams (new ViewGroup.LayoutParams (180, ViewGroup.LayoutParams.WRAP_CONTENT ));
        bt_clr.setText("C");
        bt_clr.setId((int)14);
        bt_clr.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                a = "";
                b = "";
                viewtext = "";
                c = 0;
                d = 0;
                vw.invalidate();
            }
        });
        Button bt_eq = new Button(this);
        bt_eq.setLayoutParams (new ViewGroup.LayoutParams (180, ViewGroup.LayoutParams.WRAP_CONTENT ));
        bt_eq.setText("=");
        bt_eq.setId((int)15);
        bt_clr.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                switch(flag){
                    case 0:
                        c = Integer.parseInt(a);
                        ans = c;
                        break;
                    case 1:
                        d = Integer.parseInt(b);
                        ans = c + d;
                        viewtext = a + "+" + b + "=" +String.valueOf(ans);
                        break;
                    case 2:
                        d = Integer.parseInt(b);
                        ans = c - d;
                        viewtext = a + "-" + b + "=" +String.valueOf(ans);
                        break;
                    case 3:
                        d = Integer.parseInt(b);
                        ans = c * d;
                        viewtext = a + "*" + b + "=" +String.valueOf(ans);
                        break;
                    case 4:
                        d = Integer.parseInt(b);
                        ans = c / d;
                        viewtext = a + "/" + b + "=" +String.valueOf(ans);
                        break;
                }
                vw.invalidate();
                button = 1;
                if(button ==1){
                    a = "";
                    b = "";
                    viewtext = "";
                    c = 0;
                    d = 0;
                }
            }
        });
        layout2.addView(bt_7);
        layout2.addView(bt_8);
        layout2.addView(bt_9);
        layout2.addView(bt_div);
        layout1.addView(bt_4);
        layout1.addView(bt_5);
        layout1.addView(bt_6);
        layout1.addView(bt_mul);
        layout3.addView(bt_1);
        layout3.addView(bt_2);
        layout3.addView(bt_3);
        layout3.addView(bt_sub);
        layout4.addView(bt_0);
        layout4.addView(bt_clr);
        layout4.addView(bt_add);
        layout4.addView(bt_eq);

        layout0.addView(vw); //vw : 내가 만든 view (캔버스)
        layout0.addView(layout2); //버튼 레이아웃 상단
        layout0.addView(layout1); //버튼 레이아웃 중앙
        layout0.addView(layout3); //버튼 레이아웃 하단
        layout0.addView(layout4); //버튼 레이아웃 하단
        vw.setFocusable(true);
        vw.setFocusableInTouchMode (true);
        setContentView(layout0); //layout0을 보이도록 하는 코드 -> xml을 사용하지 않음

    }

    protected class MyView extends View {
        public MyView(Context context) {
            super(context);
            }
        public void onDraw(Canvas canvas) {
            String str = viewtext;
            canvas.drawText("hello",100,100, Pnt_BLACK);
            if(button == 1){

                button = 0;
                invalidate();
            }

            canvas.drawColor(Color.LTGRAY);
            Paint Pnt = new Paint();
            canvas.drawRect(0,0,1080,100,Pnt_WHITE);
            Pnt.setAntiAlias(true); } }
}