/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.settings;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;
import java.util.Objects;

/**
 *
 * @author Juno
 */
public class DrawMode {
  
  private final Settings settings;
  
  private boolean free;
  
  private boolean circle;
  
  private boolean rectangle;
  
  private boolean triangle;
  
  private boolean fill;
  
  private int thickness;
  
  private int dashed;

  public DrawMode(Settings s) {
    this.settings = Objects.requireNonNull(s);
    this.free = true;
    this.circle = false;
    this.rectangle = false;
    this.triangle = false;
    this.fill = false;
    this.thickness = 1;
    this.dashed = 0;
  }
  
  public Settings settings() {
    return settings;
  }

  public boolean isFreeMode() {
    return free;
  }

  public DrawMode setFreeMode(boolean free) {
    this.free = free;
    settings.fireEvent(SettingsChangeEvent.DRAW_SHAPE);
    return this;
  }

  public boolean isCircleMode() {
    return circle;
  }

  public DrawMode setCircleMode(boolean circle) {
    this.circle = circle;
    settings.fireEvent(SettingsChangeEvent.DRAW_SHAPE);
    return this;
  }

  public boolean isRectangleMode() {
    return rectangle;
  }

  public DrawMode setRectangleMode(boolean rectangle) {
    this.rectangle = rectangle;
    settings.fireEvent(SettingsChangeEvent.DRAW_SHAPE);
    return this;
  }

  public boolean isTriangleMode() {
    return rectangle;
  }

  public DrawMode setTriangleMode(boolean triangle) {
    this.triangle = triangle;
    settings.fireEvent(SettingsChangeEvent.DRAW_SHAPE);
    return this;
  }

  public boolean isFillMode() {
    return fill;
  }

  public DrawMode setFillMode(boolean fill) {
    this.fill = fill;
    settings.fireEvent(SettingsChangeEvent.DRAW_FILL);
    return this;
  }

  public int getThickness() {
    return thickness;
  }

  public DrawMode setThickness(int thickness) {
    this.thickness = thickness;
    settings.fireEvent(SettingsChangeEvent.DRAW_THICKNESS);
    return this;
  }

  public int getDashedBorder() {
    return dashed;
  }

  public DrawMode setDashedBorder(int dashed) {
    this.dashed = dashed;
    return this;
  }
  
  public Stroke getStroke() {
    return dashed > 0 
        ? new BasicStroke(
            Integer.valueOf(thickness).floatValue(), 
            BasicStroke.CAP_BUTT, 
            BasicStroke.JOIN_BEVEL, 0, 
            new float[]{Integer.valueOf(dashed).floatValue()}, 0
        )
        : new BasicStroke(
            Integer.valueOf(thickness).floatValue(), 
            BasicStroke.CAP_BUTT, 
            BasicStroke.JOIN_BEVEL
        );
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 61 * hash + (this.free ? 1 : 0);
    hash = 61 * hash + (this.circle ? 1 : 0);
    hash = 61 * hash + (this.rectangle ? 1 : 0);
    hash = 61 * hash + (this.fill ? 1 : 0);
    hash = 61 * hash + this.thickness;
    hash = 61 * hash + this.dashed;
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
    final DrawMode other = (DrawMode) obj;
    if (this.free != other.free) {
      return false;
    }
    if (this.circle != other.circle) {
      return false;
    }
    if (this.rectangle != other.rectangle) {
      return false;
    }
    if (this.fill != other.fill) {
      return false;
    }
    if (this.thickness != other.thickness) {
      return false;
    }
    if (this.dashed != other.dashed) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "DrawMode{free=" + free + ", circle=" + circle + ", rectangle=" + rectangle + ", fill=" + fill + ", borderLength=" + thickness + ", dashed=" + dashed + '}';
  }
  
}
