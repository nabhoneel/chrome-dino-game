import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import components.Ground;
import components.Dino;

class GamePanel extends JPanel implements KeyListener, Runnable {
  
  private int WIDTH;
  private int HEIGHT;
  private Thread animator;
  
  private boolean running = false;
  private boolean gameOver = false;
  
  private int groundX, groundY;
  private int i;
  
  BufferedImage groundImage = null;
  
  Ground ground;
  Dino dino;
  
  public GamePanel() {
    this.WIDTH = UserInterface.WIDTH;
    this.HEIGHT = UserInterface.HEIGHT;
    
    ground = new Ground(HEIGHT);
    dino = new Dino(Ground.GROUND_Y);
    
    this.groundX = 0;
    this.groundY = (int)(HEIGHT - 0.25 * HEIGHT);
    
    setSize(WIDTH, HEIGHT);
    setVisible(true);
  }
  
  public void paint(Graphics g) {
    super.paint(g);
    ground.create(g);
    dino.create(g);
  }
  
  public void run() {
    running = true;
    i = 0;
    while(running) {
      updateGame();
      repaint();      
      try {
        Thread.sleep(50);
      } catch(InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
  
  public void updateGame() {
    ground.update();
    dino.update();
    // game complete condition
  }
  
  public void keyTyped(KeyEvent e) {
    // System.out.println(e);
    if(e.getKeyChar() == ' ') {      
      if (animator == null || !running) {
        System.out.println("Game starts");        
        animator = new Thread(this);
        animator.start();     
        dino.startRunning();   
      } else {
        dino.jump();
      }
    }
  }
  
  public void keyPressed(KeyEvent e) {
    // System.out.println("keyPressed: "+e);
  }
  
  public void keyReleased(KeyEvent e) {
    // System.out.println("keyReleased: "+e);
  }
}