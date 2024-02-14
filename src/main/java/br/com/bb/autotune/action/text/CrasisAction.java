/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.action.text;

import br.com.bb.autotune.Autotune;
import br.com.bb.autotune.TextPoint;
import java.awt.event.KeyEvent;

/**
 *
 * @author Juno
 */
public class CrasisAction implements TextAction {

  @Override
  public boolean accept(KeyEvent[] e) {
    return e[2] != null && e[2].getExtendedKeyCode() == 129 && e[1].getExtendedKeyCode() == 16;
  }

  @Override
  public void perform(KeyEvent[] e, TextPoint t) {
    int code = (mod(e[2]) * e[2].getExtendedKeyCode() -1) * mod(e[0]) * e[0].getExtendedKeyCode();
    if(Autotune.KEYCODES_MAP.containsKey(code)) {
      t.text().append(Autotune.KEYCODES_MAP.get(code));
    }
  }
  
}