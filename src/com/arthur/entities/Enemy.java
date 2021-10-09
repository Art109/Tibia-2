package com.arthur.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.arthur.main.Game;
import com.arthur.main.Sound;
import com.arthur.world.AStar;
import com.arthur.world.Camera;
import com.arthur.world.Vector2i;
import com.arthur.world.World;

public class Enemy extends Entity{
	
	private double speed = 1.6;
	
	
	public int right_dir = 0, left_dir = 1, up_dir = 2 , down_dir =3;
	public int dir = down_dir;
	
	private int frames = 0, max_frames= 5 , index_x = 0, index_y = 0 , max_index_x = 1, max_index_y = 2;
	private boolean moved = false;
	private BufferedImage[] rightEnemy;
	private BufferedImage[] leftEnemy;
	private BufferedImage[] upEnemy;
	private BufferedImage[] downEnemy;
	
	private BufferedImage	damageRightEnemy;
	private BufferedImage	damageLeftEnemy;
	private BufferedImage	damageEnemy;
	
	private boolean isDamaged = false;
	
	private int life = 50;
	private int DamageFrames = 0;
	

	
	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		rightEnemy = new BufferedImage[2] ;
		leftEnemy = new BufferedImage[2];
		upEnemy = new BufferedImage[3];
		downEnemy = new BufferedImage[3];
		
		for(int i = 0 ; i < 2 ; i++) {
			rightEnemy[i] = Game.spritesheet.getSprite(129, 0 + (i *16),16,16);
		}
		for(int i = 0 ; i < 2 ; i++) {
			leftEnemy[i] = Game.spritesheet.getSprite(144, 0 + (i *16),16,16);
		}
		for(int i = 0 ; i < 3 ; i++) {
			upEnemy[i] = Game.spritesheet.getSprite(97, 0 + (i *16),16,16);
		}
		for(int i = 0 ; i < 3 ; i++) {
			downEnemy[i] = Game.spritesheet.getSprite(114, 0 + (i *16),16,16);
		}
		depth = 0;
		
		damageRightEnemy = Game.spritesheet.getSprite(128,48, 16, 16);
		damageLeftEnemy = Game.spritesheet.getSprite(144,48, 16, 16);
		damageEnemy = Game.spritesheet.getSprite(97,48, 16, 16);
	}
	
	public void tick() {
		moved = false;
		
		if(calcularDistancia(this.getX(),Game.player.getX(),this.getY(),Game.player.getY()) < 100) {
			
			if(this.isColidingWithPlayer() == false) {
				if(new Random().nextInt(100) < 60) {
					//IA
					followPath(path);
					moved = true;
					if(x < Game.player.getX()) {
						dir = right_dir;

					}else if (x > Game.player.getX()) {
						dir = left_dir;
					}
					if(y < Game.player.getY()) {
						dir = up_dir;
					}else if(y > Game.player.getY()) {
						dir = down_dir;
					}
				}
				if(new Random().nextInt(100) < 5) {
					if(path == null || path.size() == 0) {
						Vector2i start = new Vector2i((int)(x/16),(int)(y/16));
						Vector2i end = new Vector2i((int)(Game.player.x/16),(int)(Game.player.y/16));
						path = AStar.findPath(Game.world,start,end);
					}
				}
					/*if(x < Game.player.getX() && World.isFree((int)(x + speed),this.getY()) && !isColiding((int)(x + speed),this.getY())) {
						dir = right_dir;
						//x+=speed;
					}else if (x > Game.player.getX() && World.isFree((int)(x - speed),this.getY()) && !isColiding((int)(x - speed),this.getY())) {
						dir = left_dir;
						//x-=speed;
					}
					if(y < Game.player.getY() && World.isFree(this.getX(),(int)(y + speed)) && !isColiding(this.getX(),(int)(y + speed))) {
						dir = up_dir;
						//y += speed;
					}else if(y > Game.player.getY() && World.isFree(this.getX(),(int)(y - speed)) && !isColiding(this.getX(),(int)(y - speed))) {
						dir = down_dir;
						//y -= speed;
					}*/
				
	
			}
			else {
				// em contato com o player
				if(Game.rand.nextInt(100) < 5) {
					Sound.hitEffect.play();
					Game.player.life--;
					Game.player.isDamaged = true;
				}
			
			}
		}
		ColidingWithAttack();
		
		if(isDamaged) {
			this.DamageFrames++;
			if(DamageFrames == 2) {
				this.DamageFrames = 0;
				isDamaged = false;
			}
		}
		
		if(moved) {
			frames++;
			if(frames == max_frames) {
				frames = 0;
				index_x++;
				index_y++;
				if(index_x > max_index_x) {
					index_x = 0;
				}
				if(index_y > max_index_y) {
					index_y = 0;
				}
			}
		}
		if(life <= 0) {
			Die();
		}
	}
	
	public void Die() {
		Game.entities.remove(this);
	}
	/*A colisão ocorre por varios frames assim o inimigo leva mais dano do que deveria por ataque DEVE SER CONSERTADO*/
	public void ColidingWithAttack() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof WeaponAttack) {
				if(Entity.isColiding(this, e)) {
					if(Game.rand.nextInt(100) < 25) {
						Sound.hitEffect.play();
						life -= 5;
						isDamaged = true;
					}
					return;
				}
			}
		}
	}
	public boolean isColidingWithPlayer() {
		
		Rectangle enemy_current = new Rectangle(this.getX() + maskx,this.getY() + masky,mwidth,mheight);
		Rectangle player = new Rectangle(Game.player.getX(),Game.player.getY(),16,16);
		
		return enemy_current.intersects(player);
	}
	
	
	
	public void render(Graphics g) {
		if(!isDamaged) {
			if(dir == right_dir) {
				g.drawImage(rightEnemy[index_x], this.getX()- Camera.x, this.getY() - Camera.y,null);
				}
			else if(dir == left_dir) {
				g.drawImage(leftEnemy[index_x], this.getX()- Camera.x, this.getY() - Camera.y,null);
				}
			else if(dir == up_dir) {
				g.drawImage(upEnemy[index_y], this.getX()- Camera.x, this.getY() - Camera.y,null);
				} 
			else if(dir == down_dir) {
				g.drawImage(downEnemy[index_y], this.getX()- Camera.x, this.getY() - Camera.y,null);
				}
			}
		else{
			if(dir == right_dir) {
				g.drawImage(damageRightEnemy, this.getX()- Camera.x, this.getY() - Camera.y,null);
			}
			if(dir == left_dir) {
				g.drawImage(damageLeftEnemy, this.getX()- Camera.x, this.getY() - Camera.y,null);
			}
			else{
				g.drawImage(damageEnemy, this.getX()- Camera.x, this.getY() - Camera.y,null);
			}
		}
	}	
	
}
