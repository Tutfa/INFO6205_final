package FinalProject.src;

import FinalProject.src.variable.Isolation;
import FinalProject.src.util.MyPanel;
import FinalProject.src.util.configuration;
import FinalProject.src.variable.People;
import FinalProject.src.variable.PeoplePool;

import javax.swing.*;
import java.util.List;
import java.util.Random;

public class main {

    public static void main(String[] args) {
        initIsolation();
        initPanel();
        initInfected();
    }
    private static void initPanel(){
        MyPanel p = new MyPanel();
        Thread panelThread = new Thread(p);
        JFrame frame = new JFrame();
        frame.add(p);
        frame.setSize(configuration.CITY_WIDTH + IsolationWidth + 300, configuration.CITY_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setTitle("COVID19 VIRUS SPREAD SIMULATION");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panelThread.start();
    }

    private static int IsolationWidth;

    private static void initIsolation(){
        IsolationWidth = Isolation.getInstance().getWidth();
    }

    private static void initInfected(){
        List<People> people = PeoplePool.getInstance().getPeopleList();
        for (int i = 0; i < configuration.ORIGINAL_INFECT; i++) {
            People person;
            do{
                person = people.get(new Random().nextInt(people.size() - 1));
            }while (person.isInfected());
            person.infection();
        }
    }
}
