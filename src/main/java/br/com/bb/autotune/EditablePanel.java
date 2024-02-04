/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune;

import br.com.bb.autotune.settings.DialogSettings;
import br.com.bb.autotune.settings.Settings;
import java.awt.AWTException;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
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
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;

/**
 *
 * @author F6036477
 */
public class EditablePanel extends JPanel implements MouseListener, MouseMotionListener, KeyListener {
  
  private final Autotune auto;
  
  private final Settings settings;
  
  private final DialogSettings dialogsets;
  
  private final List<TextPoint> textPoints;
  
  private final List<Consumer<Autotune>> actions;
  
  private final JPopupMenu popup;
  
  private BufferedImage background;
  
  private TextPoint current;
  
  private Rectangle rect;
  
  private KeyEvent oldKeyEvent;
  
  private KeyEvent prevKeyEvent;
  
  public EditablePanel(JFrame owner, Autotune a) {
    this.auto = Objects.requireNonNull(a);
    this.actions = new LinkedList<>();
    this.textPoints = new LinkedList();
    this.popup = createPopupMenu();
    this.settings = new Settings();
    this.dialogsets = new DialogSettings(owner, settings);
    try {
      Robot r = new Robot();
      this.background = r.createScreenCapture(new Rectangle(
          Toolkit.getDefaultToolkit().getScreenSize())
      );
    }
    catch(AWTException e) {
      throw new RuntimeException(e);
    }
    this.addMouseListener(this);
    this.addKeyListener(this);
    this.addMouseMotionListener(this);
  }
  
  private JPopupMenu createPopupMenu() {
    IconFontSwing.register(FontAwesome.getIconFont());
    JPopupMenu menu = new JPopupMenu();
    JMenu file = new JMenu("File");
    file.setIcon(IconFontSwing.buildIcon(FontAwesome.FILE_O, 12f));
    JMenuItem iopensts = new JMenuItem("Load Settings");
    iopensts.setIcon(IconFontSwing.buildIcon(FontAwesome.FOLDER_OPEN_O, 12f));
    JMenuItem iopenimg = new JMenuItem("Load Image");
    iopenimg.setIcon(IconFontSwing.buildIcon(FontAwesome.FOLDER_OPEN_O, 12f));
    JMenuItem isavests = new JMenuItem("Save Settings");
    isavests.setIcon(IconFontSwing.buildIcon(FontAwesome.FLOPPY_O, 12f));
    JMenuItem isaveimg = new JMenuItem("Save Image");
    isaveimg.setIcon(IconFontSwing.buildIcon(FontAwesome.FILE_IMAGE_O, 12f));
    file.add(iopensts);
    file.add(iopenimg);
    file.add(new JPopupMenu.Separator());
    file.add(isavests);
    file.add(isaveimg);
    
    JMenuItem isets = new JMenuItem("Settings");
    isets.setIcon(IconFontSwing.buildIcon(FontAwesome.COG, 12f));
    isets.addActionListener(e->{
      dialogsets.showDialog();
      System.out.printf("* DialogSets Size: %s%n", dialogsets.getSize());
    });
    JMenuItem iupdate = new JMenuItem("Update");
    iupdate.setIcon(IconFontSwing.buildIcon(FontAwesome.REFRESH, 12f));
    //JCheckBoxMenuItem irec = new JCheckBoxMenuItem("Record", IconFontSwing.buildIcon(FontAwesome.CIRCLE, 12f, Color.RED));
    JMenuItem irec = new JMenuItem("Record");
    irec.setIcon(IconFontSwing.buildIcon(FontAwesome.CIRCLE_O, 12f, Color.BLACK));
    irec.addActionListener(e->{
      settings.setRecord(!settings.isRecord());
      if(settings.isRecord()) {
        irec.setIcon(IconFontSwing.buildIcon(FontAwesome.CIRCLE, 12f, Color.RED));
      }
      else {
        irec.setIcon(IconFontSwing.buildIcon(FontAwesome.CIRCLE_O, 12f, Color.BLACK));
      }
      irec.repaint();
    });
    JMenuItem iplay = new JMenuItem("Play");
    iplay.setIcon(IconFontSwing.buildIcon(FontAwesome.PLAY_CIRCLE, 12f));
    JMenuItem iexit = new JMenuItem("Exit");
    iexit.setIcon(IconFontSwing.buildIcon(FontAwesome.SIGN_OUT, 12f));
    iexit.addActionListener(e->System.exit(0));
    
    menu.add(file);
    menu.add(isets);
    menu.add(iupdate);
    menu.add(irec);
    menu.add(iplay);
    menu.add(new JPopupMenu.Separator());
    menu.add(iexit);
    return menu;
  }
  
  public Settings settings() {
    return settings;
  }
  
  @Override
  public void paint(Graphics g) {
    super.paint(g);
    g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);
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
      gg.setColor(settings.getCurrentColor().color());
      gg.draw(rect);
    }
  }
  
  private void addAction(Consumer<Autotune> c) {
    if(settings.isRecord()) actions.add(c);
  }
  
  @Override
  public void mouseClicked(MouseEvent e) {
    System.out.printf("* mouseClicked( %s )%n", e);
    if(MouseEvent.BUTTON1 == e.getButton()) {
      current = new TextPoint(e.getPoint(), settings.getFont(), settings.getCurrentColor().color());
      textPoints.add(current);
    }
    else if(MouseEvent.BUTTON3 == e.getButton()) {
      if(e.isControlDown()) {
        popup.show(this, e.getX(), e.getY());
      }
      else {
        rect = null;
        repaint();
      }
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
        addAction(Autotune.CHAR_MAP.get(e.isShiftDown() ? '?' : '/'));
        break;
      case 0x10000c7:
        addAction(Autotune.CHAR_MAP.get(e.isShiftDown() || Character.isUpperCase(e.getKeyChar()) ? 'Ç' : 'ç'));
        break;
      default:
        addAction(a->a.keydo(e));
        break;
    }
  }
  
  private int mod(KeyEvent e) {
    int mod = 1;
    if(e.isShiftDown() || Character.isUpperCase(e.getKeyChar())) {
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
  
  @Override 
  public void mouseMoved(MouseEvent e) {
    addAction(a->a.mouseMove(e.getPoint()));
  }

  @Override
  public String toString() {
    return "EditablePanel{" + "settings=" + settings + ", textPoints=" + textPoints + ", actions=" + actions + ", current=" + current + ", rect=" + rect + '}';
  }
  
}
