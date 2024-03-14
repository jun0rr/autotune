/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune;

import br.com.bb.autotune.icon.FontAwesome;
import br.com.bb.autotune.icon.FontIcon;
import br.com.bb.autotune.script.fn.Fn;
import br.com.bb.autotune.script.fn.FnSource;
import br.com.bb.autotune.settings.Settings;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Juno
 */
public class DialogFunction extends JDialog {
  
  private final Frame owner;
  
  private final EditorPanel panel;
  
  private final JTextField iname;
  
  private final JTextArea icode;
  
  public DialogFunction(Frame owner, EditorPanel p) {
    super(owner, "Custom Function", true);
    this.owner = owner;
    this.panel = p;
    this.iname = new JTextField();
    this.icode = new JTextArea();
    this.setIconImage(FontIcon.createImage(FontAwesome.TERMINAL, 14f));
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
  
  private void populate() {
    Font labelfont = new Font(Font.SANS_SERIF, Font.PLAIN, 9);
    
    JLabel label = new JLabel("Function Name");
    label.setFont(labelfont);
    GridBagConstraints c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = 0;
    c.gridwidth = 2;
    c.gridheight = 1;
    c.anchor = GridBagConstraints.LINE_START;
    c.insets = new Insets(10, 20, 5, 20);
    add(label, c);
    
    iname.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
    iname.setPreferredSize(new Dimension(250, 25));
    c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = 1;
    c.gridwidth = 2;
    c.gridheight = 1;
    c.anchor = GridBagConstraints.LINE_START;
    c.insets = new Insets(0, 20, 10, 20);
    add(iname, c);
    
    label = new JLabel("Function Code");
    label.setFont(labelfont);
    c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = 2;
    c.gridwidth = 2;
    c.gridheight = 1;
    c.anchor = GridBagConstraints.LINE_START;
    c.insets = new Insets(0, 20, 5, 20);
    add(label, c);
    
    icode.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
    JScrollPane scroll = new JScrollPane(icode);
    scroll.setPreferredSize(new Dimension(250, 250));
    c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = 3;
    c.gridwidth = 2;
    c.gridheight = 4;
    c.anchor = GridBagConstraints.LINE_START;
    c.insets = new Insets(0, 20, 10, 20);
    add(scroll, c);
    
    JPanel sep = new JPanel();
    sep.setBackground(Color.GRAY);
    sep.setPreferredSize(new Dimension(250, 1));
    c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = 7;
    c.gridwidth = 2;
    c.gridheight = 1;
    c.anchor = GridBagConstraints.CENTER;
    c.insets = new Insets(5, 20, 5, 20);
    add(sep, c);
    
    JButton cancel = new JButton("Cancel", FontIcon.createIcon(FontAwesome.BAN, Settings.RED_DARKEN3.color(), 12f));
    cancel.addActionListener(e->{
      DialogFunction.this.setVisible(false);
    });
    c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = 8;
    c.gridheight = 1;
    c.anchor = GridBagConstraints.LINE_END;
    c.insets = new Insets(10, 20, 10, 10);
    add(cancel, c);
    
    JButton ok = new JButton("Ok", FontIcon.createIcon(FontAwesome.CHECK_CIRCLE, Settings.GREEN_DARKEN3.color(), 12f));
    ok.addActionListener(e->{
      String name = iname.getText();
      String code = icode.getText();
      if(name != null && !name.trim().isEmpty() && code != null && !code.trim().isEmpty()) {
        Fn fn = panel.getFnContext().createFunction(new FnSource(name.toLowerCase(), code));
        panel.addRecordAction(String.format("function %s() { %s }", name.toLowerCase(), code), 
            FontIcon.createIcon(FontAwesome.TERMINAL, 12f), 
            a->fn.run(panel.getFnContext())
        );
      }
      DialogFunction.this.setVisible(false);
    });
    c = new GridBagConstraints();
    c.gridx = 1;
    c.gridy = 8;
    c.gridheight = 1;
    c.anchor = GridBagConstraints.LINE_END;
    c.insets = new Insets(10, 10, 10, 20);
    add(ok, c);
  }
  
}
