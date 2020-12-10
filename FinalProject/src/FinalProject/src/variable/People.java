package FinalProject.src.variable;

import FinalProject.src.util.MyPanel;
import FinalProject.src.util.Point;
import FinalProject.src.util.configuration;

import java.util.List;
import java.util.Random;

public class People extends Point {
    private City city;

    private MoveTarget moveTarget;

    public MoveTarget getMoveTarget() {
        return moveTarget;
    }

    public void setMoveTarget(int x, int y) {
        moveTarget = new MoveTarget(x, y);
    }

    int sig = 1;

    double targetXU;
    double targetYU;
    double targetSig = 50;

    public interface State{
        int NORMAL = 0;         //Good people
        int CONTACTED = 1;         //Contacted by patients
        int CONTACTEDBYSUPER = 2;     //Contacted by super spreaders
        int CONFIRMED = 3;      //People who infect COVID-19
        int SUPERSPREADER = 4;   //Super spreaders
        int FREEZE = 5;         //People stay quarantine

    }

    public People(City city, int x, int y){
        super(x, y);
        this.city = city;
        targetXU = func.stdGaussian(100, x);
        targetYU = func.stdGaussian(100, y);
    }

    public boolean wantMove(){

        return func.stdGaussian(sig, configuration.WANTMOVE) > 0;
    }

    private int state = State.NORMAL;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    int confirmedTime = 0;      //People COVID19 confirmed time.

    public boolean isInfected() {
        return state == State.CONFIRMED || state == State.SUPERSPREADER;
    }

    public void beInfected() {
        if (state == State.CONTACTED) {
            if (Math.random() <= (configuration.NORMAL_RATE * (1 - 0.15 * configuration.MASK_WEAR_RATE))) {
                infection();
            }
        }
        if (state == State.CONTACTEDBYSUPER) {
            if (Math.random() <= (configuration.SUPER_RATE * (1 - 0.15 * configuration.MASK_WEAR_RATE))) {
                infection();
            }
        }
    }

    public void infection() {
        if (Math.random() < configuration.K) {
            state = State.SUPERSPREADER;
        } else {
            state = State.CONFIRMED;
        }
        confirmedTime = MyPanel.worldTime;
    }



    public double distance(People people){
        return Math.sqrt(Math.pow(getX() - people.getX(), 2) + Math.pow(getY() - people.getY(), 2));
    }

    public void freezy(){
        state = State.FREEZE;
    }

    public void action(){

        if (state == State.FREEZE)
            return;

        if (!wantMove()){
            return;
        }

        if (moveTarget == null || moveTarget.isArrived()){
            double targetX = func.stdGaussian(targetSig, targetXU);
            double targetY = func.stdGaussian(targetSig, targetYU);

            moveTarget = new MoveTarget((int) targetX, (int) targetY);
        }

        int dX = moveTarget.getX() - getX();
        int dY = moveTarget.getY() - getY();

        double length = Math.sqrt(Math.pow(dX, 2) + Math.pow(dY, 2));

        if(length < 1){
            moveTarget.setArrived(true);
            return;
        }

        int udX = (int)(dX / length);
        if (udX == 0 && dX != 0){
            if (dX > 0){
                udX = 1;
            }else
                udX = -1;
        }

        int udY = (int)(dY / length);
        if (udY == 0 && dY != 0){
            if (dY > 0)
                udY = 1;
            else udY = -1;
        }

        if (moveTarget.getX() > configuration.CITY_WIDTH || moveTarget.getX() < 0){
            dX = dY = 0;

            if (udX > 0){
                udX = -udX;
            }
        }

        if (moveTarget.getY() > configuration.CITY_HEIGHT || moveTarget.getY() < 0){
            dX = dY = 0;

            if (udY > 0){
                udY = - udY;
            }
        }

        moveTo(dX, dY);
    }

    public Room useRoom;

    private float SAFE_DIST = 2f; //Safe distance.

    public void update(){

        if (state == State.FREEZE){
            return;
        }

        if (state >= State.CONTACTED
                && MyPanel.worldTime - confirmedTime >= configuration.ROOM_RECEIVE_TIME){

            if (Math.random() < configuration.ISO_RATE){
                Room room = Isolation.getInstance().pickRoom();

                useRoom = room;
                state = State.FREEZE;
                setX(room.getX());
                setY(room.getY());
                room.setEmpty(false);

                return;
            }
        }

        double stdShadowTime = func.stdGaussian(5, configuration.SHADOW_TIME);

        if (MyPanel.worldTime - confirmedTime > stdShadowTime &&
                (state == State.CONTACTED || state == State.CONTACTEDBYSUPER)){
            beInfected();
        }

        if (MyPanel.worldTime - confirmedTime > configuration.ISO_TIME
                && (state == State.CONTACTED || state == State.CONTACTEDBYSUPER)) {
            state = State.NORMAL;
        }

        if (state >= State.CONFIRMED && MyPanel.worldTime - confirmedTime >= stdShadowTime){
            if (Math.random() < configuration.ISO_RATE) {
                Room room = Isolation.getInstance().pickRoom();

                useRoom = room;
                state = State.FREEZE;
                setX(room.getX());
                setY(room.getY());
                room.setEmpty(false);
            }
        }

        action();

        List<People> people = PeoplePool.getInstance().peopleList;
        if (state >= State.CONTACTED){
            return;
        }

        for (People person : people) {
            if (person.getState() < State.CONFIRMED){
                continue;
            }
            if (distance(person) < SAFE_DIST){
                if (person.state == State.CONFIRMED) {
                    state = State.CONTACTED;
                    confirmedTime = MyPanel.worldTime;

                }
                if (person.state == State.SUPERSPREADER) {
                    state = State.CONTACTEDBYSUPER;
                    confirmedTime = MyPanel.worldTime;

                    break;
                }

            }
        }
    }


}
