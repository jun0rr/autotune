/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.action;

import br.com.bb.autotune.Autotune;
import java.util.Objects;
import java.util.function.Consumer;
import javax.swing.Icon;

/**
 *
 * @author F6036477
 */
public class DefaultRecordAction implements RecordAction {
  
  private final String text;
  
  private final Icon icon;
  
  private final Consumer<Autotune> action;
  
  public DefaultRecordAction(Consumer<Autotune> c, Icon icon, String fmt, Object... args) {
    this.action = Objects.requireNonNull(c);
    this.icon = Objects.requireNonNull(icon);
    this.text = String.format(fmt, args);
  }

  @Override
  public String getText() {
    return text;
  }
  
  @Override
  public Icon getIcon() {
    return icon;
  }

  @Override
  public void accept(Autotune a) {
    action.accept(a);
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 71 * hash + Objects.hashCode(this.text);
    hash = 71 * hash + Objects.hashCode(this.icon);
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
    if (!Objects.equals(this.text, other.text)) {
      return false;
    }
    return Objects.equals(this.icon, other.icon);
  }

  @Override
  public String toString() {
    return text;
  }

}
