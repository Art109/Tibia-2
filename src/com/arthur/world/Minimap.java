package com.arthur.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import com.arthur.main.Game;

public class Minimap {
	public static BufferedImage minimap;
	public static int[] minimapPixels;
	
	public Minimap() {
		minimap = new BufferedImage(World.WIDTH,World.HEIGHT,BufferedImage.TYPE_INT_RGB);
		minimapPixels = ((DataBufferInt)minimap.getRaster().getDataBuffer()).getData();
	}
	
	public void renderMinimap(Graphics g) {
		for(int i = 0 ; i < minimapPixels.length; i++) {
			minimapPixels[i] = 0;
		}
		for(int xx = 0 ; xx < World.WIDTH; xx++) {
			for(int yy = 0 ; yy < World.HEIGHT; yy++) {
				if(World.tiles[xx + (yy * World.WIDTH)] instanceof Wall_Tile) {
					minimapPixels[xx + (yy * World.WIDTH)] = 0x606060;
				}
				if(World.tiles[xx + (yy * World.WIDTH)] instanceof FloorTile) {
					minimapPixels[xx + (yy * World.WIDTH)] = 0x4CFF00;
				}
				if(World.tiles[xx + (yy * World.WIDTH)] instanceof DungeonFloorTile) {
					minimapPixels[xx + (yy * World.WIDTH)] = 0x808080;
				}
			}
		}
		int xPlayer = Game.player.getX()/16;
		int yPlayer = Game.player.getY()/16;
		minimapPixels[xPlayer + (yPlayer * World.WIDTH)] = 0x0000ff;
		
		g.drawImage(minimap,300,350,World.WIDTH*5,World.HEIGHT*5,null);
	}
}
