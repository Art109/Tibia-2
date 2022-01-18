package com.arthur.entities;


import java.awt.image.BufferedImage;

import com.arthur.main.Game;


public class WeaponAttack extends Entity  {
	
	private int dx, dy;
	private int frames = 0 , maxFrames = 1 ;

	public WeaponAttack(int x, int y, int width, int height, BufferedImage sprite, int dx,int dy) {
		super(x, y, width, height);
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


	
	
	
}
