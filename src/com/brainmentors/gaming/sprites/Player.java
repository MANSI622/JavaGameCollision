package com.brainmentors.gaming.sprites;

import java.awt.Graphics;

import javax.swing.ImageIcon;

public class Player extends Sprite {
	
	public Player() {
		w=200;
		h=200;
		x=50;
		y=370;
		image =new ImageIcon(Player.class.getResource("flappybird.png"));
		
	}
	
	public void move() {
		x=x+speed;
	}
	
	public boolean outOfScreen() {
		return x>=1500;
	}
}
