package com.arthur.world;



public class DungeonFloorTile extends Tile{
	

	public DungeonFloorTile(int x, int y) {
		super(x, y);
		sprite = World.tiles_spritesheet.getSprite(16, 0, 16, 16);
	}

}
