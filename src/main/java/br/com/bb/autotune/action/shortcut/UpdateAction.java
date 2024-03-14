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
public class UpdateAction extends AbstractPanelAction {
  
  public static final String SHORTCUT_REGEX = "key(Press|Release)\\(\\s?\\'.\\'\\=%d\\s?\\)";
  
  public UpdateAction() {
    super("UpdateAction");
  }
  
  @Override
  public boolean accept(EditorPanel p) {
    return p.getLastKeyEvents()[0] != null
        && KeyEvent.VK_F5 == p.getLastKeyEvents()[0].getExtendedKeyCode() 
        && p.getLastKeyEvents()[0].isAltDown()
        && KeyEvent.KEY_RELEASED == p.getLastKeyEvents()[0].getID();
  }
  
  @Override
  public void perform(EditorPanel p) {
    removeShortcutRecords(p, KeyEvent.VK_ALT, KeyEvent.VK_F5);
    p.getOwner().setVisible(false);
    p.getAutotune().delay(150);
    p.getRecordActions().stream()
        .skip(p.getActionIndex().get())
        .peek(a->p.getActionIndex().incrementAndGet())
        .peek(a->p.getAutotune().delay(30))
        .forEach(a->a.accept(p.getAutotune()));
    p.getAutotune().delay(150);
    p.getBackgroundImage().set(
        p.getAutotune().takeScreenshot()
    );
    p.getOwner().setVisible(true);
  }
  
}
