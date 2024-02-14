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
public class LineAction extends AbstractShapeAction {

  public LineAction(Settings s) {
    super(s);
  }
  
  @Override
  public boolean accept() {
    return DrawMode.LINE == settings.getDrawSettings().getDrawMode();
  }

  @Override
  public void perform(MouseEvent e, Reference<ShapeInfo> current, List<ShapeInfo> shapes) {
    Point origin = getOrigin(e, current);
    Polygon p = new Polygon();
    p.addPoint(origin.x, origin.y);
    p.addPoint(e.getPoint().x, e.getPoint().y);
    current.set(new ShapeInfo(origin, p,
        settings.getDrawSettings().getStroke(), 
        settings.getCurrentColor().color(), false
    ));
  }
  
}
