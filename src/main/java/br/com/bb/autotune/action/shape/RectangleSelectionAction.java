/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.action.shape;

import br.com.bb.autotune.Reference;
import br.com.bb.autotune.ShapeInfo;
import br.com.bb.autotune.settings.DrawSettings.DrawMode;
import br.com.bb.autotune.settings.Settings;
import java.awt.BasicStroke;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 *
 * @author Juno
 */
public class RectangleSelectionAction extends AbstractShapeAction {

  public RectangleSelectionAction(Settings s) {
    super(s);
  }
  
  @Override
  public boolean accept() {
    return DrawMode.NONE == settings.getDrawSettings().getDrawMode();
  }

  @Override
  public void perform(MouseEvent e, Reference<ShapeInfo> current, List<ShapeInfo> shapes) {
    Point origin = getOrigin(e, current);
    Rectangle rect;
    if(current.isEmpty()) {
      rect = new Rectangle(origin.x, origin.y, 0, 0);
    }
    else {
      Rectangle bounds = current.get().getShape().getBounds();
      rect = new Rectangle(
        Math.min(e.getX(), bounds.x),
        Math.min(e.getY(), bounds.y),
        Math.max(bounds.x + bounds.width - e.getX(), e.getX() - bounds.x),
        Math.max(bounds.y + bounds.height - e.getY(), e.getY() - bounds.y)
      );
    }
    current.set(new ShapeInfo(origin, rect, 
        new BasicStroke(2f, BasicStroke.CAP_BUTT, 
            BasicStroke.JOIN_BEVEL, 0, new float[]{5f}, 0
        ), 
        settings.getCurrentColor().color(), false)
    );
  }
  
}
