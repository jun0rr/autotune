/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.bb.autotune.action;

/**
 *
 * @author F6036477
 */
public interface RecordScriptEntry {
  
  public RecordAction parse(String s);
  
  public String toString(RecordAction ra);
  
}
