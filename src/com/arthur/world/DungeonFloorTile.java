package com.arthur.world;

import java.awt.image.BufferedImage;

import com.arthur.main.Game;

public class DungeonFloorTile extends Tile{
	
	public static BufferedImage sprite = Game.spritesheet.getSprite(0, 48, 16, 16);

	public DungeonFloorTile(int x, int y, BufferedImage sprite) {
		super(x, y, sprite);
		// TODO Auto-generated constructor stub
	}

}
