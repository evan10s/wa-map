package com.strat.wamap;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class drawMap extends AppCompatActivity {
    ArrayList<Location> locs = new ArrayList<>();

    /*
    * Returns a Path representing two Locations in the ArrayList locs
    *
     */
    private Path getPath(int startLocIndex, int endLocIndex) {
        return new Path(locs.get(startLocIndex), locs.get(endLocIndex));
    }

    private void drawPath(int startLoc, int endLoc) {
        locs.addAll(Arrays.asList(new Location("Circle", 120, 95), //0
                new Location("Bullring", 230, 95), //1
                new Location("Library", 216, 60), //2
                new Location("Richardson", 218, 137), //3
                new Location("New1", 271, 85), //4
                new Location("New2", 345, 85), //5
                new Location("Carlos", 275, 162), //6
                new Location("Moss", 339, 149), //7
                new Location("Store", 429, 157), //8
                new Location("Brand", 150, 180) //9
        ));

        ArrayList<Path> paths = new ArrayList<>();
        paths.addAll(Arrays.asList(getPath(0, 1),
                getPath(1, 2),
                getPath(1, 3),
                getPath(1, 4),
                getPath(3, 6),
                getPath(4, 6),
                getPath(6, 7),
                getPath(4, 7),
                getPath(7, 8),
                getPath(4, 8),
                getPath(2, 4),
                getPath(1, 9),
                getPath(9, 3)
        ));



       /* for (Path path : paths) {

        }*/

    }

    public static class MyCustomView extends View {
        public MyCustomView (Context context) {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_map);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        getSupportActionBar().hide();
        //Intent intent = getIntent();
        //int startLoc = intent.getIntExtra("com.strat.wamap.start",-1);
        //int endLoc = intent.getIntExtra("com.strat.wamap.end",-1);
        TextView text = (TextView) findViewById(R.id.textView);
        Log.i("info","invalidate theoretically called");
            text.invalidate();
        text.postInvalidate();
    }
}
