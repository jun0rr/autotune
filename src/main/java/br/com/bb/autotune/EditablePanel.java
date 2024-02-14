/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune;

import br.com.bb.autotune.action.CancelSelectionAction;
import br.com.bb.autotune.action.DefaultRecordAction;
import br.com.bb.autotune.action.DeleteSelectionAction;
import br.com.bb.autotune.action.FinishShapeDrawAction;
import br.com.bb.autotune.action.PanelAction;
import br.com.bb.autotune.action.RecordAction;
import br.com.bb.autotune.action.SaveImageAction;
import br.com.bb.autotune.action.SetCurrentTextAction;
import br.com.bb.autotune.action.ShowPopupMenuAction;
import br.com.bb.autotune.action.UpdateAction;
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
import br.com.bb.autotune.action.text.AbstractTextAction;
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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
public class EditablePanel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener, KeyListener, Consumer<SettingsChangeEvent> {
  
  private final Autotune auto;
  
  private final JFrame owner;
  
  private final Settings settings;
  
  private final DialogSettings dialogsets;
  
  private final List<TextPoint> textPoints;
  
  private final List<ShapeInfo> shapes;
  
  private final List<RecordAction> recordActions;
  
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
  
  private final List<AbstractTextAction> textActions;
  
  private final List<PanelAction> shapeActions;
  
  private final Dimension screenSize;
  
  private final SaveImageAction saveImageAction;
  
  private final UpdateAction updateAction;
  
  private float xmod, ymod;
  
  public EditablePanel(JFrame owner, Autotune a) {
    this.auto = Objects.requireNonNull(a);
    this.owner = Objects.requireNonNull(owner);
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
    this.recordActions = new LinkedList<>();
    this.panelActions = new LinkedList<>();
    this.panelActions.add(new CancelSelectionAction());
    this.panelActions.add(new DeleteSelectionAction());
    this.panelActions.add(new FinishShapeDrawAction());
    this.saveImageAction = new SaveImageAction();
    this.panelActions.add(saveImageAction);
    this.panelActions.add(new SetCurrentTextAction());
    this.panelActions.add(new ShowPopupMenuAction());
    this.updateAction = new UpdateAction();
    this.panelActions.add(updateAction);
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
    this.shapeActions.add(new ArrowDownAction());
    this.shapeActions.add(new ArrowLeftAction());
    this.shapeActions.add(new ArrowRightAction());
    this.shapeActions.add(new ArrowUpAction());
    this.shapeActions.add(new CircleAction());
    this.shapeActions.add(new FreeDrawAction());
    this.shapeActions.add(new LineAction());
    this.shapeActions.add(new RectangleAction());
    this.shapeActions.add(new TriangleAction());
    this.shapeActions.add(new RectangleSelectionAction());
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
  
  public List<RecordAction> getRecordActions() {
    return recordActions;
  }
  
  public List<AbstractTextAction> getTextActions() {
    return textActions;
  }

  public List<PanelAction> getShapeActions() {
    return shapeActions;
  }

  public Dimension getScreenSize() {
    return screenSize;
  }
  
  public EditablePanel addRecordAction(String name, Consumer<Autotune> c) {
    if(settings.isRecord()) recordActions.add(new DefaultRecordAction(name, c));
    return this;
  }
  
  public EditablePanel addRecordAction(Consumer<Autotune> c, String fmt, Object...args) {
    if(settings.isRecord()) recordActions.add(new DefaultRecordAction(c, fmt, args));
    return this;
  }
  
  public EditablePanel addPanelAction(PanelAction a) {
    panelActions.add(a);
    return this;
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
    icopy.addActionListener(e->addRecordAction("copyScreenshot", a->a.copyScreenshot()));
    JMenuItem idelay = new JMenuItem("Delay");
    idelay.setIcon(IconFontSwing.buildIcon(FontAwesome.CLOCK_O, 12f));
    idelay.addActionListener(e->addRecordAction(
        a->a.delay(settings.getAutoDelay()), 
        "delay( %d )", settings.getAutoDelay())
    );
    JMenuItem itype = new JMenuItem("Type Clipboard");
    itype.setIcon(IconFontSwing.buildIcon(FontAwesome.KEYBOARD_O, 12f));
    itype.addActionListener(e->addRecordAction("typeClipboard", a->a.typeClipboard()));
    actionsMenu.add(icopy);
    actionsMenu.add(idelay);
    actionsMenu.add(itype);
    
    JMenuItem isaveimg = new JMenuItem("Save Image");
    isaveimg.setIcon(IconFontSwing.buildIcon(FontAwesome.FLOPPY_O, 12f));
    isaveimg.addActionListener(a->saveImageAction.perform(this));
    
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
    iupdate.addActionListener(e->updateAction.perform(this));
    
    JMenuItem irev = new JMenuItem("Rewind");
    irev.setIcon(IconFontSwing.buildIcon(FontAwesome.ANGLE_DOUBLE_LEFT, 12f));
    irev.addActionListener(e->actionIndex.set(0));
    
    JMenuItem iplay = new JMenuItem("Play");
    iplay.setIcon(IconFontSwing.buildIcon(FontAwesome.PLAY_CIRCLE, 12f));
    iplay.addActionListener(e->{
      actionIndex.set(0);
      updateAction.perform(this);
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
      System.out.printf("* selection.isPresent(): %s%n", selection);
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
  
  private void addKeyEvent(KeyEvent e) {
    keyEvents[2] = keyEvents[1];
    keyEvents[1] = keyEvents[0];
    keyEvents[0] = e;
  }
  
  @Override
  public void mouseClicked(MouseEvent e) {
    addMouseEvent(e);
    addKeyEvent(null);
    panelActions.stream()
        .filter(a->a.accept(this))
        .forEach(a->a.perform(this));
  }

  @Override
  public void mousePressed(MouseEvent e) {
    addMouseEvent(e);
    addKeyEvent(null);
    mousedo(e);
    panelActions.stream()
        .filter(a->a.accept(this))
        .forEach(a->a.perform(this));
  }

  @Override public void mouseReleased(MouseEvent e) {
    addMouseEvent(e);
    addKeyEvent(null);
    mousedo(e);
    panelActions.stream()
        .filter(a->a.accept(this))
        .forEach(a->a.perform(this));
  }

  private Point modPoint(Point p) {
    return new Point(Math.round(p.x * xmod), Math.round(p.y * ymod));
  }
  
  private void mousedo(MouseEvent e) {
    if(e.isControlDown()) return;
    Point mod = modPoint(e.getPoint());
    switch(e.getID()) {
      //case MouseEvent.MOUSE_MOVED:
        //System.out.printf(">> action: mouseMove( %s )%n", modPoint(e.getPoint()));
        //addAction(a->a.mouseMove(modPoint(e.getPoint())));
        //break;
      case MouseEvent.MOUSE_PRESSED:
        System.out.printf(">> action: mouseMove( %s )%n", mod);
        addRecordAction(a->a.mouseMove(mod), "mouseMove( %d, %d )", mod.x, mod.y);
        System.out.printf(">> action: mousePress( %d )%n", e.getButton());
        addRecordAction(a->a.mousePress(e.getButton()),  "mousePress( %d )", e.getButton());
        break;
      case MouseEvent.MOUSE_RELEASED:
        System.out.printf(">> action: mouseMove( %s )%n", mod);
        addRecordAction(a->a.mouseMove(mod), "mouseMove( %d, %d )", mod.x, mod.y);
        System.out.printf(">> action: mouseRelease( %d )%n", e.getButton());
        addRecordAction(a->a.mouseRelease(e.getButton()),  "mouseRelease( %d )", e.getButton());
        break;
      default:
        break;
    }
  }
  
  private void keydo(KeyEvent e) {
    switch(e.getExtendedKeyCode()) {
      case 0x2f:
        System.out.printf(">> action: keydo( /? )%n");
        addRecordAction("keyType( ?/ )", Autotune.CHAR_MAP.get(e.isShiftDown() ? '?' : '/'));
        break;
      case 0x10000c7:
        System.out.printf(">> action: keydo( ç )%n");
        addRecordAction("keyType( çÇ )", Autotune.CHAR_MAP.get(e.isShiftDown() || Character.isUpperCase(e.getKeyChar()) ? 'Ç' : 'ç'));
        break;
      default:
        if(KeyEvent.KEY_PRESSED == e.getID()) {
          System.out.printf(">> action: keyPress( %s=%d )%n", e.getKeyChar(), e.getExtendedKeyCode());
        }
        else {
          System.out.printf(">> action: keyRelease( %s=%d )%n", e.getKeyChar(), e.getExtendedKeyCode());
        }
        addRecordAction(a->a.keydo(e), "keyType( %s=%d )", e.getKeyChar(), e.getExtendedKeyCode());
        break;
    }
  }
  
  @Override 
  public void keyPressed(KeyEvent e) {
    addKeyEvent(e);
    addMouseEvent(null);
    keydo(e);
    panelActions.stream()
        .filter(a->a.accept(this))
        .forEach(a->a.perform(this));
  }
  
  @Override
  public void keyReleased(KeyEvent e) {
    addKeyEvent(e);
    addMouseEvent(null);
    keydo(e);
    panelActions.stream()
        .filter(a->a.accept(this))
        .forEach(a->a.perform(this));
    textActions.stream()
        .filter(a->a.accept(this))
        .forEach(a->a.perform(this));
    repaint();
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    addMouseEvent(e);
    addKeyEvent(null);
    panelActions.stream()
        .filter(a->a.accept(this))
        .forEach(a->a.perform(this));
    shapeActions.stream()
        .filter(a->a.accept(this))
        .forEach(a->a.perform(this));
    repaint();
  }
  
  @Override
  public void mouseWheelMoved(MouseWheelEvent e) {
    System.out.printf(">> action: mouseWheel( %d )%n", e.getWheelRotation());
    addRecordAction(a->a.mouseWheel(e.getWheelRotation()), "mouseWheel( %d )", e.getWheelRotation());
    addKeyEvent(null);
  }
  
  @Override public void mouseMoved(MouseEvent e) {}

  @Override public void mouseEntered(MouseEvent e) {}

  @Override public void mouseExited(MouseEvent e) {}

  @Override public void keyTyped(KeyEvent e) {}
  
  @Override
  public String toString() {
    return "EditablePanel{" + "settings=" + settings + ", textPoints=" + textPoints + ", recordActions=" + recordActions + ", currentText=" + currentText + ", currentShape=" + currentShape + ", actionIndex=" + actionIndex + ", screenSize=" + screenSize + '}';
  }
  
}
