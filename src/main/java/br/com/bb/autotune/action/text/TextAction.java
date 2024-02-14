/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.bb.autotune.action.text;

import br.com.bb.autotune.TextPoint;
import java.awt.event.KeyEvent;

/**
 *
 * @author Juno
 */

public interface TextAction {
  
  public boolean accept(KeyEvent[] e);
  
  public void perform(KeyEvent[] e, TextPoint t);
  
  public default int mod(KeyEvent e) {
    int mod = 1;
    if(e.isShiftDown() || Character.isUpperCase(e.getKeyChar())) {
      mod *= 16;
    }
    if(e.isControlDown()) {
      mod *= 17;
    }
    if(e.isAltDown()) {
      mod *= 18;
    }
    return mod;
  }
  
}
