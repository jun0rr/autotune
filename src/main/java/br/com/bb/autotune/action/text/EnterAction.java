/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.action.text;

import br.com.bb.autotune.EditorPanel;
import java.awt.event.KeyEvent;

/**
 *
 * @author Juno
 */
public class EnterAction extends AbstractTextAction {
  
  public EnterAction() {
    super("EnterAction");
  }

  @Override
  public boolean accept(EditorPanel p) {
    return p.getCurrentText().isPresent() 
        && KeyEvent.VK_ENTER == p.getLastKeyEvents()[0].getKeyCode()
        && !p.getLastKeyEvents()[0].isAltDown();
  }

  @Override
  public void perform(EditorPanel p) {
    p.getCurrentText().get().text().append('\n');
  }
  
}
