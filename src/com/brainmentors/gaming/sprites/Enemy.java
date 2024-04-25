package com.brainmentors.gaming.sprites;

import java.awt.Graphics;

import javax.swing.ImageIcon;

public class Enemy extends Sprite{

	
	public Enemy(int x , int speed) {
		y=20;
		this.x=x;
		this.speed=speed;
		w=200;
		h=200;
		image =new ImageIcon(Enemy.class.getResource("bottompipe.png"));
	}
	
	
}
