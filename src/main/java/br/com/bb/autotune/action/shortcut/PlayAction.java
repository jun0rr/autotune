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
public class PlayAction extends AbstractPanelAction {
  
  public PlayAction() {
    super("UpdateAction");
  }
  
  @Override
  public boolean accept(EditorPanel p) {
    //System.out.printf("* UpdateAction.accept(\n"
        //+ "  - lastKeyEvents[0]=%s\n"
        //+ "  - lastKeyEvents[1]=%s\n"
        //+ "  - lastKeyEvents[2]=%s\n)\n", 
        //p.getLastKeyEvents()[0], 
        //p.getLastKeyEvents()[1], 
        //p.getLastKeyEvents()[2]
    //);
    return p.getLastKeyEvents()[0] != null
        && KeyEvent.VK_F7 == p.getLastKeyEvents()[0].getExtendedKeyCode() 
        && p.getLastKeyEvents()[0].isAltDown()
        && KeyEvent.KEY_RELEASED == p.getLastKeyEvents()[0].getID();
  }
  
  @Override
  public void perform(EditorPanel p) {
    removeShortcutRecords(p, KeyEvent.VK_ALT, KeyEvent.VK_F7);
    p.getActionIndex().set(0);
    p.getOwner().setVisible(false);
    p.getAutotune().delay(200);
    p.getRecordActions().stream()
        .skip(p.getActionIndex().get())
        .peek(a->p.getActionIndex().incrementAndGet())
        .peek(a->p.getAutotune().delay(50))
        .forEach(a->a.accept(p));
    p.getAutotune().delay(200);
    p.getBackgroundImage().set(
        p.getAutotune().takeScreenshot()
    );
    p.getOwner().setVisible(true);
  }
  
}
