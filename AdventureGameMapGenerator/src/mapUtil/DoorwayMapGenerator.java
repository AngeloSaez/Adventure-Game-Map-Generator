package mapUtil;

import java.awt.Point;
import java.util.ArrayList;

import engine.Level;
import util.RoomData;

public class DoorwayMapGenerator {
	/*
	 * Doorway Map
	 * 
	 * 
	 * Creates a 2D array of rooms laid out like a grid. Rooms can only connect to one another
	 * through the use of doorway-like openings that can only be on 1 of 4 sides of a room.
	 * Because of this, a room can be classified only with an integer from 0 to 2^4 and that integer
	 * will describe where the doorways are on the room.
	 * 
	 * Here are the rules:
	 *  - North opening -> +1
	 *  - East opening -> +2
	 *  - South opening -> +4
	 *  - West opening -> +8
	 * 
	 * Example:
	 * A top-down room with openings on the right and left, forming a hallway, will
	 * have a doorway classification value of 10 because:
	 *  - It has an East opening ( +2 )
	 *  - It has an West opening ( +8 )
	 * 
	 */

	public static final int WORLD_WIDTH = Level.WORLD_WIDTH;
	public static final int WORLD_HEIGHT = Level.WORLD_HEIGHT;
	
	public static final double LAND_TO_WATER_RATIO = 0.8;

	private static int[][] doorwayMap = new int[WORLD_HEIGHT][WORLD_WIDTH];

	public static int[][] generateDoorwayMap() {
		/*
		 * Generate Doorway Map
		 * 
		 * 
		 * One by one rooms are added to the 2D array. A room is randomly placed somwhere a then
		 * it is chosen to start the generation. 
		 * 
		 * When a room is chosen the area around it is checked for whether or not it is empty. 
		 * If it is empty, it's information (RoomData) will be recorded in a list 'availableSpaces'.
		 * 
		 * Lastly a room is chosen from the 'availableSpaces' by random and the doorway classification
		 * values (defined above) of each the new chosen room and the currently chosen room are both
		 * added to the doorwayMap.
		 * 
		 * The 'LAND_TO_WATER_RATIO' is used to calculate the limit of rooms to create.
		 * 
		 */

		// Starting room setup and room list initializaion
		int n = (int) (Math.random() * WORLD_HEIGHT * WORLD_WIDTH);
		Point room = new Point(n / WORLD_HEIGHT, n % WORLD_HEIGHT);
		ArrayList<Point> addedRooms = new ArrayList<Point>();

		// Set a limit to how many rooms to create
		int roomLimit = (int) (WORLD_HEIGHT * WORLD_WIDTH * LAND_TO_WATER_RATIO);

		// Fill the map with rooms until the limit is reached
		boolean roomLimitReached = false;
		while (!roomLimitReached) {
			// Chooses one of the previously added rooms to start from
			if (addedRooms.size() > 0) {
				int randomIndex = (int) (Math.random() * addedRooms.size());
				room = addedRooms.get(randomIndex);
			}
			
			// Compiles a list of all available nearby rooms
			ArrayList<RoomData> availableSpaces = new ArrayList<RoomData>();
			
			// Check right, left, down, up respectively
			if (room.x + 1 < WORLD_WIDTH) {
				if (doorwayMap[room.y][room.x + 1] == 0) {
					availableSpaces.add(new RoomData(room.y, room.x + 1, "right"));
				}
			}
			if (room.x > 0) {
				if (doorwayMap[room.y][room.x - 1] == 0) {
					availableSpaces.add(new RoomData(room.y, room.x - 1, "left"));
				}
			}
			if (room.y + 1 < WORLD_HEIGHT) {
				if (doorwayMap[room.y + 1][room.x] == 0) {
					availableSpaces.add(new RoomData(room.y + 1, room.x, "down"));
				}
			}
			if (room.y > 0) {
				if (doorwayMap[room.y - 1][room.x] == 0) {
					availableSpaces.add(new RoomData(room.y - 1, room.x, "up"));
				}
			}
			
			// Pick a room from a potential spot
			if (availableSpaces.size() >= 1) {
				RoomData chosenSpace = availableSpaces.get((int) (Math.random() * availableSpaces.size()));
				
				// Update room coordinate to the new room
				room.y = chosenSpace.ry;
				room.x = chosenSpace.rx;
				
				// Add the new room to the list
				addedRooms.add(new Point(room.x, room.y));
				
				// Incremennt doorway map
				if (chosenSpace.directionFromPrevious == "right") {
					// Next room
					doorwayMap[chosenSpace.ry][chosenSpace.rx] += 8;
					// Previous room
					doorwayMap[chosenSpace.ry][chosenSpace.rx - 1] += 2;

				}
				if (chosenSpace.directionFromPrevious == "left") {
					// Next room
					doorwayMap[chosenSpace.ry][chosenSpace.rx] += 2;
					// Previous room
					doorwayMap[chosenSpace.ry][chosenSpace.rx + 1] += 8;
				}
				if (chosenSpace.directionFromPrevious == "down") {
					// Next room
					doorwayMap[chosenSpace.ry][chosenSpace.rx] += 1;
					// Previous room
					doorwayMap[chosenSpace.ry - 1][chosenSpace.rx] += 4;

				}
				if (chosenSpace.directionFromPrevious == "up") {
					// Next room
					doorwayMap[chosenSpace.ry][chosenSpace.rx] += 4;
					// Previous
					doorwayMap[chosenSpace.ry + 1][chosenSpace.rx] += 1;
				}
			}

			// Evaluate if limit has been reached
			if (addedRooms.size() >= roomLimit)
				roomLimitReached = true;
		}
		return doorwayMap;
	}

}
