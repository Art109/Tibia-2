package com.arthur.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.arthur.main.Game;
import com.arthur.world.Camera;

public class WeaponAttack extends Entity  {
	
	private int dx, dy;
	private int frames = 0 , maxFrames = 1 ;

	public WeaponAttack(int x, int y, int width, int height, BufferedImage sprite, int dx,int dy) {
		super(x, y, width, height, sprite);
		this.dx = dx;
		this.dy = dy;
	}

	public void tick() {
		frames++;
		if(frames == maxFrames) {
			frames = 0;
			Game.entities.remove(this);
			return;
		}
	}

	public void render(Graphics g) {

	}	
	
	
	
}
