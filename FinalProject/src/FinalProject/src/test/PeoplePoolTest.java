package FinalProject.src.test;

import FinalProject.src.util.configuration;
import FinalProject.src.variable.People;
import FinalProject.src.variable.PeoplePool;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class PeoplePoolTest {

    @Test
    public void getPeopleSizeTest(){
        configuration.POPULATION_SIZE = 1000;
        int x = PeoplePool.getInstance().getPeopleSize(People.State.NORMAL);
        assertEquals(1000, x);
    }

    @Test
    public void poolTest() {
        List<People> pool = PeoplePool.getInstance().getPeopleList();
        int count = 0;
        int size = configuration.POPULATION_SIZE;
        int dX = configuration.CITY_WIDTH / ((int)Math.sqrt(size) + 1);
        int dY = configuration.CITY_HEIGHT / ((int)Math.sqrt(size) + 2);
        for (int i = 0; i < size - 1; i++) {
            int x2 = pool.get(i + 1).getX();
            int x1 = pool.get(i).getX();
            int y2 = pool.get(i + 1).getY();
            int y1 = pool.get(i).getY();

            if (x2 - x1 == 0) {
                if (y2 - y1 != dY) {
                    count++;
                }
            } else if ((x2 - x1 != dX) && (x2 != dX)) {
                count++;
            }
        }
        assertEquals(0, count);

    }
}
