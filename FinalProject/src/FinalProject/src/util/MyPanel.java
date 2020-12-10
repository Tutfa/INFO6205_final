package FinalProject.src.util;

import FinalProject.src.variable.Isolation;
import FinalProject.src.variable.People;
import FinalProject.src.variable.PeoplePool;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MyPanel extends JPanel implements Runnable{

    public MyPanel(){
        super();
        this.setBackground(Color.BLACK);
    }

    public void paint(Graphics g){
        super.paint(g);

        g.setColor(Color.PINK);

        g.drawRect(Isolation.getInstance().getX(), Isolation.getInstance().getY(), Isolation.getInstance().getWidth(),
                Isolation.getInstance().getHeight());
        g.setColor(Color.blue);
        g.drawString("Isolation Area", Isolation.getInstance().getX() + Isolation.getInstance().getWidth() / 4,
                Isolation.getInstance().getY() - 16);
        List<People> people = PeoplePool.getInstance().getPeopleList();
        if (people == null){
            return;
        }
        for (People person : people) {
            switch (person.getState()){
                case People.State.NORMAL:{
                    g.setColor(Color.LIGHT_GRAY);
                    break;
                }

                case People.State.CONTACTED:{
                    g.setColor(Color.YELLOW);
                    break;
                }

                case People.State.CONFIRMED:{
                    g.setColor(Color.red);
                    break;
                }

                case People.State.SUPERSPREADER:{
                    g.setColor(Color.red);
                    break;
                }

                case People.State.CONTACTEDBYSUPER:{
                    g.setColor(Color.YELLOW);
                    break;
                }

                case People.State.FREEZE:{
                    g.setColor(Color.cyan);
                    break;
                }
            }

            person.update();
            g.fillOval(person.getX(), person.getY(), 3, 3);
        }
        int captionStartOffsetX = 800 + Isolation.getInstance().getWidth() + 40;
        int captionStartOffsetY = 40;
        int captionSize = 24;

        //Show Data Form
        g.setColor(Color.WHITE);
        g.drawString("Sum Population: " + configuration.POPULATION_SIZE, captionStartOffsetX, captionStartOffsetY);
        g.setColor(Color.gray);
        g.drawString("Normal Population: " + PeoplePool.getInstance().getPeopleSize(People.State.NORMAL), captionStartOffsetX, captionStartOffsetY + captionSize);
        g.setColor(Color.YELLOW);
        g.drawString("Contacted Population: " + (PeoplePool.getInstance().getPeopleSize(People.State.CONTACTED) + PeoplePool.getInstance().getPeopleSize(People.State.CONTACTEDBYSUPER)), captionStartOffsetX, captionStartOffsetY + 2 * captionSize);
        g.setColor(Color.red);
        g.drawString("Confirmed Population: " + (PeoplePool.getInstance().getPeopleSize(People.State.CONFIRMED) + PeoplePool.getInstance().getPeopleSize(People.State.SUPERSPREADER)), captionStartOffsetX, captionStartOffsetY + 3 * captionSize);
        g.setColor(Color.CYAN);
        g.drawString("Qurantine Population: " + PeoplePool.getInstance().getPeopleSize(People.State.FREEZE), captionStartOffsetX, captionStartOffsetY + 4 * captionSize);
        g.setColor(Color.GREEN);
        g.drawString("Empty Room: " + Math.max(configuration.ROOM_COUNT - PeoplePool.getInstance().getPeopleSize(People.State.FREEZE), 0), captionStartOffsetX, captionStartOffsetY + 5 * captionSize);
        g.setColor(Color.ORANGE);
        g.drawString("World Time(Day): " + (int)(worldTime / 10.0), captionStartOffsetX, captionStartOffsetY + 6 * captionSize);

    }

    public static int worldTime = 0;

    Timer timer = new Timer();

    class MyTimerTask extends TimerTask{
        public void run(){
            MyPanel.this.repaint();
            worldTime++;
        }
    }

    @Override
    public void run() {
        timer.schedule(new MyTimerTask(), 0, 100);
    }
}
