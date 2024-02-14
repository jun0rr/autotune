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
public class ArrowUpAction extends AbstractShapeAction {

  public ArrowUpAction(Settings s) {
    super(s);
  }
  
  @Override
  public boolean accept() {
    return DrawMode.ARROW_UP == settings.getDrawSettings().getDrawMode();
  }

  @Override
  public void perform(MouseEvent e, Reference<ShapeInfo> current, List<ShapeInfo> shapes) {
    Point origin = getOrigin(e, current);
    Rectangle size = getSize(e, current);
    Polygon d = new Polygon();
    d.addPoint(size.x + size.width/2, size.y);
    d.addPoint(size.x + size.width, size.y + size.height/2);
    d.addPoint(size.x + size.width/4*3, size.y + size.height/2);
    d.addPoint(size.x + size.width/4*3, size.y + size.height);
    d.addPoint(size.x + size.width/4, size.y + size.height);
    d.addPoint(size.x + size.width/4, size.y + size.height/2);
    d.addPoint(size.x, size.y + size.height/2);
    current.set(new ShapeInfo(origin, d,
        settings.getDrawSettings().getStroke(),
        settings.getCurrentColor().color(),
        settings.getDrawSettings().isFillEnabled()
    ));
  }
  
}
