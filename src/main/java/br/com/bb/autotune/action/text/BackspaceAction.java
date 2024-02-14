/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.action.text;

import br.com.bb.autotune.EditablePanel;
import java.awt.event.KeyEvent;

/**
 *
 * @author Juno
 */
public class BackspaceAction extends AbstractTextAction {
  
  public BackspaceAction() {
    super("BackspaceAction");
  }

  @Override
  public boolean accept(EditablePanel p) {
    return p.getCurrentText().isPresent() 
        && KeyEvent.VK_BACK_SPACE == p.getLastKeyEvents()[0].getKeyCode();
  }

  @Override
  public void perform(EditablePanel p) {
    p.getCurrentText().get().text().deleteCharAt(
        p.getCurrentText().get().text().length() -1
    );
  }
  
}
