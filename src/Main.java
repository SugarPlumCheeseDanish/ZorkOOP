import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private static final int ARRAY_TOP_BOUND = 7;

    private static boolean[][] roomBuilder = new boolean[ARRAY_TOP_BOUND][ARRAY_TOP_BOUND];     //defaults to false
    private static ArrayList<Room> rooms = new ArrayList<>();
    private static Scanner keyboard = new Scanner(System.in);
    private static Random randomGenerator = new Random();

    // room one location
    private static final int ROOM_ONE_I = 3;
    private static final int ROOM_ONE_J = 3;

    //***********************************************************************
    // THIS WILL NEED CODE !!! A method to set them watching out for bounds
    // set INITIAL bounds on i & j, based on initial location
    // CAREFUL!!! initial bounds need to be > 1 for this stuff... and < UPPER...
    private static int iLowerNorth = ROOM_ONE_I - 1;
    private static int iUpperSouth = ROOM_ONE_I + 1;
    private static int jUpperEast = ROOM_ONE_J + 1;
    private static int jLowerWest = ROOM_ONE_J - 1;



//    private static int iLowerNorth = 0;
//    private static int iUpperSouth = ROOM_ONE_I + 1;
//    private static int jUpperEast = ROOM_ONE_J + 1;
//    private static int jLowerWest = 0;
    //***********************************************************************

    // for building maze, using pre-generated roomDB
    private static int roomIndex = 1;

    // for testing as we code...
    // build a foundRoom boolean array (mask) to use for printing out rooms encountered so far

    public static void main(String[] args){

        // set + build initial room
        roomBuilder[ROOM_ONE_I][ROOM_ONE_J] = true;

        roomList();
        mazeGenerator();

        System.out.println("Room Builder array: ");
        System.out.println();
        for (int i = 0; i < ARRAY_TOP_BOUND; i++) {
            for (int j = 0; j < ARRAY_TOP_BOUND; j++) {
                System.out.print("\t"+roomBuilder[i][j]);
            }
            System.out.print("\n");
        }

    } // end main

    private static int generateWithinBounds(int upper, int lower){
        // you have to add one to the range, because subtraction "leaves one out"
        // when we want to include lower and include upper...
        int range = upper - lower + 1;
        return randomGenerator.nextInt(range) + lower;
    }

    private static void adjustBounds(int i, int j){
        // this method adjusts the bounds for building the maze
        // we check to see if our current location would push our bounds out further...
        if (((i-1) < iLowerNorth) && ((i-1) >= 0)) { iLowerNorth = i-1;}
        if (((i+1) > iUpperSouth) && ((i+1) < ARRAY_TOP_BOUND)) { iUpperSouth = i+1;}
        if (((j+1) > jUpperEast) && ((j+1) < ARRAY_TOP_BOUND)) { jUpperEast = j+1;}
        if (((j-1) < jLowerWest) && ((j-1) >= 0)) { jLowerWest = j-1;}
    }

    private static void mazeGenerator() {

        while(roomIndex < 8){

            System.out.println("\nTop of While Loop");
            System.out.println("Room Counter is: " + roomIndex);
            int i = generateWithinBounds(iUpperSouth, iLowerNorth);
            int j = generateWithinBounds(jUpperEast, jLowerWest);
            System.out.println("Testing location: [" + i + "][" + j + "]");

            // if my current location is empty :: available for possible building...
            if (!roomBuilder[i][j]) {
                // check NORTH : (i - 1, j)
                if(checkSurround((i-1), j, "North")) {
                    roomBuilder[i][j] = true;
                    linkRoom(i, j);
                    adjustBounds(i, j);
                    roomIndex++;
                    continue;
                }
                // check SOUTH : (i + 1, j)
                if(checkSurround((i+1), j, "South")) {
                    roomBuilder[i][j] = true;
                    linkRoom(i, j);
                    adjustBounds(i, j);
                    roomIndex++;
                    continue;
                }
                // check EAST : (i, j + 1)
                if(checkSurround(i, (j+1), "East")) {
                    roomBuilder[i][j] = true;
                    linkRoom(i, j);
                    adjustBounds(i, j);
                    roomIndex++;
                    continue;
                }
                // check WEST : (i, j - 1)
                if(checkSurround(i, (j-1), "West")) {
                    roomBuilder[i][j] = true;
                    linkRoom(i, j);
                    adjustBounds(i, j);
                    roomIndex++;
                    continue;
                }
            }
            else {
                // System.out.println("Room was taken!");
            }
        } // end while

    } // end MazeGenerator

    private static void linkRoom(int myCurrentI, int myCurrentJ) {
        Room myRoom = rooms.get(roomIndex);
        myRoom.setI(myCurrentI);
        myRoom.setJ(myCurrentJ);

        for(int k = 0; k < roomIndex; k++) {
            Room foundRoom = rooms.get(k);
            int foundRoomI = foundRoom.getI();
            int foundRoomJ = foundRoom.getJ();

            // for every preceding room (aka foundRoom) we are going to check
            // every cardinal direction of that preceding room
            // to see if it connects to my room and if so then link them by direction

            // tweak foundRoom NORTH : foundRoomI - 1
            if(foundRoomI - 1 == myCurrentI && foundRoomJ == myCurrentJ) {
                foundRoom.setNorth(myRoom.getId());
                myRoom.setSouth(foundRoom.getId());
            }

            // tweak foundRoom SOUTH : foundRoomI + 1
            if(foundRoomI + 1 == myCurrentI && foundRoomJ == myCurrentJ) {
                foundRoom.setSouth(myRoom.getId());
                myRoom.setNorth(foundRoom.getId());
            }

            // tweak foundRoom EAST : foundRoomJ + 1
            if(foundRoomJ + 1 == myCurrentJ && foundRoomI == myCurrentI) {
                foundRoom.setEast(myRoom.getId());
                myRoom.setWest(foundRoom.getId());
            }

            // tweak foundRoom WEST : foundRoomJ - 1
            if(foundRoomJ - 1 == myCurrentJ && foundRoomI == myCurrentI) {
                foundRoom.setWest(myRoom.getId());
                myRoom.setEast(foundRoom.getId());
            }
        } // end for loop

    }

    private static boolean checkSurround(int i, int j, String direction){
        try {
            if (roomBuilder[i][j]) {
                // encountered a room, this means we can build! because we are now "connected"
                // System.out.println("found room to the " + direction + "!");
                // set next room (get index roomCounter) in the ArrayList's location field
                return true;
            } else {
                return false;   // encountered an empty room, not "connected" to an existing room
            }
        }
        catch (ArrayIndexOutOfBoundsException e) {
            return false;       // same as encountering an empty room, not "connected" to an existing room
        }
    }

    public static void roomList() {
        rooms.add(new Room(1, ROOM_ONE_I, ROOM_ONE_J, "Hallway", "dead scorpion", -1, -1, -1, -1));
        rooms.add(new Room(2, 0, 0, "Living Room", "piano", -1, -1, -1, -1));
        rooms.add(new Room(3, 0, 0, "Library", "spiders", -1, -1, -1, -1));
        rooms.add(new Room(4, 0, 0, "Kitchen", "bats", -1, -1, -1, -1));
        rooms.add(new Room(5, 0, 0, "Dining Room", "dust and an empty box", -1, -1, -1, -1));
        rooms.add(new Room(6, 0, 0, "Vault", "3 walking skeletons", -1, -1, -1, -1));
        rooms.add(new Room(7, 0, 0, "Parlor", "treasure chest", -1, -1, -1, -1));
        rooms.add(new Room(8, 0, 0, "Secret Room", "piles of gold", -1, -1, -1, -1));
    }

} // end class


//        // TESTING CODE
//        int[] intArray = new int[5];
//        System.out.println(Arrays.toString(intArray)+"\n");

//        // check our roomBuilder array
//        for (int i = 0; i < 5; i++) {
//            for (int j = 0; j < 5; j++) {
//                System.out.print("\t"+roomBuilder[i][j]+"\t");
//            }
//            System.out.print("\n");
//        }

//        // generate within bounds TESTING CODE
//        System.out.println("Upper: " + upper);
//        System.out.println("Lower: " + lower);
//        // you have to add one to the range, because subtraction "leaves one out"
//        // when we want to include lower and include upper...
//        int range = upper - lower + 1;
//        System.out.println("Range: " + range);
//        for (int i=0; i < 10; i++) {
//            System.out.println(randomGenerator.nextInt(range) + lower);
//        }
