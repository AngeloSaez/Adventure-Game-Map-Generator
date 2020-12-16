package engine;

import java.awt.Color;
import java.awt.Graphics2D;

import mapUtil.DoorwayMapGenerator;
import mapUtil.ElevationMapGenerator;
import mapUtil.ElevationMapGenerator.ElevationType;
import mapUtil.RoomGenerator;
import mapUtil.TerrainMapGenerator;
import mapUtil.TerrainMapGenerator.TerrainType;
import mapUtil.TileMapGenerator;
import util.Style;

public class Level extends GameState {

	// Block resolution
	int tileRes = 6; // 6
	
	// World constants
	public final static int WORLD_WIDTH = 16; // 16
	public final static int WORLD_HEIGHT = 8; // 8
	public final static int ROOM_WIDTH = 17;
	public final static int ROOM_HEIGHT = 11;
	public final static int TOTAL_WIDTH = WORLD_WIDTH * ROOM_WIDTH;
	public final static int TOTAL_HEIGHT = WORLD_HEIGHT * ROOM_HEIGHT;
	
	// Game maps
	private int[][] doorwayMap;
	private ElevationType[][] elevationMap;
	private TerrainType[][] terrainMap;
	private int[][] tileMap;
	
	public Level(Main main) {
		super(main);
		doorwayMap = DoorwayMapGenerator.generateDoorwayMap();
		elevationMap = ElevationMapGenerator.generateElevationMap(doorwayMap);
		terrainMap = TerrainMapGenerator.generateTerrainMap(doorwayMap);
		tileMap = TileMapGenerator.generateTileMap(doorwayMap, elevationMap, terrainMap);
	}

	public void render(Graphics2D g) {
		renderTileMap(g);

		// renderRoomOutlines(g);

	}

	private void renderTileMap(Graphics2D g) {
		Color blockColor = Color.gray;
		Color noBlockColor = Color.darkGray;
		for (int ry = 0; ry < WORLD_HEIGHT; ry++) {
			for (int rx = 0; rx < WORLD_WIDTH; rx++) {
				// Set the color based off of the elevation
				ElevationType elevationValue = elevationMap[ry][rx];
				if (elevationValue == ElevationMapGenerator.ElevationType.OCEAN) {
					blockColor = Style.BLUE;
					noBlockColor = Style.BLUE;
				}
				else if (elevationValue == ElevationMapGenerator.ElevationType.BEACH) {
					blockColor = Style.BROWN;
					noBlockColor = Style.TAN;
				}
				else if (elevationValue == ElevationMapGenerator.ElevationType.PLAINS) {
					blockColor = Style.GREEN;
					noBlockColor = Style.TAN;
				}
				else if (elevationValue == ElevationMapGenerator.ElevationType.MOUNTAIN) {
					blockColor = Style.BROWN;
					noBlockColor = Style.TAN;
				}
				
				// Goes through tilemap but keeps track of the room
				for (int ty = 0; ty < ROOM_HEIGHT; ty++) {
					for (int tx = 0; tx < ROOM_WIDTH; tx++) {
						int roomOffsetY = ry * ROOM_HEIGHT;
						int roomOffsetX = rx * ROOM_WIDTH;
						
						if (tileMap[ty + roomOffsetY][tx + roomOffsetX] == 0) {
							g.setColor(noBlockColor);
						} else {
							g.setColor(blockColor);
						}
						g.fillRect((roomOffsetX + tx) * tileRes, (roomOffsetY + ty) * tileRes, tileRes, tileRes);

					}
				}

			}
		}
	}

	private void renderTile(Graphics2D g, int x, int y) {
		g.fillRect(x * tileRes, y * tileRes, tileRes, tileRes);
	}

	private void renderRoomOutlines(Graphics2D g) {
		g.setColor(Color.WHITE);
		for (int y = 0; y < WORLD_HEIGHT; y++) {
			for (int x = 0; x < WORLD_WIDTH; x++) {
				g.drawRect(x * tileRes * ROOM_WIDTH, y * tileRes * ROOM_HEIGHT, tileRes * ROOM_WIDTH,
						tileRes * ROOM_HEIGHT);
				g.drawString("D: " + doorwayMap[y][x], x * tileRes * ROOM_WIDTH, y * tileRes * ROOM_HEIGHT + tileRes * 1);
				g.drawString("E: " + elevationMap[y][x], x * tileRes * ROOM_WIDTH, y * tileRes * ROOM_HEIGHT + tileRes * 2);
				g.drawString("T: " + terrainMap[y][x], x * tileRes * ROOM_WIDTH, y * tileRes * ROOM_HEIGHT + tileRes * 3);
			}
		}
	}

}
