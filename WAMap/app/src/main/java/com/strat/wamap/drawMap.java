package com.strat.wamap;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class drawMap extends AppCompatActivity {
    ArrayList<Location> locs = new ArrayList<>();
    ArrayList<Path> paths = new ArrayList<>();

    /*
    * Returns a Path representing two Locations in the ArrayList locs
    *
     */
    private Path getPath(int startLocIndex, int endLocIndex) {
        return new Path(locs.get(startLocIndex), locs.get(endLocIndex));
    }

    private void drawPathInit () {
        locs.addAll(Arrays.asList(new Location("Circle", 470, 360), //0
                new Location("Bullring", 930, 357), //1
                new Location("Library", 877, 297), //2
                new Location("Richardson", 865, 550), //3
                new Location("New1", 1080,350), //4
                new Location("New2", 1340, 340), //5
                new Location("Carlos", 1060, 660), //6
                new Location("Moss", 1340, 586), //7
                new Location("Store", 1690, 620), //8
                new Location("Brand", 653, 705) //9
        ));


        paths.addAll(Arrays.asList(getPath(0, 1),
                getPath(1, 2),
                getPath(1, 3),
                getPath(1, 4),
                getPath(3, 6),
                getPath(4, 6),
                getPath(6, 7),
                //getPath(4, 7),
                getPath(7, 8),
                //getPath(4, 8),
                getPath(2, 4),
                getPath(1, 9),
                getPath(9, 3),
                getPath(5, 8),
                getPath(5, 7)
        ));



       /* for (Path path : paths) {

        }*/

    }

    private void processMap() {
        drawPathInit();
        ImageView map = (ImageView) findViewById(R.id.imageView);
        Log.i("info", "image width: " + map.getWidth());
        Bitmap bmp = Bitmap.createBitmap(map.getWidth(), map.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        map.draw(c);

        Paint lineColor = new Paint();
        lineColor.setColor(Color.parseColor("#A32136"));
        lineColor.setStrokeWidth(10);
        //Path p = paths.get(0);
        //c.drawLine(p.start.x, p.start.y, p.end.x, p.end.y, lineColor);
        for (Path p : paths) {
            c.drawLine(p.start.x, p.start.y, p.end.x, p.end.y, lineColor);
            /*Log.i("info","coords x1 " + p.end.x);
            Log.i("info","coords y1 " + p.end.y);
            Log.i("info","coords x2 " + p.end.x);
            Log.i("info","coords y2 " + p.end.y);*/
        }
        map.setImageBitmap(bmp);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_map);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        getSupportActionBar().hide();
        ImageView map = (ImageView) findViewById(R.id.imageView);
        map.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i("info","" + event.getX());
                Log.i("info","" + event.getY());
                return false;
            }
        });
        /*map.post(new Runnable() {
            @Override
            public void run() {
                ImageView map = (ImageView) findViewById(R.id.imageView);
                int height = map.getHeight();
                int width = map.getWidth();
                crearPunto(30, 40, 100,150, Color.WHITE);
            }
        });*/
        Intent intent = getIntent();
        int startLoc = intent.getIntExtra("com.strat.wamap.start",-1);
        int endLoc = intent.getIntExtra("com.strat.wamap.end",-1);
        Log.i("info","startloc " + startLoc);
        Log.i("info","endLoc" + endLoc);
        // text = (TextView) findViewById(R.id.textView);
        //MapDrawing mapTest = (MapDrawing) findViewById(R.id.test);
        //Log.i("info","invalidate theoretically called");
        //   mapTest.invalidate();
        //mapTest.postInvalidate();

        ViewTreeObserver vto = map.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ImageView map = (ImageView) findViewById(R.id.imageView);
                Log.i("info","on global layout map.height() = " + map.getHeight());
                Log.i("info","on global layout map.width() = " + map.getWidth());
                processMap();

                ViewTreeObserver vto = map.getViewTreeObserver();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    vto.removeOnGlobalLayoutListener(this);
                } else {
                    vto.removeGlobalOnLayoutListener(this);
                }
            }
        });


    }
}
