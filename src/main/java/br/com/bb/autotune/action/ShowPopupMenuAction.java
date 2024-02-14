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
public class ShowPopupMenuAction extends AbstractPanelAction {
  
  public ShowPopupMenuAction() {
    super("ShowPopupMenu");
  }
  
  @Override
  public boolean accept(EditablePanel p) {
    return KeyEvent.VK_F1 == p.getLastKeyEvents()[0].getExtendedKeyCode() 
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
        .forEach(a->a.perform(p));
    p.getAutotune().delay(100);
    p.getBackgroundImage().set(
        p.getAutotune().takeScreenshot()
    );
    p.getOwner().setVisible(true);
  }
  
}
