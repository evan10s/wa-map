package com.strat.wamap;


import junit.framework.Assert;

import org.junit.Test;

public class LocationTest extends drawMap{

    @Test
    public void locationCreateTest() throws Exception {
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
    public void pathCreateTest() throws Exception {
        Location start = new Location("1",10,10);
        Location end = new Location("2",20,20);
        Path p = new Path(start, end);

        Assert.assertTrue(p.start instanceof Location);
        Assert.assertTrue(p.end instanceof Location);
    }

    @Test
    public void iathMidpointDistanceTesT() throws Exception {
        Location s1 = new Location("1",10,10);
        Location e1 = new Location("2",20,20); //mdpt 15,15
        Location s2 = new Location("1",30,10);
        Location e2 = new Location("2",40,20); //mdpt 35,15
        Path p1 = new Path(s1,e1);
        Path p2 = new Path(s2,e2);
        Assert.assertEquals(20,getMidpointDist(p1, p2), .0001);
    }

    @Test
    public void pointsMatchTest() throws Exception {
        Location l1 = new Location("1",10,10);
        Location l2 = new Location("2",10,10);
        Assert.assertTrue(pointsMatch(l1,l2));
    }

    @Test
    public void distanceBetweenPointsTest() throws Exception {
        Location l1 = new Location("1",10,10);
        Location l2 = new Location("2",10,50);
        Assert.assertEquals(40, distanceBetweenPoints(l1,l2),.0001);
    }
}
