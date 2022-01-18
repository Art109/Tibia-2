package com.arthur.entities;


public class LifePotion extends coletaveis {
	

	public LifePotion(int x, int y, int width, int height) {
		super(x, y, width, height);
		sprite = spritesheet.getSprite(16, 0, 16, 16);
		depth = 0;
	}

}
