package com.strat.wamap;

public class Path {
    Location start;
    Location end;

    public Path (Location start, Location end) {
        this.start = start;
        this.end = end;
    }

    /*
    * Return a new Location representing the midpoint of this Path
     */
    public Location getMidPoint() {
        return new Location ("midpoint",(start.x + end.x)/2.0, (start.y + end.y)/2.0);
    }
}
