/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.bb.autotune.action;

import br.com.bb.autotune.EditablePanel;

/**
 *
 * @author Juno
 */
public interface PanelAction {
  
  public String getName();
  
  public boolean accept(EditablePanel p);
  
  public void perform(EditablePanel p);
  
}
