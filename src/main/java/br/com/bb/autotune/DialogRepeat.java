/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune;

import br.com.bb.autotune.icon.FontAwesome;
import br.com.bb.autotune.icon.FontIcon;
import br.com.bb.autotune.settings.Settings;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

/**
 *
 * @author Juno
 */
public class DialogRepeat extends JDialog {
  
  private final Frame owner;
  
  private final SpinnerNumberModel linesModel;
  
  private final SpinnerNumberModel timesModel;
  
  private final int[] repeat;
  
  public DialogRepeat(Frame owner) {
    super(owner, "Repeat", true);
    this.owner = owner;
    this.repeat = new int[2];
    this.linesModel = new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1);
    this.timesModel = new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1);
    this.setIconImage(FontIcon.createImage(FontAwesome.REPEAT, 14f));
    this.setLayout(new GridBagLayout());
    this.populate();
    this.pack();
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
  
  public int[] getRepeat() {
    return repeat;
  }
  
  private void populate() {
    JLabel llines = new JLabel("Lines", FontIcon.createIcon(FontAwesome.BARS, 14f), SwingConstants.LEFT);
    GridBagConstraints c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = 0;
    c.gridheight = 2;
    c.anchor = GridBagConstraints.LINE_START;
    c.insets = new Insets(10, 20, 10, 10);
    add(llines, c);
    
    Font labelfont = new Font(Font.SANS_SERIF, Font.PLAIN, 9);
    
    JLabel label = new JLabel("Lines to repeat");
    label.setFont(labelfont);
    c = new GridBagConstraints();
    c.gridx = 1;
    c.gridy = 0;
    c.gridheight = 1;
    c.anchor = GridBagConstraints.LINE_START;
    c.insets = new Insets(10, 10, 5, 10);
    add(label, c);
    
    JSpinner lines = new JSpinner(linesModel);
    lines.setPreferredSize(new Dimension(60, 25));
    c = new GridBagConstraints();
    c.gridx = 1;
    c.gridy = 1;
    c.gridheight = 1;
    c.anchor = GridBagConstraints.LINE_START;
    c.insets = new Insets(0, 10, 10, 10);
    add(lines, c);
    
    JPanel sep = new JPanel();
    sep.setBackground(Color.GRAY);
    sep.setPreferredSize(new Dimension(175, 1));
    c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = 2;
    c.gridwidth = 4;
    c.gridheight = 1;
    c.anchor = GridBagConstraints.CENTER;
    c.insets = new Insets(5, 20, 5, 20);
    add(sep, c);
    
    JLabel ltimes = new JLabel("Times", FontIcon.createIcon(FontAwesome.REPEAT, 14f), SwingConstants.LEFT);
    c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = 3;
    c.gridheight = 2;
    c.anchor = GridBagConstraints.LINE_START;
    c.insets = new Insets(10, 20, 10, 10);
    add(ltimes, c);
    
    label = new JLabel("Times to repeat");
    label.setFont(labelfont);
    c = new GridBagConstraints();
    c.gridx = 1;
    c.gridy = 3;
    c.gridheight = 1;
    c.anchor = GridBagConstraints.LINE_START;
    c.insets = new Insets(10, 10, 5, 10);
    add(label, c);
    
    JSpinner times = new JSpinner(timesModel);
    times.setPreferredSize(new Dimension(60, 25));
    c = new GridBagConstraints();
    c.gridx = 1;
    c.gridy = 4;
    c.gridheight = 1;
    c.anchor = GridBagConstraints.LINE_START;
    c.insets = new Insets(0, 10, 10, 10);
    add(times, c);
    
    sep = new JPanel();
    sep.setBackground(Color.GRAY);
    sep.setPreferredSize(new Dimension(175, 1));
    c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = 5;
    c.gridwidth = 2;
    c.gridheight = 1;
    c.anchor = GridBagConstraints.CENTER;
    c.insets = new Insets(5, 20, 5, 20);
    add(sep, c);
    
    JButton cancel = new JButton("Cancel", FontIcon.createIcon(FontAwesome.BAN, Settings.RED_DARKEN3.color(), 12f));
    ActionListener cancelListener = e->{
      repeat[0] = 0;
      repeat[1] = 0;
      DialogRepeat.this.setVisible(false);
    };
    cancel.addActionListener(cancelListener);
    c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = 6;
    c.gridheight = 1;
    c.anchor = GridBagConstraints.LINE_START;
    c.insets = new Insets(10, 20, 10, 10);
    add(cancel, c);
    this.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosed(WindowEvent e) {
        cancelListener.actionPerformed(null);
      }
    });
    
    JButton ok = new JButton("Ok", FontIcon.createIcon(FontAwesome.CHECK_CIRCLE, Settings.GREEN_DARKEN3.color(), 12f));
    ok.addActionListener(e->{
      repeat[0] = linesModel.getNumber().intValue();
      repeat[1] = timesModel.getNumber().intValue();
      DialogRepeat.this.setVisible(false);
    });
    c = new GridBagConstraints();
    c.gridx = 1;
    c.gridy = 6;
    c.gridheight = 1;
    c.anchor = GridBagConstraints.LINE_START;
    c.insets = new Insets(10, 10, 10, 20);
    add(ok, c);
  }
  
}
