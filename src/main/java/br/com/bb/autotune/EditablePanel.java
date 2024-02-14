/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune;

import br.com.bb.autotune.action.PanelAction;
import br.com.bb.autotune.action.shape.AbstractShapeAction;
import br.com.bb.autotune.action.shape.ArrowDownAction;
import br.com.bb.autotune.action.shape.ArrowLeftAction;
import br.com.bb.autotune.action.shape.ArrowRightAction;
import br.com.bb.autotune.action.shape.ArrowUpAction;
import br.com.bb.autotune.action.shape.CircleAction;
import br.com.bb.autotune.action.shape.FreeDrawAction;
import br.com.bb.autotune.action.shape.LineAction;
import br.com.bb.autotune.action.shape.RectangleAction;
import br.com.bb.autotune.action.shape.RectangleSelectionAction;
import br.com.bb.autotune.action.shape.TriangleAction;
import br.com.bb.autotune.action.text.AcuteAction;
import br.com.bb.autotune.action.text.BackspaceAction;
import br.com.bb.autotune.action.text.CircumflexAction;
import br.com.bb.autotune.action.text.CrasisAction;
import br.com.bb.autotune.action.text.EnterAction;
import br.com.bb.autotune.action.text.CharacterAction;
import br.com.bb.autotune.action.text.TabAction;
import br.com.bb.autotune.action.text.TextAction;
import br.com.bb.autotune.action.text.TildeAction;
import br.com.bb.autotune.settings.DialogSettings;
import br.com.bb.autotune.settings.DrawSettings;
import br.com.bb.autotune.settings.DrawSettings.DrawMode;
import br.com.bb.autotune.settings.Settings;
import br.com.bb.autotune.settings.SettingsChangeEvent;
import java.awt.AWTException;
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
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;

/**
 *
 * @author F6036477
 */
public class EditablePanel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener, KeyListener, Consumer<SettingsChangeEvent> {
  
  private final Autotune auto;
  
  private final JFrame owner;
  
  private final Settings settings;
  
  private final DialogSettings dialogsets;
  
  private final List<TextPoint> textPoints;
  
  private final List<ShapeInfo> shapes;
  
  private final List<PanelAction> recordActions;
  
  private final List<PanelAction> panelActions;
  
  private final JPopupMenu popupMenu;
  
  private final JMenuItem drawItem;
  
  private final Reference<BufferedImage> background;
  
  private final Reference<TextPoint> currentText;
  
  private final Reference<ShapeInfo> currentShape;
  
  private final Reference<ShapeInfo> selection;
  
  private final AtomicInteger actionIndex;
  
  private final KeyEvent[] keyEvents;
  
  private final MouseEvent[] mouseEvents;
  
  private final List<TextAction> textActions;
  
  private final List<AbstractShapeAction> shapeActions;
  
  private final Dimension screenSize;
  
  private float xmod, ymod;
  
  public EditablePanel(JFrame owner, Autotune a) {
    this.auto = Objects.requireNonNull(a);
    this.owner = Objects.requireNonNull(owner);
    this.recordActions = new LinkedList<>();
    this.panelActions = new LinkedList<>();
    this.textPoints = new LinkedList();
    this.shapes = new LinkedList();
    this.drawItem = new JMenuItem("Draw");
    this.popupMenu = createPopupMenu();
    this.settings = new Settings();
    settings.addListener(this);
    this.dialogsets = new DialogSettings(owner, settings);
    this.currentText = new Reference();
    this.currentShape = new Reference();
    this.selection = new Reference();
    this.actionIndex = new AtomicInteger(0);
    this.keyEvents = new KeyEvent[3];
    this.mouseEvents = new MouseEvent[3];
    this.textActions = new ArrayList<>();
    this.textActions.add(new EnterAction());
    this.textActions.add(new TabAction());
    this.textActions.add(new BackspaceAction());
    this.textActions.add(new AcuteAction());
    this.textActions.add(new CrasisAction());
    this.textActions.add(new TildeAction());
    this.textActions.add(new CircumflexAction());
    this.textActions.add(new CharacterAction());
    this.shapeActions = new ArrayList<>();
    this.shapeActions.add(new ArrowDownAction(settings));
    this.shapeActions.add(new ArrowLeftAction(settings));
    this.shapeActions.add(new ArrowRightAction(settings));
    this.shapeActions.add(new ArrowUpAction(settings));
    this.shapeActions.add(new CircleAction(settings));
    this.shapeActions.add(new FreeDrawAction(settings));
    this.shapeActions.add(new LineAction(settings));
    this.shapeActions.add(new RectangleAction(settings));
    this.shapeActions.add(new TriangleAction(settings));
    this.shapeActions.add(new RectangleSelectionAction(settings));
    try {
      Robot r = new Robot();
      this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      this.background = new Reference(r.createScreenCapture(new Rectangle(screenSize)));
    }
    catch(AWTException e) {
      throw new RuntimeException(e);
    }
    this.addKeyListener(this);
    this.addMouseListener(this);
    this.addMouseMotionListener(this);
    this.addMouseWheelListener(this);
  }
  
  public Settings getSettings() {
    return settings;
  }
  
  public DialogSettings getDialogSettings() {
    return this.dialogsets;
  }
  
  public JPopupMenu getPopupMenu() {
    return popupMenu;
  }
  
  public Autotune getAutotune() {
    return auto;
  }
  
  public KeyEvent[] getLastKeyEvents() {
    return keyEvents;
  }
  
  public MouseEvent[] getLastMouseEvents() {
    return mouseEvents;
  }
  
  public AtomicInteger getActionIndex() {
    return actionIndex;
  }
  
  public Reference<ShapeInfo> getSelectionShape() {
    return selection;
  }
  
  public Reference<ShapeInfo> getCurrentShape() {
    return currentShape;
  }
  
  public Reference<TextPoint> getCurrentText() {
    return currentText;
  }
  
  public Reference<BufferedImage> getBackgroundImage() {
    return background;
  }

  public JFrame getOwner() {
    return owner;
  }

  public List<TextPoint> getTextPoints() {
    return textPoints;
  }

  public List<ShapeInfo> getShapes() {
    return shapes;
  }

  public List<PanelAction> getPanelActions() {
    return panelActions;
  }
  
  public List<PanelAction> getRecordActions() {
    return recordActions;
  }
  
  public List<TextAction> getTextActions() {
    return textActions;
  }

  public List<AbstractShapeAction> getShapeActions() {
    return shapeActions;
  }

  public Dimension getScreenSize() {
    return screenSize;
  }
  
  public EditablePanel addRecordAction(PanelAction a) {
    if(settings.isRecord()) recordActions.add(a);
    return this;
  }
  
  public EditablePanel addPanelAction(PanelAction a) {
    panelActions.add(a);
    return this;
  }
  
  public void saveImage() {
    JFileChooser chooser = new JFileChooser();
    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    chooser.setMultiSelectionEnabled(false);
    int opt = chooser.showSaveDialog(EditablePanel.this);
    if(JFileChooser.APPROVE_OPTION == opt) {
      File f = chooser.getSelectedFile();
      try {
        BufferedImage img = new BufferedImage(background.get().getWidth(), background.get().getHeight(), background.get().getType());
        EditablePanel.this.paint(img.getGraphics());
        String message = "Image saved successfully!";
        if(selection.isPresent()) {
          message = "Image selection saved successfully!";
          Rectangle r = selection.get().getShape().getBounds();
          img = img.getSubimage(r.x, r.y, r.width, r.height);
        }
        ImageIO.write(img, "jpg", f);
        JOptionPane.showMessageDialog(EditablePanel.this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
      }
      catch(IOException e) {
        JOptionPane.showMessageDialog(EditablePanel.this, e, "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }
  
  public void update() {
    owner.setVisible(false);
    auto.delay(100);
    actions.stream()
        .skip(actionIndex.get())
        .peek(c->actionIndex.incrementAndGet())
        .peek(c->auto.delay(10))
        .forEach(c->c.accept(auto));
    auto.delay(100);
    background.set(auto.takeScreenshot());
    owner.setVisible(true);
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
    }
    else {
      settings.getDrawSettings().setDrawMode(DrawSettings.DrawMode.RECTANGLE);
    }
    toggleDrawItem();
  }
  
  private JPopupMenu createPopupMenu() {
    IconFontSwing.register(FontAwesome.getIconFont());
    JPopupMenu menu = new JPopupMenu();
    
    JMenu actionsMenu = new JMenu("Actions");
    actionsMenu.setIcon(IconFontSwing.buildIcon(FontAwesome.BULLSEYE, 12f));
    JMenuItem icopy = new JMenuItem("Copy Screenshot");
    icopy.setIcon(IconFontSwing.buildIcon(FontAwesome.CLONE, 12f));
    icopy.addActionListener(e->addAction(a->a.copyScreenshot()));
    JMenuItem idelay = new JMenuItem("Delay");
    idelay.setIcon(IconFontSwing.buildIcon(FontAwesome.CLOCK_O, 12f));
    idelay.addActionListener(e->addAction(a->a.delay(settings.getAutoDelay())));
    JMenuItem itype = new JMenuItem("Type Clipboard");
    itype.setIcon(IconFontSwing.buildIcon(FontAwesome.KEYBOARD_O, 12f));
    itype.addActionListener(e->addAction(a->a.typeClipboard()));
    actionsMenu.add(icopy);
    actionsMenu.add(idelay);
    actionsMenu.add(itype);
    
    JMenuItem isaveimg = new JMenuItem("Save Image");
    isaveimg.setIcon(IconFontSwing.buildIcon(FontAwesome.FLOPPY_O, 12f));
    isaveimg.addActionListener(a->saveImage());
    
    JMenuItem isets = new JMenuItem("Settings");
    isets.setIcon(IconFontSwing.buildIcon(FontAwesome.COG, 12f));
    isets.addActionListener(e->dialogsets.showDialog());
    
    JMenu shapesMenu = new JMenu("Shapes");
    shapesMenu.setIcon(IconFontSwing.buildIcon(FontAwesome.OBJECT_GROUP, 12f));
    for(int i = 0; i < DialogSettings.SHAPE_OPTIONS.length; i++) {
      JLabel l = (JLabel) DialogSettings.SHAPE_OPTIONS[i];
      JMenuItem item = new JMenuItem(l.getText());
      item.setIcon(l.getIcon());
      final int idx = i;
      item.addActionListener(e->{
        settings.getDrawSettings().setDrawMode(DrawMode.values()[idx]);
      });
      shapesMenu.add(item);
    }
    
    drawItem.setIcon(IconFontSwing.buildIcon(FontAwesome.PAINT_BRUSH, 12f, Settings.GREEN_DARKEN3.color()));
    drawItem.addActionListener(e->toggleDrawSettings());
    
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
    
    JMenuItem iupdate = new JMenuItem("Update");
    iupdate.setIcon(IconFontSwing.buildIcon(FontAwesome.REFRESH, 12f));
    iupdate.addActionListener(e->update());
    
    JMenuItem irev = new JMenuItem("Rewind");
    irev.setIcon(IconFontSwing.buildIcon(FontAwesome.ANGLE_DOUBLE_LEFT, 12f));
    irev.addActionListener(e->actionIndex.set(0));
    
    JMenuItem iplay = new JMenuItem("Play");
    iplay.setIcon(IconFontSwing.buildIcon(FontAwesome.PLAY_CIRCLE, 12f));
    iplay.addActionListener(e->{
      actionIndex.set(0);
      update();
    });
    
    JMenuItem iexit = new JMenuItem("Exit");
    iexit.setIcon(IconFontSwing.buildIcon(FontAwesome.SIGN_OUT, 12f));
    iexit.addActionListener(e->System.exit(0));
    
    menu.add(actionsMenu);
    menu.add(new JPopupMenu.Separator());
    menu.add(isaveimg);
    menu.add(isets);
    menu.add(shapesMenu);
    menu.add(drawItem);
    menu.add(irec);
    menu.add(iupdate);
    menu.add(irev);
    menu.add(iplay);
    menu.add(new JPopupMenu.Separator());
    menu.add(iexit);
    return menu;
  }
  
  @Override
  public void paint(Graphics g) {
    super.paint(g);
    g.drawImage(background.get(), 0, 0, this.getWidth(), this.getHeight(), this);
    textPoints.forEach(t->{
      g.setColor(t.getColor());
      g.setFont(t.getFont());
      g.drawString(t.text().toString(), t.getPoint().x, t.getPoint().y);
    });
    shapes.forEach(s->{
      Graphics2D gg = (Graphics2D) g;
      gg.setStroke(s.getStroke());
      gg.setColor(s.getColor());
      if(s.isFillEnabled()) {
        gg.fill(s.getShape());
      }
      else {
        gg.draw(s.getShape());
      }
    });
    if(settings.getDrawSettings().isDrawModeEnabled() && currentShape.isPresent()) {
      Graphics2D gg = (Graphics2D) g;
      gg.setStroke(currentShape.get().getStroke());
      gg.setColor(currentShape.get().getColor());
      if(currentShape.get().isFillEnabled()) {
        gg.fill(currentShape.get().getShape());
      }
      else {
        gg.draw(currentShape.get().getShape());
      }
    }
    else if(selection.isPresent()) {
      Graphics2D gg = (Graphics2D) g;
      gg.setStroke(selection.get().getStroke());
      gg.setColor(settings.getCurrentColor().color());
      gg.draw(selection.get().getShape());
    }
    Dimension size = getSize();
    xmod = screenSize.width / Integer.valueOf(size.width).floatValue();
    ymod = screenSize.height / Integer.valueOf(size.height).floatValue();
  }
  
  private void addMouseEvent(MouseEvent e) {
    mouseEvents[2] = mouseEvents[1];
    mouseEvents[1] = mouseEvents[0];
    mouseEvents[0]  = e;
  }
  
  @Override
  public void mouseClicked(MouseEvent e) {
    addMouseEvent(e);
    if(MouseEvent.BUTTON1 == e.getButton()) {
      currentText.set(new TextPoint(e.getPoint(), settings.getFont(), settings.getCurrentColor().color()));
      textPoints.add(currentText.get());
    }
    else if(MouseEvent.BUTTON3 == e.getButton()) {
      if(e.isControlDown()) {
        if(settings.isRecord()) {
          actions.remove(actions.size() -1);
        }
        popupMenu.show(this, e.getX(), e.getY());
      }
      else {
        selection.set(null);
        repaint();
      }
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {
    //System.out.printf("* mousePressed( %s )%n", e);
    addMouseEvent(e);
    mousedo(e);
    if(MouseEvent.BUTTON1 == e.getButton()) {
      selection.set(null);
    }
  }

  @Override public void mouseReleased(MouseEvent e) {
    //System.out.printf("* mouseReleased( %s )%n", e);
    addMouseEvent(e);
    if(currentShape.isPresent()) {
      shapes.add(currentShape.get());
      currentShape.set(null);
    }
    else {
      mousedo(e);
    }
  }

  @Override public void mouseEntered(MouseEvent e) {}

  @Override public void mouseExited(MouseEvent e) {}

  @Override public void keyTyped(KeyEvent e) {}
  
  private Point modPoint(Point p) {
    return new Point(Math.round(p.x * xmod), Math.round(p.y * ymod));
  }
  
  private void mousedo(MouseEvent e) {
    if(e.isControlDown()) return;
    switch(e.getID()) {
      //case MouseEvent.MOUSE_MOVED:
        //System.out.printf(">> action: mouseMove( %s )%n", modPoint(e.getPoint()));
        //addAction(a->a.mouseMove(modPoint(e.getPoint())));
        //break;
      case MouseEvent.MOUSE_PRESSED:
        System.out.printf(">> action: mouseMove( %s )%n", modPoint(e.getPoint()));
        addAction(a->a.mouseMove(modPoint(e.getPoint())));
        System.out.printf(">> action: mousePress( %d )%n", e.getButton());
        addAction(a->a.mousePress(e.getButton()));
        break;
      case MouseEvent.MOUSE_RELEASED:
        System.out.printf(">> action: mouseMove( %s )%n", modPoint(e.getPoint()));
        addAction(a->a.mouseMove(modPoint(e.getPoint())));
        System.out.printf(">> action: mouseRelease( %d )%n", e.getButton());
        addAction(a->a.mouseRelease(e.getButton()));
        break;
      default:
        break;
    }
  }
  
  private void keydo(KeyEvent e) {
    switch(e.getExtendedKeyCode()) {
      case 0x2f:
        System.out.printf(">> action: keydo( /? )%n");
        addAction(Autotune.CHAR_MAP.get(e.isShiftDown() ? '?' : '/'));
        break;
      case 0x10000c7:
        System.out.printf(">> action: keydo( ç )%n");
        addAction(Autotune.CHAR_MAP.get(e.isShiftDown() || Character.isUpperCase(e.getKeyChar()) ? 'Ç' : 'ç'));
        break;
      default:
        if(KeyEvent.KEY_PRESSED == e.getID()) {
          System.out.printf(">> action: keyPress( %s=%d )%n", e.getKeyChar(), e.getExtendedKeyCode());
        }
        else {
          System.out.printf(">> action: keyRelease( %s=%d )%n", e.getKeyChar(), e.getExtendedKeyCode());
        }
        addAction(a->a.keydo(e));
        break;
    }
  }
  
  @Override 
  public void keyPressed(KeyEvent e) {
    keydo(e);
  }
  
  @Override
  public void keyReleased(KeyEvent e) {
    keyEvents[0] = e;
    //System.out.printf("* keyReleased( %s )%n", e);
    if(KeyEvent.VK_DELETE == e.getKeyCode() && e.isControlDown()) {
      if(settings.isRecord()) {
        actions.remove(actions.size() -1);
      }
      if(selection.isPresent()) {
        textPoints.stream()
            .filter(t->selection.get().getShape().contains(t.getPoint()))
            .collect(Collectors.toList())
            .forEach(textPoints::remove);
        shapes.stream()
            .filter(s->selection.get().getShape().contains(s.getPoint()))
            .collect(Collectors.toList())
            .forEach(shapes::remove);
      }
      else {
        textPoints.clear();
        shapes.clear();
      }
      if(!textPoints.contains(currentText.get())){
        currentText.set(null);
      }
      selection.set(null);
    }
    else if(KeyEvent.VK_F1 == e.getKeyCode() && e.isControlDown()) {
      if(settings.isRecord()) {
        actions.remove(actions.size() -1);
      }
      update();
    }
    else {
      keydo(e);
      if(currentText.isPresent()) {
        textActions.stream()
            .filter(a->a.accept(keyEvents))
            .findFirst()
            .ifPresent(a->a.perform(keyEvents, currentText.get()));
      }
    }
    keyEvents[2] = keyEvents[1];
    keyEvents[1] = keyEvents[0];
    repaint();
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    addMouseEvent(e);
    shapeActions.stream()
        .filter(AbstractShapeAction::accept)
        .findFirst()
        .ifPresent(a->a.perform(e, 
            settings.getDrawSettings().isDrawModeEnabled() 
                ? currentShape : selection, shapes)
        );
    repaint();
  }
  
  @Override
  public void mouseWheelMoved(MouseWheelEvent e) {
    System.out.printf(">> action: mouseWheel( %d )%n", e.getWheelRotation());
    addRecordAction(a->a.mouseWheel(e.getWheelRotation()));
  }
  
  @Override 
  public void mouseMoved(MouseEvent e) {
    //System.out.printf(">> action: mouseMove( %s )%n", modPoint(e.getPoint()));
    //addAction(a->a.mouseMove(e.getPoint()));
  }

  @Override
  public String toString() {
    return "EditablePanel{" + "settings=" + settings + ", textPoints=" + textPoints + ", actions=" + actions + ", current=" + currentText + ", selection=" + selection + '}';
  }

}
