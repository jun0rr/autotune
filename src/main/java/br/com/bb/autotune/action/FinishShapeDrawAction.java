/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.action;

import br.com.bb.autotune.EditorPanel;
import java.awt.event.MouseEvent;

/**
 *
 * @author Juno
 */
public class FinishShapeDrawAction extends AbstractPanelAction {
  
  public FinishShapeDrawAction() {
    super("FinishShapeDrawAction");
  }
  
  @Override
  public boolean accept(EditorPanel p) {
    return p.getLastMouseEvents()[0] != null
        && MouseEvent.MOUSE_RELEASED == p.getLastMouseEvents()[0].getID()
        && MouseEvent.BUTTON1 == p.getLastMouseEvents()[0].getButton()
        && p.getCurrentShape().isPresent();
  }
  
  @Override
  public void perform(EditorPanel p) {
    p.getShapes().add(p.getCurrentShape().get());
    p.getCurrentShape().set(null);
  }
  
}
