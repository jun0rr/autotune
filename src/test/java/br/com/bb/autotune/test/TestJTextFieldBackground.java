/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.test;

import br.com.bb.autotune.Autotune;
import br.com.bb.autotune.EditorPanel;
import br.com.bb.autotune.script.fn.FnContext;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.junit.jupiter.api.Test;

/**
 *
 * @author F6036477
 */
public class TestJTextFieldBackground {
  
  public static final String FRAME_ICON_PATH = "/robot.png";
  
  @Test 
  public void test() throws Exception {
    try {
      Autotune auto = new Autotune();
      JFrame f = new JFrame();
      f.setTitle("Autotune");
      try {
        Image icon = ImageIO.read(getClass().getResourceAsStream(FRAME_ICON_PATH));
        f.setIconImage(icon);
      }
      catch(IOException e) {
        throw new RuntimeException(e);
      }
      //FnContext ctx = null;
      //javax.swing.JOptionPane.showMessageDialog(ctx.getEditorPanel(), "Alert Test!");
      EditorPanel a = new EditorPanel(f, auto);
      f.add(a);
      CountDownLatch cd = new  CountDownLatch(1);
      f.addWindowListener(new WindowAdapter() {
        @Override public void windowClosing(WindowEvent e) {
          cd.countDown();
        }
      });
      f.setVisible(true);
      f.setExtendedState(Frame.MAXIMIZED_BOTH);
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
