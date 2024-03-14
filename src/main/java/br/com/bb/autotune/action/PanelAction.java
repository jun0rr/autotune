/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.bb.autotune.action;

import br.com.bb.autotune.EditorPanel;

/**
 *
 * @author Juno
 */
public interface PanelAction {
  
  public String getName();
  
  public boolean accept(EditorPanel p);
  
  public void perform(EditorPanel p);
  
}
