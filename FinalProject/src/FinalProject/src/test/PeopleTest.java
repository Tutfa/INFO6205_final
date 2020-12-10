package FinalProject.src.test;

import FinalProject.src.variable.City;
import FinalProject.src.variable.People;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class PeopleTest {

    City city = new City(400, 400);


    @Test
    public void beInfectedTest(){
        People p = new People(city, 80, 80);
        p.beInfected();
        assertFalse(p.getState() == People.State.CONFIRMED || p.getState() == People.State.SUPERSPREADER);
    }


    @Test
    public void distanceTest() {
        People p = new People(city, 80, 80);
        People people = new People(city, 50, 40);
        assertEquals(50.0, p.distance(people), 0.0);
    }


    @Test
    public void freezyTest() {
        People p = new People(city, 80, 80);
        p.freezy();
        assertEquals(p.getState(), People.State.FREEZE);
    }


    @Test
    public void actionTest1() {
        People p = new People(city, 80, 80);
        p.setState(5);
        actionStopTest(p);
    }


    @Test
    public void actionTest2() {
        People p = new People(city, 50, 50);
        p.setMoveTarget(10000, 80);
        actionStopTest(p);
    }

    @Test
    public void actionTest3() {
        People p = new People(city, 50, 50);
        p.setMoveTarget(80, 1000);
        actionStopTest(p);
    }

    @Test
    public void actionTest4() {
        People p = new People(city, 50, 50);
        p.setMoveTarget(55, 51);
        int x1 = p.getX();
        int y1 = p.getY();
        p.action();
        int x2 = p.getX();
        int y2 = p.getY();
        assertEquals(55, x2);
        assertEquals(51, y2);
    }

    private void actionStopTest (People p) {
        int x1 = p.getX();
        int y1 = p.getY();
        p.action();
        int x2 = p.getX();
        int y2 = p.getY();
        assertEquals(x1, x2);
        assertEquals(y1, y2);
    }
}
