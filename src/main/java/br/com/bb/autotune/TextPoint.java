/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.util.Objects;

/**
 *
 * @author F6036477
 */
public class TextPoint {
  
  private final Point point;
  
  private final StringBuilder text;
  
  private Font font;
  
  private Color color;
  
  public TextPoint(Point p, Font f, Color c) {
    this.point = p;
    this.font = f;
    this.color = c;
    this.text = new StringBuilder();
  }
  
  public TextPoint(Point p) {
    this.point = p;
    this.text = new StringBuilder();
  }
  
  public StringBuilder text() {
    return text;
  }

  public Point getPoint() {
    return point;
  }

  public Font getFont() {
    return font;
  }

  public Color getColor() {
    return color;
  }

  public void setFont(Font font) {
    this.font = font;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 47 * hash + Objects.hashCode(this.point);
    hash = 47 * hash + Objects.hashCode(this.font);
    hash = 47 * hash + Objects.hashCode(this.color);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final TextPoint other = (TextPoint) obj;
    if (!Objects.equals(this.point, other.point)) {
      return false;
    }
    if (!Objects.equals(this.font, other.font)) {
      return false;
    }
    return Objects.equals(this.color, other.color);
  }

  @Override
  public String toString() {
    return "TextPoint{" + "point=" + point + ", text=" + text + ", font=" + font + ", color=" + color + '}';
  }
  
}
