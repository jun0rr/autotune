/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.bb.autotune.action.text;

import br.com.bb.autotune.action.PanelAction;
import java.awt.event.KeyEvent;
import java.util.Objects;

/**
 *
 * @author Juno
 */

public abstract class AbstractTextAction implements PanelAction {
  
  private final String name;
  
  public AbstractTextAction(String name) {
    this.name  = Objects.requireNonNull(name);
  }
  
  @Override
  public String getName() {
    return name;
  }
  
  public int mod(KeyEvent e) {
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
