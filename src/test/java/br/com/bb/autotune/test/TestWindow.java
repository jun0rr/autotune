/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.test;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 *
 * @author F6036477
 */
public class TestWindow {
  
  //@Test 
  public void test() throws InterruptedException, AWTException {
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    Robot r = new Robot();
    BufferedImage img = r.createScreenCapture(new Rectangle(d));
    JFrame w = new JFrame() {
      public void paint(Graphics g) {
        //super.paint(g);
        g.drawImage(img, 0, 0, img.getWidth(), img.getHeight(), null);
      }
    };
    w.setLocation(0, 0);
    w.setSize(d);
    w.addMouseListener(new MouseL());
    JTextField f = new JTextField();
    f.setSize(200, 30);
    f.setLocation(500, 250);
    f.addKeyListener(new KeyL());
    w.add(f);
    w.setVisible(true);
    f.requestFocus();
    w.repaint();
    Thread.sleep(10000);
  }
  
  public static class MouseL implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent e) {
      System.out.printf("* Mouse Clicked: %s%n", e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
      System.out.printf("* Mouse Pressed: %s%n", e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
      System.out.printf("* Mouse Released: %s%n", e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
      System.out.printf("* Mouse Entered: %s%n", e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
      System.out.printf("* Mouse Exited: %s%n", e);
    }
    
  }
  
  
  public static class KeyL implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {
      //System.out.printf("* Key Typed: %s%n", e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
      //System.out.printf("* Key Pressed: %s%n", e);
      System.out.printf("km.put('%s', new int[] {%d});%n", e.getKeyChar(), e.getExtendedKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
      //System.out.printf("* Key Released: %s%n", e);
    }
    
  }
  
}
