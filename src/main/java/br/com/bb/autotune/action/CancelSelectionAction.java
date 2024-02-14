/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.action;

import br.com.bb.autotune.EditablePanel;
import java.awt.event.MouseEvent;

/**
 *
 * @author Juno
 */
public class CancelSelectionAction extends AbstractPanelAction {
  
  public CancelSelectionAction() {
    super("CancelSelectionAction");
  }
  
  @Override
  public boolean accept(EditablePanel p) {
    return p.getLastMouseEvents()[0] != null
        && (MouseEvent.MOUSE_CLICKED == p.getLastMouseEvents()[0].getID()
        || MouseEvent.MOUSE_PRESSED == p.getLastMouseEvents()[0].getID())
        //&& MouseEvent.BUTTON3 == p.getLastMouseEvents()[0].getButton()
        && !p.getLastMouseEvents()[0].isControlDown();
  }
  
  @Override
  public void perform(EditablePanel p) {
    p.getSelectionShape().set(null);
    p.repaint();
  }
  
}
