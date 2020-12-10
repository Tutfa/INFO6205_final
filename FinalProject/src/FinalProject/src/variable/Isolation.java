package FinalProject.src.variable;

import FinalProject.src.util.Point;
import FinalProject.src.util.configuration;

import java.util.ArrayList;
import java.util.List;

public class Isolation extends Point {

    public static final int ISOAREA_X = 820;
    public static final int ISOAREA_Y = 80;
    private int width;
    private int height = 600;

    public static Isolation getInstance() {
        return isolation;
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private static Isolation isolation = new Isolation();

    private Point point = new Point(ISOAREA_X, ISOAREA_Y);
    private List<Room> rooms = new ArrayList<>();

    public List<Room> getRooms(){
        return rooms;
    }

    private Isolation(){
        super(ISOAREA_X, ISOAREA_Y + 10);
        if (configuration.ROOM_COUNT == 0){
            width = 0;
            height = 0;
        }
        
        int column = configuration.ROOM_COUNT / 100;
        width = column * 6;

        for (int i = 0; i < column; i++) {

            for (int j = 10; j < 606; j += 6) {

                Room room = new Room(point.getX() + i * 6, point.getY() + j);
                rooms.add(room);

                if (rooms.size() >= configuration.ROOM_COUNT)
                    break;
            }
        }
    }

    public Room pickRoom(){
        for (Room room : rooms) {
            if (room.isEmpty()){
                return room;
            }
        }
        return null;
    }

    public Room returnRoom(Room room){
        if (room != null){
            room.setEmpty(true);
        }
        return room;
    }

}
