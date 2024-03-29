/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune;

import br.com.bb.autotune.settings.Settings;
import java.awt.Color;
import java.awt.Point;
import java.awt.Shape;
import java.awt.Stroke;
import java.util.Objects;

/**
 *
 * @author F6036477
 */
public class ShapeInfo {
  
  private final Point point;
  
  private final Shape shape;
  
  private final Stroke stroke;
  
  private final Color color;
  
  private final boolean fill;
  
  public ShapeInfo(Point p, Shape shape, Stroke stroke, Color color, boolean fill) {
    this.point = p;
    this.shape = shape;
    this.stroke = stroke;
    this.color = color;
    this.fill = fill;
  }

  public Point getPoint() {
    return point;
  }

  public Stroke getStroke() {
    return stroke;
  }

  public Color getColor() {
    return color;
  }

  public Shape getShape() {
    return shape;
  }

  public boolean isFillEnabled() {
    return fill;
  }
  
  @Override
  public int hashCode() {
    int hash = 5;
    hash = 47 * hash + Objects.hashCode(this.point);
    hash = 47 * hash + Objects.hashCode(this.stroke);
    hash = 47 * hash + Objects.hashCode(this.color);
    hash = 47 * hash + Objects.hashCode(this.shape);
    hash = 47 * hash + Objects.hashCode(this.fill);
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
    final ShapeInfo other = (ShapeInfo) obj;
    if (!Objects.equals(this.point, other.point)) {
      return false;
    }
    if (!Objects.equals(this.stroke, other.stroke)) {
      return false;
    }
    if (!Objects.equals(this.color, other.color)) {
      return false;
    }
    return Objects.equals(this.shape, other.shape) && this.fill == other.fill;
  }

  @Override
  public String toString() {
    return "ShapePoint{point=" + point + ", shape=" + shape + ", stroke=" + stroke + ", color=" + color + ", fill=" + fill + '}';
  }
  
}
