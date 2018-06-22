package components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

import utility.Resource;

public class Obstacles {
  private class Obstacle {
    BufferedImage image;
    int x;
    int y;

    Rectangle getObstacle() {
      Rectangle obstacle = new Rectangle();
      obstacle.x = x;
      obstacle.y = y;
      obstacle.width = image.getWidth();
      obstacle.height = image.getHeight();

      return obstacle;
    }
  }
  
  private int firstX;
  private int obstacleInterval;
  private int movementSpeed;
  
  private ArrayList<BufferedImage> imageList;
  private ArrayList<Obstacle> obList;

  private Obstacle blockedAt;
  
  public Obstacles(int firstPos) {
    obList = new ArrayList<Obstacle>();
    imageList = new ArrayList<BufferedImage>();
    
    firstX = firstPos;
    obstacleInterval = 200;
    movementSpeed = 11;
    
    imageList.add(new Resource().getResourceImage("../images/Cactus-1.png"));
    imageList.add(new Resource().getResourceImage("../images/Cactus-2.png"));
    imageList.add(new Resource().getResourceImage("../images/Cactus-2.png"));
    imageList.add(new Resource().getResourceImage("../images/Cactus-1.png"));
    // imageList.add(new Resource().getResourceImage("../images/Cactus-3.png"));
    // imageList.add(new Resource().getResourceImage("../images/Cactus-4.png"));
    imageList.add(new Resource().getResourceImage("../images/Cactus-5.png"));
    
    int x = firstX;
    
    for(BufferedImage bi : imageList) {
      
      Obstacle ob = new Obstacle();
      
      ob.image = bi;
      ob.x = x;
      ob.y = Ground.GROUND_Y - bi.getHeight() + 5;
      x += obstacleInterval;
      
      obList.add(ob);
    }
  }
  
  public void update() {
    Iterator<Obstacle> looper = obList.iterator();
    
    Obstacle firstOb = looper.next();
    firstOb.x -= movementSpeed;
    
    while(looper.hasNext()) {
      Obstacle ob = looper.next();
      ob.x -= movementSpeed;
    }
    
    Obstacle lastOb = obList.get(obList.size() - 1);
    
    if(firstOb.x < -firstOb.image.getWidth()) {
      obList.remove(firstOb);
      firstOb.x = obList.get(obList.size() - 1).x + obstacleInterval;
      obList.add(firstOb);
    }
  }
  
  public void create(Graphics g) {
    for(Obstacle ob : obList) {
      g.setColor(Color.black);
      // g.drawRect(ob.getObstacle().x, ob.getObstacle().y, ob.getObstacle().width, ob.getObstacle().height);
      g.drawImage(ob.image, ob.x, ob.y, null);
    }
  }
  
  public boolean hasCollided() {
    for(Obstacle ob : obList) {
      if(Dino.getDino().intersects(ob.getObstacle())) {
        System.out.println("Dino = " + Dino.getDino() + "\nObstacle = " + ob.getObstacle() + "\n\n");
        blockedAt = ob;
        return true;
      }   
    }
    return false;
  }

  public void resume() {
    // this.obList = null;
    int x = firstX/2;   
    obList = new ArrayList<Obstacle>();
    
    for(BufferedImage bi : imageList) {
      
      Obstacle ob = new Obstacle();
      
      ob.image = bi;
      ob.x = x;
      ob.y = Ground.GROUND_Y - bi.getHeight() + 5;
      x += obstacleInterval;
      
      obList.add(ob);
    }
  }
  
}