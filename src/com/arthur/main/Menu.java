package com.arthur.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.arthur.world.World;

public class Menu {
	
	public String[] options = {"Novo Jogo","Carregar Jogo","Sair","Continuar"};
	
	public int currentOption = 0 ;
	public int maxOption = options.length - 1;
	
	public boolean up,down;

	public static boolean enter,pause;
	
	public static boolean saveExists = false, saveGame = false;
	
	public void tick() {
		if(up) {
			up = false;
			currentOption--;
			if(currentOption < 0) {
				currentOption = maxOption;
			}
		}
		if(down) {
			down = false;
			currentOption++;
			if(currentOption > maxOption) {
				currentOption = 0;
			}
		}
		if(enter) {
			enter = false;
			if(options[currentOption] == "Novo Jogo" || options[currentOption] ==  "Continuar") {
				Game.gameState = "NORMAL";
				pause = false;
				File file = new File("save.txt");
				file.delete();
			}
			else if(options[currentOption] == "Carregar Jogo") {
				File file = new File("save.txt");
				if(file.exists()) {
					String saver = loadGame(10);
					applySave(saver);
				}
			}
			else if(options[currentOption] == "Sair") {
				System.exit(0);
			}
		}
	}
	
	
	
	
	public static void applySave(String str) {
		
		String[] spl = str.split("/");
		for(int i = 0 ; i < spl.length; i++) {
			String [] spl2 = spl[i].split(":");
			switch(spl2[0]) {

				case "Map":
					World.restartGame(spl2[1]);
					Game.gameState = "NORMAL";
					pause = false;
					break;
			
				
		
				
			}
		}	
	}
		
		
	public static String loadGame(int encode) {
	
		String line = "";
		File file = new File("save.txt");
		if(file.exists()) {
			try {
				String singleLine = null;
				BufferedReader reader = new BufferedReader(new FileReader("save.txt"));
				
				try {
					while((singleLine = reader.readLine()) != null) {
						String[] transition = singleLine.split(":");
						char[] val = transition[1].toCharArray();
						transition[1] = "";
						for(int i = 0 ; i < val.length ; i++) {
							val[i] -= encode;
							transition[1] += val[i];
						}
						line += transition[0];
						line += ":";
						line += transition[1];
						line += "/";
					}
				}
				catch(IOException e) {}
			}
			catch(FileNotFoundException e){

			}
		}
		return line;
	}
	
	public static void saveGame(String[] val1, String[] val2 , int encode) {
		BufferedWriter write = null;
		try {
			write = new BufferedWriter(new FileWriter("save.txt"));
			
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		for(int i = 0; i < val1.length; i++) {
			 String current = val1[i];
			 current += ":";
			 char[] value = val2[i].toCharArray();
			 for( int j = 0 ; j < value.length ; j++) {
				 value[j] += encode;
				 current += value[j];
			 }
			 try {
				 write.write(current);
				 if(i < val1.length -1 )
					 write.newLine();
			 }
			 catch(IOException e) {
				 
			 }
		}
		try {
			write.flush();
			write.close();
		}
		catch(IOException e) {}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0,Game.WIDTH*Game.SCALE,Game.HEIGHT*Game.SCALE);
		g.setColor(Color.white);
		g.setFont(Game.newFont);
		g.drawString("Tibia 2",(Game.WIDTH * Game.SCALE)/2 - 60 , (Game.HEIGHT * Game.SCALE)/2 -120);
		
		//Opções de menu
		g.setFont(Game.newFont);
		if(pause)
			g.drawString("Continuar",(Game.WIDTH * Game.SCALE)/2 - 60 , (Game.HEIGHT * Game.SCALE)/2 );
		else
			g.drawString("Novo Jogo",(Game.WIDTH * Game.SCALE)/2 - 60 , (Game.HEIGHT * Game.SCALE)/2 );
		g.drawString("Carregar",(Game.WIDTH * Game.SCALE)/2 - 60 , (Game.HEIGHT * Game.SCALE)/2 + 40);
		g.drawString("Sair",(Game.WIDTH * Game.SCALE)/2 - 60 , (Game.HEIGHT * Game.SCALE)/2 + 80);
		
		//Seleção de Opção
		if(options[currentOption] == "Novo Jogo" || options[currentOption] == "Continuar") {
			g.setColor(Color.green);
			if(pause)
				g.drawString("Continuar",(Game.WIDTH * Game.SCALE)/2 - 60 , (Game.HEIGHT * Game.SCALE)/2 );
			else
				g.drawString("Novo Jogo",(Game.WIDTH * Game.SCALE)/2 - 60 , (Game.HEIGHT * Game.SCALE)/2 );
		}else if(options[currentOption] == "Carregar Jogo") {
			g.setColor(Color.green);
			g.drawString("Carregar",(Game.WIDTH * Game.SCALE)/2 - 60 , (Game.HEIGHT * Game.SCALE)/2 + 40);
		}else if(options[currentOption] == "Sair") {
			g.setColor(Color.green);
			g.drawString("Sair",(Game.WIDTH * Game.SCALE)/2 - 60 , (Game.HEIGHT * Game.SCALE)/2 + 80);
		}
	}
}
