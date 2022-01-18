package com.arthur.main;


import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.arthur.entities.Enemy;
import com.arthur.entities.Entity;
import com.arthur.entities.Player;
import com.arthur.graficos.Spritesheet;
import com.arthur.graficos.UI;
import com.arthur.world.Minimap;
import com.arthur.world.World;
import com.arthur.world.light.Light;

public class Game extends Canvas implements Runnable,KeyListener{
	
	private static final long serialVersionUID = 1L;
	public static  JFrame frame;
	public final static int WIDTH = 240;
	public final static int HEIGHT = 160;
	public static final int SCALE = 3 ;
	private Thread thread;
	private boolean isRunning;
	
	private BufferedImage image;

	public static List<Entity> entities;
	public static List<Enemy> enemies;
	
	public static World world;
	
	public static Player player;
	
	public static Random rand;
	
	public static String map ;
	
	public static Minimap minimap;
	
	public UI ui;
	
	public InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("PixelFont.ttf");
	public static Font newFont;
	
	public static String gameState = "MENU";
	public static String cutScene = " ";
	private boolean restartGame = false;
	
	public Menu menu;
	
	public static int[] pixels;
	
	public Light light;
	
	public boolean saveGame = false;
	
	public boolean fullscreen = false;
	
	
	//public static BufferedImage minimap;
	
	public Game() {

		
		rand = new Random();
		addKeyListener(this);
		this.setPreferredSize(new Dimension(WIDTH * SCALE , HEIGHT * SCALE));
		initFrame();
		
		// Inicializando objetos
		ui = new UI();
		image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		entities = new ArrayList<Entity>();
		enemies = new ArrayList<Enemy>();
		player = new Player(0,0,16,16);
		//entities.add(player);
		map = "forest_ruins";
		world = new World("/" + map + ".png");
		
		//minimap = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		
		try {
			newFont = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(30f);
		} catch (FontFormatException | IOException e) {
			
			e.printStackTrace();
		}
		
		
		menu = new Menu();
		minimap = new Minimap();
		
		
		
	}
	
	public void initFrame() {
		frame = new JFrame("Tibia 2");
		frame.add(this);
		if(fullscreen == true)
			frame.setUndecorated(true);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	} 
	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[]args) {
		Game game = new Game();
		game.start();
	}
	public void tick() {
			
		if(gameState == "NORMAL") {
			if(this.saveGame == true) {
				saveGame = false;
				String[] opt1 = {"Map"};
				String[] opt2 = {map};
				Menu.saveGame(opt1,opt2,10);
				System.out.println("Jogo Salvo");
				
			}
			if(map == "forest_ruins") {
				Sound.menuMusicBackground.stop();
				Sound.cryptMusicBackground.stop();
				
				Sound.forestMusicBackground.loop();
			}	
			else if(map == "dungeon") {
				Sound.menuMusicBackground.stop();
				Sound.forestMusicBackground.stop();
				Sound.cryptMusicBackground.loop();
			}	
			restartGame = false;
			Entity p = player;
			p.tick();
		
			for(int i = 0 ; i < entities.size(); i++) {
				Entity e = entities.get(i);
				e.tick();
			}

		
			if(Player.downstairs) {
				map = "dungeon";
				World.restartGame(map);
				minimap = new Minimap();
				player.downstairs = false;
			}
		}
		else if(gameState == "GAME_OVER") {
			Sound.menuMusicBackground.stop();
			Sound.cryptMusicBackground.stop();
			Sound.forestMusicBackground.stop();
			if(restartGame) {
				gameState = "NORMAL";
				map = "forest_ruins";
				Game.player = new Player(0,0,16,16);
				World.restartGame(map);;
			}
		}else if(gameState== "MENU") {
			Sound.cryptMusicBackground.stop();
			Sound.forestMusicBackground.stop();
			Sound.menuMusicBackground.loop();
			menu.tick();
		}
	}
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0,WIDTH,HEIGHT);
		/* Rederização do jogo */
		//Graphics2D g2 = (Graphics2D) g;
		world.render(g);
		
		player.render(g);;
	
		
		Collections.sort(entities,Entity.nodeSorter);
		for(int i = 0 ; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
		}
		world.light.applyLight();

		ui.render(g);
		
		
		/***/
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image,0,0, WIDTH*SCALE, HEIGHT*SCALE, null);
		if(gameState == "GAME_OVER") {
			//Escurecendo a tela e Aparecendo menu de restart
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0,0,0,200));
			g2.fillRect(0,0,WIDTH*SCALE, HEIGHT*SCALE);
			g2.setFont(newFont);
			g.setColor(Color.white);
			g.drawString("Game Over", (WIDTH*SCALE)/2 - 100, (SCALE*HEIGHT)/2);
			g.drawString("Pressione Enter para reiniciar",(WIDTH*SCALE)/2 - 250, (SCALE*HEIGHT)/2 + 40);
		}else if(gameState == "MENU") {
			menu.render(g);
			//g.setColor(Color.white);
			//g.drawImage(minimap,80,80,World.WIDTH,World.HEIGHT,null);
		}

		//g.drawImage(minimap,300,350,World.WIDTH*5,World.HEIGHT*5,null);
		bs.show();
	}
	public void run() {
		// TODO Auto-generated method stub
		requestFocus();
		long lastTime = System.nanoTime();
		double amountofticks = 60.0;
		double ns = 1000000000 / amountofticks;
		double delta = 0;
		requestFocus();
		while(isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			if (delta >= 1) {
				tick();
				render();
				delta--;
			}
		}
		
		stop();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		//Movimento
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			//Execute Ação
			player.right = true;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.left = true;
		}
		else if(e.getKeyCode() == KeyEvent.VK_UP) {
			player.up = true;
			if(gameState == "MENU") {
				menu.up = true;
			}
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			player.down = true;
			
			if(gameState == "MENU") {
				menu.down = true;
			}
		}
		
		//AÇÕES
		if(e.getKeyCode() == KeyEvent.VK_Z) { 
			//Ataque
			player.attack = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_X) { 
			//Especial
			//Game.player.stamina--;
		}
		if(e.getKeyCode() == KeyEvent.VK_C) { 
			//Magia
			//Game.player.mana--;
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER) { 
			//Menu
			if(gameState == "GAME_OVER") {
				this.restartGame = true;
			}else if(gameState == "MENU") {
				Menu.enter = true;
			}else if(gameState == "NORMAL") {
				gameState = "MENU";
				Menu.pause = true;
			}		
		}
		if(e.getKeyCode() == KeyEvent.VK_F5) {
			if(gameState == "NORMAL")
				saveGame = true;
		}

		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			//Execute Ação
			player.right = false;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.left = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			player.up = false;
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			player.down = false;
		}
		
	}

}

