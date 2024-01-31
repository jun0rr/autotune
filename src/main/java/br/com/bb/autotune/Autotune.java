/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package br.com.bb.autotune;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.List;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

/**
 *
 * @author F6036477
 */
public class Autotune {
  
  public static final Map<Character,int[]> KEY_MAP = createKeyMap();
  
  private final Robot robot;
  
  private final Random rand;
  
  private final Clipboard clip;
  
  private final FakeScreen screen;
  
  public Autotune() {
    this.rand = new Random();
    try {
      Toolkit tk = Toolkit.getDefaultToolkit();
      this.robot = new Robot();
      this.clip = tk.getSystemClipboard();
      this.screen = new FakeScreen();
      screen.setLocation(0, 0);
      screen.setSize(tk.getScreenSize());
    }
    catch(AWTException e) {
      throw new RuntimeException(e);
    }
  }
  
  public void delay() {
    robot.delay(rand.nextInt(50, 100));
  }
  
  public void delay(int millis) {
    double plus = millis * 0.3;
    robot.delay(rand.nextInt(50, Math.max((int)plus, 100)) + millis);
  }
  
  public void type(String text) {
    char[] cs = text.toCharArray();
    for(int i = 0; i < cs.length; i++) {
      int[] keys = KEY_MAP.get(cs[i]);
      if(keys == null) {
        keys = KEY_MAP.get(" ");
      }
      type(KEY_MAP.get(cs[i]));
      robot.delay(rand.nextInt(50, 100));
    }
  }
  
  public void type(int ... keys) {
    int release = -1;
    for(int i = 0; i < keys.length; i++) {
      if(keys[i] == -1) {
        release = i;
        for(int j = 0; j < i; j++) {
          robot.keyRelease(keys[j]);
        }
      }
      else {
        robot.keyPress(keys[i]);
      }
    }
    robot.delay(10);
    for(int i = keys.length -1; i > release; i--) {
      robot.keyRelease(keys[i]);
    }
  }
  
  public void typeClipboard() {
    try {
      String txt = (String) clip.getData(DataFlavor.stringFlavor);
      type(txt);
    }
    catch(Exception e) {
      throw new RuntimeException(e);
    }
  }
  
  public void copyScreenshot() {
    clip.setContents(new ImageSelection(takeScreenshot()), null);
  }
  
  public void copy() {
    type(KeyEvent.VK_CONTROL, KeyEvent.VK_C);
  }
  
  public void paste() {
    type(KeyEvent.VK_CONTROL, KeyEvent.VK_V);
  }
  
  public void keyPress(int key) {
    robot.keyPress(key);
  }
  
  public void keyRelease(int key) {
    robot.keyRelease(key);
  }
  
  public void mouseMove(int x, int y) {
    robot.mouseMove(x, y);
  }
  
  public void mouseWheel(int n) {
    robot.mouseWheel(n);
  }
  
  public void mouseDrag(Rectangle r) {
    mouseMove(r.x, r.y);
    mousePress1();
    mouseMove(r.x + r.width, r.y + r.height);
    mouseRelease1();
  }
  
  public void mousePress1() {
    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
  }
  
  public void mouseRelease1() {
    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
  }
  
  public void mouseClick1() {
    mousePress1();
    robot.delay(rand.nextInt(10, 50));
    mouseRelease1();
  }
  
  public void mousePress2() {
    robot.mousePress(InputEvent.BUTTON2_DOWN_MASK);
  }
  
  public void mouseRelease2() {
    robot.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);
  }
  
  public void mouseClick2() {
    mousePress2();
    robot.delay(rand.nextInt(10, 50));
    mouseRelease2();
  }
  
  public void mousePress3() {
    robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
  }
  
  public void mouseRelease3() {
    robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
  }
  
  public void mouseClick3() {
    mousePress3();
    robot.delay(rand.nextInt(10, 50));
    mouseRelease3();
  }
  
  public BufferedImage takeScreenshot() {
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    return robot.createScreenCapture(new Rectangle(d));
  }
  
  public Color pickColor(int x, int y) {
    return robot.getPixelColor(x, y);
  }
  
  public List<MouseEvent> takeMouseActivity() {
    try {
      screen.showAndWait(takeScreenshot());
      return screen.getMouseActivity();
    } catch (InterruptedException ex) {
      throw new RuntimeException(ex);
    }
  }
  
  public List<Point> takeMouseClick() {
    try {
      screen.showAndWait(takeScreenshot());
      return screen.getMouseActivity()
          .stream()
          .filter(e->MouseEvent.MOUSE_CLICKED == e.getID())
          .map(MouseEvent::getLocationOnScreen)
          .collect(Collectors.toList());
    } catch (InterruptedException ex) {
      throw new RuntimeException(ex);
    }
  }
  
  public List<Point> takeMousePress() {
    try {
      screen.showAndWait(takeScreenshot());
      return screen.getMouseActivity()
          .stream()
          .filter(e->MouseEvent.MOUSE_PRESSED == e.getID())
          .map(MouseEvent::getLocationOnScreen)
          .collect(Collectors.toList());
    } catch (InterruptedException ex) {
      throw new RuntimeException(ex);
    }
  }
  
  public List<Point> takeMouseRelease() {
    try {
      screen.showAndWait(takeScreenshot());
      return screen.getMouseActivity()
          .stream()
          .filter(e->MouseEvent.MOUSE_RELEASED == e.getID())
          .map(MouseEvent::getLocationOnScreen)
          .collect(Collectors.toList());
    } catch (InterruptedException ex) {
      throw new RuntimeException(ex);
    }
  }
  
  public List<MouseEvent> takeMouseWheel() {
    try {
      screen.showAndWait(takeScreenshot());
      return screen.getMouseActivity()
          .stream()
          .filter(e->MouseEvent.MOUSE_WHEEL == e.getID())
          .collect(Collectors.toList());
    } catch (InterruptedException ex) {
      throw new RuntimeException(ex);
    }
  }
  
  public Rectangle takeMouseDrag() {
    try {
      screen.showAndWait(takeScreenshot());
      Optional<MouseEvent> a = screen.getMouseActivity()
          .stream()
          .filter(e->MouseEvent.MOUSE_DRAGGED == e.getID())
          .findFirst();
      Optional<MouseEvent> b = screen.getMouseActivity()
          .stream()
          .filter(e->MouseEvent.MOUSE_DRAGGED == e.getID())
          .reduce((x,y)->y);
      if(a.isEmpty() || b.isEmpty()) {
        return null;
      }
      Point pa = a.get().getLocationOnScreen();
      Point pb = b.get().getLocationOnScreen();
      return new Rectangle(pa.x, pa.y, pb.x - pa.x, pb.y - pa.y);
    } catch (InterruptedException ex) {
      throw new RuntimeException(ex);
    }
  }
  
  private static Map<Character,int[]> createKeyMap() {
    Map<Character, int[]> km = new HashMap();
    km.put('\'', new int[] {222});
    km.put('1', new int[] {49});
    km.put('2', new int[] {50});
    km.put('3', new int[] {51});
    km.put('4', new int[] {52});
    km.put('5', new int[] {53});
    km.put('6', new int[] {54});
    km.put('7', new int[] {55});
    km.put('8', new int[] {56});
    km.put('9', new int[] {57});
    km.put('0', new int[] {48});
    km.put('-', new int[] {45});
    km.put('=', new int[] {61});
    km.put('"', new int[] {16, 222});
    km.put('“', new int[] {16, 222});
    km.put('”', new int[] {16, 222});
    km.put('!', new int[] {16, 49});
    km.put('@', new int[] {16, 50});
    km.put('#', new int[] {16, 51});
    km.put('$', new int[] {16, 52});
    km.put('%', new int[] {16, 53});
    km.put('¨', new int[] {16, 54});
    km.put('&', new int[] {16, 55});
    km.put('*', new int[] {16, 56});
    km.put('(', new int[] {16, 57});
    km.put(')', new int[] {16, 48});
    km.put('_', new int[] {16, 45});
    km.put('+', new int[] {16, 61});
    km.put('q', new int[] {81});
    km.put('w', new int[] {87});
    km.put('e', new int[] {69});
    km.put('r', new int[] {82});
    km.put('t', new int[] {84});
    km.put('y', new int[] {89});
    km.put('u', new int[] {85});
    km.put('i', new int[] {73});
    km.put('o', new int[] {79});
    km.put('p', new int[] {80});
    km.put('´', new int[] {129});
    km.put('[', new int[] {91});
    km.put('Q', new int[] {16, 81});
    km.put('W', new int[] {16, 87});
    km.put('E', new int[] {16, 69});
    km.put('R', new int[] {16, 82});
    km.put('T', new int[] {16, 84});
    km.put('Y', new int[] {16, 89});
    km.put('U', new int[] {16, 85});
    km.put('I', new int[] {16, 73});
    km.put('O', new int[] {16, 79});
    km.put('P', new int[] {16, 80});
    km.put('`', new int[] {16, 129});
    km.put('{', new int[] {16, 91});
    km.put('a', new int[] {65});
    km.put('s', new int[] {83});
    km.put('d', new int[] {68});
    km.put('f', new int[] {70});
    km.put('g', new int[] {71});
    km.put('h', new int[] {72});
    km.put('j', new int[] {74});
    km.put('k', new int[] {75});
    km.put('l', new int[] {76});
    km.put('ç', new int[] {67});
    km.put('~', new int[] {131});
    km.put(']', new int[] {93});
    km.put('A', new int[] {16, 65});
    km.put('S', new int[] {16, 83});
    km.put('D', new int[] {16, 68});
    km.put('F', new int[] {16, 70});
    km.put('G', new int[] {16, 71});
    km.put('H', new int[] {16, 72});
    km.put('J', new int[] {16, 74});
    km.put('K', new int[] {16, 75});
    km.put('L', new int[] {16, 76});
    km.put('Ç', new int[] {16, 67});
    km.put('^', new int[] {16, 131});
    km.put('}', new int[] {16, 93});
    km.put('\\', new int[] {92});
    km.put('z', new int[] {90});
    km.put('x', new int[] {88});
    km.put('c', new int[] {67});
    km.put('v', new int[] {86});
    km.put('b', new int[] {66});
    km.put('n', new int[] {78});
    km.put('m', new int[] {77});
    km.put(',', new int[] {44});
    km.put('.', new int[] {46});
    km.put(';', new int[] {59});
    km.put('/', new int[] {KeyEvent.VK_ALT, KeyEvent.VK_NUMPAD0});
    km.put('|', new int[] {16, 92});
    km.put('Z', new int[] {16, 90});
    km.put('X', new int[] {16, 88});
    km.put('C', new int[] {16, 67});
    km.put('V', new int[] {16, 86});
    km.put('B', new int[] {16, 66});
    km.put('N', new int[] {16, 78});
    km.put('M', new int[] {16, 77});
    km.put('<', new int[] {16, 44});
    km.put('>', new int[] {16, 46});
    km.put(':', new int[] {16, 59});
    km.put('?', new int[] {16, 49});
    km.put('á', new int[] {129, 65});
    km.put('à', new int[] {16, 129, -1, 65});
    km.put('ã', new int[] {131, 65});
    km.put('â', new int[] {16, 131, -1, 65});
    km.put('Á', new int[] {129, 16, 65});
    km.put('À', new int[] {16, 129, 16, 65});
    km.put('Ã', new int[] {131, 16, 65});
    km.put('Â', new int[] {16, 131, 16, 65});
    km.put('é', new int[] {129, 69});
    km.put('è', new int[] {16, 129, -1, 69});
    km.put('ê', new int[] {16, 131, -1, 69});
    km.put('É', new int[] {129, 16, 69});
    km.put('È', new int[] {16, 129, 16, 69});
    km.put('Ê', new int[] {16, 131, 16, 69});
    km.put('í', new int[] {129, 73});
    km.put('ì', new int[] {16, 129, -1, 73});
    km.put('î', new int[] {16, 131, -1, 73});
    km.put('Í', new int[] {129, 16, 73});
    km.put('Ì', new int[] {16, 129, 16, 73});
    km.put('Î', new int[] {16, 131, 16, 73});
    km.put('ó', new int[] {129, 79});
    km.put('ò', new int[] {16, 129, -1, 79});
    km.put('õ', new int[] {131, 79});
    km.put('ô', new int[] {16, 131, -1, 79});
    km.put('Ó', new int[] {129, 16, 79});
    km.put('Ò', new int[] {16, 129, 16, 79});
    km.put('Õ', new int[] {131, 16, 79});
    km.put('Ô', new int[] {16, 131, 16, 79});
    km.put('ú', new int[] {129, 85});
    km.put('ù', new int[] {16, 129, -1, 85});
    km.put('û', new int[] {131, -1, 85});
    km.put('ü', new int[] {16, 131, -1, 85});
    km.put('Ú', new int[] {129, 16, 85});
    km.put('Ù', new int[] {16, 129, 16, 85});
    km.put('Û', new int[] {131, 16, 85});
    km.put('Ü', new int[] {16, 131, 16, 85});
    km.put('ª', new int[] {17, 91});
    km.put('º', new int[] {17, 93});
    km.put('§', new int[] {17, 61});
    km.put('°', new int[] {17, 93});
    km.put('¹', new int[] {17, 49});
    km.put('²', new int[] {17, 50});
    km.put('³', new int[] {17, 51});
    km.put('£', new int[] {17, 52});
    km.put('¢', new int[] {17, 53});
    km.put('¬', new int[] {17, 54});
    km.put(' ', new int[] {32});
    km.put('\n', new int[] {10});
    return km;
  }
  
}
