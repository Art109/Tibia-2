package com.arthur.world;

import java.awt.image.BufferedImage;

import com.arthur.entities.Entity;
import com.arthur.main.Game;

public class Stairs extends Entity{

	public static BufferedImage sprite = Game.spritesheet.getSprite(16, 32, 16, 16);	
	
	public Stairs(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		// TODO Auto-generated constructor stub
	}

	
}
