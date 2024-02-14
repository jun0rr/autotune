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
public class CircleAction extends AbstractShapeAction {

  public CircleAction(Settings s) {
    super(s);
  }
  
  @Override
  public boolean accept() {
    return DrawMode.CIRCLE == settings.getDrawSettings().getDrawMode();
  }

  @Override
  public void perform(MouseEvent e, Reference<ShapeInfo> current, List<ShapeInfo> shapes) {
    Point origin = getOrigin(e, current);
    Rectangle size = getSize(e, current);
    Ellipse2D.Float circle = new Ellipse2D.Float(
        Integer.valueOf(size.x).floatValue(),
        Integer.valueOf(size.y).floatValue(),
        Integer.valueOf(size.width).floatValue(),
        Integer.valueOf(size.height).floatValue()
    );
    current.set(new ShapeInfo(origin, circle,
        settings.getDrawSettings().getStroke(),
        settings.getCurrentColor().color(),
        settings.getDrawSettings().isFillEnabled()
    ));
  }
  
}
