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
import com.arthur.graficos.Spritesheet;
import com.arthur.main.Game;

public class World {
	
	private static Tile[] tiles;
	public static int WIDTH,HEIGHT;
	public static final int Tile_Size =16 ;
	
	public World(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			tiles = new Tile[map.getHeight() * map.getWidth()];
			map.getRGB(0,0,map.getWidth(),map.getHeight(),pixels,0,map.getWidth());
			for(int xx = 0; xx < map.getWidth(); xx++) {
				for(int yy = 0; yy < map.getHeight();yy++) {
					int pixelAtual = pixels[xx +(yy * map.getWidth())];
					tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16,FloorTile.sprite );
					switch(pixelAtual) {
						case 0xFFFFFFFF:
							//parede
							tiles[xx + (yy * WIDTH)] = new Wall_Tile(xx * 16, yy * 16,Wall_Tile.sprite );
							break;
						case 0xFF4800FF:
						     //player
							Game.player.setX(xx * 16);
							Game.player.setY(yy * 16);
							break;
						case 0xFF4CFF00:
							//Life,.POTION
							LifePotion lifePotion = new LifePotion(xx * 16,yy *16,16,16,Entity.LIFEPOTION_EN);
							Game.entities.add(lifePotion);
							break;
						
						case 0xFF808080:
							//Sword
							Weapon sword = new Weapon(xx * 16,yy *16,16,16,Entity.SWORD_EN);
							Game.entities.add(sword);;
							break;
						case 0xFF00FFFF:
							//Mana Potion
							ManaPotion manaPotion = new ManaPotion(xx * 16,yy *16,16,16,Entity.MANAPOTION_EN);
							Game.entities.add(manaPotion);
							
						case 0xFFFF0000:
							//GOBLIN
							Enemy goblin = new Enemy(xx * 16,yy *16,16,16,Entity.GOBLIN_EN);
							 Game.entities.add(goblin);
							break;
						case 0xFF3F3F3F:
							//escada
							Stairs escada = new Stairs(xx * 16, yy * 16,16, 16, Stairs.sprite );
							Game.entities.add(escada);
							break;
						default:	
							break;
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
	
	public static void restartGame(String map) {
		Game.entities.clear();
		Game.enemies.clear();
		Game.entities = new ArrayList<Entity>();
		Game.enemies = new ArrayList<Enemy>();
		Game.spritesheet = new Spritesheet("/spritesheet.png");
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
	}
}
