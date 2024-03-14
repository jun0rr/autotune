/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.action.shape;

import br.com.bb.autotune.EditorPanel;
import br.com.bb.autotune.ShapeInfo;
import br.com.bb.autotune.settings.DrawSettings.DrawMode;
import java.awt.Point;
import java.awt.Polygon;

/**
 *
 * @author Juno
 */
public class FreeDrawAction extends AbstractShapeAction {

  public FreeDrawAction() {
    super("FreeDraw");
  }
  
  @Override
  public boolean accept(EditorPanel p) {
    return DrawMode.FREE == p.getSettings().getDrawSettings().getDrawMode();
  }

  @Override
  public void perform(EditorPanel p) {
    Point origin = getOrigin(p.getLastMouseEvents()[0], p.getCurrentShape());
    Polygon po = new Polygon();
    if(p.getCurrentShape().isEmpty()) {
      po.addPoint(p.getLastMouseEvents()[0].getX(), p.getLastMouseEvents()[0].getY());
      p.getCurrentShape().set(new ShapeInfo(origin, po,
          p.getSettings().getDrawSettings().getStroke(), 
          p.getSettings().getCurrentColor().color(), false
      ));
    }
    else {
      po.addPoint(origin.x, origin.y);
      po.addPoint(p.getLastMouseEvents()[0].getX(), p.getLastMouseEvents()[0].getY());
      p.getShapes().add(new ShapeInfo(p.getCurrentShape().get().getPoint(), po,
          p.getSettings().getDrawSettings().getStroke(), 
          p.getSettings().getCurrentColor().color(), false
      ));
      po = new Polygon();
      po.addPoint(p.getLastMouseEvents()[0].getX(), p.getLastMouseEvents()[0].getY());
      p.getCurrentShape().set(new ShapeInfo(p.getLastMouseEvents()[0].getPoint(), po,
          p.getSettings().getDrawSettings().getStroke(), 
          p.getSettings().getCurrentColor().color(), false
      ));
    }
  }
  
}
