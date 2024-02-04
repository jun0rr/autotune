/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Objects;
import javax.swing.Icon;

/**
 *
 * @author Juno
 */
public class IconImage {
  
  private final Icon icon;
  
  public IconImage(Icon i) {
    this.icon = Objects.requireNonNull(i);
  }
  
  public Image toImage() {
    int w = icon.getIconWidth();
    int h = icon.getIconHeight();
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    GraphicsDevice gd = ge.getDefaultScreenDevice();
    GraphicsConfiguration gc = gd.getDefaultConfiguration();
    BufferedImage image = gc.createCompatibleImage(w, h);
    Graphics2D g = image.createGraphics();
    icon.paintIcon(null, g, 0, 0);
    g.dispose();
    return image;
  }
  
}
