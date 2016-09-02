package com.strat.wamap;


import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class LocationTest {

    @Test
    public void LocationCreateTest() throws Exception {
        Location loc = new Location ("one",10,15);
        Assert.assertTrue(loc.x == 10);
        Assert.assertTrue(loc.y == 15);
        Assert.assertTrue(loc.name.equals("one"));
    }

    @Test
    public void pathMidpointTest() throws Exception {
        Location start = new Location("1",10,10);
        Location end = new Location("2",20,20);
        Path path = new Path(start, end);
        path.getMidPoint();
        Assert.assertTrue(path.getMidPoint().x==15);
        Assert.assertTrue(path.getMidPoint().y==15);
    }

    @Test
    public void PathCreateTest() throws Exception {
        Location start = new Location("1",10,10);
        Location end = new Location("2",20,20);
        Path p = new Path(start, end);

        Assert.assertTrue(p.start instanceof Location);
        Assert.assertTrue(p.end instanceof Location);
    }
}
