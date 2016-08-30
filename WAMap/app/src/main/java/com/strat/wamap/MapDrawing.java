package com.strat.wamap;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

/**
 * Created by Evan on 8/29/2016.
 */
public class MapDrawing extends View {
    public MapDrawing (Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint myPaint = new Paint();
        myPaint.setStrokeWidth(5);
        myPaint.setColor(Color.BLUE);
        myPaint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(40,50,80,100,2,2,myPaint);
        Log.i("info","onDraw called");
        invalidate();
    }
}
