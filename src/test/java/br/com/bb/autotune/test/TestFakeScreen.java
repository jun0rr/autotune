/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.test;

import br.com.bb.autotune.FakeScreen;
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 *
 * @author F6036477
 */
public class TestFakeScreen {
  
  //@Test 
  public void test() throws InterruptedException, AWTException {
    FakeScreen screen = new FakeScreen();
    screen.setLocation(0, 0);
    Dimension sd = Toolkit.getDefaultToolkit().getScreenSize();
    screen.setSize(sd);
    Robot r = new Robot();
    screen.showAndWait(r.createScreenCapture(new Rectangle(sd)));
    List<MouseEvent> evts = screen.getMouseActivity();
    System.out.printf("* Events: %d%n", evts.size());
    evts.forEach(e->System.out.printf("    - %s%n", e));
  }
  
}
