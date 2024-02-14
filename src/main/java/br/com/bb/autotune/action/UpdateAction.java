/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.action;

import br.com.bb.autotune.EditablePanel;
import java.awt.event.KeyEvent;

/**
 *
 * @author Juno
 */
public class UpdateAction extends AbstractPanelAction {
  
  public UpdateAction() {
    super("UpdateAction");
  }
  
  @Override
  public boolean accept(EditablePanel p) {
    return p.getLastKeyEvents()[0] != null
        && KeyEvent.VK_F2 == p.getLastKeyEvents()[0].getExtendedKeyCode() 
        && p.getLastKeyEvents()[0].isControlDown();
  }
  
  @Override
  public void perform(EditablePanel p) {
    p.getOwner().setVisible(false);
    p.getAutotune().delay(100);
    p.getRecordActions().stream()
        .skip(p.getActionIndex().get())
        .peek(a->p.getActionIndex().incrementAndGet())
        .peek(a->p.getAutotune().delay(10))
        .forEach(a->a.accept(p.getAutotune()));
    p.getAutotune().delay(100);
    p.getBackgroundImage().set(
        p.getAutotune().takeScreenshot()
    );
    p.getOwner().setVisible(true);
  }
  
}
