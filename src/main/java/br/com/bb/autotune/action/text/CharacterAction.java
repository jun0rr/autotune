/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.action.text;

import br.com.bb.autotune.Autotune;
import br.com.bb.autotune.EditablePanel;

/**
 *
 * @author Juno
 */
public class CharacterAction extends AbstractTextAction {
  
  public CharacterAction() {
    super("CharacterAction");
  }

  @Override
  public boolean accept(EditablePanel p) {
    return p.getCurrentText().isPresent() 
        && Autotune.CHAR_MAP.containsKey(p.getLastKeyEvents()[0].getKeyChar());
  }

  @Override
  public void perform(EditablePanel p) {
    p.getCurrentText().get().text().append(p.getLastKeyEvents()[0].getKeyChar());
  }

}
