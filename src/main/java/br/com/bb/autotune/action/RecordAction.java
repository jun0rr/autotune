/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.action;

import br.com.bb.autotune.EditorPanel;
import java.util.function.Consumer;
import javax.swing.Icon;

/**
 *
 * @author F6036477
 */
public interface RecordAction extends Consumer<EditorPanel> {
  
  public String getText();
  
  public Icon getIcon();
  
}
