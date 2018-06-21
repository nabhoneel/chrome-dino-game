package components;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import components.Ground;
import utility.Resource;

public class Dino {
  public static int dinoBaseY, dinoTopY, dinoStartX, dinoEndX;
  private int dinoTop;
  private int topPoint;
  private boolean topPointReached;
  private int jumpFactor = 20;

  private final int STAND_STILL = 1,
                    RUNNING = 2,
                    JUMPING = 3,
                    DIE = 4;
  private final int LEFT_FOOT = 1,
                    RIGHT_FOOT = 2,
                    NO_FOOT = 3;
  
  private int state;

  private int foot;

  BufferedImage image;
  BufferedImage leftFootDino;
  BufferedImage rightFootDino;

  public Dino(int height) {
    image = new Resource().getResourceImage("../images/Dino-stand.png");
    leftFootDino = new Resource().getResourceImage("../images/Dino-left-up.png");
    rightFootDino = new Resource().getResourceImage("../images/Dino-right-up.png");

    dinoBaseY = height;
    dinoTopY = height - image.getHeight();
    dinoStartX = 100;
    dinoEndX = dinoStartX + image.getWidth();
    topPoint = dinoTopY - 100;

    state = 1;
    foot = NO_FOOT;
  }

  public void create(Graphics g) {
    switch(state) {

      case STAND_STILL:
        System.out.println("stand");
        g.drawImage(image, dinoStartX, dinoTopY, null);
        break;

      case RUNNING:
        if(foot == NO_FOOT) {
          foot = LEFT_FOOT;
          g.drawImage(leftFootDino, dinoStartX, dinoTopY, null);
        } else if(foot == LEFT_FOOT) {
          foot = RIGHT_FOOT;
          g.drawImage(rightFootDino, dinoStartX, dinoTopY, null);
        } else {
          foot = LEFT_FOOT;
          g.drawImage(leftFootDino, dinoStartX, dinoTopY, null);
        }
        break;

      case JUMPING:
        if(dinoTop > topPoint && !topPointReached) {
          g.drawImage(image, dinoStartX, dinoTop -= jumpFactor, null);
          break;
        } 
        if(dinoTop == topPoint && !topPointReached) {
          topPointReached = true;
          g.drawImage(image, dinoStartX, dinoTop += jumpFactor, null);
          break;
        }         
        if(dinoTop > topPoint && topPointReached) {      
          if(dinoTopY == dinoTop && topPointReached) {
            state = RUNNING;
            topPointReached = false;
            break;
          }    
          g.drawImage(image, dinoStartX, dinoTop += jumpFactor, null);          
          break;
        }         
    }
  }

  public void update() {

  }

  public void startRunning() {
    state = RUNNING;
  }

  public void jump() {
    dinoTop = dinoTopY;
    topPointReached = false;
    state = JUMPING;
  }

  private class DinoImages {

  }

}