package com.arthur.entities;


import com.arthur.graficos.Spritesheet;

public class coletaveis extends Entity{
	
	protected static Spritesheet spritesheet;
	
	public coletaveis(int x, int y, int width, int height) {
		super(x, y, width, height);
		spritesheet = new Spritesheet("/coletaveis_spritesheet.png");
	}

}
