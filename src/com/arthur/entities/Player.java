package com.arthur.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.arthur.graficos.Spritesheet;
import com.arthur.graficos.UI;
import com.arthur.main.Game;
import com.arthur.world.Camera;
import com.arthur.world.Stairs;
import com.arthur.world.World;

public class Player extends Entity {
	
	public boolean right,up,left,down;
	public double speed = 0.7 ;
	public int right_dir = 0, left_dir = 1, up_dir = 2 , down_dir =3;
	public int dir = down_dir;
	
	private int frames = 0, max_frames= 5 , index = 0 , max_index = 2;
	private int attackFrames = 0, maxAttack_frames = 4, attackIndex = 0 , AttackMax_index = 0;
	private boolean moved = false;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage[] upPlayer;
	private BufferedImage[] downPlayer;
	
	private BufferedImage[] rightAttackPlayer;
	private BufferedImage[] leftAttackPlayer;
	private BufferedImage[] upAttackPlayer;
	private BufferedImage[] downAttackPlayer;
	
	private boolean weapon = false;
	public boolean attack = false;
	
	public BufferedImage[] playerdamage;
	public boolean isDamaged = false;
	private int DamageFrames = 0;
	
	public double life = 100, maxLife = 100;
	public double mana = 100, maxMana = 100;
	public double stamina = 100 , maxStamina = 100;
	
	public static boolean downstairs = false;
	
	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		rightPlayer = new BufferedImage[3];
		leftPlayer = new BufferedImage[3];
		upPlayer = new BufferedImage[1];
		downPlayer = new BufferedImage[3];
		
		
		
		playerdamage = new BufferedImage[4];
		
		for(int i = 0 ; i < 4 ; i++) {
			playerdamage[i] = Game.spritesheet.getSprite(33 + (16 * i), 48 ,16, 16); 
		}
		for(int i = 0; i < 3; i++) {
			rightPlayer[i] = Game.spritesheet.getSprite(65, 0 + (16 *i), 16, 16);
		}
		for(int i = 0; i < 3; i++) {
			leftPlayer[i] = Game.spritesheet.getSprite(81, 0 + (16 *i), 16, 16);
		}
		
		upPlayer[0] = Game.spritesheet.getSprite(49, 0, 16, 16);
		for(int i = 0 ; i < 3 ; i++) {
			downPlayer[i] = Game.spritesheet.getSprite(33, 0 + (i * 16),16, 16);
		}
		
		
		downAttackPlayer = new BufferedImage[5];
		rightAttackPlayer = new BufferedImage[5];
		leftAttackPlayer = new BufferedImage[5];
		
		
		for(int i = 0 ; i < 5 ; i++) {
			downAttackPlayer[i] = Game.spritesheet.getSprite(97, 64 + (i * 16),16, 16);
		}
		for(int i = 0 ; i < 5 ; i++) {
			rightAttackPlayer[i] = Game.spritesheet.getSprite(113, 64 + (i * 16),16, 16);
		}
		for(int i = 0 ; i < 5 ; i++) {
			leftAttackPlayer[i] = Game.spritesheet.getSprite(129, 64 + (i * 16),15, 16);
		}
		
	}
	
	public void tick() {
		moved = false;
		if(right && World.isFree((int)(x + speed),this.getY())) {
			moved = true;
			dir = right_dir;
			x+=speed;
		}else if(left && World.isFree((int)(x - speed),this.getY())) {
			moved = true;
			dir = left_dir;
			x-=speed;
		}
		if(up && World.isFree(this.getX(),(int)(y - speed))) {
			moved = true;
			dir = up_dir;
			y-=speed;
		}else if(down && World.isFree(this.getX(),(int)(y + speed))) {
			moved = true;
			dir = down_dir;
			y+=speed;
		}
		
		if(moved) {
			frames++;
			if(frames == max_frames) {
				frames = 0;
				index++;
				if(index > max_index) {
					index = 0;
				}
			}
		}
		
		checkLifePotion();
		checkManaPotion();
		if(downstairs == false) {
			checkStairs();
		}
		else {
			downstairs = false;
		}	
		
		if(isDamaged) {
			this.DamageFrames++;
			if(DamageFrames == 2) {
				this.DamageFrames = 0;
				isDamaged = false;
			}
		}
		
		if(attack && weapon) {
			//animação de attack + dano
			
			int dx = 0;
			int dy = 0;
			int px = 0;
			int py = 0;
			int width = 0;
			int height = 0;		
			int AttackFrame = 0;
			int MaxAttackFrame = 5;
			if(dir == right_dir) {
				dx = 1;
				py = 10;
				px = 16;
				width = 8;
				height = 3;
				AttackMax_index = 5;
			}else if (dir == left_dir) {
				dx = -1;
				py = 10;
				px = -8;
				width = 8;
				height = 3;
				AttackMax_index = 5;
			}else if( dir == up_dir) {
				dy = 1;
				py = -9;
				px = 7;
				width = 3;
				height = 8;
			}else if (dir == down_dir) {
				dy = -1;
				py = 17;
				px = 6;
				width = 3;
				height = 8;
				AttackMax_index = 5;
			}
			
			WeaponAttack swordattack = new WeaponAttack(this.getX() + px,this.getY() + py,width,height,null,dx,dy);
			Game.entities.add(swordattack);
			
			
			attackFrames++;
			if(attackFrames == maxAttack_frames) {
				attackFrames = 0;
				attackIndex++;
				if(attackIndex == AttackMax_index) {
					attackIndex = 0;
					attack = false;
				}
				
			}
			
			
		}
		
		if(life <=0) {
			Game.gameState = "GAME_OVER";
			/*Game.entities.clear();
			Game.enemies.clear();
			Game.entities = new ArrayList<Entity>();
			Game.enemies = new ArrayList<Enemy>();
			Game.spritesheet = new Spritesheet("/spritesheet.png");
			Game.player = new Player(0,0,16,16,Game.spritesheet.getSprite(33,0, 16, 16));
			Game.entities.add(Game.player);
			Game.world = new World("/map.png");
			return;*/
		}
		
		if(!weapon) {
			checkWeapon();
			if(weapon) {
				WeaponEquiped();}
		}
		
		Camera.x = Camera.clamp(this.getX() -(Game.WIDTH/2),0,World.WIDTH * 16 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() -(Game.HEIGHT/2),0,World.HEIGHT * 16 - Game.HEIGHT); 
	}
	
	private void WeaponEquiped() {
		
		
		for(int i = 0 ; i < 4 ; i++) {
			playerdamage[i] = Game.spritesheet.getSprite(33 +(16 * i), 112 ,16, 16); 
		}
		for(int i = 0; i < 3; i++) {
			rightPlayer[i] = Game.spritesheet.getSprite(65, 64 + (16 *i), 16, 16);
		}
		for(int i = 0; i < 3; i++) {
			leftPlayer[i] = Game.spritesheet.getSprite(81, 64 + (16 *i), 16, 16);
		}			
		upPlayer[0] = Game.spritesheet.getSprite(49, 64, 16, 16);
		for(int i = 0 ; i < 3 ; i++) {
			downPlayer[i] = Game.spritesheet.getSprite(33, 64 + (i * 16),16, 16);
		}
	}
	
	public void  checkWeapon() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Weapon) {
				if(Entity.isColiding(this, e)) {
						weapon = true;
						Game.entities.remove(i);
				}			
			}
		}
	}
	
	public void checkStairs() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Stairs) {
				if(Entity.isColiding(this, e)) {
					if(downstairs == false) {
						downstairs = true;
					}else {
						downstairs = false;
					}
					
					
					
					return;
				}
			}
		}
	}
	public void  checkManaPotion() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof ManaPotion) {
				if(Entity.isColiding(this, e)) {
					if(mana < 100){
						mana+= 8;
						Game.entities.remove(i);
						if(mana >= 100) {
							mana = 100;
						}			
					}
					
					
					return;
				  }
				}
		}
	}
	public void  checkLifePotion() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof LifePotion) {
				if(Entity.isColiding(this, e)) {
					if(life < 100){
						life+= 8;
						Game.entities.remove(i);
						if(life >= 100) {
							life = 100;
				}			
			}
						
					
					
					return;
				}
			}
		}
	}

	public void render(Graphics g) {
		if(attack && weapon) {
			if(dir == down_dir) {
				g.drawImage(downAttackPlayer[attackIndex], this.getX()- Camera.x, this.getY() - Camera.y,null);
			}
			if(dir == left_dir) {
				g.drawImage(leftAttackPlayer[attackIndex], this.getX()- Camera.x, this.getY() - Camera.y,null);
			}
			if(dir == right_dir) {
				g.drawImage(rightAttackPlayer[attackIndex], this.getX()- Camera.x, this.getY() - Camera.y,null);
			}
		}	
		else if(!isDamaged) {
			if(dir == right_dir) {
				g.drawImage(rightPlayer[index], this.getX()- Camera.x, this.getY() - Camera.y,null);
				}
			else if(dir == left_dir) {
				g.drawImage(leftPlayer[index], this.getX()- Camera.x, this.getY() - Camera.y,null);
				}
			else if(dir == up_dir) {
				g.drawImage(upPlayer[0], this.getX()- Camera.x, this.getY() - Camera.y,null);
				} 
			else if(dir == down_dir) {
				g.drawImage(downPlayer[index], this.getX()- Camera.x, this.getY() - Camera.y,null);
				}
		}else {
			if(dir == right_dir) {
				g.drawImage(playerdamage[2], this.getX()- Camera.x, this.getY() - Camera.y,null);
				}
			else if(dir == left_dir) {
				g.drawImage(playerdamage[3], this.getX()- Camera.x, this.getY() - Camera.y,null);
				}
			else if(dir == up_dir) {
				g.drawImage(playerdamage[1], this.getX()- Camera.x, this.getY() - Camera.y,null);
				} 
			else if(dir == down_dir) {
				g.drawImage(playerdamage[0], this.getX()- Camera.x, this.getY() - Camera.y,null);
				}
		}
	}
}
