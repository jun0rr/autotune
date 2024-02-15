/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package br.com.bb.autotune;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;

/**
 *
 * @author F6036477
 */
public class Autotune {
  
  public static final Map<Character,Consumer<Autotune>> CHAR_MAP = createCharMap();
  
  public static final Map<Integer,Character> KEYCODES_MAP = createKeyCodesMap();
  
  private final Robot robot;
  
  private final Random rand;
  
  private final Clipboard clip;
  
  public Autotune() {
    this.rand = new Random();
    try {
      this.robot = new Robot();
      this.clip = Toolkit.getDefaultToolkit().getSystemClipboard();
    }
    catch(AWTException e) {
      throw new RuntimeException(e);
    }
  }
  
  public Autotune delay() {
    robot.delay(rand.nextInt(50, 100));
    return this;
  }
  
  public Autotune delay(int millis) {
    double plus = millis * 0.3;
    robot.delay(rand.nextInt(50, Math.max((int)plus, 100)) + millis);
    return this;
  }
  
  public Autotune type(String text) {
    char[] cs = text.toCharArray();
    for(int i = 0; i < cs.length; i++) {
      Consumer<Autotune> c = CHAR_MAP.get(cs[i]);
      if(c == null) {
        c = CHAR_MAP.get(' ');
      }
      c.accept(this);
      robot.delay(rand.nextInt(50, 100));
    }
    return this;
  }
  
  public Autotune type(int ... keys) {
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
    return this;
  }
  
  public Autotune typeClipboard() {
    try {
      String txt = (String) clip.getData(DataFlavor.stringFlavor);
      type(txt);
    }
    catch(Exception e) {
      throw new RuntimeException(e);
    }
    return this;
  }
  
  public Autotune copyScreenshot() {
    clip.setContents(new ImageSelection(takeScreenshot()), null);
    return this;
  }
  
  public Autotune copyScreenshot(Rectangle r) {
    clip.setContents(new ImageSelection(takeScreenshot(r)), null);
    return this;
  }
  
  public Autotune putClipboard(String s) {
    clip.setContents(new StringSelection(s), null);
    return this;
  }
  
  public Autotune putClipboard(Image i) {
    clip.setContents(new ImageSelection(i), null);
    return this;
  }
  
  public String getClipboardString() {
    try {
      return clip.isDataFlavorAvailable(DataFlavor.stringFlavor) 
          ? (String) clip.getData(DataFlavor.stringFlavor) 
          : null;
    }
    catch(UnsupportedFlavorException | IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  public Image getClipboardImage() {
    try {
      return clip.isDataFlavorAvailable(DataFlavor.imageFlavor) 
          ? (Image) clip.getData(DataFlavor.imageFlavor) 
          : null;
    }
    catch(UnsupportedFlavorException | IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  public Autotune copy() {
    type(KeyEvent.VK_CONTROL, KeyEvent.VK_C);
    return this;
  }
  
  public Autotune paste() {
    type(KeyEvent.VK_CONTROL, KeyEvent.VK_V);
    return this;
  }
  
  public Autotune keyPress(int key) {
    robot.keyPress(key);
    return this;
  }
  
  public Autotune keyRelease(int key) {
    robot.keyRelease(key);
    return this;
  }
  
  public Autotune mouseMove(int x, int y) {
    robot.mouseMove(x, y);
    return this;
  }
  
  public Autotune mouseMove(Point p) {
    robot.mouseMove(p.x, p.y);
    return this;
  }
  
  public Autotune keydo(KeyEvent e) {
    switch(e.getID()) {
      case KeyEvent.KEY_PRESSED:
        robot.keyPress(e.getExtendedKeyCode());
        break;
      case KeyEvent.KEY_RELEASED:
        robot.keyRelease(e.getExtendedKeyCode());
        break;
      default:
        break;
    }
    return this;
  }
  
  public Autotune mouseWheel(int n) {
    robot.mouseWheel(n);
    return this;
  }
  
  public Autotune mouseDrag(Rectangle r) {
    mouseMove(r.x, r.y);
    mousePress1();
    mouseMove(r.x + r.width, r.y + r.height);
    mouseRelease1();
    return this;
  }
  
  public Autotune mousePress(int btn) {
    switch(btn) {
      case MouseEvent.BUTTON1:
        mousePress1();
        break;
      case MouseEvent.BUTTON2:
        mousePress2();
        break;
      case MouseEvent.BUTTON3:
        mousePress3();
        break;
      default:
        throw new RuntimeException("No such mouse button: " + btn);
    }
    return this;
  }
  
  public Autotune mouseRelease(int btn) {
    switch(btn) {
      case MouseEvent.BUTTON1:
        mouseRelease1();
        break;
      case MouseEvent.BUTTON2:
        mouseRelease2();
        break;
      case MouseEvent.BUTTON3:
        mouseRelease3();
        break;
      default:
        throw new RuntimeException("No such mouse button: " + btn);
    }
    return this;
  }
  
  public Autotune mouseClick(int btn) {
    switch(btn) {
      case MouseEvent.BUTTON1:
        mouseClick1();
        break;
      case MouseEvent.BUTTON2:
        mouseClick2();
        break;
      case MouseEvent.BUTTON3:
        mouseClick3();
        break;
      default:
        throw new RuntimeException("No such mouse button: " + btn);
    }
    return this;
  }
  
  public Autotune mousePress1() {
    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
    return this;
  }
  
  public Autotune mouseRelease1() {
    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    return this;
  }
  
  public Autotune mouseClick1() {
    mousePress1();
    robot.delay(rand.nextInt(10, 50));
    mouseRelease1();
    return this;
  }
  
  public Autotune mousePress2() {
    robot.mousePress(InputEvent.BUTTON2_DOWN_MASK);
    return this;
  }
  
  public Autotune mouseRelease2() {
    robot.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);
    return this;
  }
  
  public Autotune mouseClick2() {
    mousePress2();
    robot.delay(rand.nextInt(10, 50));
    mouseRelease2();
    return this;
  }
  
  public Autotune mousePress3() {
    robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
    return this;
  }
  
  public Autotune mouseRelease3() {
    robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
    return this;
  }
  
  public Autotune mouseClick3() {
    mousePress3();
    robot.delay(rand.nextInt(10, 50));
    mouseRelease3();
    return this;
  }
  
  public BufferedImage takeScreenshot() {
    return takeScreenshot(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
  }
  
  public BufferedImage takeScreenshot(Rectangle r) {
    return robot.createScreenCapture(r);
  }
  
  public Color pickColor(int x, int y) {
    return robot.getPixelColor(x, y);
  }
  /*
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
  
  public List<MouseEvent> takeMousePress() {
    try {
      screen.showAndWait(takeScreenshot());
      return screen.getMouseActivity()
          .stream()
          .filter(e->MouseEvent.MOUSE_PRESSED == e.getID())
          .collect(Collectors.toList());
    } catch (InterruptedException ex) {
      throw new RuntimeException(ex);
    }
  }
  
  public List<MouseEvent> takeMouseRelease() {
    try {
      screen.showAndWait(takeScreenshot());
      return screen.getMouseActivity()
          .stream()
          .filter(e->MouseEvent.MOUSE_RELEASED == e.getID())
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
  */
  private static Map<Character,Consumer<Autotune>> createCharMap() {
    Map<Character, Consumer<Autotune>> km = new HashMap();
    km.put('\'', a->a.type(222));
    km.put('1', a->a.type(49));
    km.put('2', a->a.type(50));
    km.put('3', a->a.type(51));
    km.put('4', a->a.type(52));
    km.put('5', a->a.type(53));
    km.put('6', a->a.type(54));
    km.put('7', a->a.type(55));
    km.put('8', a->a.type(56));
    km.put('9', a->a.type(57));
    km.put('0', a->a.type(48));
    km.put('-', a->a.type(45));
    km.put('=', a->a.type(61));
    km.put('"', a->a.type(16, 222));
    km.put('“', a->a.type(16, 222));
    km.put('”', a->a.type(16, 222));
    km.put('!', a->a.type(16, 49));
    km.put('@', a->a.type(16, 50));
    km.put('#', a->a.type(16, 51));
    km.put('$', a->a.type(16, 52));
    km.put('%', a->a.type(16, 53));
    km.put('¨', a->a.type(16, 54));
    km.put('&', a->a.type(16, 55));
    km.put('*', a->a.type(16, 56));
    km.put('(', a->a.type(16, 57));
    km.put(')', a->a.type(16, 48));
    km.put('_', a->a.type(16, 45));
    km.put('+', a->a.type(16, 61));
    km.put('q', a->a.type(81));
    km.put('w', a->a.type(87));
    km.put('e', a->a.type(69));
    km.put('r', a->a.type(82));
    km.put('t', a->a.type(84));
    km.put('y', a->a.type(89));
    km.put('u', a->a.type(85));
    km.put('i', a->a.type(73));
    km.put('o', a->a.type(79));
    km.put('p', a->a.type(80));
    km.put('´', a->a.type(129));
    km.put('[', a->a.type(91));
    km.put('Q', a->a.type(16, 81));
    km.put('W', a->a.type(16, 87));
    km.put('E', a->a.type(16, 69));
    km.put('R', a->a.type(16, 82));
    km.put('T', a->a.type(16, 84));
    km.put('Y', a->a.type(16, 89));
    km.put('U', a->a.type(16, 85));
    km.put('I', a->a.type(16, 73));
    km.put('O', a->a.type(16, 79));
    km.put('P', a->a.type(16, 80));
    km.put('`', a->a.type(16, 129));
    km.put('{', a->a.type(16, 91));
    km.put('a', a->a.type(65));
    km.put('s', a->a.type(83));
    km.put('d', a->a.type(68));
    km.put('f', a->a.type(70));
    km.put('g', a->a.type(71));
    km.put('h', a->a.type(72));
    km.put('j', a->a.type(74));
    km.put('k', a->a.type(75));
    km.put('l', a->a.type(76));
    km.put('ç', a->a.putClipboard("ç").paste());
    km.put('~', a->a.type(131));
    km.put(']', a->a.type(93));
    km.put('A', a->a.type(16, 65));
    km.put('S', a->a.type(16, 83));
    km.put('D', a->a.type(16, 68));
    km.put('F', a->a.type(16, 70));
    km.put('G', a->a.type(16, 71));
    km.put('H', a->a.type(16, 72));
    km.put('J', a->a.type(16, 74));
    km.put('K', a->a.type(16, 75));
    km.put('L', a->a.type(16, 76));
    km.put('Ç', a->a.putClipboard("Ç").paste());
    km.put('^', a->a.type(16, 131));
    km.put('}', a->a.type(16, 93));
    km.put('\\', a->a.type(92));
    km.put('z', a->a.type(90));
    km.put('x', a->a.type(88));
    km.put('c', a->a.type(67));
    km.put('v', a->a.type(86));
    km.put('b', a->a.type(66));
    km.put('n', a->a.type(78));
    km.put('m', a->a.type(77));
    km.put(',', a->a.type(44));
    km.put('.', a->a.type(46));
    km.put(';', a->a.type(59));
    km.put('/', a->a.putClipboard("/").paste());
    km.put('|', a->a.type(16, 92));
    km.put('Z', a->a.type(16, 90));
    km.put('X', a->a.type(16, 88));
    km.put('C', a->a.type(16, 67));
    km.put('V', a->a.type(16, 86));
    km.put('B', a->a.type(16, 66));
    km.put('N', a->a.type(16, 78));
    km.put('M', a->a.type(16, 77));
    km.put('<', a->a.type(16, 44));
    km.put('>', a->a.type(16, 46));
    km.put(':', a->a.type(16, 59));
    km.put('?', a->a.putClipboard("?").paste());
    km.put('á', a->a.type(129, 65));
    km.put('à', a->a.type(16, 129, -1, 65));
    km.put('ã', a->a.type(131, 65));
    km.put('â', a->a.type(16, 131, -1, 65));
    km.put('Á', a->a.type(129, 16, 65));
    km.put('À', a->a.type(16, 129, 16, 65));
    km.put('Ã', a->a.type(131, 16, 65));
    km.put('Â', a->a.type(16, 131, 16, 65));
    km.put('é', a->a.type(129, 69));
    km.put('è', a->a.type(16, 129, -1, 69));
    km.put('ê', a->a.type(16, 131, -1, 69));
    km.put('É', a->a.type(129, 16, 69));
    km.put('È', a->a.type(16, 129, 16, 69));
    km.put('Ê', a->a.type(16, 131, 16, 69));
    km.put('í', a->a.type(129, 73));
    km.put('ì', a->a.type(16, 129, -1, 73));
    km.put('î', a->a.type(16, 131, -1, 73));
    km.put('Í', a->a.type(129, 16, 73));
    km.put('Ì', a->a.type(16, 129, 16, 73));
    km.put('Î', a->a.type(16, 131, 16, 73));
    km.put('ó', a->a.type(129, 79));
    km.put('ò', a->a.type(16, 129, -1, 79));
    km.put('õ', a->a.type(131, 79));
    km.put('ô', a->a.type(16, 131, -1, 79));
    km.put('Ó', a->a.type(129, 16, 79));
    km.put('Ò', a->a.type(16, 129, 16, 79));
    km.put('Õ', a->a.type(131, 16, 79));
    km.put('Ô', a->a.type(16, 131, 16, 79));
    km.put('ú', a->a.type(129, 85));
    km.put('ù', a->a.type(16, 129, -1, 85));
    km.put('û', a->a.type(131, -1, 85));
    km.put('ü', a->a.type(16, 135, -1, 85));
    km.put('Ú', a->a.type(129, 16, 85));
    km.put('Ù', a->a.type(16, 129, 16, 85));
    km.put('Û', a->a.type(131, 16, 85));
    km.put('Ü', a->a.type(16, 135, 16, 85));
    km.put('ª', a->a.type(17, 91));
    km.put('º', a->a.type(17, 93));
    km.put('§', a->a.type(17, 61));
    km.put('°', a->a.putClipboard("°").paste());
    km.put('¹', a->a.type(17, 49));
    km.put('²', a->a.type(17, 50));
    km.put('³', a->a.type(17, 51));
    km.put('£', a->a.type(17, 52));
    km.put('¢', a->a.type(17, 53));
    km.put('¬', a->a.type(17, 54));
    km.put(' ', a->a.type(32));
    km.put('\n', a->a.type(10));
    km.put('\t', a->a.type(KeyEvent.VK_TAB));
    return km;
  }
  
  private static Map<Integer,Character> createKeyCodesMap() {
    Map<Integer,Character> map = new HashMap<>();
    map.put((129-1)*65, 'á');
    map.put((16*129-1)*65, 'à');
    map.put((131-1)*65, 'ã');
    map.put((16*131-1)*65, 'â');
    map.put((129-1)*16*65, 'Á');
    map.put((16*129-1)*16*65, 'À');
    map.put((131-1)*16*65, 'Ã');
    map.put((16*131-1)*16*65, 'Â');
    map.put((129-1)*69, 'é');
    map.put((16*129-1)*69, 'è');
    map.put((16*131-1)*69, 'ê');
    map.put((129-1)*16*69, 'É');
    map.put((16*129-1)*16*69, 'È');
    map.put((16*131-1)*16*69, 'Ê');
    map.put((129-1)*73, 'í');
    map.put((16*129-1)*73, 'ì');
    map.put((16*131-1)*73, 'î');
    map.put((129-1)*16*73, 'Í');
    map.put((16*129-1)*16*73, 'Ì');
    map.put((16*131-1)*16*73, 'Î');
    map.put((129-1)*79, 'ó');
    map.put((16*129-1)*79, 'ò');
    map.put((131-1)*79, 'õ');
    map.put((16*131-1)*79, 'ô');
    map.put((129-1)*16*79, 'Ó');
    map.put((16*129-1)*16*79, 'Ò');
    map.put((131-1)*16*79, 'Õ');
    map.put((16*131-1)*16*79, 'Ô');
    map.put((129-1)*85, 'ú');
    map.put((16*129-1)*85, 'ù');
    map.put((131-1)*85, 'û');
    map.put((129-1)*16*85, 'Ú');
    map.put((16*129-1)*16*85, 'Ù');
    map.put((131-1)*16*85, 'Û');
    return map;
  }
  
}
