package com.arthur.world;

import java.awt.image.BufferedImage;

import com.arthur.main.Game;

public class DungeonWallTile extends Wall_Tile {

	public static BufferedImage sprite = Game.spritesheet.getSprite(16, 48, 16, 16); 
	
	public DungeonWallTile(int x, int y, BufferedImage sprite) {
		super(x, y, sprite);

	}

}
