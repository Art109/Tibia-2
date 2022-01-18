package com.arthur.world;




public class DungeonWallTile extends Wall_Tile {

	
	public DungeonWallTile(int x, int y) {
		super(x, y);
		sprite = World.tiles_spritesheet.getSprite(16, 16, 16, 16);

	}

}
