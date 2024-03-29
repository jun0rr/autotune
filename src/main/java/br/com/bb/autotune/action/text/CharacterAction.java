/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.action.text;

import br.com.bb.autotune.Autotune;
import br.com.bb.autotune.EditorPanel;

/**
 *
 * @author Juno
 */
public class CharacterAction extends AbstractTextAction {
  
  public CharacterAction() {
    super("CharacterAction");
  }

  @Override
  public boolean accept(EditorPanel p) {
    return p.getCurrentText().isPresent() 
        && p.getLastKeyEvents()[0] != null
        && Autotune.CHAR_MAP.containsKey(p.getLastKeyEvents()[0].getKeyChar())
        && !p.getLastKeyEvents()[0].isAltDown();
  }

  @Override
  public void perform(EditorPanel p) {
    p.getCurrentText().get().text().append(p.getLastKeyEvents()[0].getKeyChar());
  }

}
