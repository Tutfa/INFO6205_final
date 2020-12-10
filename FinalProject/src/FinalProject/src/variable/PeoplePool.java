package FinalProject.src.variable;

import FinalProject.src.util.configuration;

import java.util.ArrayList;
import java.util.List;

public class PeoplePool {

    private static PeoplePool peoplePool = new PeoplePool();

    public static PeoplePool getInstance(){
        return peoplePool;
    }

    List<People> peopleList = new ArrayList<>();

    public List<People> getPeopleList() {
        return peopleList;
    }

    public int getPeopleSize(int state){

        if (state == -1){
            return peopleList.size();
        }

        int i = 0;
        for (People people : peopleList) {
            if (people.getState() == state){
                i++;
            }
        }
        return i;
    }

    private PeoplePool(){
        City city = new City(400, 400);

        int size = configuration.POPULATION_SIZE;

        for (int i = 0; i < size; i++) {

            int row = (int)Math.sqrt(size);
            int disX = configuration.CITY_WIDTH / (row + 1);
            int disY = configuration.CITY_HEIGHT / (row + 2);

            int x = disX * (i % row + 1);
            int y = disY * (i / row + 1);

            peopleList.add(new People(city, x, y));
        }

    }
}

