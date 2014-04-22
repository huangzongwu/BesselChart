package com.tomkey.testcubic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;

import com.anjuke.library.uicomponent.chart.curve.ChartData;
import com.anjuke.library.uicomponent.chart.curve.ChartData.LabelTransform;
import com.anjuke.library.uicomponent.chart.curve.CurveChart;
import com.anjuke.library.uicomponent.chart.curve.Point;
import com.anjuke.library.uicomponent.chart.curve.Series;

public class MainActivity extends Activity {
    CurveChart chart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chart=(CurveChart)findViewById(R.id.chart);
        List<Series> seriess=new ArrayList<Series>();
        seriess.add(getRandomSeries(Color.RED, false));
        seriess.add(getRandomSeries(Color.GREEN, false));
        ChartData data=new ChartData();
        data.setLabelTransform(new LabelTransform() {
            
            @Override
            public String verticalTransform(int valueY) {
                Log.d("zqt", "step valueY="+valueY);
                return String.format("%.1f万", valueY/10000f);
            }
            
            @Override
            public String horizontalTransform(int valueX) {
                return String.format("%s月", valueX);
            }
        });
        data.setSeriesList(seriess);
//        chart.getStyle().setGridColor(Color.parseColor("#66CCCCCC"));
        chart.setData(data);
//        chart.setVelocityX(1.2f);
//        chart.setSmoothness(0.5f);
    }
    private Series getRandomSeries(int color,boolean willDrawing){
        
        List<Point> points=new ArrayList<Point>();
        Random random=new Random();
        if(willDrawing){
            for(int i=0;i<12;i++){
                points.add(new Point(i+1, 20000+1000*random.nextInt(10),true));
            } 
        }else{
            for(int i=0;i<36;i++){
                points.add(new Point(i+1, 20000+1000*random.nextInt(10),i%3==1));
            } 
        }
        return new Series(color, points);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    public class CubicView extends View{
        Path p = new Path();
        Paint mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
//        RectF bounds = new RectF();
        PathEffect effect=new CornerPathEffect(10);
        
        public CubicView(Context context) {
            super(context);
        }
        private float start=0;
        private float lastTouchEventX=-1;
        @Override
        public boolean onTouchEvent(MotionEvent event) {
            Log.d("zqt", "onTouchEvent="+event.getAction());
            
            if(event.getAction()== MotionEvent.ACTION_MOVE&&lastTouchEventX>=0){
                start=start+event.getX()-lastTouchEventX;
                        invalidate();                        
                Log.d("zqt", "onTouchEvent lastTouchEventX="+lastTouchEventX);
            }
            lastTouchEventX=event.getX();
            return true;
        }
        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawColor(Color.WHITE);
//            canvas.drawColor(0,android.graphics.PorterDuff.Mode.CLEAR);
            p.reset();
            canvas.translate(start, 0);
            p.moveTo(0, 0);
//            p.lineTo(2000, 500);
            Log.d("zqt", "onDraw lastTouchEventX="+start);
//            for (int i = 1; i <= 15; i++) {
//                p.lineTo(i*200, (float)Math.random() * 1000);
//            }
            p.cubicTo(10, 20, 20, 30, 30, 40);
            p.moveTo(20, 30);
            p.cubicTo(30, 40, 80, 90, 100, 300);
            p.moveTo(80, 90);
            p.cubicTo(100, 300, 330, 200,340, 90);
            p.moveTo(330, 200);
            p.cubicTo(340, 90, 400, 200, 450, 270);
            p.moveTo(400, 200);
            p.cubicTo(450, 270, 500, 210, 530, 150);
            p.moveTo(500, 210);
            p.cubicTo(530, 150, 600, 290,650,300);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(5);
            mPaint.setColor(Color.GREEN);
            mPaint.setPathEffect(effect);
            canvas.drawPath(p, mPaint);
//            canvas.translate(0, 28);
        }
    }
}
