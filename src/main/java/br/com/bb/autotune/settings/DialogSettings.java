/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.settings;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;

/**
 *
 * @author Juno
 */
public class DialogSettings extends JDialog implements Consumer<SettingsChangeEvent> {
  
  public static final String ICON_PATH = "/gear.png";
  
  private final Settings settings;
  
  private final Frame owner;
  
  private final JTextField fontFamily;
  
  private final JComboBox fontStyle;
  
  private final SpinnerNumberModel fontSizeModel;
    
  private final JSpinner fontSize;
  
  private final JTextField currentColor;
  
  private final List<ColorDisplay> lastColors;
  
  public DialogSettings(Frame owner, Settings ps) {
    super(owner, "Settings", true);
    this.owner = owner;
    this.settings = Objects.requireNonNull(ps).addListener(this);
    this.fontFamily = new JTextField(settings.getFont().getFamily());
    this.fontStyle = new JComboBox(new Object[]{"Plain", "Bold", "Italic", "Bold+Italic"});
    this.fontSizeModel = new SpinnerNumberModel(settings.getFont().getSize(), 1, 60, 1);
    this.fontSize = new JSpinner(fontSizeModel);
    this.currentColor = new JTextField(settings.getCurrentColor().colorHex());
    this.lastColors = new ArrayList<>(4);
    try {
      Image icon = ImageIO.read(getClass().getResourceAsStream(ICON_PATH));
      setIconImage(icon);
    }
    catch(IOException e) {
      throw new RuntimeException(e);
    }
    GridBagLayout grid = new GridBagLayout();
    setLayout(grid);
    populate();
    pack();
  }
  
  public void showDialog() {
    Dimension fd = owner.getSize();
    Point fl = owner.getLocation();
    int x = fl.x;
    int y = fl.y;
    if(fd.width > getSize().width) {
      x = fl.x + (fd.width - getSize().width) / 2;
    }
    if(fd.height > getSize().height) {
      y = fl.y + (fd.height - getSize().height) / 2;
    }
    setLocation(x, y);
    setVisible(true);
  }
  
  @Override
  public void accept(SettingsChangeEvent e) {
    if(SettingsChangeEvent.COLOR == e) {
      List<ColorInfo> next4 = settings.next4colors();
      currentColor.setText(next4.get(0).colorHex());
      for(int i = 0; i < lastColors.size(); i++) {
        ColorInfo ci = next4.get(i);
        lastColors.get(i).setColorInfo(ci);
      }
    }
    repaint();
  }
  
  private void populate() {
    Font labelfont = new Font(Font.SANS_SERIF, Font.PLAIN, 9);
    createFontPanel(labelfont);
    createColorPanel(labelfont);
    createDrawPanel(labelfont);
    createAutoDelayPanel(labelfont);
    addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseMoved(MouseEvent e) {
        DialogSettings.this.repaint();
      }
    });
  }
  
  private void createFontPanel(Font labelfont) {
    JLabel lfont = new JLabel("Font", IconFontSwing.buildIcon(FontAwesome.FONT, 14f), SwingConstants.LEFT);
    GridBagConstraints c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = 0;
    c.gridheight = 2;
    c.anchor = GridBagConstraints.LINE_START;
    c.insets = new Insets(10, 20, 10, 10);
    add(lfont, c);
    
    JLabel lname = new JLabel("Font Family");
    lname.setFont(labelfont);
    c = new GridBagConstraints();
    c.gridx = 1;
    c.gridy = 0;
    c.gridheight = 1;
    c.anchor = GridBagConstraints.LINE_START;
    c.insets = new Insets(10, 10, 5, 10);
    add(lname, c);
    
    fontFamily.setPreferredSize(new Dimension(100, 25));
    c = new GridBagConstraints();
    c.gridx = 1;
    c.gridy = 1;
    c.gridheight = 1;
    c.anchor = GridBagConstraints.LINE_START;
    c.insets = new Insets(0, 10, 10, 10);
    add(fontFamily, c);
    
    JLabel lstyle = new JLabel("Font Style");
    lstyle.setFont(labelfont);
    c = new GridBagConstraints();
    c.gridx = 2;
    c.gridy = 0;
    c.gridheight = 1;
    c.anchor = GridBagConstraints.LINE_START;
    c.insets = new Insets(10, 10, 5, 10);
    add(lstyle, c);
    
    if(settings.getFont().isPlain()) {
      fontStyle.setSelectedIndex(0);
    }
    else if(settings.getFont().isBold() && settings.getFont().isItalic()) {
      fontStyle.setSelectedIndex(3);
    }
    else if(settings.getFont().isBold()) {
      fontStyle.setSelectedIndex(1);
    }
    else if(settings.getFont().isItalic()) {
      fontStyle.setSelectedIndex(2);
    }
    fontStyle.addActionListener(e->{
      switch(fontStyle.getSelectedIndex()) {
        case 1:
          settings.setFont(settings.getFont().deriveFont(Font.BOLD));
          break;
        case 2:
          settings.setFont(settings.getFont().deriveFont(Font.ITALIC));
          break;
        case 3:
          settings.setFont(settings.getFont().deriveFont(Font.BOLD | Font.ITALIC));
          break;
        default:
          settings.setFont(settings.getFont().deriveFont(Font.PLAIN));
          break;
      }
      fontFamily.setText(settings.getFont().getFamily());
      fontFamily.repaint();
      DialogSettings.this.repaint();
    });
    //fontstyle.addMouseMotionListener(new MouseMotionAdapter() {
      //public void mouseMoved(MouseEvent e) {
        //dsets.repaint();
      //}
    //});
    c = new GridBagConstraints();
    c.gridx = 2;
    c.gridy = 1;
    c.gridheight = 1;
    c.anchor = GridBagConstraints.LINE_START;
    c.insets = new Insets(0, 10, 10, 10);
    add(fontStyle, c);
    
    JLabel lsize = new JLabel("Font Size");
    lsize.setFont(labelfont);
    c = new GridBagConstraints();
    c.gridx = 3;
    c.gridy = 0;
    c.gridheight = 1;
    c.anchor = GridBagConstraints.LINE_START;
    c.insets = new Insets(10, 10, 5, 10);
    add(lsize, c);
    
    fontSize.addChangeListener(e->settings.setFont(
        settings.getFont().deriveFont(
            fontSizeModel.getNumber().floatValue())
    ));
    fontSize.addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseMoved(MouseEvent e) {
        DialogSettings.this.repaint();
      }
    });
    fontSize.setPreferredSize(new Dimension(50, 25));
    c = new GridBagConstraints();
    c.gridx = 3;
    c.gridy = 1;
    c.gridheight = 1;
    c.anchor = GridBagConstraints.LINE_START;
    c.insets = new Insets(0, 10, 10, 20);
    add(fontSize, c);
    
    JPanel sep = new JPanel();
    sep.setBackground(Color.GRAY);
    sep.setPreferredSize(new Dimension(370, 1));
    c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = 2;
    c.gridwidth = 4;
    c.gridheight = 1;
    c.anchor = GridBagConstraints.CENTER;
    c.insets = new Insets(5, 20, 5, 20);
    add(sep, c);
  }
  
  private void createColorPanel(Font labelfont) {
    JLabel lcolor = new JLabel("Color", IconFontSwing.buildIcon(FontAwesome.EYEDROPPER, 14f), SwingConstants.LEFT);
    GridBagConstraints c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = 3;
    c.gridheight = 2;
    c.anchor = GridBagConstraints.LINE_START;
    c.insets = new Insets(10, 20, 10, 10);
    add(lcolor, c);
    
    JLabel lhex = new JLabel("Hex");
    lhex.setFont(labelfont);
    c = new GridBagConstraints();
    c.gridx = 1;
    c.gridy = 3;
    c.gridheight = 1;
    c.anchor = GridBagConstraints.LINE_START;
    c.insets = new Insets(10, 10, 5, 10);
    add(lhex, c);
    
    currentColor.addKeyListener(new KeyAdapter() {
      @Override
      public void keyReleased(KeyEvent e) {
        if(KeyEvent.VK_ENTER == e.getExtendedKeyCode()
            && !lastColors.get(0).getText().equalsIgnoreCase(currentColor.getText())) {
          settings.addColor(Color.decode(currentColor.getText()));
        }
      }
    });
    currentColor.setPreferredSize(new Dimension(100, 25));
    c = new GridBagConstraints();
    c.gridx = 1;
    c.gridy = 4;
    c.gridheight = 1;
    c.anchor = GridBagConstraints.LINE_START;
    c.insets = new Insets(0, 10, 10, 10);
    add(currentColor, c);
    
    JLabel lastc = new JLabel("Last Colors");
    lastc.setFont(labelfont);
    c = new GridBagConstraints();
    c.gridx = 2;
    c.gridy = 3;
    c.gridheight = 1;
    c.anchor = GridBagConstraints.LINE_START;
    c.insets = new Insets(10, 10, 5, 10);
    add(lastc, c);
    
    JPanel lastcpanel = new JPanel();
    GridBagLayout l = new GridBagLayout();
    lastcpanel.setLayout(l);
    Insets insets = new Insets(0, 0, 0, 10);
    List<ColorInfo> next4 = settings.next4colors();
    for(int i = 0; i < next4.size(); i++) {
      ColorInfo ci = next4.get(i);
      ColorDisplay cd = new ColorDisplay(settings, ci);
      cd.setFont(labelfont);
      GridBagConstraints gc = new GridBagConstraints();
      gc.gridx = i;
      gc.gridy = 0;
      gc.anchor = GridBagConstraints.CENTER;
      gc.insets = insets;
      lastcpanel.add(cd, gc);
      lastColors.add(cd);
    }
    
    c = new GridBagConstraints();
    c.gridx = 2;
    c.gridy = 4;
    c.gridheight = 1;
    c.gridwidth = 2;
    c.anchor = GridBagConstraints.LINE_START;
    c.insets = new Insets(0, 10, 10, 10);
    add(lastcpanel, c);

    JPanel sep = new JPanel();
    sep.setBackground(Color.GRAY);
    sep.setPreferredSize(new Dimension(370, 1));
    c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = 5;
    c.gridwidth = 4;
    c.gridheight = 1;
    c.anchor = GridBagConstraints.CENTER;
    c.insets = new Insets(5, 20, 5, 20);
    add(sep, c);
  }
  
  private void createDrawPanel(Font labelfont) {
    JLabel ldraw = new JLabel("Draw", IconFontSwing.buildIcon(FontAwesome.PICTURE_O, 14f), SwingConstants.LEFT);
    GridBagConstraints c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = 6;
    c.gridheight = 2;
    c.anchor = GridBagConstraints.LINE_START;
    c.insets = new Insets(10, 20, 10, 10);
    add(ldraw, c);
    
    JLabel lshape = new JLabel("Shape");
    lshape.setFont(labelfont);
    c = new GridBagConstraints();
    c.gridx = 1;
    c.gridy = 6;
    c.gridheight = 1;
    c.anchor = GridBagConstraints.LINE_START;
    c.insets = new Insets(10, 10, 5, 10);
    add(lshape, c);
    
    JComboBox jshape = new JComboBox(new Object[]{"Free", "Circle", "Rectangle", "Triangle"});
    jshape.setPreferredSize(new Dimension(100, 25));
    if(settings.getDrawMode().isCircleMode()) {
      jshape.setSelectedIndex(1);
    }
    else if(settings.getDrawMode().isRectangleMode()) {
      jshape.setSelectedIndex(2);
    }
    else if(settings.getDrawMode().isTriangleMode()) {
      jshape.setSelectedIndex(3);
    }
    else {
      jshape.setSelectedIndex(0);
    }
    jshape.addActionListener(e->{
      settings.getDrawMode().setFreeMode(false);
      settings.getDrawMode().setCircleMode(false);
      settings.getDrawMode().setRectangleMode(false);
      settings.getDrawMode().setCircleMode(false);
      switch(jshape.getSelectedIndex()) {
        case 1:
          settings.getDrawMode().setCircleMode(true);
          break;
        case 2:
          settings.getDrawMode().setRectangleMode(true);
          break;
        case 3:
          settings.getDrawMode().setTriangleMode(true);
          break;
        default:
          settings.getDrawMode().setFreeMode(true);
          break;
      }
    });
    //jshape.addMouseMotionListener(new MouseMotionAdapter() {
      //public void mouseMoved(MouseEvent e) {
        //dsets.repaint();
      //}
    //});
    c = new GridBagConstraints();
    c.gridx = 1;
    c.gridy = 7;
    c.gridheight = 1;
    c.anchor = GridBagConstraints.LINE_START;
    c.insets = new Insets(0, 10, 10, 10);
    add(jshape, c);
    
    JLabel lthick = new JLabel("Thickness");
    lthick.setFont(labelfont);
    c = new GridBagConstraints();
    c.gridx = 2;
    c.gridy = 6;
    c.gridheight = 1;
    c.anchor = GridBagConstraints.LINE_START;
    c.insets = new Insets(10, 10, 5, 10);
    add(lthick, c);
    
    SpinnerNumberModel model = new SpinnerNumberModel(settings.getDrawMode().getThickness(), 1, 10, 1);
    JSpinner jthick = new JSpinner(model);
    jthick.addChangeListener(e->settings.getDrawMode()
        .setThickness(model.getNumber().intValue())
    );
    //jthick.addMouseMotionListener(new MouseMotionAdapter() {
      //public void mouseMoved(MouseEvent e) {
        //dsets.repaint();
      //}
    //});
    jthick.setPreferredSize(new Dimension(80, 25));
    c = new GridBagConstraints();
    c.gridx = 2;
    c.gridy = 7;
    c.gridheight = 1;
    c.anchor = GridBagConstraints.LINE_START;
    c.insets = new Insets(0, 10, 10, 20);
    add(jthick, c);
    
    JLabel lfill = new JLabel("Fill Shape");
    lfill.setFont(labelfont);
    c = new GridBagConstraints();
    c.gridx = 3;
    c.gridy = 6;
    c.gridheight = 1;
    c.anchor = GridBagConstraints.LINE_START;
    c.insets = new Insets(10, 10, 5, 10);
    add(lfill, c);
    
    JCheckBox jfill = new JCheckBox("Fill");
    jfill.addActionListener(e->{
      settings.getDrawMode().setFillMode(jfill.isSelected());
      System.out.println(settings);
    });
    c = new GridBagConstraints();
    c.gridx = 3;
    c.gridy = 7;
    c.gridheight = 1;
    c.anchor = GridBagConstraints.LINE_START;
    c.insets = new Insets(0, 10, 10, 20);
    add(jfill, c);
    
    JPanel sep = new JPanel();
    sep.setBackground(Color.GRAY);
    sep.setPreferredSize(new Dimension(370, 1));
    c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = 8;
    c.gridwidth = 4;
    c.gridheight = 1;
    c.anchor = GridBagConstraints.CENTER;
    c.insets = new Insets(5, 20, 5, 20);
    add(sep, c);
  }
  
  private void createAutoDelayPanel(Font labelfont) {
    JLabel lborder = new JLabel("Auto Delay", IconFontSwing.buildIcon(FontAwesome.CLOCK_O, 14f), SwingConstants.LEFT);
    GridBagConstraints c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = 9;
    c.gridheight = 2;
    c.anchor = GridBagConstraints.LINE_START;
    c.insets = new Insets(10, 20, 10, 10);
    add(lborder, c);
    
    JLabel lmillis = new JLabel("Milliseconds");
    lmillis.setFont(labelfont);
    c = new GridBagConstraints();
    c.gridx = 1;
    c.gridy = 9;
    c.gridheight = 1;
    c.anchor = GridBagConstraints.LINE_START;
    c.insets = new Insets(10, 10, 5, 10);
    add(lmillis, c);
    
    SpinnerNumberModel model = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 100);
    JSpinner jdelay = new JSpinner(model);
    jdelay.addChangeListener(e->{
      settings.setAutoDelay(model.getNumber().intValue());
    });
    DialogSettings dsets = this;
    jdelay.addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseMoved(MouseEvent e) {
        dsets.repaint();
      }
    });
    jdelay.setPreferredSize(new Dimension(100, 25));
    c = new GridBagConstraints();
    c.gridx = 1;
    c.gridy = 10;
    c.gridwidth = 2;
    c.gridheight = 1;
    c.anchor = GridBagConstraints.LINE_START;
    c.insets = new Insets(0, 10, 10, 20);
    add(jdelay, c);
  }
  
}
