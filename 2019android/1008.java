package com.example.a1008_1;

import androidx.appcompat.app.AppCompatActivity ;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

        String TAG = "tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        float a = (float)7.5;
        float b = 4;
        Calculator ct = new Calculator(a, b);
        float result_addition;
        float result_multiplication;
        result_addition = ct.adder();
        result_multiplication = ct.multiplier();
        Log.i(TAG,"Calculator: "+7.5+"+"+4+"="+result_addition);
        Log.i(TAG,"Calculator: "+7.5+"*"+4+"="+result_multiplication);

        ExtendedCalculator ect = new ExtendedCalculator((float)3.5, (float)2.8);
        float result_addition2;
        float result_multiplication2;
        float result_subtraction;
        float result_division;
        result_addition2 = ect.adder();
        result_multiplication2 = ect.multiplier();
        result_subtraction = ect.subtractor();
        result_division = ect.divider();
        Log.i(TAG,"ExtendedCalculator: "+3.5+"+"+2.8+"="+result_addition2);
        Log.i(TAG,"ExtendedCalculator: "+3.5+"*"+2.8+"="+result_multiplication2);
        Log.i(TAG,"ExtendedCalculator: "+3.5+"-"+2.8+"="+result_subtraction);
        Log.i(TAG,"ExtendedCalculator: "+3.5+"/"+2.8+"="+result_division);
        */

        Counter cnt = new Counter((int)5, (int) 150);
        cnt.log();
        }
    class Calculator {
        float a, b;
        Calculator(float a, float b) {
            this.a = a;
            this.b = b;
            Log.i(TAG,"Calculator object has been generated.");
        }
        public float adder() {
            float out = a+b;
            return out;
        }
        public float multiplier() {
            float out = a*b;
            return out;
        }
    }

    class ExtendedCalculator extends Calculator {
        ExtendedCalculator(float a, float b) {
            // super((float)7.5, (float)3); : 부모 클래스의 생성자에 있던 변수 a, b에 값을 넣어준다
            super(a, b);//부모 클래스 생성자에 있던 변수 a, b에 받아온 값을 넣어주고 시작한다
            Log.i(TAG,"ExtendedCalculator object has been generated.");
        }
        public float subtractor() {
            float out = a-b;
            return out;
        }
        public float divider() {
            float out = a/b;
            return out;
        }
    }

    class Counter {
        int a, b;
        Counter(int a, int b){
            this.a = a;
            this.b = b;
        }
        public void log(){
            int sum = 0;
            if (a < b) {
                for(int i = a; i < b; i++){
                    sum += i;
                    Log.i(TAG, "Count : "+ sum);
                }
            }
            else if (a >= b)
            {
                int temp;
                temp = a;
                a = b;
                b = temp;
                for(int i = a; i < b; i++){
                    sum += i;
                    Log.i(TAG, "Count : "+ sum);
                }
            }
            else{
                Log.i(TAG, "Invalid Number!!");
            }
        }


    }
}

