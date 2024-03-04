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
public class ToggleRecordAction extends AbstractPanelAction {
  
  public ToggleRecordAction() {
    super("ToggleRecordAction");
  }
  
  @Override
  public boolean accept(EditablePanel p) {
    return p.getLastKeyEvents()[0] != null
        && KeyEvent.VK_R == p.getLastKeyEvents()[0].getExtendedKeyCode() 
        && p.getLastKeyEvents()[0].isAltDown();
  }
  
  @Override
  public void perform(EditablePanel p) {
    if(p.getSettings().isRecord()) {
      p.getRecordActions().remove(p.getRecordActions().size() -1);
      p.getRecordActions().remove(p.getRecordActions().size() -1);
    }
    p.getSettings().setRecord(!p.getSettings().isRecord());
  }
  
}
