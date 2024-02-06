/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.settings;

import br.com.bb.autotune.ShapeInfo;
import java.awt.BasicStroke;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.Objects;

/**
 *
 * @author Juno
 */
public class DrawSettings {
  
  public static enum DrawMode { 
    NONE, FREE, LINE, RECTANGLE, TRIANGLE, CIRCLE, ARROW_UP, ARROW_RIGHT, ARROW_DOWN, ARROW_LEFT
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
  
  public boolean isFreeModeEnabled() {
    return DrawMode.FREE == mode;
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
  
  public Shape getShape(int x, int y, int w, int h) {
    System.out.printf("* getShape( %d, %d, %d, %d )%n", x, y, w, h);
    switch(mode) {
      case ARROW_DOWN:
        Polygon a = new Polygon();
        a.addPoint(x + w/4, y);
        a.addPoint(x + w/4, y + h/2);
        a.addPoint(x, y + h/2);
        a.addPoint(x + w/2, y + h);
        a.addPoint(x + w, y + h/2);
        a.addPoint(x + w/4*3, y + h/2);
        a.addPoint(x + w/4*3, y);
        return a;
      case ARROW_LEFT:
        Polygon b = new Polygon();
        b.addPoint(x, y + h/2);
        b.addPoint(x + w/2, y);
        b.addPoint(x + w/2, y + h/4);
        b.addPoint(x + w, y + h/4);
        b.addPoint(x + w, y + h/4*3);
        b.addPoint(x + w/2, y + h/4*3);
        b.addPoint(x + w/2, y + h);
        return b;
      case ARROW_RIGHT:
        Polygon c = new Polygon();
        c.addPoint(x, y + h/4);
        c.addPoint(x + w/2, y + h/4);
        c.addPoint(x + w/2, y);
        c.addPoint(x + w, y + h/2);
        c.addPoint(x + w/2, y + h);
        c.addPoint(x + w/2, y + h/4*3);
        c.addPoint(x, y + h/4*3);
        return c;
      case ARROW_UP:
        Polygon d = new Polygon();
        d.addPoint(x + w/2, y);
        d.addPoint(x + w, y + h/2);
        d.addPoint(x + w/4*3, y + h/2);
        d.addPoint(x + w/4*3, y + h);
        d.addPoint(x + w/4, y + h);
        d.addPoint(x + w/4, y + h/2);
        d.addPoint(x, y + h/2);
        return d;
      case CIRCLE:
        return new Ellipse2D.Float(
            Integer.valueOf(x).floatValue(),
            Integer.valueOf(y).floatValue(),
            Integer.valueOf(w).floatValue(),
            Integer.valueOf(h).floatValue()
        );
      case FREE:
         return new Ellipse2D.Float(
            Integer.valueOf(x).floatValue(),
            Integer.valueOf(y).floatValue(),
            Integer.valueOf(thickness).floatValue(),
            Integer.valueOf(thickness).floatValue()
        );
      case LINE:
        return new Line2D.Float(
            Integer.valueOf(x).floatValue(),
            Integer.valueOf(y).floatValue(),
            Integer.valueOf(x + w).floatValue(),
            Integer.valueOf(y + h).floatValue()
        );
      case RECTANGLE:
        return new Rectangle(x, y, w, h);
      case TRIANGLE:
        Polygon p = new Polygon();
        p.addPoint(x + w/2, y);
        p.addPoint(x + w, y + h);
        p.addPoint(x, y + h);
        return p;
      default:
        return null;
    }
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
