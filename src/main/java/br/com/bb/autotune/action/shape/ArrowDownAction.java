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
import java.awt.Rectangle;

/**
 *
 * @author Juno
 */
public class ArrowDownAction extends AbstractShapeAction {
  
  public ArrowDownAction() {
    super("ArrowDown");
  }

  @Override
  public boolean accept(EditablePanel p) {
    return DrawMode.ARROW_DOWN == p.getSettings().getDrawSettings().getDrawMode();
  }

  @Override
  public void perform(EditablePanel p) {
    Point origin = getOrigin(p.getLastMouseEvents()[0], p.getCurrentShape());
    Rectangle size = getSize(p.getLastMouseEvents()[0], p.getCurrentShape());
    Polygon a = new Polygon();
    a.addPoint(size.x + size.width/4, size.y);
    a.addPoint(size.x + size.width/4, size.y + size.height/2);
    a.addPoint(size.x, size.y + size.height/2);
    a.addPoint(size.x + size.width/2, size.y + size.height);
    a.addPoint(size.x + size.width, size.y + size.height/2);
    a.addPoint(size.x + size.width/4*3, size.y + size.height/2);
    a.addPoint(size.x + size.width/4*3, size.y);
    p.getCurrentShape().set(new ShapeInfo(origin, a,
        p.getSettings().getDrawSettings().getStroke(),
        p.getSettings().getCurrentColor().color(),
        p.getSettings().getDrawSettings().isFillEnabled()
    ));
  }
  
}
