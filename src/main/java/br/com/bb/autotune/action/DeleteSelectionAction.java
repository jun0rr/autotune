/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.action;

import br.com.bb.autotune.EditablePanel;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.stream.Collectors;

/**
 *
 * @author Juno
 */
public class DeleteSelectionAction extends AbstractPanelAction {
  
  public DeleteSelectionAction() {
    super("DeleteSelectionAction");
  }
  
  @Override
  public boolean accept(EditablePanel p) {
    return p.getLastKeyEvents()[0] != null
        && KeyEvent.VK_DELETE == p.getLastKeyEvents()[0].getKeyCode()
        && p.getLastKeyEvents()[0].isControlDown();
  }
  
  @Override
  public void perform(EditablePanel p) {
    if(p.getSettings().isRecord()) {
      p.getRecordActions().remove(p.getRecordActions().size() -1);
    }
    if(p.getSelectionShape().isPresent()) {
      p.getTextPoints().stream()
          .filter(t->p.getSelectionShape().get().getShape().contains(t.getPoint()))
          .collect(Collectors.toList())
          .forEach(p.getTextPoints()::remove);
      p.getShapes().stream()
          .filter(s->p.getSelectionShape().get().getShape().contains(s.getPoint()))
          .collect(Collectors.toList())
          .forEach(p.getShapes()::remove);
    }
    else {
      p.getTextPoints().clear();
      p.getShapes().clear();
    }
    if(!p.getTextPoints().contains(p.getCurrentText().get())){
      p.getCurrentText().set(null);
    }
    p.getSelectionShape().set(null);
  }
  
}