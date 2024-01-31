/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.test;

import br.com.bb.autotune.Autotune;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 *
 * @author F6036477
 */
public class TestAutotune {
  
  public static final Autotune auto = new Autotune();
  
  //@Test 
  public void testTakeMouseActivity() {
    List<MouseEvent> evts = auto.takeMouseActivity();
    System.out.printf("* Events: %d%n", evts.size());
    evts.forEach(e->System.out.printf("    - %s%n", e));
  }
  
  //@Test 
  public void testTakeMouseClick() {
    List<Point> evts = auto.takeMouseClick();
    System.out.printf("* Points: %d%n", evts.size());
    evts.forEach(e->System.out.printf("    - %s%n", e));
  }
  
  //@Test 
  public void testTakeMouseDrag() {
    Rectangle drag = auto.takeMouseDrag();
    System.out.printf("* Mouse Drag: %s%n", drag);
  }
  
  @Test 
  public void testGoToBrowser() {
    for(int i = 0; i < 5; i++) {
      auto.mouseMove(325, 702);
      auto.mouseClick1();
      auto.delay();
      auto.mouseDrag(new Rectangle(24, 201, 921, 280));
      auto.copy();
      auto.delay(5000);
      auto.mouseMove(528, 703);
      auto.mouseClick1();
      auto.delay();
      auto.mouseMove(52, 98);
      auto.mouseClick1();
      auto.delay();
      auto.type(KeyEvent.VK_CONTROL, KeyEvent.VK_A);
      auto.type(KeyEvent.VK_DELETE);
      auto.type(String.format("<<%d>>\n", i));
      auto.typeClipboard();
      auto.delay(20000);
    }
  }
  
}
