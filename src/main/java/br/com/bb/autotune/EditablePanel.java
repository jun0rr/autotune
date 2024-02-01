/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune;

import java.awt.AWTException;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

/**
 *
 * @author F6036477
 */
public class EditablePanel extends JPanel implements MouseListener, MouseMotionListener, KeyListener {
  
  private final BufferedImage background;
  
  private Font font;
  
  private Color fontColor;
  
  private final List<TextPoint> textPoints;
  
  private TextPoint current;
  
  private Rectangle rect;
  
  private int delay;
  
  private KeyEvent oldKeyEvent;
  
  private KeyEvent prevKeyEvent;
  
  private final JPopupMenu popup;
  
  private final Autotune auto;
  
  private final List<Consumer<Autotune>> actions;
  
  public EditablePanel(Autotune a) {
    this.auto = Objects.requireNonNull(a);
    this.actions = new LinkedList<>();
    this.popup = createPopupMenu();
    try {
      Robot r = new Robot();
      this.background = r.createScreenCapture(new Rectangle(
          Toolkit.getDefaultToolkit().getScreenSize())
      );
      this.font = new Font("Consolas", Font.BOLD, 18);
      this.fontColor = Color.BLACK;
      this.textPoints = new LinkedList();
      this.addMouseListener(this);
      this.addKeyListener(this);
      this.addMouseMotionListener(this);
    }
    catch(AWTException e) {
      throw new RuntimeException(e);
    }
  }
  
  private JPopupMenu createPopupMenu() {
    JPopupMenu menu = new JPopupMenu();
    JMenu actions = new JMenu("<Actions>");
    JMenuItem isets = new JMenuItem("Settings");
    JMenuItem ifont = new JMenuItem("Font");
    JMenuItem icolor = new JMenuItem("Color");
    JMenuItem idelay = new JMenuItem("Autodelay");
    JMenuItem iexec = new JMenuItem("Execute");
    JMenuItem iupdate = new JMenuItem("Update");
    JMenuItem iexit = new JMenuItem("Exit");
    return menu;
  }
  
  @Override
  public void setFont(Font f) {
    this.font = f;
    if(current != null) {
      current.setFont(f);
    }
  }
  
  public void setFontColor(Color c) {
    this.fontColor = c;
    if(current != null) {
      current.setColor(c);
    }
  }
  
  public void setAutoDelay(int ms) {
    this.delay = ms;
  }
  
  @Override
  public void paint(Graphics g) {
    super.paint(g);
    //g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);
    if(!textPoints.isEmpty()) {
      textPoints.forEach(t->{
        //Graphics gg = g.create();
        g.setColor(t.getColor());
        g.setFont(t.getFont());
        g.drawString(t.text().toString(), t.getPoint().x, t.getPoint().y);
        //g.dispose();
      });
    }
    if(rect != null) {
      Graphics2D gg = (Graphics2D) g;
      gg.setStroke(new BasicStroke(2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{5f}, 0));
      gg.setColor(Color.blue);
      gg.draw(rect);
    }
  }
  
  @Override
  public void mouseClicked(MouseEvent e) {
    System.out.printf("* mouseClicked( %s )%n", e);
    if(MouseEvent.BUTTON1 == e.getButton()) {
      current = new TextPoint(e.getPoint(), font,  fontColor);
      textPoints.add(current);
    }
    else if(MouseEvent.BUTTON3 == e.getButton()) {
      rect = null;
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {
    System.out.printf("* mousePressed( %s )%n", e);
    actions.add(a->a.mousedo(e));
    rect = new Rectangle(e.getPoint().x, e.getPoint().y, 0, 0);
  }

  @Override public void mouseReleased(MouseEvent e) {
    actions.add(a->a.mousedo(e));
  }

  @Override public void mouseEntered(MouseEvent e) {}

  @Override public void mouseExited(MouseEvent e) {}

  @Override public void keyTyped(KeyEvent e) {}
  
  private void keydo(KeyEvent e) {
    switch(e.getExtendedKeyCode()) {
      case 0x2f:
        actions.add(Autotune.CHAR_MAP.get(e.isShiftDown() ? '?' : '/'));
        break;
      case 0x10000c7:
        actions.add(Autotune.CHAR_MAP.get(e.isShiftDown() ? 'Ç' : 'ç'));
        break;
      default:
        actions.add(a->a.keydo(e));
        break;
    }
  }
  
  private int mod(KeyEvent e) {
    int mod = 1;
    if(e.isShiftDown()) {
      mod *= 16;
    }
    if(e.isControlDown()) {
      mod *= 17;
    }
    if(e.isAltDown()) {
      mod *= 18;
    }
    return mod;
  }

  @Override 
  public void keyPressed(KeyEvent e) {
    keydo(e);
  }
  
  @Override
  public void keyReleased(KeyEvent e) {
    System.out.printf("* keyReleased( %s )%n", e);
    if(KeyEvent.VK_DELETE == e.getKeyCode() && e.isControlDown()) {
      if(rect != null) {
        textPoints.stream()
            .filter(t->rect.contains(t.getPoint()))
            .collect(Collectors.toList())
            .forEach(textPoints::remove);
      }
      else {
        textPoints.clear();
      }
      if(!textPoints.contains(current)){
        current = null;
      }
      rect = null;
    }
    else {
      keydo(e);
      if(current != null) {
        if(KeyEvent.VK_ENTER == e.getKeyCode()) {
          current.text().append('\n');
        }
        else if(KeyEvent.VK_TAB == e.getKeyCode()) {
          current.text().append('\t');
        }
        else if(KeyEvent.VK_BACK_SPACE == e.getKeyCode()) {
          current.text().deleteCharAt(current.text().length() -1);
        }
        else if(prevKeyEvent != null && prevKeyEvent.getExtendedKeyCode() == 129) {
          int code = (mod(prevKeyEvent) * prevKeyEvent.getExtendedKeyCode() -1) * mod(e) * e.getExtendedKeyCode();
          if(Autotune.KEYCODES_MAP.containsKey(code)) {
            current.text().append(Autotune.KEYCODES_MAP.get(code));
          }
        }
        else if(oldKeyEvent != null && oldKeyEvent.getExtendedKeyCode() == 129 && prevKeyEvent.getExtendedKeyCode() == 16) {
          int code = (mod(oldKeyEvent) * oldKeyEvent.getExtendedKeyCode() -1) * mod(e) * e.getExtendedKeyCode();
          if(Autotune.KEYCODES_MAP.containsKey(code)) {
            current.text().append(Autotune.KEYCODES_MAP.get(code));
          }
        }
        else if(prevKeyEvent != null && prevKeyEvent.getExtendedKeyCode() == 131) {
          int code = (mod(prevKeyEvent) * prevKeyEvent.getExtendedKeyCode() -1) * mod(e) * e.getExtendedKeyCode();
          if(Autotune.KEYCODES_MAP.containsKey(code)) {
            current.text().append(Autotune.KEYCODES_MAP.get(code));
          }
        }
        else if(oldKeyEvent != null && oldKeyEvent.getExtendedKeyCode() == 131 && prevKeyEvent.getExtendedKeyCode() == 16) {
          int code = (mod(oldKeyEvent) * oldKeyEvent.getExtendedKeyCode() -1) * mod(e) * e.getExtendedKeyCode();
          if(Autotune.KEYCODES_MAP.containsKey(code)) {
            current.text().append(Autotune.KEYCODES_MAP.get(code));
          }
        }
        else if(Autotune.CHAR_MAP.containsKey(e.getKeyChar())) {
          current.text().append(e.getKeyChar());
        }
      }
    }
    oldKeyEvent = prevKeyEvent;
    prevKeyEvent = e;
    repaint();
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    if(rect == null) {
      rect = new Rectangle(e.getPoint().x, e.getPoint().y, 0, 0);
    }
    else {
      rect = new Rectangle(rect.x, rect.y, e.getPoint().x - rect.x, e.getPoint().y - rect.y);
    }
    repaint();
  }
  
  @Override public void mouseMoved(MouseEvent e) {
    actions.add(a->a.mouseMove(e.getPoint()));
  }
  
}
