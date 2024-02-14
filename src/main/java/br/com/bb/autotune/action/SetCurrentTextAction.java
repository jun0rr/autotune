/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.action;

import br.com.bb.autotune.EditablePanel;
import br.com.bb.autotune.TextPoint;
import java.awt.event.MouseEvent;

/**
 *
 * @author Juno
 */
public class SetCurrentTextAction extends AbstractPanelAction {
  
  public SetCurrentTextAction() {
    super("SetCurrentTextAction");
  }
  
  @Override
  public boolean accept(EditablePanel p) {
    return p.getLastMouseEvents()[0] != null
        && MouseEvent.MOUSE_CLICKED == p.getLastMouseEvents()[0].getID()
        && MouseEvent.BUTTON1 == p.getLastMouseEvents()[0].getButton();
  }
  
  @Override
  public void perform(EditablePanel p) {
    p.getCurrentText().set(new TextPoint(p.getLastMouseEvents()[0].getPoint(), p.getSettings().getFont(), p.getSettings().getCurrentColor().color()));
    p.getTextPoints().add(p.getCurrentText().get());
  }
  
}
