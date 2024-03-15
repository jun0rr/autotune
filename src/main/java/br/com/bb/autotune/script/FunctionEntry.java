/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.script;

import br.com.bb.autotune.action.DefaultRecordAction;
import br.com.bb.autotune.action.RecordAction;
import br.com.bb.autotune.icon.FontAwesome;
import br.com.bb.autotune.icon.FontIcon;
import br.com.bb.autotune.script.fn.Fn;
import br.com.bb.autotune.script.fn.FnContext;
import br.com.bb.autotune.script.fn.FnSource;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author F6036477
 */
public class FunctionEntry implements RecordScriptEntry {

  public static final String ENTRY_REGEX = "fun\\s([a-z_]+)\\(\\)\\s\\{\\s?(.+)\\s?\\}";
  
  public static final Pattern ENTRY_PATTERN = Pattern.compile(ENTRY_REGEX);
  
  public final FnContext fnCtx;
  
  public FunctionEntry(FnContext c) {
    this.fnCtx = Objects.requireNonNull(c);
  }
  
  @Override
  public boolean canParse(String s) {
    return ENTRY_PATTERN.asMatchPredicate().test(s);
  }
  
  @Override
  public RecordAction parse(String s) {
    Matcher m = ENTRY_PATTERN.matcher(s);
    if(!m.matches()) throw new IllegalArgumentException("Cannot parse entry: " + s);
    String name = m.group(1);
    String code = m.group(2);
    Fn fn = fnCtx.getFunction(name) != null 
        ? fnCtx.getFunction(name) 
        : fnCtx.createFunction(new FnSource(name, code));
    return new DefaultRecordAction(a->fn.run(fnCtx), 
        FontIcon.createIcon(FontAwesome.TERMINAL, 12f), 
        "fun %s() { %s }", name, code
    );
  }

  @Override
  public String toString(RecordAction ra) {
    return ra.getText();
  }

}
