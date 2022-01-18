package com.arthur.world;



public class FloorTile extends Tile {

	
	
	public FloorTile(int x, int y) {
		super(x, y);
		sprite = World.tiles_spritesheet.getSprite(0, 0, 16, 16);
	}

}
