/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.action.shape;

import br.com.bb.autotune.EditablePanel;
import br.com.bb.autotune.ShapeInfo;
import br.com.bb.autotune.settings.DrawSettings.DrawMode;
import java.awt.BasicStroke;
import java.awt.Point;
import java.awt.Rectangle;

/**
 *
 * @author Juno
 */
public class RectangleSelectionAction extends AbstractShapeAction {

  public RectangleSelectionAction() {
    super("RectangleSelection");
  }
  
  @Override
  public boolean accept(EditablePanel p) {
    return DrawMode.NONE == p.getSettings().getDrawSettings().getDrawMode();
  }

  @Override
  public void perform(EditablePanel p) {
    Point origin = getOrigin(p.getLastMouseEvents()[0], p.getSelectionShape());
    Rectangle rect;
    if(p.getSelectionShape().isEmpty()) {
      rect = new Rectangle(origin.x, origin.y, 0, 0);
    }
    else {
      Rectangle bounds = p.getSelectionShape().get().getShape().getBounds();
      rect = new Rectangle(
        Math.min(p.getLastMouseEvents()[0].getX(), bounds.x),
        Math.min(p.getLastMouseEvents()[0].getY(), bounds.y),
        Math.max(bounds.x + bounds.width - p.getLastMouseEvents()[0].getX(), p.getLastMouseEvents()[0].getX() - bounds.x),
        Math.max(bounds.y + bounds.height - p.getLastMouseEvents()[0].getY(), p.getLastMouseEvents()[0].getY() - bounds.y)
      );
    }
    p.getSelectionShape().set(new ShapeInfo(origin, rect, 
        new BasicStroke(2f, BasicStroke.CAP_BUTT, 
            BasicStroke.JOIN_BEVEL, 0, new float[]{5f}, 0
        ), 
        p.getSettings().getCurrentColor().color(), false)
    );
  }
  
}
