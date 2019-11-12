public class Room {
    private int id;                         // this is a unique identifier
    private int i, j;                       // maps directly to the roomBuilder array
    private String name;                    // descriptive name
    private String description;             // what's in the room...
    private int north, south, east, west;   // construct to -1 default?


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

    // display the Room to the user
    public String display() {
        String str = "";
        str += "You are in Room #" + id + ", the " + name + ".\nYou see " + description + ".\n";
        str += "You can move to the: \n";
        if(north != -1) { str += "\t[\"n\"] North \n"; }
        if (south != -1) { str += "\t[\"s\"] South \n"; }
        if (east != -1) { str += "\t[\"e\"] East \n"; }
        if (west != -1) { str += "\t[\"w\"] West \n"; }
        str += "\t[\"q\"] or Quit \n";
        str += "\t[\"v\"] to View \n";
        str += "Your choice: ";
        return str;
    }
}
