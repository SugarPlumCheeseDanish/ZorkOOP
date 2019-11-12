import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private static final int ARRAY_TOP_BOUND = 7;

    private static boolean[][] roomBuilder = new boolean[ARRAY_TOP_BOUND][ARRAY_TOP_BOUND];     //defaults to false
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
    private static int roomCounter = 0;

    // for testing as we code...
    // build a foundRoom boolean array (mask) to use for printing out rooms encountered so far

    public static void main(String[] args){

        // set + build initial room
        roomBuilder[ROOM_ONE_I][ROOM_ONE_J] = true;

        MazeGenerator();

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

    private static void MazeGenerator() {

        while(roomCounter < 15){

            System.out.println("\nTop of While Loop");
            System.out.println("Room Counter is: " + roomCounter);
            int i = generateWithinBounds(iUpperSouth, iLowerNorth);
            int j = generateWithinBounds(jUpperEast, jLowerWest);
            System.out.println("Testing location: [" + i + "][" + j + "]");

            // if my current location is empty :: available for possible building...
            if (!roomBuilder[i][j]) {
                // check NORTH : (i - 1, j)
                if(checkSurround((i-1), j, "North")) {
                    roomBuilder[i][j] = true;
                    adjustBounds(i, j);
                    continue;
                }
                // check SOUTH : (i + 1, j)
                if(checkSurround((i+1), j, "South")) {
                    roomBuilder[i][j] = true;
                    adjustBounds(i, j);
                    continue;
                }
                // check EAST : (i, j - 1)
                if(checkSurround(i, (j-1), "East")) {
                    roomBuilder[i][j] = true;
                    adjustBounds(i, j);
                    continue;
                }
                // check WEST : (i, j + 1)
                if(checkSurround(i, (j+1), "West")) {
                    roomBuilder[i][j] = true;
                    adjustBounds(i, j);
                    continue;
                }
            }
            else {
                // System.out.println("Room was taken!");
            }
        } // end while

    } // end MazeGenerator

    private static boolean checkSurround(int i, int j, String direction){
        try {
            if (roomBuilder[i][j]) {
                // encountered a room, this means we can build! because we are now "connected"
                // System.out.println("found room to the " + direction + "!");
                // set next room (get index roomCounter) in the ArrayList's location field
                roomCounter++;
                return true;
            } else {
                return false;   // encountered an empty room, not "connected" to an existing room
            }
        }
        catch (ArrayIndexOutOfBoundsException e) {
            return false;       // same as encountering an empty room, not "connected" to an existing room
        }
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
