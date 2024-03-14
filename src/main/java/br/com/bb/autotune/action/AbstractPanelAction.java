/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.action;

import br.com.bb.autotune.EditorPanel;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 *
 * @author Juno
 */
public abstract class AbstractPanelAction implements PanelAction {
  
  public static final String SHORTCUT_REGEX = "key(Press|Release)\\(\\s?\\'.\\'\\=%d\\s?\\)";
  
  public static final String MOUSE_CLICK_REGEX = "mouse(Press|Release)\\(\\s?%d\\s?\\)";
  
  private final String name;
  
  public AbstractPanelAction(String name) {
    this.name = Objects.requireNonNull(name);
  }
  
  public void removeShortcutRecords(EditorPanel p, int ... keys) {
    if(p.getSettings().isRecord()) {
      for(int i = p.getRecordActions().size() -1; i >= 0 && i >= p.getRecordActions().size() -5; i--) {
        RecordAction r = p.getRecordActions().get(i);
        boolean test = false;
        for(int j = 0; j < keys.length; j++) {
          test = test || Pattern.matches(String.format(SHORTCUT_REGEX, keys[j]), r.getText());
        }
        if(test) {
          p.getRecordActions().remove(i);
        }
      }
    }
  }
  
  public void removeMouseClickRecords(EditorPanel p, int ... buttons) {
    if(p.getSettings().isRecord()) {
      for(int i = p.getRecordActions().size() -1; i >= 0 && i >= p.getRecordActions().size() -5; i--) {
        RecordAction r = p.getRecordActions().get(i);
        boolean test = false;
        for(int j = 0; j < buttons.length; j++) {
          test = test || Pattern.matches(String.format(MOUSE_CLICK_REGEX, buttons[j]), r.getText());
        }
        if(test) {
          p.getRecordActions().remove(i);
        }
      }
    }
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
