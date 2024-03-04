/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.script;

import br.com.bb.autotune.Autotune;
import br.com.bb.autotune.action.DefaultRecordAction;
import br.com.bb.autotune.action.RecordAction;
import br.com.bb.autotune.icon.FontAwesome;
import br.com.bb.autotune.icon.FontIcon;
import java.awt.event.KeyEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author F6036477
 */
public class KeyTypeEntry implements RecordScriptEntry {

  public static final String ENTRY_REGEX = "keyType\\(\\s?\\'(.)\\'\\=([0-9]{1,10})\\s?\\)";
  
  public static final Pattern ENTRY_PATTERN = Pattern.compile(ENTRY_REGEX);
  
  @Override
  public boolean canParse(String s) {
    return ENTRY_PATTERN.asMatchPredicate().test(s);
  }
  
  @Override
  public RecordAction parse(String s) {
    Matcher m = ENTRY_PATTERN.matcher(s);
    if(!m.matches()) throw new IllegalArgumentException("Cannot parse entry: " + s);
    char keyChar = m.group(1).charAt(0);
    int keyCode = Integer.parseInt(m.group(2));
    switch(keyCode) {
      case 0x2f:
        return new DefaultRecordAction(Autotune.CHAR_MAP.get(keyChar), 
            FontIcon.createIcon(FontAwesome.KEYBOARD_O, 14f), s);
      case 0x10000c7:
        return new DefaultRecordAction(Autotune.CHAR_MAP.get(keyChar), 
            FontIcon.createIcon(FontAwesome.KEYBOARD_O, 14f), s);
      default:
        return new DefaultRecordAction(a->{
            a.keyPress(keyCode);
            a.delay();
            a.keyRelease(keyCode);
          }, FontIcon.createIcon(FontAwesome.KEYBOARD_O, 14f), s);
    }
  }

  @Override
  public String toString(RecordAction ra) {
    return ra.getText();
  }

}
