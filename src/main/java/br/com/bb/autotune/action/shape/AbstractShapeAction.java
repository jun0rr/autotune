/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.action.shape;

import br.com.bb.autotune.EditablePanel;
import br.com.bb.autotune.Reference;
import br.com.bb.autotune.ShapeInfo;
import br.com.bb.autotune.action.PanelAction;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.Objects;

/**
 *
 * @author Juno
 */
public abstract class AbstractShapeAction implements PanelAction {
  
  private final String name;
  
  public AbstractShapeAction(String name) {
    this.name  = Objects.requireNonNull(name);
  }
  
  @Override
  public String getName() {
    return name;
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

  public abstract boolean accept(EditablePanel p);
  
  public abstract void perform(EditablePanel p);

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 71 * hash + Objects.hashCode(this.name);
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
    final AbstractShapeAction other = (AbstractShapeAction) obj;
    return Objects.equals(this.name, other.name);
  }

  @Override
  public String toString() {
    return "ShapeAction{" + "name=" + name + '}';
  }
  
}
