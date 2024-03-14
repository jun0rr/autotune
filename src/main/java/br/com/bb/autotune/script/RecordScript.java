/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.script;

import br.com.bb.autotune.action.RecordAction;
import br.com.bb.autotune.script.fn.FnContext;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author F6036477
 */
public class RecordScript {
  
  private final List<RecordScriptEntry> entries;
  
  public RecordScript() {
    this.entries = new LinkedList<>();
    entries.add(new CopyScreenshotEntry());
    entries.add(new DelayEntry());
    entries.add(new KeyPressEntry());
    entries.add(new KeyReleaseEntry());
    entries.add(new KeyTypeEntry());
    entries.add(new MouseMoveEntry());
    entries.add(new MousePressEntry());
    entries.add(new MouseReleaseEntry());
    entries.add(new MouseWheelEntry());
    entries.add(new RepeatEntry());
    entries.add(new TypeClipboardEntry());
  }
  
  public List<RecordScriptEntry> getScriptEntries() {
    return entries;
  }
  
  public List<RecordAction> parseScript(String script) {
    List<RecordAction> recs = new LinkedList<>();
    List<String> lines = List.of(script.split("\n"));
    lines.stream()
        .map(this::parseRecord)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .forEach(recs::add);
    return recs;
  }
  
  public Optional<RecordAction> parseRecord(String s) {
    return entries.stream()
        .filter(e->e.canParse(s))
        .findFirst()
        .map(e->e.parse(s));
  }
  
}
