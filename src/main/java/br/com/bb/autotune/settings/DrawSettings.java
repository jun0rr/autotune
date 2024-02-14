/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.settings;

import java.awt.BasicStroke;
import java.awt.Stroke;
import java.util.Objects;

/**
 *
 * @author Juno
 */
public class DrawSettings {
  
  public static enum DrawMode { 
    NONE, 
    LINE, 
    RECTANGLE, 
    TRIANGLE, 
    CIRCLE, 
    ARROW_UP, 
    ARROW_RIGHT, 
    ARROW_DOWN, 
    ARROW_LEFT,
    FREE
  }
  
  private final Settings settings;
  
  private DrawMode mode;
  
  private boolean fill;
  
  private int thickness;
  
  public DrawSettings(Settings s) {
    this.settings = Objects.requireNonNull(s);
    this.mode = DrawMode.NONE;
    this.fill = false;
    this.thickness = 1;
  }
  
  public Settings settings() {
    return settings;
  }
  
  public boolean isDrawModeEnabled() {
    return DrawMode.NONE != mode;
  }
  
  public DrawMode getDrawMode() {
    return mode;
  }
  
  public DrawSettings setDrawMode(DrawMode m) {
    this.mode = Objects.requireNonNull(m);
    settings.fireEvent(SettingsChangeEvent.DRAW_MODE);
    return this;
  }
  
  public boolean isFillEnabled() {
    return fill;
  }

  public DrawSettings setFillEnabled(boolean fill) {
    this.fill = fill;
    settings.fireEvent(SettingsChangeEvent.DRAW_FILL);
    return this;
  }

  public int getThickness() {
    return thickness;
  }

  public DrawSettings setThickness(int thickness) {
    this.thickness = thickness;
    settings.fireEvent(SettingsChangeEvent.DRAW_THICKNESS);
    return this;
  }
  
  public Stroke getStroke() {
    return new BasicStroke(
        Integer.valueOf(thickness).floatValue(), 
        BasicStroke.CAP_BUTT, 
        BasicStroke.JOIN_BEVEL
    );
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 67 * hash + Objects.hashCode(this.mode);
    hash = 67 * hash + (this.fill ? 1 : 0);
    hash = 67 * hash + this.thickness;
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
    final DrawSettings other = (DrawSettings) obj;
    if (this.fill != other.fill) {
      return false;
    }
    if (this.thickness != other.thickness) {
      return false;
    }
    return this.mode == other.mode;
  }

  @Override
  public String toString() {
    return "DrawSettings{" + "mode=" + mode + ", fill=" + fill + ", thickness=" + thickness + '}';
  }

}
