/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.action;

import br.com.bb.autotune.EditablePanel;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 *
 * @author Juno
 */
public class DefaultPanelAction implements PanelAction {
  
  private final String name;
  
  private final Predicate<EditablePanel> accept;
  
  private final Consumer<EditablePanel> perform;

  public DefaultPanelAction(String name, Predicate<EditablePanel> accept, Consumer<EditablePanel> perform) {
    this.name = Objects.requireNonNull(name);
    this.accept = Objects.requireNonNull(accept);
    this.perform = Objects.requireNonNull(perform);
  }

  @Override
  public boolean accept(EditablePanel p) {
    return accept.test(p);
  }

  @Override
  public void perform(EditablePanel p) {
    perform.accept(p);
  }

  public String getName() {
    return name;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 37 * hash + Objects.hashCode(this.name);
    hash = 37 * hash + Objects.hashCode(this.accept);
    hash = 37 * hash + Objects.hashCode(this.perform);
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
    final DefaultPanelAction other = (DefaultPanelAction) obj;
    if (!Objects.equals(this.name, other.name)) {
      return false;
    }
    if (!Objects.equals(this.accept, other.accept)) {
      return false;
    }
    return Objects.equals(this.perform, other.perform);
  }

  @Override
  public String toString() {
    return "DefaultPanelAction{" + "name=" + name + '}';
  }
  
}
