/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.script;

import br.com.bb.autotune.action.RecordAction;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author F6036477
 */
public class RecordScript {
  
  private final List<RecordAction> records;
  
  public RecordScript(List<RecordAction> records) {
    this.records = Objects.requireNonNull(records);
  }
  
  
  
}
