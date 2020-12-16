package mapUtil;

import engine.Level;
import mapUtil.ElevationMapGenerator.ElevationType;
import mapUtil.TerrainMapGenerator.TerrainType;

public class TileMapGenerator {

	// World constants
	public final static int WORLD_WIDTH = Level.WORLD_WIDTH;
	public final static int WORLD_HEIGHT = Level.WORLD_HEIGHT;
	public final static int ROOM_WIDTH = Level.ROOM_WIDTH;
	public final static int ROOM_HEIGHT = Level.ROOM_HEIGHT;
	public final static int TOTAL_WIDTH = Level.TOTAL_WIDTH;
	public final static int TOTAL_HEIGHT = Level.TOTAL_HEIGHT;
	
	private static int[][] tileMap = new int[TOTAL_HEIGHT][TOTAL_WIDTH];
	
	public static int[][] generateTileMap(int[][] doorwayMap, ElevationType[][] elevationMap, TerrainType[][] terrainMap) {
		
		for (int ry = 0; ry < WORLD_HEIGHT; ry++) {
			for (int rx = 0; rx < WORLD_WIDTH; rx++) {
				
				// Check Elevation
				if (elevationMap[ry][rx] != ElevationType.NULL) {
					int doorwayValue = doorwayMap[ry][rx];
					ElevationType elevationValue = elevationMap[ry][rx];
					TerrainType terrainValue = terrainMap[ry][rx];
					
					int[][] room = RoomGenerator.generateRoom(doorwayValue, elevationValue, terrainValue);
					
					for (int ty = 0; ty < ROOM_HEIGHT; ty++) {
						for (int tx = 0; tx < ROOM_WIDTH; tx++) {
							int roomOffsetY = ry * ROOM_HEIGHT;
							int roomOffsetX = rx * ROOM_WIDTH;
							tileMap[ty + roomOffsetY][tx + roomOffsetX] = room[ty][tx];
						}
					}
					
				}
			}
		}
		
		
		return tileMap;
	}
	
	
	
}
