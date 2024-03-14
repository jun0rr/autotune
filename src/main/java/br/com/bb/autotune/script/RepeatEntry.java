/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.script;

import br.com.bb.autotune.action.RecordAction;
import br.com.bb.autotune.action.RepeatRecordAction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author F6036477
 */
public class RepeatEntry implements RecordScriptEntry {

  public static final String ENTRY_REGEX = "repeat([0-9]{1,6})\\(\\s?([0-9]{1,6}),\\s?([0-9]{1,6})\\s?\\)";
  
  public static final Pattern ENTRY_PATTERN = Pattern.compile(ENTRY_REGEX);
  
  @Override
  public boolean canParse(String s) {
    return ENTRY_PATTERN.asMatchPredicate().test(s);
  }
  
  @Override
  public RecordAction parse(String s) {
    Matcher m = ENTRY_PATTERN.matcher(s);
    if(!m.matches()) throw new IllegalArgumentException("Cannot parse entry: " + s);
    int id = Integer.parseInt(m.group(1));
    int lines = Integer.parseInt(m.group(2));
    int times = Integer.parseInt(m.group(3));
    return new RepeatRecordAction(id, lines, times);
  }

  @Override
  public String toString(RecordAction ra) {
    return ra.getText();
  }

}
