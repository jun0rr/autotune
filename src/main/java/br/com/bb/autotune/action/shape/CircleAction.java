/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.action.shape;

import br.com.bb.autotune.EditorPanel;
import br.com.bb.autotune.ShapeInfo;
import br.com.bb.autotune.settings.DrawSettings.DrawMode;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

/**
 *
 * @author Juno
 */
public class CircleAction extends AbstractShapeAction {

  public CircleAction() {
    super("Circle");
  }
  
  @Override
  public boolean accept(EditorPanel p) {
    return DrawMode.CIRCLE == p.getSettings().getDrawSettings().getDrawMode();
  }

  @Override
  public void perform(EditorPanel p) {
    Point origin = getOrigin(p.getLastMouseEvents()[0], p.getCurrentShape());
    Rectangle size = getSize(p.getLastMouseEvents()[0], p.getCurrentShape());
    Ellipse2D.Float circle = new Ellipse2D.Float(
        Integer.valueOf(size.x).floatValue(),
        Integer.valueOf(size.y).floatValue(),
        Integer.valueOf(size.width).floatValue(),
        Integer.valueOf(size.height).floatValue()
    );
    p.getCurrentShape().set(new ShapeInfo(origin, circle,
        p.getSettings().getDrawSettings().getStroke(),
        p.getSettings().getCurrentColor().color(),
        p.getSettings().getDrawSettings().isFillEnabled()
    ));
  }
  
}
