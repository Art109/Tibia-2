package com.arthur.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.arthur.graficos.Spritesheet;

public class Tile {
	
	
	
	protected BufferedImage sprite;
	private int x,y;
	
	public Tile(int x, int y) {
		
		
		this.x = x;
		this.y = y;
	}
	public void render(Graphics g) {
		g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
	}
}