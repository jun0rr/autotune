/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.action.shape;

import br.com.bb.autotune.EditorPanel;
import br.com.bb.autotune.ShapeInfo;
import br.com.bb.autotune.settings.DrawSettings.DrawMode;
import br.com.bb.autotune.settings.Settings;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;

/**
 *
 * @author Juno
 */
public class ArrowLeftAction extends AbstractShapeAction {

  public ArrowLeftAction() {
    super("ArrowLeft");
  }
  
  @Override
  public boolean accept(EditorPanel p) {
    return DrawMode.ARROW_LEFT == p.getSettings().getDrawSettings().getDrawMode();
  }

  @Override
  public void perform(EditorPanel p) {
    Point origin = getOrigin(p.getLastMouseEvents()[0], p.getCurrentShape());
    Rectangle size = getSize(p.getLastMouseEvents()[0], p.getCurrentShape());
    Polygon b = new Polygon();
    b.addPoint(size.x, size.y + size.height/2);
    b.addPoint(size.x + size.width/2, size.y);
    b.addPoint(size.x + size.width/2, size.y + size.height/4);
    b.addPoint(size.x + size.width, size.y + size.height/4);
    b.addPoint(size.x + size.width, size.y + size.height/4*3);
    b.addPoint(size.x + size.width/2, size.y + size.height/4*3);
    b.addPoint(size.x + size.width/2, size.y + size.height);
    p.getCurrentShape().set(new ShapeInfo(origin, b,
        p.getSettings().getDrawSettings().getStroke(),
        p.getSettings().getCurrentColor().color(),
        p.getSettings().getDrawSettings().isFillEnabled()
    ));
  }
  
}
