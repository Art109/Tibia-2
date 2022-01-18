package com.arthur.entities;


public class espadaSimples extends Weapon {
	 

	public espadaSimples(int x, int y, int width, int height) {
		super(x, y, width, height);
		sprite = spritesheet.getSprite(32, 0, 16, 16);
		
	}

}
