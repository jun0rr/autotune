/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.action.text;

import br.com.bb.autotune.Autotune;
import br.com.bb.autotune.EditablePanel;
import br.com.bb.autotune.TextPoint;
import java.awt.event.KeyEvent;

/**
 *
 * @author Juno
 */
public class TildeAction extends AbstractTextAction {
  
  public TildeAction() {
    super("TildeAction");
  }

  @Override
  public boolean accept(EditablePanel p) {
    return p.getCurrentText().isPresent() 
        && p.getLastKeyEvents()[1] != null && p.getLastKeyEvents()[1].getExtendedKeyCode() == 131;
  }

  @Override
  public void perform(EditablePanel p) {
    int code = (mod(p.getLastKeyEvents()[1]) * p.getLastKeyEvents()[1].getExtendedKeyCode() -1) 
        * mod(p.getLastKeyEvents()[0]) * p.getLastKeyEvents()[0].getExtendedKeyCode();
    if(Autotune.KEYCODES_MAP.containsKey(code)) {
      p.getCurrentText().get().text().append(Autotune.KEYCODES_MAP.get(code));
    }
  }
  
}
