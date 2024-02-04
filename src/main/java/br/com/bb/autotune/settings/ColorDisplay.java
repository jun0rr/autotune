/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.settings;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.swing.JButton;

/**
 *
 * @author Juno
 */
public class ColorDisplay extends JButton implements ActionListener {
  
  public static final Dimension PREFERRED_DIMENSION = new Dimension(30, 25);
  
  private final Settings settings;
  
  private ColorInfo color;
  
  public ColorDisplay(Settings ps, ColorInfo c) {
    this.settings = Objects.requireNonNull(ps);
    this.setOpaque(true);
    this.setForeground(Color.BLACK);
    this.setPreferredSize(PREFERRED_DIMENSION);
    setColorInfo(c);
    this.addActionListener(this);
  }

  public ColorInfo getColorInfo() {
    return color;
  }
  
  public String getTooltip() {
    return String.format("%s (%s)", color.name(), color.colorHex());
  }

  public ColorDisplay setColorInfo(ColorInfo color) {
    this.color = color;
    this.setBackground(color.color());
    this.setText(color.name());
    this.setToolTipText(getTooltip());
    return this;
  }
  
  @Override
  public void actionPerformed(ActionEvent e) {
    settings.setColorIndex(color.index());
  }

  @Override
  public String toString() {
    return "ColorDisplay{" + "color=" + color + '}';
  }
  
}
