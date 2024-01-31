/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.List;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.ImageObserver;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import javax.swing.JWindow;

/**
 *
 * @author F6036477
 */
public class FakeScreen extends JWindow implements ImageObserver, MouseListener, MouseMotionListener, MouseWheelListener {
  
  private Image image;
  
  private Dimension dimension;
  
  private CountDownLatch count;
  
  private final List<MouseEvent> activity;
  
  public FakeScreen() {
    super();
    this.activity = new LinkedList<>();
    this.addMouseListener(this);
    this.addMouseMotionListener(this);
    this.addMouseWheelListener(this);
  }
  
  public List<MouseEvent> getMouseActivity() {
    return activity;
  }
  
  public void paintImage(Image img) {
    this.image = Objects.requireNonNull(img);
    dimension = new Dimension(image.getWidth(this), image.getHeight(this));
  }
  
  public void showAndWait(Image img) throws InterruptedException {
    count = new CountDownLatch(1);
    this.activity.clear();
    this.setVisible(true);
    this.requestFocus();
    paintImage(img);
    count.await();
  }
  
  @Override
  public void paint(Graphics g) {
    g.drawImage(image, 0, 0, dimension.width, dimension.height, null);
  }
  
  @Override
  public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
    dimension = new Dimension(width, height);
    repaint();
    return true;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    if(e.getButton() == MouseEvent.BUTTON3 && e.getClickCount() == 3) {
      setVisible(false);
      count.countDown();
    }
    else {
      activity.add(e);
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {
    activity.add(e);
    //System.out.printf("* mousePressed( %s )%n", e);
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    activity.add(e);
    //System.out.printf("* mouseReleased( %s )%n", e);
  }

  @Override public void mouseEntered(MouseEvent e) {}

  @Override public void mouseExited(MouseEvent e) {}

  @Override
  public void mouseDragged(MouseEvent e) {
    activity.add(e);
    //System.out.printf("* mouseDragged( %s )%n", e);
  }

  @Override
  public void mouseMoved(MouseEvent e) {
    activity.add(e);
    //System.out.printf("* mouseMoved( %s )%n", e);
  }

  @Override
  public void mouseWheelMoved(MouseWheelEvent e) {
    activity.add(e);
    //System.out.printf("* mouseWheelMoved( %s )%n", e);
  }
  
}
