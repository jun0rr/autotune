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
public class ShowRecordListAction extends AbstractPanelAction {
  
  public ShowRecordListAction() {
    super("ShowRecordListAction");
  }
  
  @Override
  public boolean accept(EditablePanel p) {
    return p.getLastKeyEvents()[0] != null
        && KeyEvent.VK_L == p.getLastKeyEvents()[0].getExtendedKeyCode() 
        && p.getLastKeyEvents()[0].isControlDown();
  }
  
  @Override
  public void perform(EditablePanel p) {
    p.getRecordActions().remove(p.getRecordActions().size() -1);
    p.getRecordActions().remove(p.getRecordActions().size() -1);
    p.getDialogRecords().showDialog();
  }
  
}
