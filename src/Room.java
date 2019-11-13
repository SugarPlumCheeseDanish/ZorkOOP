public class Room {
    private int id;                         // this is a unique identifier
    private int i, j;                       // maps directly to the roomBuilder array
    private String name;                    // descriptive name
    private String description;             // what's in the room...
    private int north, south, east, west;   // construct to -1 default?

    // this is for the room that HAS A LINK **TO** the secret room
    private boolean secret;                 // whether it HAS a secret room
    private boolean secretOpen;             // whether the secret room is AVAILABLE
    private int secretRoomIndex;            // index of your secret room

    // this is for the room that **IS** a secret room
    private int secretExit;                 // this is to exit the secret room
    private boolean iAmSecret;

    public Room() {
    }

    public Room(int id, int i, int j, String name, String description, int north, int south, int east, int west) {
        this.id = id;
        this.i = i;
        this.j = j;
        this.name = name;
        this.description = description;
        this.north = north;
        this.south = south;
        this.east = east;
        this.west = west;

        // initialize secret fields
        this.secret = false;
        this.secretOpen = false;
        this.secretRoomIndex = 0;
        this.iAmSecret = false;
        this.secretExit = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNorth() {
        return north;
    }

    public void setNorth(int north) {
        this.north = north;
    }

    public int getSouth() {
        return south;
    }

    public void setSouth(int south) {
        this.south = south;
    }

    public int getEast() {
        return east;
    }

    public void setEast(int east) {
        this.east = east;
    }

    public int getWest() {
        return west;
    }

    public void setWest(int west) {
        this.west = west;
    }

    public boolean isSecret() {
        return secret;
    }

    public void setSecret(boolean secret) {
        this.secret = secret;
    }

    public boolean isSecretOpen() {
        return secretOpen;
    }

    public void setSecretOpen(boolean secretOpen) {
        this.secretOpen = secretOpen;
    }

    public int getSecretRoomIndex() {
        return secretRoomIndex;
    }

    public void setSecretRoomIndex(int secretRoomIndex) {
        this.secretRoomIndex = secretRoomIndex;
    }

    public int getSecretExit() {
        return secretExit;
    }

    public void setSecretExit(int secretExit) {
        this.secretExit = secretExit;
    }

    public boolean isiAmSecret() {
        return iAmSecret;
    }

    public void setiAmSecret(boolean iAmSecret) {
        this.iAmSecret = iAmSecret;
    }

    // display the Room to the user
    public String display() {
        String str = "";
        str += "\nYou are in Room #" + id + ", the " + name + ". ";
        if(!iAmSecret) { str += "You see " + description + ".\n";}
        else { str += "You see \n\t* * * " + description + " * * *\n"; }
        if (!iAmSecret) {str += "You can move to the: \n\n";}
        if (north != -1) { str += "\t[\"n\"] North \n"; }
        if (south != -1) { str += "\t[\"s\"] South \n"; }
        if (east != -1) { str += "\t[\"e\"] East \n"; }
        if (west != -1) { str += "\t[\"w\"] West \n"; }
        if (secret && secretOpen) { str += "\t[\"*\"] Secret Room \n"; }
        if (iAmSecret) { str += "\n\t[\"x\"] to exit the Secret Room \n"; }
        str += "\nQuit (\"q\") or View map (\"v\"): ";
//        str += "\t[\"q\"] or Quit \n";
//        str += "\t[\"v\"] to View \n";
//        str += "Your choice: ";
        return str;
    }
}
