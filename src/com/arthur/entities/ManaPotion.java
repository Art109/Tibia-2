package com.arthur.entities;


public class ManaPotion extends coletaveis {


	
	public ManaPotion(int x, int y, int width, int height) {
		super(x, y, width, height);
		sprite = spritesheet.getSprite(0, 0, 16, 16);
		depth = 0;
	}

}
