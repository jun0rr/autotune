/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.test;

import br.com.bb.autotune.icon.FontAwesome;
import br.com.bb.autotune.icon.FontIcon;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.CountDownLatch;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.junit.jupiter.api.Test;

/**
 *
 * @author F6036477
 */
public class TestIcon {
  
  @Test 
  public void test() throws Exception {
    try {
      JFrame f = new JFrame("Icon");
      //Font font = FontIcon.createFont(getClass().getResourceAsStream("/fontawesome-webfont.ttf"));
      //Font font = FontIcon.createFontAwesome();
      JButton btn = new JButton("Click Me!", FontIcon.createIcon(FontAwesome.COG, Color.BLUE, 24f));
      btn.addActionListener(e->{
        JOptionPane.showMessageDialog(f, "Button clicked!", "Click", JOptionPane.INFORMATION_MESSAGE);
      });
      f.add(btn);
      f.pack();
      CountDownLatch cd = new  CountDownLatch(1);
      f.addWindowListener(new WindowAdapter() {
        @Override public void windowClosing(WindowEvent e) {
          System.out.printf("* windowClosing( %s )%n", e);
          cd.countDown();
        }
      });
      f.setVisible(true);
      cd.await();
    }
    catch(Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
}
