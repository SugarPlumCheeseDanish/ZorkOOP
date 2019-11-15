# ZorkOOP
Object Oriented Zork!

# Implementation

## The Random Maze
Our implementation of Zork starts you out at Room #1, which is set in a pre-determined location in the Maze.
The rest of the rooms are randomly placed into the Maze from that point.
Every room is connected to at least one other room.

Our code can easily scale to a larger number or rooms. Currently there are nine rooms.
The number of rooms and their contents are currently hard-coded.

The size of the field that the maze is built on can also be adjusted. It is currently set to a 7x7 square field.

## Navigating around the Maze
The user can move North, South, East, West.

## The Map
At any point, the user has the option of Viewing their current map.
The map shows the rooms which have been discovered so far.
It also shows the user's current location, indicated by a star.

## The Secret Room
Right now, one room also connects to a Secret Room!
Their connection is hard-coded in (along with other room content details) but the details of the implementation are scalable/OO.
There can be more than one Secret Room, although presently there is only one.
However, you can only have one Secret Room connected to a given room!

Once you enter a room that connects to the secret room, the *chance* of being able to access to the Secret Room is one in four.

Navigating to and from the Secret Room is special. It is not a cardinal direction.

The secret room does not show up on the Map, even after it has been "found".
When you are inside of the Secret Room, your "current location" on the map
is the same as the location of the room that links to the Secret Room.

However, when you're in the Secret Room the Map does (now) display a **special** star symbol
to distinguish it from the *regular* star symbol when you are in a regular room.
