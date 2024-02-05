/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.test;

import br.com.bb.autotune.Autotune;
import br.com.bb.autotune.EditablePanel;
import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.concurrent.CountDownLatch;
import javax.swing.JFrame;
import org.junit.jupiter.api.Test;

/**
 *
 * @author F6036477
 */
public class TestJTextFieldBackground {
  
  @Test 
  public void test() throws Exception {
    try {
      Autotune auto = new Autotune();
      JFrame f = new JFrame();
      f.setExtendedState(Frame.MAXIMIZED_BOTH);
      EditablePanel a = new EditablePanel(f, auto);
      //a.setSize(280, 130);
      //a.setLocation(10, 10);
      //f.setLayout(null);
      f.add(a);
      CountDownLatch cd = new  CountDownLatch(1);
      f.addWindowListener(new WindowListener() {
        @Override public void windowClosing(WindowEvent e) {
          System.out.printf("* windowClosing( %s )%n", e);
          cd.countDown();
        }
        @Override public void windowClosed(WindowEvent e) {}
        @Override public void windowOpened(WindowEvent e) {}
        @Override public void windowIconified(WindowEvent e) {}
        @Override public void windowDeiconified(WindowEvent e) {}
        @Override public void windowActivated(WindowEvent e) {}
        @Override public void windowDeactivated(WindowEvent e) {}
      });
      f.setVisible(true);
      f.requestFocus();
      a.requestFocus();
      cd.await();
    }
    catch(Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
}
