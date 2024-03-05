/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.action;

import br.com.bb.autotune.EditablePanel;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 *
 * @author Juno
 */
public class ShowPopupMenuAction extends AbstractPanelAction {
  
  public ShowPopupMenuAction() {
    super("ShowPopupMenuAction");
  }
  
  @Override
  public boolean accept(EditablePanel p) {
    return p.getLastKeyEvents()[0] != null
        && KeyEvent.VK_F1 == p.getLastKeyEvents()[0].getExtendedKeyCode() 
        && p.getLastKeyEvents()[0].isAltDown()
        || (p.getLastMouseEvents()[0] != null
        && MouseEvent.MOUSE_CLICKED == p.getLastMouseEvents()[0].getID() 
        && MouseEvent.BUTTON3 == p.getLastMouseEvents()[0].getButton()
        && p.getLastMouseEvents()[0].isAltDown());
  }
  
  @Override
  public void perform(EditablePanel p) {
    Point xy;
    if(p.getLastMouseEvents()[0] != null
        && MouseEvent.BUTTON3 == p.getLastMouseEvents()[0].getButton()
        && p.getLastMouseEvents()[0].isControlDown()) {
      xy = p.getLastMouseEvents()[0].getPoint();
    }
    else {
      Point loc = p.getLocationOnScreen();
      Dimension dim = p.getSize();
      xy = new Point(loc.x + dim.width / 2, loc.y + dim.height / 2);
    }
    p.getPopupMenu().show(p, xy.x, xy.y);
    removeShortcutRecords(p, KeyEvent.VK_ALT, KeyEvent.VK_F1);
    removeMouseClickRecords(p, MouseEvent.BUTTON3);
  }
  
}
