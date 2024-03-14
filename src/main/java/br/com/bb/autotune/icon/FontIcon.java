/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.icon;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author F6036477
 */
public class FontIcon {
  
  private static Font iconFont = null;
  
  private static void checkFont() {
    if(iconFont == null) {
      try {
        iconFont = createFontAwesome();
      }
      catch(Exception e) {
        throw new RuntimeException(e);
      }
    }
  }
  
  public static Font createFontAwesome() throws IOException, FontFormatException {
    return createFont(FontIcon.class.getResourceAsStream("/fontawesome-webfont.ttf"));
  }
  
  public static Font createFont(InputStream is) throws IOException, FontFormatException {
    return Font.createFont(Font.TRUETYPE_FONT, is);
  }
  
  public static Image createImage(Font font, FontAwesome icon, Color color, float size) {
    JLabel label = new JLabel();
    label.setForeground(color);
    label.setFont(font.deriveFont(size));
    label.setText(String.valueOf(icon.getUnicode()));
    Dimension dim = label.getPreferredSize();
    int width = dim.width + 1;
    int height = dim.height + 1;
    label.setSize(width, height);
    BufferedImage bufImage =
            new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = bufImage.createGraphics();
    g2d.setRenderingHint(
            RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    g2d.setRenderingHint(
            RenderingHints.KEY_FRACTIONALMETRICS,
            RenderingHints.VALUE_FRACTIONALMETRICS_ON);
    label.print(g2d);
    g2d.dispose();
    return bufImage;
  }
  
  public static Image createImage(FontAwesome icon, Color color, float size) {
    checkFont();
    return createImage(iconFont, icon, color, size);
  }
  
  public static Image createImage(FontAwesome icon, float size) {
    checkFont();
    return createImage(iconFont, icon, Color.BLACK, size);
  }
  
  public static Icon createIcon(Font font, FontAwesome icon, Color color, float size) {
    return new ImageIcon(createImage(font, icon, color, size));
  }
  
  public static Icon createIcon(FontAwesome icon, Color color, float size) {
    checkFont();
    return createIcon(iconFont, icon, color, size);
  }
  
  public static Icon createIcon(FontAwesome icon, float size) {
    checkFont();
    return createIcon(iconFont, icon, Color.BLACK, size);
  }
  
}
