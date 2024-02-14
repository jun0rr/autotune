/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.action;

import br.com.bb.autotune.Autotune;
import java.util.Objects;
import java.util.function.Consumer;

/**
 *
 * @author F6036477
 */
public class DefaultRecordAction implements RecordAction {
  
  private final String name;
  
  private final Consumer<Autotune> action;
  
  public DefaultRecordAction(String name, Consumer<Autotune> c) {
    this.name = Objects.requireNonNull(name);
    this.action = Objects.requireNonNull(c);
  }
  
  public DefaultRecordAction(Consumer<Autotune> c, String fmt, Object... args) {
    this(String.format(fmt, args), c);
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void accept(Autotune a) {
    action.accept(a);
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 41 * hash + Objects.hashCode(this.name);
    hash = 41 * hash + Objects.hashCode(this.action);
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
    final DefaultRecordAction other = (DefaultRecordAction) obj;
    if (!Objects.equals(this.name, other.name)) {
      return false;
    }
    return Objects.equals(this.action, other.action);
  }

  @Override
  public String toString() {
    return "RecordAction{" + "name=" + name + '}';
  }
  
}
