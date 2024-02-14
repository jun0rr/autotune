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
public class ArrowUpAction extends AbstractShapeAction {

  public ArrowUpAction() {
    super("ArrowUp");
  }
  
  @Override
  public boolean accept(EditablePanel p) {
    return DrawMode.ARROW_UP == p.getSettings().getDrawSettings().getDrawMode();
  }

  @Override
  public void perform(EditablePanel p) {
    Point origin = getOrigin(p.getLastMouseEvents()[0], p.getCurrentShape());
    Rectangle size = getSize(p.getLastMouseEvents()[0], p.getCurrentShape());
    Polygon d = new Polygon();
    d.addPoint(size.x + size.width/2, size.y);
    d.addPoint(size.x + size.width, size.y + size.height/2);
    d.addPoint(size.x + size.width/4*3, size.y + size.height/2);
    d.addPoint(size.x + size.width/4*3, size.y + size.height);
    d.addPoint(size.x + size.width/4, size.y + size.height);
    d.addPoint(size.x + size.width/4, size.y + size.height/2);
    d.addPoint(size.x, size.y + size.height/2);
    p.getCurrentShape().set(new ShapeInfo(origin, d,
        p.getSettings().getDrawSettings().getStroke(),
        p.getSettings().getCurrentColor().color(),
        p.getSettings().getDrawSettings().isFillEnabled()
    ));
  }
  
}
