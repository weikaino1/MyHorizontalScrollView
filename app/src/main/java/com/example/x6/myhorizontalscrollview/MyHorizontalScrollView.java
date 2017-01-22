package com.example.x6.myhorizontalscrollview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by x6 on 2017/1/20.
 */
public class MyHorizontalScrollView extends HorizontalScrollView {

    private Context context;

    private LinearLayout linLayout;
    private int width;
    private int height;
    private int textSize;
    private int leftPadding;
    private int rightPadding;
    private List<Integer> moveXList = new ArrayList<>();
    private List<TextView> tvList = new ArrayList<>();


    public MyHorizontalScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(context,attrs);
    }

    public MyHorizontalScrollView(Context context) {
        super(context);
    }

    private void init(Context context,AttributeSet attrs) {
        TypedArray ta= context.obtainStyledAttributes(attrs,R.styleable.MyHorizontalScrollView);
        textSize = ta.getInteger(R.styleable.MyHorizontalScrollView_textSize,15);
        leftPadding = ta.getInteger(R.styleable.MyHorizontalScrollView_leftPadding,15);
        rightPadding = ta.getInteger(R.styleable.MyHorizontalScrollView_rightPadding,15);
        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);

        initChildX();
    }

    //计算 textview的宽度
    private void initChildX(){
        if(getChildCount()>0){
            ViewGroup view = (ViewGroup) getChildAt(0);
            if(view.getChildCount()>0){
                moveXList.clear();
                //第一个数据 无需移动
                moveXList.add(0);
                int moveX = 0;
                for(int i=0; i<view.getChildCount()-1; i++){
                    moveX += view.getChildAt(i).getMeasuredWidth();
                    moveXList.add(moveX);
                }
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        addLinLayout(context);
        super.onLayout(changed, l, t, r, b);
    }

    private void addLinLayout(Context context) {

        if(this.getChildCount()==1)return ;

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                width,height);
        linLayout = new LinearLayout(context);
        linLayout.setGravity(LinearLayout.HORIZONTAL);
        this.addView(linLayout,layoutParams);
    }

    public void addTag(String[] data) {

        if(linLayout!=null)
            linLayout.removeAllViews();

        for(int i=0; i<data.length; i++){

            LinearLayout.LayoutParams titleLayout = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    height);

            TextView tv = new TextView(context);
            if(i == 0){
                tv.setTextColor(0xff46abff);
            }else{
                tv.setTextColor(0xff333333);
            }

            tv.setText(data[i]);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
            tv.setGravity(Gravity.VERTICAL_GRAVITY_MASK);
            tv.setPadding(dip2px(context,leftPadding), 0, dip2px(context,rightPadding), 0);

            final int index = i;
            tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    smoothScrollTo(moveXList.get(index),0);
                    for(int n=0; n<tvList.size(); n++){
                        tvList.get(n).setTextColor(0xFF333333);
                    }
                    ((TextView) v).setTextColor(0xFF46ABFF);
                    listener.onTouchLetterChanged(index);
                }
            });
            tvList.add(tv);
            if(linLayout!=null)
                linLayout.addView(tv,titleLayout);
        }
        scrollTo(0,0);
        this.invalidate();
    }


    private int dip2px(Context context, float dpValue) {
        if (context != null) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dpValue * scale + 0.5f);
        } else {
            return 0;
        }
    }

    private OnTouchLetterChangedListener listener;

    public OnTouchLetterChangedListener getListener() {
        return listener;
    }

    public void setListener(OnTouchLetterChangedListener listener) {
        this.listener = listener;
    }

    public interface OnTouchLetterChangedListener{
        public void onTouchLetterChanged(int index);
    }
}
