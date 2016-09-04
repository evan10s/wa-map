package com.strat.wamap;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;

public class drawMap extends AppCompatActivity {
    ArrayList<Location> locs = new ArrayList<>();
    ArrayList<Path> paths = new ArrayList<>();
    boolean showAllOverride = false;
    /*
    * Returns a Path representing two Locations in the ArrayList locs
    *
     */

    public drawMap () {

    }

    private Path getPath(int startLocIndex, int endLocIndex) {
        return new Path(locs.get(startLocIndex), locs.get(endLocIndex));
    }

    private void drawPathInit (String mapView) {
        if (mapView.equals("west")) { //paths for the west map
            locs.addAll(Arrays.asList(new Location("Circle", 146,912), //0
                    new Location("Bullring", 357, 512), //1
                    new Location("Library", 241, 562), //2
                    new Location("Richardson", 550, 571), //3
                    new Location("New1", 410,433), //4
                    new Location("New2", 451, 270), //5
                    new Location("Carlos", 665, 410), //6
                    new Location("Moss", 685, 325), //7
                    new Location("Store", 645, 230), //8
                    new Location("Brand", 860, 720) //9
            ));


            paths.addAll(Arrays.asList(getPath(0, 1),
                    getPath(1, 2),
                    getPath(1, 3),
                    getPath(1, 4),
                    getPath(3, 6),
                    getPath(4, 6),
                    getPath(6, 7),
                    getPath(7, 8),
                    getPath(2, 4),
                    getPath(0, 3),
                    getPath(9, 3),
                    getPath(5, 8),
                    getPath(5, 7),
                    getPath(0,9)
            ));
        }



    }

    private void showAllPaths(String mapView) {
        drawPathInit(mapView); //define the paths
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

    //Get the distance between the midpoints of two paths
    public double getMidpointDist(Path path1, Path path2) {
        Location p1Midpoint = path1.getMidPoint();
        Location p2Midpoint = path2.getMidPoint();
        return distanceBetweenPoints(p1Midpoint,p2Midpoint);
    }

    public double distanceBetweenPoints (Location l1, Location l2) {
        return Math.sqrt(Math.pow(l1.x - l2.x,2) + Math.pow(l1.y - l2.y,2));
    }

    public boolean pointsMatch (Location loc1, Location loc2) {
        return loc1.x == loc2.x && loc1.y == loc2.y;
    }

    private ArrayList<Path> getPathsWithPoint(Location pt) {
        ArrayList<Path> matchingPaths = new ArrayList<>();
        for (Path p : paths) {
            if (pointsMatch(pt,p.start) || pointsMatch(pt,p.end) || pointsMatch(pt, p.start) || pointsMatch(pt,p.end)) {
                matchingPaths.add(p);
            }
        }
        return matchingPaths;
    }

    /*
    * Determines which end of a given Path is closer to a given point and returns the closer end as a Location
    * @param pathToCheck The path to check
    * @param goalPoint The point for which you want to find which end of the given path is cloest
    * @return A Location representing the endpoint of the path that is closest to goalPoint
     */
    private Location getClosestEndPoint(Path pathToCheck, Location goalPoint) {
        //compare distance of path start point & goal point and path end point & goal point
        if (distanceBetweenPoints(pathToCheck.start,goalPoint) < distanceBetweenPoints(pathToCheck.end,goalPoint)) { //start point is closer
            return pathToCheck.start;
        } else { //end point is closer
            return pathToCheck.end;
        }
    }

    private boolean pathNotAlreadyChosen (Path toCheck, ArrayList<Path> pathList) {
        boolean pathFound = false;
        for (Path p : pathList) {
            if (pointsMatch(p.start,toCheck.start) && pointsMatch(p.end,toCheck.end)) { //the path has already been chosen if the given path corresponds to a path with the same start and end point in pathList
                pathFound = true;
                break;
            }
        }
        return pathFound;
    }

    /*
    * Gets all the paths touching startPt, then finds then single Path whose midpoint is closest to stopPt.
    * The function will call itself again until startPt is the same as stopPt
    * @param startPt The point to base search for Paths on
    * @param stopPt The goal point which indicates when the process is finished
    * @param pathList An ArrayList of Paths to contain matching paths found by this function
    * @return An ArrayList of Paths representing the results of the function
     */
    private ArrayList<Path> calcPath (Location startPt, Location stopPt, ArrayList<Path> pathList) {
        ArrayList<Path> startPtPaths; //ArrayList containing matching Paths - that is, Paths which contain this point
        startPtPaths = getPathsWithPoint(startPt); //fill the ArrayList using a function that performs task describing in the line above

        Path closestPathToEndPt = startPtPaths.get(0); //initial value
        boolean certainDone = false; //to handle cases where another path's midpoint is closer but there's a path whose endpoint is the destination
        for (int i = 1; i < startPtPaths.size(); i++) { //search for minimum distance
            Path currElem = startPtPaths.get(i);
            boolean currentPathAlreadyChosen = pathNotAlreadyChosen(startPtPaths.get(i), pathList);
            if (!currentPathAlreadyChosen) {
                if (distanceBetweenPoints(currElem.getMidPoint(), stopPt) < distanceBetweenPoints(closestPathToEndPt.getMidPoint(), stopPt)) { //only set this path as closest if it's not already in pathList; this is to prevent backtracking which can cause an infinite recursive cycle
                    closestPathToEndPt = startPtPaths.get(i);
                }
                if (pointsMatch(currElem.start, stopPt) || pointsMatch(currElem.end, stopPt)) {
                    closestPathToEndPt = startPtPaths.get(i);
                    certainDone = true;
                    break;
                }
            }
        }

        pathList.add(closestPathToEndPt); //add the path to the list of Paths to show

        //fix for an edge case - sometimes the closest point to the end point on the path chosen is the startPt given to the functon
        //  this results in infinite recursion and to fix this, we should take the other point on the path chosen
        Location newStartPt = getClosestEndPoint(closestPathToEndPt,stopPt); //get the new start point for the function by finding the end of the chosen path that's closest to the destination

        if (pointsMatch(newStartPt,startPt)) { //make sure that the new start point isn't the same one as this function's parameter startPt
            if (pointsMatch(closestPathToEndPt.start,newStartPt)) { //if the new start point IS the same one as the startPt parameter, use the other point on the path
                newStartPt = closestPathToEndPt.end;                //   to do that, we need to figure out which end of the chosen path is the new start point, and then choose the opposite end of the chosen path
            } else {
                newStartPt = closestPathToEndPt.start;
            }
        }

        if (pointsMatch(newStartPt,stopPt) || certainDone) {
            return pathList;
        } else {
            return calcPath(newStartPt, stopPt, pathList);
        }

    }

    private void processMap(String mapView, int startLoc, int endLoc) {
        drawPathInit(mapView); //define the paths
        ArrayList<Path> startPtPaths = new ArrayList<>(); //create an array list to store the paths to be drawn
        ArrayList<Path> endPtPaths = new ArrayList<>();
        ArrayList<Path> pathsToShow = new ArrayList<>();
        Location start = locs.get(startLoc);
        Location end = locs.get(endLoc);
        //first, add paths that have a start or end point that is the start or end location; add all possibilities to an ArrayList, then take the one whose midpoint is closest to the destination
        pathsToShow = calcPath(start, end, pathsToShow);

        //iterate through pathstoDisplay so that there is only one Path showing connected to the starting point
        endPtPaths = getPathsWithPoint(end);

        //for (Path p: pathsToDisplay) {

        //}
        //Log.i("info",pathsToDisplay.toString());

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
        for (Path p : pathsToShow) {
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
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE); //make sure the screen stays landscape
        //getSupportActionBar().hide(); //hide the action bar
        ImageView map = (ImageView) findViewById(R.id.imageView);
        if (BuildConfig.DEBUG) { //only do this in debug builds
            map.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Log.i("info", "" + event.getX());
                    Log.i("info", "" + event.getY());
                    return false;
                }
            });
        }
        Intent intent = getIntent();
        final int startLoc = intent.getIntExtra("com.strat.wamap.start",-1);
        final int endLoc = intent.getIntExtra("com.strat.wamap.end",-1);
        final String mapView = intent.getStringExtra("com.strat.wamap.mapviewdir");
        final String src = intent.getStringExtra("com.strat.wamap.src");


        if (src.equals("debug_show_all")) {
            showAllOverride = true;
        } else {
            Log.i("info", "startloc " + startLoc);
            Log.i("info", "endLoc" + endLoc);
        }
        //draw the map
        ViewTreeObserver vto = map.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ImageView map = (ImageView) findViewById(R.id.imageView);
                Log.i("info","on global layout map.height() = " + map.getHeight());
                Log.i("info","on global layout map.width() = " + map.getWidth());
                if (showAllOverride) {
                    showAllPaths(mapView);
                } else {
                    processMap(mapView, startLoc, endLoc);
                }
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
