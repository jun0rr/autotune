/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.action.text;

import br.com.bb.autotune.TextPoint;
import java.awt.event.KeyEvent;

/**
 *
 * @author Juno
 */
public class BackspaceAction implements TextAction {

  @Override
  public boolean accept(KeyEvent[] e) {
    return KeyEvent.VK_BACK_SPACE == e[0].getKeyCode();
  }

  @Override
  public void perform(KeyEvent[] e, TextPoint t) {
    t.text().deleteCharAt(t.text().length() -1);
  }
  
}
