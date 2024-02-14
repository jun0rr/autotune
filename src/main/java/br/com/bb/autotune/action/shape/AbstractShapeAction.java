/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.action.shape;

import br.com.bb.autotune.Reference;
import br.com.bb.autotune.ShapeInfo;
import br.com.bb.autotune.settings.Settings;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Juno
 */
public abstract class AbstractShapeAction {
  
  protected final Settings settings;
  
  protected AbstractShapeAction(Settings s) {
    this.settings = Objects.requireNonNull(s);
  }
  
  protected Point getOrigin(MouseEvent e, Reference<ShapeInfo> current) {
    return current.isEmpty()
        ? e.getPoint()
        : current.get().getPoint();
  }
  
  protected Rectangle getSize(MouseEvent e, Reference<ShapeInfo> current) {
    int x = e.getPoint().x;
    int y = e.getPoint().y;
    int w = 0;
    int h = 0;
    if(current.isPresent()) {
      Point before = current.get().getPoint();
      x = Math.min(e.getX(), before.x);
      y = Math.min(e.getY(), before.y);
      w = Math.max(e.getX(), before.x) - x;
      h = Math.max(e.getY(), before.y) - y;
    }
    return new Rectangle(x, y, w, h);
  }

  public abstract boolean accept();
  
  public abstract void perform(MouseEvent e, Reference<ShapeInfo> current, List<ShapeInfo> shapes);
  
}
