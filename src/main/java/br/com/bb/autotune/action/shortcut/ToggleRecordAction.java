/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.action.shortcut;

import br.com.bb.autotune.EditorPanel;
import br.com.bb.autotune.action.AbstractPanelAction;
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
  public boolean accept(EditorPanel p) {
    //System.out.printf("* ToggleRecordAction.accept: keys[0]=(%s:%d, alt:%s), keys[1]=(%s:%d, alt:%s), keys[2]=(%s:%d, alt:%s)%n", 
        //p.getLastKeyEvents()[0] != null ? p.getLastKeyEvents()[0].getKeyChar() : null,
        //p.getLastKeyEvents()[0] != null ? p.getLastKeyEvents()[0].getKeyCode() : 0,
        //p.getLastKeyEvents()[0] != null ? p.getLastKeyEvents()[0].isAltDown() : false,
        //p.getLastKeyEvents()[1] != null ? p.getLastKeyEvents()[1].getKeyChar() : null,
        //p.getLastKeyEvents()[1] != null ? p.getLastKeyEvents()[1].getKeyCode() : 0,
        //p.getLastKeyEvents()[1] != null ? p.getLastKeyEvents()[1].isAltDown() : false,
        //p.getLastKeyEvents()[2] != null ? p.getLastKeyEvents()[2].getKeyChar() : null,
        //p.getLastKeyEvents()[2] != null ? p.getLastKeyEvents()[2].getKeyCode() : 0,
        //p.getLastKeyEvents()[2] != null ? p.getLastKeyEvents()[2].isAltDown() : false
    //);
    return p.getLastKeyEvents()[0] != null
        && KeyEvent.VK_R == p.getLastKeyEvents()[0].getExtendedKeyCode() 
        && p.getLastKeyEvents()[0].isAltDown()
        && KeyEvent.KEY_RELEASED == p.getLastKeyEvents()[0].getID();
  }
  
  @Override
  public void perform(EditorPanel p) {
    removeShortcutRecords(p, KeyEvent.VK_ALT, KeyEvent.VK_R);
    p.getSettings().setRecord(!p.getSettings().isRecord());
  }
  
}
