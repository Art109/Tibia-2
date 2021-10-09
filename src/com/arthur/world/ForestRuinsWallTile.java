package com.arthur.world;

import java.awt.image.BufferedImage;

import com.arthur.main.Game;

public class ForestRuinsWallTile extends Wall_Tile {
	
	public ForestRuinsWallTile(int x, int y, BufferedImage sprite) {
		super(x, y, sprite);
		
	}

	public static BufferedImage sprite = Game.spritesheet.getSprite(16, 0, 16, 16);
}
