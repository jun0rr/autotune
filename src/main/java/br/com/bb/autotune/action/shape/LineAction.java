/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.action.shape;

import br.com.bb.autotune.EditablePanel;
import br.com.bb.autotune.ShapeInfo;
import br.com.bb.autotune.settings.DrawSettings.DrawMode;
import java.awt.Point;
import java.awt.Polygon;

/**
 *
 * @author Juno
 */
public class LineAction extends AbstractShapeAction {

  public LineAction() {
    super("Line");
  }
  
  @Override
  public boolean accept(EditablePanel p) {
    return DrawMode.LINE == p.getSettings().getDrawSettings().getDrawMode();
  }

  @Override
  public void perform(EditablePanel p) {
    Point origin = getOrigin(p.getLastMouseEvents()[0], p.getCurrentShape());
    Polygon po = new Polygon();
    po.addPoint(origin.x, origin.y);
    po.addPoint(p.getLastMouseEvents()[0].getPoint().x, p.getLastMouseEvents()[0].getPoint().y);
    p.getCurrentShape().set(new ShapeInfo(origin, po,
        p.getSettings().getDrawSettings().getStroke(), 
        p.getSettings().getCurrentColor().color(), false
    ));
  }
  
}
