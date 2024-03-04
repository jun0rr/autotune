/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.script;

import br.com.bb.autotune.action.DefaultRecordAction;
import br.com.bb.autotune.action.RecordAction;
import br.com.bb.autotune.icon.FontAwesome;
import br.com.bb.autotune.icon.FontIcon;
import java.awt.Rectangle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author F6036477
 */
public class CopyScreenshotEntry implements RecordScriptEntry {

  public static final String ENTRY_REGEX = "copyScreenshot\\(\\s?([0-9]{1,5}),\\s?([0-9]{1,5}),\\s?([0-9]{1,5}),\\s?([0-9]{1,5})\\s?\\)";
  
  public static final Pattern ENTRY_PATTERN = Pattern.compile(ENTRY_REGEX);
  
  @Override
  public boolean canParse(String s) {
    return ENTRY_PATTERN.asMatchPredicate().test(s);
  }
  
  @Override
  public RecordAction parse(String s) {
    Matcher m = ENTRY_PATTERN.matcher(s);
    if(!m.matches()) throw new IllegalArgumentException("Cannot parse entry: " + s);
    int x = Integer.parseInt(m.group(1));
    int y = Integer.parseInt(m.group(2));
    int w = Integer.parseInt(m.group(3));
    int h = Integer.parseInt(m.group(4));
    return new DefaultRecordAction(a->a.copyScreenshot(new Rectangle(x, y, w, h)), 
        FontIcon.createIcon(FontAwesome.CLONE, 14f), s);
  }

  @Override
  public String toString(RecordAction ra) {
    return ra.getText();
  }

}
