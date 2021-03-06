package com.arthur.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.arthur.entities.Enemy;
import com.arthur.entities.Entity;
import com.arthur.entities.LifePotion;
import com.arthur.entities.ManaPotion;
import com.arthur.entities.Weapon;
import com.arthur.entities.espadaSimples;
import com.arthur.graficos.Spritesheet;
import com.arthur.main.Game;
import com.arthur.world.light.Light;

public class World {
	
	public static Tile[] tiles;
	public static int WIDTH,HEIGHT;
	public static final int Tile_Size =16 ;
	public Light light;
	public static Spritesheet tiles_spritesheet;
	
	public World(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			light = new Light();
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			tiles_spritesheet = new Spritesheet("/tiles_spritesheet.png");
			tiles = new Tile[map.getHeight() * map.getWidth()];
			map.getRGB(0,0,map.getWidth(),map.getHeight(),pixels,0,map.getWidth());
			for(int xx = 0; xx < map.getWidth(); xx++) {
				for(int yy = 0; yy < map.getHeight();yy++) {
					int pixelAtual = pixels[xx +(yy * map.getWidth())];
					if(Game.map == "forest_ruins") {
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16);
					}
					else if(Game.map == "dungeon") {
						tiles[xx + (yy * WIDTH)] = new DungeonFloorTile(xx * 16, yy * 16);
					}
					if(pixelAtual == 0xFFFFFFFF ) {
						//parede
						if(Game.map == "forest_ruins") {
							tiles[xx + (yy * WIDTH)] = new ForestRuinsWallTile(xx * 16, yy * 16);
						}
						else if(Game.map == "dungeon") {
							tiles[xx + (yy * WIDTH)] = new DungeonWallTile(xx * 16, yy * 16);
						}
					}
					else if(pixelAtual == 0xFF4800FF ) {
						//player
						Game.player.setX(xx * 16);
						Game.player.setY(yy * 16);
					}
					else if(pixelAtual == 0xFF4CFF00) {
						LifePotion lifePotion = new LifePotion(xx * 16,yy *16,16,16);
						Game.entities.add(lifePotion);
					}
					else if(pixelAtual == 0xFF00FFFF) {
						//Mana Potion
						ManaPotion manaPotion = new ManaPotion(xx * 16,yy *16,16,16);
						Game.entities.add(manaPotion);
					}
					else if(pixelAtual == 0xFF808080) {
						//Sword
						espadaSimples sword = new espadaSimples(xx * 16,yy *16,16,16);
						Game.entities.add(sword);
					}
					else if(pixelAtual == 0xFF3F3F3F ) {
						//escada
						tiles[xx + (yy * WIDTH)] = new Stairs(xx * 16, yy * 16);
					}
					else if(pixelAtual == 0xFFFF0000) {
						//GOBLIN
						Enemy goblin = new Enemy(xx * 16,yy *16,16,16);
						Game.entities.add(goblin);
					}
					else if(pixelAtual == 0xFF383F38) {
						//parede da dungeon
						tiles[xx + (yy * WIDTH)] = new DungeonWallTile(xx * 16, yy * 16);
					}
				}
			}
			
			
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static boolean isFree(int xnext,int ynext) {
		int x1 = xnext / Tile_Size;
		int y1= ynext/ Tile_Size;
		
		int x2 = (xnext + Tile_Size - 1) / Tile_Size;
		int y2= ynext/ Tile_Size;
		
		int x3 = xnext/ Tile_Size;
		int y3= (ynext + Tile_Size - 1) / Tile_Size;
		
		int x4 = (xnext + Tile_Size - 1) / Tile_Size;
		int y4= (ynext + Tile_Size - 1)/ Tile_Size;
		
		return !(tiles[x1 + (y1 * World.WIDTH)] instanceof Wall_Tile ||
				tiles[x2 + (y2 * World.WIDTH)] instanceof Wall_Tile ||
				tiles[x3 + (y3 * World.WIDTH)] instanceof Wall_Tile ||
				tiles[x4 + (y4 * World.WIDTH)] instanceof Wall_Tile);
	}
	
	public static boolean isStair(int xnext,int ynext) {
		int x1 = xnext / Tile_Size;
		int y1= ynext/ Tile_Size;
		
		int x2 = (xnext + Tile_Size - 1) / Tile_Size;
		int y2= ynext/ Tile_Size;
		
		int x3 = xnext/ Tile_Size;
		int y3= (ynext + Tile_Size - 1) / Tile_Size;
		
		int x4 = (xnext + Tile_Size - 1) / Tile_Size;
		int y4= (ynext + Tile_Size - 1)/ Tile_Size;
		
		return (tiles[x1 + (y1 * World.WIDTH)] instanceof Stairs ||
				tiles[x2 + (y2 * World.WIDTH)] instanceof Stairs ||
				tiles[x3 + (y3 * World.WIDTH)] instanceof Stairs ||
				tiles[x4 + (y4 * World.WIDTH)] instanceof Stairs);
	}
	
	public static void restartGame(String map) {
		Game.entities.clear();
		Game.enemies.clear();
		Game.entities = new ArrayList<Entity>();
		Game.enemies = new ArrayList<Enemy>();
		Game.world = new World("/" + map +".png");
		return;
	}
	
	public void render(Graphics g) {
		int xstart = Camera.x >> 4 ;
		int ystart = Camera.y >> 4  ;
		
		int xfinal = xstart + (Game.WIDTH >> 4 );
		int yfinal = ystart + (Game.HEIGHT >> 4 );
		
		for (int xx = xstart ; xx <= xfinal; xx++) {
			for(int yy = ystart ; yy <= yfinal ; yy++) {
				if(xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT) {
					continue;
				}
				Tile tile = tiles[xx + (yy * WIDTH)];
				tile.render(g);
			}
		}
		
		//light.applyLight();

	}
}
