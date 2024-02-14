/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.action.shape;

import br.com.bb.autotune.Reference;
import br.com.bb.autotune.ShapeInfo;
import br.com.bb.autotune.settings.DrawSettings.DrawMode;
import br.com.bb.autotune.settings.Settings;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.List;

/**
 *
 * @author Juno
 */
public class FreeDrawAction extends AbstractShapeAction {

  public FreeDrawAction(Settings s) {
    super(s);
  }
  
  @Override
  public boolean accept() {
    return DrawMode.FREE == settings.getDrawSettings().getDrawMode();
  }

  @Override
  public void perform(MouseEvent e, Reference<ShapeInfo> current, List<ShapeInfo> shapes) {
    Point origin = getOrigin(e, current);
    Polygon p = new Polygon();
    if(current.isEmpty()) {
      p.addPoint(e.getX(), e.getY());
      current.set(new ShapeInfo(origin, p,
          settings.getDrawSettings().getStroke(), 
          settings.getCurrentColor().color(), false
      ));
    }
    else {
      p.addPoint(origin.x, origin.y);
      p.addPoint(e.getX(), e.getY());
      shapes.add(new ShapeInfo(current.get().getPoint(), p,
          settings.getDrawSettings().getStroke(), 
          settings.getCurrentColor().color(), false
      ));
      p = new Polygon();
      p.addPoint(e.getX(), e.getY());
      current.set(new ShapeInfo(e.getPoint(), p,
          settings.getDrawSettings().getStroke(), 
          settings.getCurrentColor().color(), false
      ));
    }
  }
  
}
