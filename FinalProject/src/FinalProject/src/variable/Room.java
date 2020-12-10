package FinalProject.src.variable;

import FinalProject.src.util.Point;

public class Room extends Point {

    public Room(int x, int y) {
        super(x, y);
    }

    private boolean isEmpty = true;

    public boolean isEmpty(){
        return isEmpty;
    }

    public void setEmpty(boolean empty){
        isEmpty = empty;
    }
}
