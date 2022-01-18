package com.arthur.world;





public class Stairs extends Tile{

	
	public Stairs(int x, int y) {
		super(x, y);


		sprite = World.tiles_spritesheet.getSprite(0, 32, 16, 16);
	}

	
}
