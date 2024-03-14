/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.action.shape;

import br.com.bb.autotune.EditorPanel;
import br.com.bb.autotune.ShapeInfo;
import br.com.bb.autotune.settings.DrawSettings.DrawMode;

/**
 *
 * @author Juno
 */
public class RectangleAction extends AbstractShapeAction {

  public RectangleAction() {
    super("Rectangle");
  }
  
  @Override
  public boolean accept(EditorPanel p) {
    return DrawMode.RECTANGLE == p.getSettings().getDrawSettings().getDrawMode();
  }

  @Override
  public void perform(EditorPanel p) {
    p.getCurrentShape().set(new ShapeInfo(getOrigin(p.getLastMouseEvents()[0], p.getCurrentShape()), 
        getSize(p.getLastMouseEvents()[0], p.getCurrentShape()),
        p.getSettings().getDrawSettings().getStroke(),
        p.getSettings().getCurrentColor().color(),
        p.getSettings().getDrawSettings().isFillEnabled()
    ));
  }
  
}
