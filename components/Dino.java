package components;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import components.Ground;
import javafx.scene.paint.Color;
import utility.Resource;

public class Dino {
  private static int dinoBaseY, dinoTopY, dinoStartX, dinoEndX;
  private static int dinoTop, dinoBottom, topPoint;

  private static boolean topPointReached;
  private static int jumpFactor = 20;

  public static final int STAND_STILL = 1,
                    RUNNING = 2,
                    JUMPING = 3,
                    DIE = 4;
  private final int LEFT_FOOT = 1,
                    RIGHT_FOOT = 2,
                    NO_FOOT = 3;
  
  private static int state;

  private int foot;

  static BufferedImage image;
  BufferedImage leftFootDino;
  BufferedImage rightFootDino;
  BufferedImage deadDino;

  public Dino() {
    image = new Resource().getResourceImage("../images/Dino-stand.png");
    leftFootDino = new Resource().getResourceImage("../images/Dino-left-up.png");
    rightFootDino = new Resource().getResourceImage("../images/Dino-right-up.png");
    deadDino = new Resource().getResourceImage("../images/Dino-big-eyes.png");

    dinoBaseY = Ground.GROUND_Y + 5;
    dinoTopY = Ground.GROUND_Y - image.getHeight() + 5;
    dinoStartX = 100;
    dinoEndX = dinoStartX + image.getWidth();
    topPoint = dinoTopY - 120;

    state = 1;
    foot = NO_FOOT;
  }

  public void create(Graphics g) {
    dinoBottom = dinoTop + image.getHeight();

    // g.drawRect(getDino().x, getDino().y, getDino().width, getDino().height);

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
        if(dinoTop >= topPoint && !topPointReached) {
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
      case DIE: 
        g.drawImage(deadDino, dinoStartX, dinoTop, null);    
        break;     
    }
  }

  public void die() {
    state = DIE;
  }

  public static Rectangle getDino() {
    Rectangle dino = new Rectangle();
    dino.x = dinoStartX;

    if(state == JUMPING && !topPointReached) dino.y = dinoTop - jumpFactor;
    else if(state == JUMPING && topPointReached) dino.y = dinoTop + jumpFactor;
    else if(state != JUMPING) dino.y = dinoTop;

    dino.width = image.getWidth();
    dino.height = image.getHeight();

    return dino;
  }

  public void startRunning() {
    dinoTop = dinoTopY;
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