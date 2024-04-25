package com.brainmentors.gaming;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.brainmentors.gaming.sprites.Enemy;
import com.brainmentors.gaming.sprites.Player;

public class Board extends JPanel {
    Timer timer;
    
    BufferedImage backgroundImage;
    Player player;
    Enemy enemies[] = new Enemy[3];
    int score = 0; // Variable to store player's score
    int health = 100; // Player's initial health
    int maxHealth = 100; // Maximum health
    int healthBarWidth = 200; // Width of the health bar
    
    public Board() {
        setSize(1500, 900);
        loadBackgroundImage();
        player = new Player();
        loadEnemies();
        gameLoop();
        setFocusable(true);
        bindEvents();
    }
    
    private void GameOver(Graphics pen) {
        if(player.outOfScreen()) {
            pen.setFont(new Font("times", Font.BOLD, 30));
            pen.setColor(Color.BLUE);
            pen.drawString(" GAME WIN ", 1500 / 2, 880 / 2);
            timer.stop();
            return;
        }
        for(Enemy enemy: enemies) {
            if(isCollide(enemy)) {
                pen.setFont(new Font("times", Font.BOLD, 30));
                pen.setColor(Color.RED);
                pen.drawString(" GAME OVER ", 1500 / 2, 880 / 2);
                timer.stop();
            } 
        }
    }
    
    private boolean isCollide(Enemy enemy) {
        int xdis = Math.abs(player.x - enemy.x);
        int ydis = Math.abs(player.y - enemy.y);
        int maxH = Math.max(player.h, enemy.h);
        int maxW = Math.max(player.w, enemy.w);
        return xdis <= maxW - 70 && ydis <= maxH - 70;
    }
    
    private void bindEvents() {
        addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    player.speed = 10;
                } else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
                    player.speed = -10;
                }
            }
    
            @Override
            public void keyReleased(KeyEvent e) {
                player.speed = 0;
            }
    
            @Override
            public void keyTyped(KeyEvent e) {
                // Handle key typed events here
            }
        });
    }
    
    private void loadEnemies() {
        int x = 400;
        int gap = 350;
        int speed = 4;
        for(int i = 0; i < enemies.length; i++) {
            enemies[i] = new Enemy(x, speed);
            x += gap;
            speed += 4;
        }
    }
    
    private void gameLoop() {
        timer = new Timer(50, (e) -> {
            updateGame(); // Update game state
            repaint(); // Repaint the panel
        });
        timer.start(); // Start the game loop
    }
    
    private void updateGame() {
        player.move(); // Move the player
        for(Enemy enemy: enemies) {
            enemy.move(); // Move each enemy
        }
        checkCollisions(); // Check for collisions
        incrementScore(); // Increment the score
    }
    
    private void checkCollisions() {
        // Check collision with enemies
        for(Enemy enemy: enemies) {
            if(isCollide(enemy)) {
                health -= 20; // Reduce health on collision
                if(health <= 0) {
                    health = 0;
                    // Game Over logic here
                    timer.stop(); // Stop the game if health reaches 0
                }
            }
        }
        
        // Check if player is out of screen
        if(player.outOfScreen()) {
            // Game Win logic here
            timer.stop(); // Stop the game if player goes out of screen
        }
    }
    
    private void incrementScore() {
        score++; // Increment the score
    }
    
    private void loadBackgroundImage() {
        try {
            backgroundImage = ImageIO.read(Board.class.getResource("flappybirdbg.png")); // Load background image
        } catch (IOException e) {
            System.out.println("Background Image not found......."); // Print error message
            System.exit(1); // Exit the program if image not found
            e.printStackTrace();
        }
    }
    
    private void printEnemy(Graphics pen) {
        for(Enemy enemy: enemies) {
            enemy.draw(pen); // Draw each enemy
        }
    }
    
    @Override
    public void paintComponent(Graphics pen) {
        super.paintComponent(pen);
        pen.drawImage(backgroundImage, 0, 0, 1500, 900, null); // Draw background image
        player.draw(pen); // Draw player
        printEnemy(pen); // Draw enemies
        drawHUD(pen); // Draw Health and Score
        GameOver(pen); // Check for game over condition
    }
    
    private void drawHUD(Graphics pen) {
        // Draw Health Bar
        pen.setColor(Color.RED); // Set color to red
        pen.fillRect(20, 20, healthBarWidth, 20); // Draw background of health bar
        pen.setColor(Color.GREEN); // Set color to green
        int currentHealthWidth = (int) ((double) health / maxHealth * healthBarWidth); // Calculate current health bar width
        pen.fillRect(20, 20, currentHealthWidth, 20); // Draw current health bar
        
        // Draw Score
        pen.setColor(Color.WHITE); // Set color to white
        pen.setFont(new Font("Arial", Font.BOLD, 20)); // Set font
        pen.drawString("Score: " + score, 20, 60); // Draw score
    }
}
