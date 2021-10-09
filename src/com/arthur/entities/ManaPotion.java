package com.arthur.entities;

import java.awt.image.BufferedImage;

import com.arthur.main.Game;

public class ManaPotion extends Entity {

	public static BufferedImage sprite = Game.spritesheet.getSprite(16, 16, 16, 16);
	
	public ManaPotion(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		// TODO Auto-generated constructor stub
	}

}
