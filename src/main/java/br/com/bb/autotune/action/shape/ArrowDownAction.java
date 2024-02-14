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
import java.util.List;

/**
 *
 * @author Juno
 */
public class ArrowDownAction extends AbstractShapeAction {

  public ArrowDownAction(Settings s) {
    super(s);
  }
  
  @Override
  public boolean accept() {
    return DrawMode.ARROW_DOWN == settings.getDrawSettings().getDrawMode();
  }

  @Override
  public void perform(MouseEvent e, Reference<ShapeInfo> current, List<ShapeInfo> shapes) {
    Point origin = getOrigin(e, current);
    Rectangle size = getSize(e, current);
    Polygon a = new Polygon();
    a.addPoint(size.x + size.width/4, size.y);
    a.addPoint(size.x + size.width/4, size.y + size.height/2);
    a.addPoint(size.x, size.y + size.height/2);
    a.addPoint(size.x + size.width/2, size.y + size.height);
    a.addPoint(size.x + size.width, size.y + size.height/2);
    a.addPoint(size.x + size.width/4*3, size.y + size.height/2);
    a.addPoint(size.x + size.width/4*3, size.y);
    current.set(new ShapeInfo(origin, a,
        settings.getDrawSettings().getStroke(),
        settings.getCurrentColor().color(),
        settings.getDrawSettings().isFillEnabled()
    ));
  }
  
}
