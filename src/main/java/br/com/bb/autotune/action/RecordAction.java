/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.action;

import br.com.bb.autotune.Autotune;
import java.util.function.Consumer;

/**
 *
 * @author F6036477
 */
public interface RecordAction extends Consumer<Autotune> {
  
  public String getName();
  
}
