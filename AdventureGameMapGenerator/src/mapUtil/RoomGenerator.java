package mapUtil;

import engine.Level;
import mapUtil.ElevationMapGenerator.ElevationType;
import mapUtil.TerrainMapGenerator.TerrainType;

public class RoomGenerator {
	
	public final static int ROOM_WIDTH = Level.ROOM_WIDTH;
	public final static int ROOM_HEIGHT = Level.ROOM_HEIGHT;
	
	public final static int DOORWAY_RADIUS = 1;
	
	private static boolean WIDTH_EVEN = ROOM_WIDTH % 2 == 0;
	private static boolean HEIGHT_EVEN = ROOM_HEIGHT % 2 == 0;
	private static int V_MIDPOINT = ROOM_HEIGHT / 2;
	private static int H_MIDPOINT = ROOM_WIDTH / 2;
	
	private static int[][] roomMap = new int[ROOM_HEIGHT][ROOM_WIDTH];
	
	public static int[][] generateRoom(int doorwayValue, ElevationType elevationValue, TerrainType terrainValue) {
		
		// Set entirety to walls
		for (int ty = 0; ty < ROOM_HEIGHT; ty++) {
			for (int tx = 0; tx < ROOM_WIDTH; tx++) {
				roomMap[ty][tx] = 1;
			}
		}
		
		// Generate based off of the elevation
		switch (terrainValue) {
		case SIMPLE:
			generateSimpleRoom(doorwayValue);
			break;
		case PARALLEL:
			generateSimpleRoom(doorwayValue);
			generateParallelRoom(doorwayValue);
			break;
		case STRAIGHT:
			generateStraightRoom(doorwayValue);
			break;
		}

		// Return result
		return roomMap;
	}
	
	private static void generateSimpleRoom(int doorwayValue) {
		// Empty out the center
		for (int ty = 0; ty < ROOM_HEIGHT; ty++) {
			for (int tx = 0; tx < ROOM_WIDTH; tx++) {
				if (!(ty == 0 || ty == ROOM_HEIGHT - 1 || tx == 0 || tx == ROOM_WIDTH - 1)) {
					roomMap[ty][tx] = 0;
				}
			}
		}

		// Doorway carving
		boolean[] doorwayValues = doorwayValueToArray(doorwayValue);
		
		if (doorwayValues[0]) {
			if (HEIGHT_EVEN) {
				for (int i = 0; i < DOORWAY_RADIUS; i++) {
					roomMap[V_MIDPOINT - i][0] = 0;
					roomMap[V_MIDPOINT + i + 1][0] = 0;
				}
			} else {
				roomMap[V_MIDPOINT][0] = 0;
				for (int i = 0; i < DOORWAY_RADIUS; i++) {
					roomMap[V_MIDPOINT - i - 1][0] = 0;
					roomMap[V_MIDPOINT + i + 1][0] = 0;
				}
			}
		}

		if (doorwayValues[1]) {
			if (WIDTH_EVEN) {
				for (int i = 0; i < DOORWAY_RADIUS; i++) {
					roomMap[ROOM_HEIGHT - 1][H_MIDPOINT + i] = 0;
					roomMap[ROOM_HEIGHT - 1][H_MIDPOINT - i - 1] = 0;
				}
			} else {
				roomMap[ROOM_HEIGHT - 1][H_MIDPOINT] = 0;
				for (int i = 0; i < DOORWAY_RADIUS; i++) {
					roomMap[ROOM_HEIGHT - 1][H_MIDPOINT - i - 1] = 0;
					roomMap[ROOM_HEIGHT - 1][H_MIDPOINT + i + 1] = 0;
				}
			}
		}

		if (doorwayValues[2]) {
			if (HEIGHT_EVEN) {
				for (int i = 0; i < DOORWAY_RADIUS; i++) {
					roomMap[V_MIDPOINT - i][ROOM_WIDTH - 1] = 0;
					roomMap[V_MIDPOINT + i + 1][ROOM_WIDTH - 1] = 0;
				}
			} else {
				roomMap[V_MIDPOINT][ROOM_WIDTH - 1] = 0;
				for (int i = 0; i < DOORWAY_RADIUS; i++) {
					roomMap[V_MIDPOINT - i - 1][ROOM_WIDTH - 1] = 0;
					roomMap[V_MIDPOINT + i + 1][ROOM_WIDTH - 1] = 0;
				}
			}
		}

		if (doorwayValues[3]) {
			if (WIDTH_EVEN) {
				for (int i = 0; i < DOORWAY_RADIUS; i++) {
					roomMap[0][H_MIDPOINT + i] = 0;
					roomMap[0][H_MIDPOINT - i - 1] = 0;
				}
			} else {
				roomMap[0][H_MIDPOINT] = 0;
				for (int i = 0; i < DOORWAY_RADIUS; i++) {
					roomMap[0][H_MIDPOINT - i - 1] = 0;
					roomMap[0][H_MIDPOINT + i + 1] = 0;
				}
			}
		}

	}
	
	private static void generateParallelRoom(int doorwayValue) {
		for (int y = 2; y < ROOM_HEIGHT - 2; y++) {
			for (int x = 2; x < ROOM_WIDTH - 2; x++) {
				roomMap[y][x] = 1;
			}
		}
	}
	
	private static void generateStraightRoom(int doorwayValue) {
		boolean[] doorwayValues = doorwayValueToArray(doorwayValue);
		
		// Carve a plus sign into the room
		for (int x = 0; x < ROOM_WIDTH; x++) {
			if (HEIGHT_EVEN) {
				for (int i = 0; i < DOORWAY_RADIUS; i++) {
					roomMap[V_MIDPOINT - i][x] = 0;
					roomMap[V_MIDPOINT + i + 1][x] = 0;
				}
			} else {
				for (int i = 0; i < DOORWAY_RADIUS; i++) {
					roomMap[V_MIDPOINT][x] = 0;
					roomMap[V_MIDPOINT - i - 1][x] = 0;
					roomMap[V_MIDPOINT + i + 1][x] = 0;
				}
			}
		}
		
		
		for (int y = 0; y < ROOM_HEIGHT; y++) {
			if (WIDTH_EVEN) {
				for (int i = 0; i < DOORWAY_RADIUS; i++) {
					roomMap[y][H_MIDPOINT - i - 1] = 0;
					roomMap[y][H_MIDPOINT + i] = 0;
				}
			} else {
				for (int i = 0; i < DOORWAY_RADIUS; i++) {
					roomMap[y][H_MIDPOINT] = 0;
					roomMap[y][H_MIDPOINT - i - 1] = 0;
					roomMap[y][H_MIDPOINT + i + 1] = 0;
				}
			}
		}
		
		// Cover irrelevant parts of the plus
		if (!doorwayValues[0]) {
			for (int y = 0; y < ROOM_HEIGHT; y++) {
				for (int x = 0; x < ROOM_WIDTH; x++) {
					if (WIDTH_EVEN) {
						if (x < H_MIDPOINT - DOORWAY_RADIUS) roomMap[y][x] = 1;
					} else {
						if (x < H_MIDPOINT - DOORWAY_RADIUS) roomMap[y][x] = 1;
					}
				}
			}
		}

		if (!doorwayValues[1]) {
			for (int y = 0; y < ROOM_HEIGHT; y++) {
				for (int x = 0; x < ROOM_WIDTH; x++) {
					if (HEIGHT_EVEN) {
						if (y > V_MIDPOINT + DOORWAY_RADIUS + 1) roomMap[y][x] = 1;
					} else {
						if (y > V_MIDPOINT + DOORWAY_RADIUS) roomMap[y][x] = 1;
					}
				}
			}
		}

		if (!doorwayValues[2]) {
			for (int y = 0; y < ROOM_HEIGHT; y++) {
				for (int x = 0; x < ROOM_WIDTH; x++) {
					if (WIDTH_EVEN) {
						if (x > H_MIDPOINT + DOORWAY_RADIUS - 1) roomMap[y][x] = 1;
					} else {
						if (x > H_MIDPOINT + DOORWAY_RADIUS) roomMap[y][x] = 1;
					}
				}
			}
		}

		if (!doorwayValues[3]) {
			for (int y = 0; y < ROOM_HEIGHT; y++) {
				for (int x = 0; x < ROOM_WIDTH; x++) {
					if (HEIGHT_EVEN) {
						if (y < V_MIDPOINT - DOORWAY_RADIUS) roomMap[y][x] = 1;
					} else {
						if (y < V_MIDPOINT - DOORWAY_RADIUS) roomMap[y][x] = 1;
					}
				}
			}
		}
		
	}
	
	private static boolean[] doorwayValueToArray(int doorwayValue) {
		boolean[] result = { false, false, false, false };
		int temp = doorwayValue;
		
		result[0] = (temp >= 8) ? true : false;
		if (result[0]) temp %= 8;
		result[1] = (temp >= 4) ? true : false;
		if (result[1]) temp %= 4;
		result[2] = (temp >= 2) ? true : false;
		if (result[2]) temp %= 2;
		result[3] = (temp >= 1) ? true : false;
		
		return result;
		
	}

}
