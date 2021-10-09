package com.arthur.world.light;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.arthur.main.Game;
import com.arthur.world.Camera;
import com.arthur.world.Tile;

public class Light {
	
	public BufferedImage lightMap;
	public int[] lightMapPixels;
	
	public Light() {
		
		try {
			lightMap =ImageIO.read(getClass().getResource("/" + Game.map +"_light.png"));
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		lightMapPixels = new int[lightMap.getWidth() * lightMap.getHeight()];
		
		lightMap.getRGB(0, 0, lightMap.getWidth(), lightMap.getHeight(), lightMapPixels, 0 , lightMap.getWidth());
	}
	
	
	public  void applyLight() {
		for(int xx = 0; xx < Game.WIDTH ; xx++) {
			for(int yy = 0 ; yy < Game.HEIGHT ; yy++ ) {
				if(lightMapPixels[xx + (yy * Game.WIDTH)] == 0xffffffff) {
					Game.pixels[xx + (yy * Game.WIDTH) ] = 0 ;
				}
			
			}
		}
	}
}
