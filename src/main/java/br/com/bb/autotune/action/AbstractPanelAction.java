/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.action;

import java.util.Objects;

/**
 *
 * @author Juno
 */
public abstract class AbstractPanelAction implements PanelAction {
  
  private final String name;
  
  public AbstractPanelAction(String name) {
    this.name = Objects.requireNonNull(name);
  }
  
  @Override
  public String getName() {
    return name;
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 97 * hash + Objects.hashCode(this.name);
    hash = 97 * hash + Objects.hashCode(getClass().getName());
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final AbstractPanelAction other = (AbstractPanelAction) obj;
    return Objects.equals(this.name, other.name);
  }

  @Override
  public String toString() {
    return "PanelAction{" + "name=" + name + '}';
  }
  
}
