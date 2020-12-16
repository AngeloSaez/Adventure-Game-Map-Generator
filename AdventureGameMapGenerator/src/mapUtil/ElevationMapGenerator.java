package mapUtil;

import java.awt.Point;
import java.util.ArrayList;

import engine.Level;

public class ElevationMapGenerator {
	/*
	 * Elevation Map
	 * 
	 * 
	 * Creates a 2D array of rooms laid out like a grid.
	 * This map gives information on whether the respective room falls into any of the climate/elevation
	 * categories listed in the below enum (ocean, beach, plains, mountain).
	 * 
	 * This has only a visual effect on the blocks within that room.
	 * 
	 * Here are the rules:
	 *  - OCEANS are spaces that have values that are 0 on the doorway map
	 *  - BEACHES are spaces adjacent to oceans
	 *  - PLAINS are spaces that are within 'HDIST_TO_MOUNTAINS' from an ocean
	 *  - MOUNTAINS are spaces that are not within 'HDIST_TO_MOUNTAINS' from an ocean
	 *  
	 *  'HDIST_TO_MOUNTAINS' is only used to check horizontal distance from a beach and not vertical.
	 *  
	 */
	
	
	public static enum ElevationType {
		NULL,
		OCEAN,
		BEACH,
		PLAINS,
		MOUNTAIN,
	}
	
	public static final int WORLD_HEIGHT = Level.WORLD_HEIGHT;
	public static final int WORLD_WIDTH = Level.WORLD_WIDTH;
	
	private static final int HDIST_FOR_MOUNTAINS = WORLD_WIDTH / 4 + 1; // Or just use 4 or something

	
	private static ElevationType[][] elevationMap = new ElevationType[WORLD_HEIGHT][WORLD_WIDTH];
	
	public static ElevationType[][] generateElevationMap(int[][] doorwayMap) {
		/*
		 * Generate Elevation Map
		 * 
		 * 
		 * Creates a 2D array that represents the terrain of all of the spaces on the map.
		 * Rules for classification are explained in the first comment.
		 * 
		 */
		
		// Initialize
		for (int y = 0; y < WORLD_HEIGHT; y++) {
			for (int x = 0; x < WORLD_WIDTH; x++) {
				elevationMap[y][x] = ElevationType.NULL;
			}
		}
		
		// Oceans
		ArrayList<Point> oceanList = new ArrayList<Point>();
		for (int y = 0; y < WORLD_HEIGHT; y++) {
			for (int x = 0; x < WORLD_WIDTH; x++) {
				if (doorwayMap[y][x] == 0) {
					elevationMap[y][x] = ElevationType.OCEAN;
					oceanList.add(new Point(x, y));
				}
			}
		}
		
		// Beach
		ArrayList<Point> beachList = new ArrayList<Point>();
		for (Point p : oceanList) {
			// North, east, south, west setting to a beach
			if (p.y > 0) {
				if (elevationMap[p.y - 1][p.x] == ElevationType.NULL) {
					elevationMap[p.y - 1][p.x] = ElevationType.BEACH;
					beachList.add(new Point(p.x, p.y - 1));
				}
			}
			if (p.x < WORLD_WIDTH - 1) {
				if (elevationMap[p.y][p.x + 1] == ElevationType.NULL) {
					elevationMap[p.y][p.x + 1] = ElevationType.BEACH;
					beachList.add(new Point(p.x + 1, p.y));
				}
			}
			if (p.y < WORLD_HEIGHT - 1) {
				if (elevationMap[p.y + 1][p.x] == ElevationType.NULL) {
					elevationMap[p.y + 1][p.x] = ElevationType.BEACH;
					beachList.add(new Point(p.x, p.y + 1));
				}
			}
			if (p.x > 0) {
				if (elevationMap[p.y][p.x - 1] == ElevationType.NULL) {
					elevationMap[p.y][p.x - 1] = ElevationType.BEACH;
					beachList.add(new Point(p.x - 1, p.y));
				}
			}
		}		
		
		// Plains
		for (Point p : beachList) {
			// Set spaces to the east and west into plains if within range
			for (int e = p.x; (e < WORLD_WIDTH && e - p.x < HDIST_FOR_MOUNTAINS); e++) {
				if (elevationMap[p.y][e] == ElevationType.NULL) {
					elevationMap[p.y][e] = ElevationType.PLAINS;
				}
			}
			for (int w = p.x; (w > 0 && p.x - w < HDIST_FOR_MOUNTAINS); w--) {
				if (elevationMap[p.y][w] == ElevationType.NULL) {
					elevationMap[p.y][w] = ElevationType.PLAINS;
				}
			}
		}
		
		// Mountains
		for (int y = 0; y < WORLD_HEIGHT; y++) {
			for (int x = 0; x < WORLD_WIDTH; x++) {
				if (elevationMap[y][x] == ElevationType.NULL) {
					elevationMap[y][x] = ElevationType.MOUNTAIN;
				}
			}
		}
		
		return elevationMap;
	}
	
	
	
	
	
}
