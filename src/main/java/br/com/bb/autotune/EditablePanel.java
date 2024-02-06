/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune;

import br.com.bb.autotune.settings.DialogSettings;
import br.com.bb.autotune.settings.DrawSettings;
import br.com.bb.autotune.settings.DrawSettings.DrawMode;
import br.com.bb.autotune.settings.Settings;
import br.com.bb.autotune.settings.SettingsChangeEvent;
import java.awt.AWTException;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
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
public class EditablePanel extends JPanel implements MouseListener, MouseMotionListener, KeyListener, Consumer<SettingsChangeEvent> {
  
  private final Autotune auto;
  
  private final Settings settings;
  
  private final DialogSettings dialogsets;
  
  private final List<TextPoint> textPoints;
  
  private final List<ShapeInfo> shapes;
  
  private final List<Consumer<Autotune>> actions;
  
  private final JPopupMenu popup;
  
  private final JMenuItem drawItem;
  
  private BufferedImage background;
  
  private TextPoint currentText;
  
  private Rectangle select;
  
  private ShapeInfo currentShape;
  
  private KeyEvent oldKeyEvent;
  
  private KeyEvent prevKeyEvent;
  
  public EditablePanel(JFrame owner, Autotune a) {
    this.auto = Objects.requireNonNull(a);
    this.actions = new LinkedList<>();
    this.textPoints = new LinkedList();
    this.shapes = new LinkedList();
    this.drawItem = new JMenuItem("Draw");
    this.popup = createPopupMenu();
    this.settings = new Settings();
    settings.addListener(this);
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
  
  public void accept(SettingsChangeEvent e) {
    switch(e) {
      case COLOR:
        break;
      case DELAY:
        break;
      case DRAW_FILL:
        break;
      case DRAW_MODE:
        toggleDrawItem();
        break;
      case DRAW_THICKNESS:
        break;
      case FONT:
        break;
      case RECORD:
        break;
      default:
        break;
    }
  }
  
  private void toggleDrawItem() {
    if(settings.getDrawSettings().isDrawModeEnabled()) {
      drawItem.setText("Select");
      drawItem.setIcon(IconFontSwing.buildIcon(FontAwesome.EXTERNAL_LINK, 12f));
    }
    else {
      drawItem.setText("Draw");
      drawItem.setIcon(IconFontSwing.buildIcon(FontAwesome.PAINT_BRUSH, 12f, Settings.GREEN_DARKEN3.color()));
    }
  }
  
  private void toggleDrawSettings() {
    if(settings.getDrawSettings().isDrawModeEnabled()) {
      settings.getDrawSettings().setDrawMode(DrawSettings.DrawMode.NONE);
      //drawItem.setText("Draw");
      //drawItem.setIcon(IconFontSwing.buildIcon(FontAwesome.PAINT_BRUSH, 12f, Settings.GREEN_DARKEN3.color()));
    }
    else {
      settings.getDrawSettings().setDrawMode(DrawSettings.DrawMode.RECTANGLE);
      //drawItem.setText("Select");
      //drawItem.setIcon(IconFontSwing.buildIcon(FontAwesome.EXTERNAL_LINK, 12f));
    }
    toggleDrawItem();
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
    });
    
    drawItem.setIcon(IconFontSwing.buildIcon(FontAwesome.PAINT_BRUSH, 12f, Settings.GREEN_DARKEN3.color()));
    drawItem.addActionListener(e->toggleDrawSettings());
    
    JMenuItem iupdate = new JMenuItem("Update");
    iupdate.setIcon(IconFontSwing.buildIcon(FontAwesome.REFRESH, 12f));
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
    menu.add(drawItem);
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
    textPoints.forEach(t->{
      g.setColor(t.getColor());
      g.setFont(t.getFont());
      g.drawString(t.text().toString(), t.getPoint().x, t.getPoint().y);
    });
    shapes.forEach(s->{
      Graphics2D gg = (Graphics2D) g;
      gg.setStroke(s.getStroke());
      gg.setColor(s.getColor());
      if(s.isFill()) {
        gg.fill(s.getShape());
      }
      else {
        gg.draw(s.getShape());
      }
    });
    if(settings.getDrawSettings().isDrawModeEnabled() && currentShape != null) {
      Graphics2D gg = (Graphics2D) g;
      gg.setStroke(currentShape.getStroke());
      gg.setColor(currentShape.getColor());
      if(settings.getDrawSettings().isFillEnabled()) {
        gg.fill(currentShape.getShape());
      }
      else {
        gg.draw(currentShape.getShape());
      }
    }
    else if(select != null) {
      Graphics2D gg = (Graphics2D) g;
      gg.setStroke(new BasicStroke(2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{5f}, 0));
      gg.setColor(settings.getCurrentColor().color());
      gg.draw(select);
    }
  }
  
  private void addAction(Consumer<Autotune> c) {
    if(settings.isRecord()) actions.add(c);
  }
  
  @Override
  public void mouseClicked(MouseEvent e) {
    System.out.printf("* mouseClicked( %s )%n", e);
    if(MouseEvent.BUTTON1 == e.getButton()) {
      currentText = new TextPoint(e.getPoint(), settings.getFont(), settings.getCurrentColor().color());
      textPoints.add(currentText);
    }
    else if(MouseEvent.BUTTON3 == e.getButton()) {
      if(e.isControlDown()) {
        popup.show(this, e.getX(), e.getY());
      }
      else {
        select = null;
        repaint();
      }
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {
    System.out.printf("* mousePressed( %s )%n", e);
    actions.add(a->a.mousedo(e));
    select = new Rectangle(e.getPoint().x, e.getPoint().y, 0, 0);
  }

  @Override public void mouseReleased(MouseEvent e) {
    System.out.printf("* mouseReleased( %s )%n", e);
    if(currentShape != null) {
      shapes.add(currentShape);
      currentShape = null;
    }
    else {
      actions.add(a->a.mousedo(e));
    }
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
      if(select != null) {
        textPoints.stream()
            .filter(t->select.contains(t.getPoint()))
            .collect(Collectors.toList())
            .forEach(textPoints::remove);
        shapes.stream()
            .filter(s->select.contains(s.getPoint()))
            .collect(Collectors.toList())
            .forEach(shapes::remove);
      }
      else {
        textPoints.clear();
      }
      if(!textPoints.contains(currentText)){
        currentText = null;
      }
      select = null;
    }
    else {
      keydo(e);
      if(currentText != null) {
        if(KeyEvent.VK_ENTER == e.getKeyCode()) {
          currentText.text().append('\n');
        }
        else if(KeyEvent.VK_TAB == e.getKeyCode()) {
          currentText.text().append('\t');
        }
        else if(KeyEvent.VK_BACK_SPACE == e.getKeyCode()) {
          currentText.text().deleteCharAt(currentText.text().length() -1);
        }
        else if(prevKeyEvent != null && prevKeyEvent.getExtendedKeyCode() == 129) {
          int code = (mod(prevKeyEvent) * prevKeyEvent.getExtendedKeyCode() -1) * mod(e) * e.getExtendedKeyCode();
          if(Autotune.KEYCODES_MAP.containsKey(code)) {
            currentText.text().append(Autotune.KEYCODES_MAP.get(code));
          }
        }
        else if(oldKeyEvent != null && oldKeyEvent.getExtendedKeyCode() == 129 && prevKeyEvent.getExtendedKeyCode() == 16) {
          int code = (mod(oldKeyEvent) * oldKeyEvent.getExtendedKeyCode() -1) * mod(e) * e.getExtendedKeyCode();
          if(Autotune.KEYCODES_MAP.containsKey(code)) {
            currentText.text().append(Autotune.KEYCODES_MAP.get(code));
          }
        }
        else if(prevKeyEvent != null && prevKeyEvent.getExtendedKeyCode() == 131) {
          int code = (mod(prevKeyEvent) * prevKeyEvent.getExtendedKeyCode() -1) * mod(e) * e.getExtendedKeyCode();
          if(Autotune.KEYCODES_MAP.containsKey(code)) {
            currentText.text().append(Autotune.KEYCODES_MAP.get(code));
          }
        }
        else if(oldKeyEvent != null && oldKeyEvent.getExtendedKeyCode() == 131 && prevKeyEvent.getExtendedKeyCode() == 16) {
          int code = (mod(oldKeyEvent) * oldKeyEvent.getExtendedKeyCode() -1) * mod(e) * e.getExtendedKeyCode();
          if(Autotune.KEYCODES_MAP.containsKey(code)) {
            currentText.text().append(Autotune.KEYCODES_MAP.get(code));
          }
        }
        else if(Autotune.CHAR_MAP.containsKey(e.getKeyChar())) {
          currentText.text().append(e.getKeyChar());
        }
      }
    }
    oldKeyEvent = prevKeyEvent;
    prevKeyEvent = e;
    repaint();
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    int x = e.getPoint().x;
    int y = e.getPoint().y;
    if(settings.getDrawSettings().isDrawModeEnabled()) {
      if(currentShape == null) {
        currentShape = new ShapeInfo(e.getPoint(),
            settings.getDrawSettings().getShape(x, y, 0, 0),
            settings.getDrawSettings().getStroke(),
            settings.getCurrentColor().color(),
            settings.getDrawSettings().isFillEnabled()
        );
      }
      else if(DrawMode.LINE == settings.getDrawSettings().getDrawMode()) {
        Polygon p = new Polygon();
        p.addPoint(currentShape.getPoint().x, currentShape.getPoint().y);
        p.addPoint(x, y);
        currentShape = new ShapeInfo(currentShape.getPoint(), p,
            settings.getDrawSettings().getStroke(), 
            settings.getCurrentColor().color(), false
        );
      }
      else if(DrawMode.FREE == settings.getDrawSettings().getDrawMode()) {
        Polygon p = new Polygon();
        p.addPoint(currentShape.getPoint().x, currentShape.getPoint().y);
        p.addPoint(x, y);
        shapes.add(new ShapeInfo(currentShape.getPoint(), p,
            settings.getDrawSettings().getStroke(), 
            settings.getCurrentColor().color(), false
        ));
        p = new Polygon();
        p.addPoint(x, y);
        currentShape = new ShapeInfo(e.getPoint(), p,
            settings.getDrawSettings().getStroke(), 
            settings.getCurrentColor().color(), false
        );
      }
      else {
        Point before = currentShape.getPoint();
        int sx = Math.min(x, before.x);
        int sy = Math.min(y, before.y);
        int sw = Math.max(x, before.x) - sx;
        int sh = Math.max(y, before.y) - sy;
        currentShape = new ShapeInfo(before,
            settings.getDrawSettings().getShape(sx, sy, sw, sh),
            settings.getDrawSettings().getStroke(),
            settings.getCurrentColor().color(),
            settings.getDrawSettings().isFillEnabled()
        );
      }
    }
    else {
      if(select == null) {
        select = new Rectangle(x, y, 0, 0);
      }
      else {
        int sx = Math.min(x, select.x);
        int sy = Math.min(y, select.y);
        int sw = Math.max(select.x + select.width - x, x - select.x);
        int sh = Math.max(select.y + select.height - y, y - select.y);
        select = new Rectangle(sx, sy, sw, sh);
      }
    }
    repaint();
  }
  
  @Override 
  public void mouseMoved(MouseEvent e) {
    addAction(a->a.mouseMove(e.getPoint()));
  }

  @Override
  public String toString() {
    return "EditablePanel{" + "settings=" + settings + ", textPoints=" + textPoints + ", actions=" + actions + ", current=" + currentText + ", rect=" + select + '}';
  }
  
}
