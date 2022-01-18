package com.arthur.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.List;

import com.arthur.graficos.Spritesheet;
import com.arthur.main.Game;
import com.arthur.world.Camera;
import com.arthur.world.Node;
import com.arthur.world.Vector2i;

public class Entity {
	
	

	protected double x;
	protected double y;
	protected int width;
	protected int height;
	protected int speed;
	public int depth;
	
	public int maskx= 8,masky = 8, mwidth = 10 , mheight = 10;
	protected List<Node> path;
	
	protected BufferedImage sprite;
	
	
	public Entity(int x , int y, int width , int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		this.maskx = 0;
		this.masky = 0;
		this.mwidth = width;
		this.mheight = height;
	}
	
	public static Comparator<Entity> nodeSorter = new Comparator<Entity>() {
		
		@Override
		
		public int compare(Entity n0,Entity n1) {
			if(n1.depth < n0.depth) {
				return - 1;
			}
			if(n1.depth > n0.depth) {
				return + 1;
			}
			return 0;
		}
	};
	
	public void setMask(int maskx,int masky,int mwidth, int mheight) {
		this.maskx = maskx;
		this.masky = masky;
		this.mwidth = mwidth;
		this.mheight = mheight;
	}
	
	public void setX(int newX) {
		this.x = newX;
	}
	public void setY(int newY) {
		this.y = newY;
	}
	public int getX() {
		return (int)this.x;
	}
	
	public int getY() {
		return (int)this.y;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public void tick() {}
	
	public void followPath(List<Node> path) {
		if(path != null) {
			if(path.size() > 0) {
				Vector2i target = path.get(path.size() - 1).tile;
				if(x < target.x * 16 ) {
					x += speed;
				}
				else if(x > target.x * 16) {
					x -= speed;
				}
				
				if(y < target.y * 16) {
					y += speed;
				}
				else if(y > target.y * 16) {
					y -= speed;
				}
				
				if(x == target.x * 16 && y == target.y * 16 ) {
					path.remove(path.size() - 1);
				}
			}
		}
	}
	
	public static boolean isColiding(Entity e1,Entity e2) {
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskx,e1.getY() + e1.masky,e1.mwidth,e1.mheight);
		Rectangle e2Mask = new Rectangle(e2.getX() + e2.maskx,e2.getY() + e2.masky,e2.mwidth,e2.mheight);
		
		return e1Mask.intersects(e2Mask);
	}
	
	public boolean isColiding(int xnext,int ynext) {
		Rectangle enemy_current = new Rectangle(xnext + maskx,ynext + masky,mwidth,mheight);	
		for(int i = 0 ; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e == this) {
				continue;
			}
			Rectangle targetEnemy = new Rectangle(e.getX() + maskx,e.getY()+ masky,mwidth,mheight);
			if(enemy_current.intersects(targetEnemy)) {
				return true;
			}
		}
		return false;
	}
	
	public double calcularDistancia(int x1, int x2 , int y1 , int y2) {
		return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite,this.getX() - Camera.x,this.getY() - Camera.y, null);
	}
	
}
