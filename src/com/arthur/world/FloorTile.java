package com.arthur.world;

import java.awt.image.BufferedImage;

import com.arthur.main.Game;

public class FloorTile extends Tile {

	public static BufferedImage sprite = Game.spritesheet.getSprite(0, 0, 16, 16);
	
	public FloorTile(int x, int y, BufferedImage sprite) {
		super(x, y, sprite);
		
	}

}
