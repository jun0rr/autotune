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
import java.awt.Rectangle;

/**
 *
 * @author Juno
 */
public class TriangleAction extends AbstractShapeAction {

  public TriangleAction() {
    super("Triangle");
  }
  
  @Override
  public boolean accept(EditorPanel p) {
    return DrawMode.TRIANGLE == p.getSettings().getDrawSettings().getDrawMode();
  }

  @Override
  public void perform(EditorPanel p) {
    Point origin = getOrigin(p.getLastMouseEvents()[0], p.getCurrentShape());
    Rectangle size = getSize(p.getLastMouseEvents()[0], p.getCurrentShape());
    Polygon po = new Polygon();
    po.addPoint(size.x + size.width/2, size.y);
    po.addPoint(size.x + size.width, size.y + size.height);
    po.addPoint(size.x, size.y + size.height);
    p.getCurrentShape().set(new ShapeInfo(origin, po,
        p.getSettings().getDrawSettings().getStroke(),
        p.getSettings().getCurrentColor().color(),
        p.getSettings().getDrawSettings().isFillEnabled()
    ));
  }
  
}
