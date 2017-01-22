package com.example.x6.myhorizontalscrollview;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private String[] data = {"哈","哈1","哈11","哈123","哈1234","哈5342"
            ,"哈沙发","哈案案发的方式","哈问问特如果","哈",};

    private String[] data2 = {"改变1","改变123","改变地方","改变45区v","改变234地方","改变"
            ,"改变","改变2热情为让发的是犯法","改变","改变",};

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            hol.addTag(data);
        }
    };
    private MyHorizontalScrollView hol;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hol = (MyHorizontalScrollView) findViewById(R.id.hol);
        btn = (Button) findViewById(R.id.btn);

        //网络获取数据 延时 1S
        handler.sendEmptyMessageDelayed(0,1000);


        hol.setListener(new MyHorizontalScrollView.OnTouchLetterChangedListener() {
            @Override
            public void onTouchLetterChanged(int index) {
                Toast.makeText(MainActivity.this,"当前点击:"+index,Toast.LENGTH_SHORT).show();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hol.addTag(data2);
            }
        });
    }
}
