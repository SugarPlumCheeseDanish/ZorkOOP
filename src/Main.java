import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {

    // FINAL variables for MAZE BUILDING ::
    // sets the SQUARE shape of our overall "field" for the room maze
    private static final int ARRAY_TOP_BOUND = 7;
    // sets room 1's location. be sure to set within bounds
    private static final int ROOM_ONE_I = 3;
    private static final int ROOM_ONE_J = 3;

    // Other variables for MAZE BUILDING ::
    private static Random randomGenerator = new Random();
    // these will be set via a function call at the top of main (needs logic)
    private static int iLowerNorth;
    private static int iUpperSouth;
    private static int jUpperEast;
    private static int jLowerWest;
    // for adding rooms from pre-set ArrayList<Room> roomDB
    private static int roomIndex;
    // the room maze field :: boolean value indicates whether a spot is taken by a room or not
    private static boolean[][] roomLocation = new boolean[ARRAY_TOP_BOUND][ARRAY_TOP_BOUND];     //defaults to false

    // Variables for setting our pre-set DATABASE of ROOMS
    // the roomDB :: preset via method call to setRoomDB()
    private static ArrayList<Room> roomDB = new ArrayList<>();
    // total number of pre-set rooms in current implementation :: set in method setRoomDB()
    private static int numRooms;

    // GENERAL USE variables
    private static Scanner keyboard = new Scanner(System.in);
    private static int navigationIndex;

    // for testing as we code...
    // build a foundRoom boolean array (mask) to use for printing out rooms encountered so far
    private static boolean[][] foundRooms = new boolean[ARRAY_TOP_BOUND][ARRAY_TOP_BOUND]; // default false
    private static int[][] map = new int[ARRAY_TOP_BOUND][ARRAY_TOP_BOUND];  // default zero

    public static void main(String[] args){

        setBounds();
        setRoomDB();
        generateMaze();

//        System.out.println("Room Location Array: ");
//        System.out.println();
//        for (int i = 0; i < ARRAY_TOP_BOUND; i++) {
//            for (int j = 0; j < ARRAY_TOP_BOUND; j++) {
//                System.out.print("\t"+ roomLocation[i][j]);
//            }
//            System.out.print("\n");
//        }

        // general intro ::
        System.out.println("\nWelcome to Zork!");
        String userStr;

        // Just for room one ::
        navigationIndex = 0;
        Room myRoom = roomDB.get(navigationIndex);

        // for any choice after that ::
        while(true) {
//            System.out.println("Top of do-while..........");

            // Navigation Step ::
            if (navigationIndex < 0) {
                System.out.println("Invalid navigation option! Please try again...");
                // redisplay same room, don't do anything else...
                System.out.print(myRoom.display());
                userStr = keyboard.nextLine();
            }
            else {
//                System.out.println("Valid option, yay!!!!");
                myRoom = roomDB.get(navigationIndex);
                unMask(myRoom);
                if(myRoom.isSecret() && !(myRoom.isSecretOpen())) {unMaskSecret(myRoom);}
                System.out.print(myRoom.display());
                userStr = keyboard.nextLine();
            }

            // For debuggin ::
            System.out.println("User Choice: " + userStr);

            // Set next choice ::
            if (userStr.equalsIgnoreCase("n")) {
                // user wants to navigate north
                navigationIndex = myRoom.getNorth()-1;
            }
            else if (userStr.equalsIgnoreCase("s")) {
                // user wants to navigate south
                navigationIndex = myRoom.getSouth()-1;
            }
            else if (userStr.equalsIgnoreCase("e")) {
                // user wants to navigate east
                navigationIndex = myRoom.getEast()-1;
            }
            else if (userStr.equalsIgnoreCase("w")) {
                // user wants to navigate west
                navigationIndex = myRoom.getWest()-1;
            }
            else if (userStr.equalsIgnoreCase("v")) {
                view(myRoom);
            }
            else if (userStr.equalsIgnoreCase("*")) {
                if(myRoom.isSecret() && myRoom.isSecretOpen()){
                    // we have only one secret room right now, and it's room index 9.
                    navigationIndex = myRoom.getSecretRoomIndex();
                } else {
                    System.out.println("Invalid navigation option. Please try again...");
                }
            }
            else if (userStr.equalsIgnoreCase("x")) {
                if(myRoom.isiAmSecret()) {
                    navigationIndex = myRoom.getSecretExit();
                }
                else {
                    System.out.println("Invalid navigation option. Please try again...");
                }
            }
            else if (userStr.equalsIgnoreCase("q")){
                // user wants to quit!
                break;
            }
            else {
                System.out.println("Invalid navigation option. Please try again...");
            }

        }
        // end play loop

    } // end main

    // **************************************
    // unMaskSecret :: this opens up a (hard-coded) secret room...
    // **************************************
    private static void unMaskSecret(Room myRoom){
        // use Math.random() to set up a one in four chance
        int random = 1 + ((int) (Math.random() * 4));
        if (random == 3) {
            System.out.println();
            System.out.println("\t************************************");
            System.out.println("\t \u2606 You have found a Secret Room! \u2606");
            System.out.println("\t************************************");
            myRoom.setSecretOpen(true);
        }
    }

    // **************************************
    // unMask :: unMask a room on the room map
    // this is just for telling us that a room has been visited!
    // **************************************
    private static void unMask(Room myRoom){
        foundRooms[myRoom.getI()][myRoom.getJ()] = true;
    }

    // **********************************
    // view :: to Show our room Array
    // this method shows the true/false roomLocation array,
    // for now!
    // *********************************
    private static void view(Room myRoom){

//        System.out.println("Room Location BOOLEAN Map Array: \n");
//        System.out.println();
//        for (int i = 0; i < ARRAY_TOP_BOUND; i++) {
//            for (int j = 0; j < ARRAY_TOP_BOUND; j++) {
//                System.out.print("\t"+ roomLocation[i][j]);
//            }
//            System.out.print("\n\n");
//        }
//

//****************************************************************************
//      UNCOMMENT THIS CODE in order to SHOW a CHEAT MAP above the USER MAP
//****************************************************************************
       // System.out.println();
       // for (int i = 0; i < ARRAY_TOP_BOUND; i++) {
       //     for (int j = 0; j < ARRAY_TOP_BOUND; j++) {
       //         if (map[i][j] != 0) {
       //             System.out.print("\t" + map[i][j]);
       //         }
       //         else {
       //             System.out.print("\t");
       //         }
       //     }
       //     System.out.print("\n\n");
       // }


        //************************************************************
        // USER MAP, with "STAR" Marks the Spot for current location
        //************************************************************
        for (int i = 0; i < ARRAY_TOP_BOUND; i++) {
            for (int j = 0; j < ARRAY_TOP_BOUND; j++) {
                if(foundRooms[i][j]){
                    // show a special symbol on our CURRENT LOCATION
                    if(i == myRoom.getI() && j == myRoom.getJ()) {
                        if (myRoom.isiAmSecret()){
                            // if you are INSIDE the SECRET ROOM, show a SPECIAL STAR
                            System.out.print("\t\u2388");
                        }
                        else {
                            // otherwise, show a STAR to mark our current location in a normal room
                            System.out.print("\t\u2606");
                        }
                    }
                    // otherwise show the room number
                    else {System.out.print("\t"+ map[i][j]);}
                }
                else {
                    // if there's no room there, put a TAB (empty space)
                    System.out.print("\t");
                }
            }
            System.out.print("\n\n");
        }

//        System.out.println("Rooms Found so far, BOOLEAN Array (this is our mask): \n");
//        System.out.println();
//        for (int i = 0; i < ARRAY_TOP_BOUND; i++) {
//            for (int j = 0; j < ARRAY_TOP_BOUND; j++) {
//                System.out.print("\t"+ foundRooms[i][j]);
//            }
//            System.out.print("\n\n");
//        }

    }

    // *********************************************************************************
    // linkRoom method :: links up the current room, at location (i,j), to any prior rooms
    // the current room to be added to our maze is at roomIndex in our roomDB ArrayList
    // the current room's location is specified by local vars myCurrentI and myCurrentJ
    // this function will LINK the current room to any of the previous rooms which are adjacent
    // *********************************************************************************
    private static void linkRoom(int myCurrentI, int myCurrentJ) {
        Room myRoom = roomDB.get(roomIndex);
        myRoom.setI(myCurrentI);
        myRoom.setJ(myCurrentJ);
        map[myCurrentI][myCurrentJ] = myRoom.getId();

        // SECRET ROOM CHECK ::
        // we know that it's room 3 that has the secret room
        // And, we know that Room 3's secret room is room 10 (index = 9)
        // what we're doing here, is giving our secret room of index 9 the SAME LOCATION as room 3!
        if (myRoom.getId() == 3) {
            int secretIndex = myRoom.getSecretRoomIndex();
            roomDB.get(secretIndex).setI(myCurrentI);
            roomDB.get(secretIndex).setJ(myCurrentJ);
        }

        for(int k = 0; k < roomIndex; k++) {
            Room foundRoom = roomDB.get(k);
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

    //*************************************************************
    // generateMaze method :: randomly generate connected maze of rooms
    // using pre-set rooms as defined in our roomsDB ArrayList
    //*************************************************************
    private static void generateMaze() {

        // set initial room in maze!
        roomLocation[ROOM_ONE_I][ROOM_ONE_J] = true;
        map[ROOM_ONE_I][ROOM_ONE_J] = 1;

        // tracks the current room we are assigning (we already did room zero, above!)
        roomIndex = 1;

        // continue looping until all rooms have been assigned
        while(roomIndex < numRooms){

//            System.out.println("\nTop of While Loop");
//            System.out.println("Room Counter is: " + roomIndex);
            int i = generateWithinBounds(iUpperSouth, iLowerNorth);
            int j = generateWithinBounds(jUpperEast, jLowerWest);
//            System.out.println("Testing location: [" + i + "][" + j + "]");

            // if my current location is empty :: available for possible building...
            if (!roomLocation[i][j]) {
                // check NORTH : (i - 1, j)
                if(checkSurround((i-1), j, "North")) {
                    roomLocation[i][j] = true;
                    linkRoom(i, j);
                    adjustBounds(i, j);
                    roomIndex++;
                    continue;
                }
                // check SOUTH : (i + 1, j)
                if(checkSurround((i+1), j, "South")) {
                    roomLocation[i][j] = true;
                    linkRoom(i, j);
                    adjustBounds(i, j);
                    roomIndex++;
                    continue;
                }
                // check EAST : (i, j + 1)
                if(checkSurround(i, (j+1), "East")) {
                    roomLocation[i][j] = true;
                    linkRoom(i, j);
                    adjustBounds(i, j);
                    roomIndex++;
                    continue;
                }
                // check WEST : (i, j - 1)
                if(checkSurround(i, (j-1), "West")) {
                    roomLocation[i][j] = true;
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

    private static boolean checkSurround(int i, int j, String direction){
        try {
            if (roomLocation[i][j]) {
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

    //***********************************************************************
    // setRoomDB method :: hard-codes the initial values for our rooms
    //***********************************************************************
    private static void setRoomDB() {
        roomDB.add(new Room(1, ROOM_ONE_I, ROOM_ONE_J, "Hallway", "a dead scorpion", -1, -1, -1, -1));
        roomDB.add(new Room(2, 0, 0, "Living Room", "a piano", -1, -1, -1, -1));
        // hard coded room that connects to a secret room. INDEX = 2.
        roomDB.add(new Room(3, 0, 0, "Library", "some spiders", -1, -1, -1, -1));
        roomDB.get(2).setSecret(true);
        roomDB.get(2).setSecretOpen(false);
        roomDB.get(2).setSecretRoomIndex(9);
        roomDB.add(new Room(4, 0, 0, "Kitchen", "bats", -1, -1, -1, -1));
        roomDB.add(new Room(5, 0, 0, "Dining Room", "dust and an empty box", -1, -1, -1, -1));
        roomDB.add(new Room(6, 0, 0, "Vault", "3 walking skeletons", -1, -1, -1, -1));
        roomDB.add(new Room(7, 0, 0, "Parlor", "a treasure chest", -1, -1, -1, -1));
        roomDB.add(new Room(8, 0, 0, "Sun Room", "nothing but a faint glow coming in through the windows", -1, -1, -1, -1));
        roomDB.add(new Room(9, 0, 0, "Bedroom", "a dead body", -1, -1, -1, -1));
        numRooms = 9;
        // stop mapping here :: number of Rooms for MAPPING is 'numRooms'
        // SECRET room(s) below, these do not go on the overall map
        // hard-coded secret room. INDEX = 9. to get back to room 3, go West.
        roomDB.add(new Room(10, 0, 0, "Secret Room", "piles of gold", -1, -1, -1, -1));
        roomDB.get(9).setiAmSecret(true);
        roomDB.get(9).setSecretExit(2);     // this means you link to room 3 to exit secret room 10
    }

    //***********************************************************************
    // setBounds() method :: to safely set the bounds based on initial (static final) values
    // this method is a safeguard for if we wish to tweak the initial values of our game
    // ... maybe one day those initial values could be set via String[] args to main(), so...
    //***********************************************************************
    private static void setBounds() {
        if (ROOM_ONE_I - 1 >= 0) {iLowerNorth = ROOM_ONE_I - 1;} else {iLowerNorth = 0;}
        if (ROOM_ONE_I + 1 < ARRAY_TOP_BOUND) {iUpperSouth = ROOM_ONE_I + 1;} else {iUpperSouth = (ARRAY_TOP_BOUND-1);}
        if (ROOM_ONE_J + 1 < ARRAY_TOP_BOUND) {jUpperEast = ROOM_ONE_J + 1;} else {jUpperEast = (ARRAY_TOP_BOUND-1);}
        if (ROOM_ONE_J - 1 >= 0) {jLowerWest = ROOM_ONE_J - 1;} else {jLowerWest = (ARRAY_TOP_BOUND-1);}
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
