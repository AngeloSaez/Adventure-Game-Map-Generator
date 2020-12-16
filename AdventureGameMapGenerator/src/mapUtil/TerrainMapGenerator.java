package mapUtil;

import java.awt.Point;
import java.util.ArrayList;

import engine.Level;

public class TerrainMapGenerator {
	/*
	 * Terrain Map
	 * 
	 * 
	 *  
	 */
	
	public static enum TerrainType {
		SIMPLE,
		PARALLEL,
		STRAIGHT,
	}
	
	public static final int WORLD_HEIGHT = Level.WORLD_HEIGHT;
	public static final int WORLD_WIDTH = Level.WORLD_WIDTH;
	
	private static TerrainType[][] terrainMap = new TerrainType[WORLD_HEIGHT][WORLD_WIDTH];
	
	private static final int TERRAIN_SWITCH_DIST = WORLD_WIDTH / 4;
	
	public static TerrainType[][] generateTerrainMap(int[][] doorwayMap) {
		/*
		 * Generate Terrain Map
		 * 
		 * 
		 * Creates a 2D array that represents the terrain of all of the spaces on the map.
		 * Rules for classification are explained in the first comment.
		 * 
		 */
		
		// Choose an initial starting terrain
		TerrainType currentTerrain = getTypeFromInt((int) (Math.random() * 4));
		int terrainAssignmentCount = 0;
		for (int y = 0; y < WORLD_HEIGHT; y++) {
			for (int x = 0; x < WORLD_WIDTH; x++) {
				// Set terrain
				terrainMap[y][x] = currentTerrain;
				// Increment
				terrainAssignmentCount++;
				// Switch terrain
				if (terrainAssignmentCount == TERRAIN_SWITCH_DIST) {
					currentTerrain = getTypeFromInt((int) (Math.random() * 4));
					terrainAssignmentCount = 0;
				}
			}
		}
		return terrainMap;
	}
	
	private static TerrainType getTypeFromInt(int n) {
		switch (n) {
		case 0:
			return TerrainType.SIMPLE;
		case 1:
			return TerrainType.SIMPLE;
		case 2:
			return TerrainType.PARALLEL;
		case 3:
			return TerrainType.STRAIGHT;
		}
		return TerrainType.SIMPLE;
	}
	
	
	
}
