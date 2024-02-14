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
public class CharacterAction implements TextAction {

  @Override
  public boolean accept(KeyEvent[] e) {
    return Autotune.CHAR_MAP.containsKey(e[0].getKeyChar());
  }

  @Override
  public void perform(KeyEvent[] e, TextPoint t) {
    t.text().append(e[0].getKeyChar());
  }
  
}
