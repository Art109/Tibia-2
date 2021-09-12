package com.arthur.world;

import java.awt.image.BufferedImage;

import com.arthur.main.Game;

public class Wall_Tile extends Tile {
	
	public static BufferedImage sprite = Game.spritesheet.getSprite(16, 0, 16, 16);
	
	public Wall_Tile(int x, int y, BufferedImage sprite) {
		super(x, y, sprite);

	}

}
