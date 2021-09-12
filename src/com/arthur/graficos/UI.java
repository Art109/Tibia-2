package com.arthur.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.arthur.entities.Player;
import com.arthur.main.Game;

public class UI {
	
	public void render(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(8, 130, 50, 8);
		g.setColor(Color.red);
		g.fillRect(8, 130,(int)((Game.player.life/Game.player.maxLife) *50), 8);
		g.setColor(Color.black);
		g.fillRect(8, 140, 50, 8);
		g.setColor(Color.green);
		g.fillRect(8, 140,(int)((Game.player.stamina/Game.player.maxStamina)* 50), 8);
		g.setColor(Color.black);
		g.fillRect(8, 150, 50, 8);
		g.setColor(Color.blue);
		g.fillRect(8, 150,(int)((Game.player.mana/Game.player.maxMana)* 50), 8);
		g.setColor(Color.white);
		g.setFont(Game.newFont.deriveFont(9f));
		g.drawString((int)Game.player.life+ "/" + (int)Game.player.maxLife, 8, 138);
		g.drawString((int)(Game.player.stamina)+ "/" + (int)(Game.player.maxStamina), 8, 148);
		g.drawString((int)Game.player.mana+ "/" + (int)Game.player.maxMana, 8, 158);
	}
}
