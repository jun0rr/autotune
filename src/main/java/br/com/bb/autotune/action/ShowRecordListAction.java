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
        && p.getLastKeyEvents()[0].isAltDown()
        && KeyEvent.KEY_RELEASED == p.getLastKeyEvents()[0].getID();
  }
  
  @Override
  public void perform(EditablePanel p) {
    removeShortcutRecords(p, KeyEvent.VK_ALT, KeyEvent.VK_L);
    p.getDialogRecords().showDialog();
  }
  
}
