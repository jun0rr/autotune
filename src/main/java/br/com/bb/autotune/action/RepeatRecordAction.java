/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.action;

import br.com.bb.autotune.Autotune;
import br.com.bb.autotune.icon.FontAwesome;
import br.com.bb.autotune.icon.FontIcon;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import javax.swing.Icon;

/**
 *
 * @author F6036477
 */
public class RepeatRecordAction implements RecordAction {
  
  public static final AtomicInteger REPEAT_ID = new AtomicInteger(0);
  
  private final int id, lines, times;
  
  public RepeatRecordAction(int id, int lines, int times) {
    this.id = id;
    this.lines = lines;
    this.times = times;
  }
  
  public RepeatRecordAction(int lines, int times) {
    this(REPEAT_ID.getAndIncrement(), lines, times);
  }
  
  public int getId() {
    return id;
  }

  @Override
  public String getText() {
    return String.format("repeat%d( %d, %d )", id, lines, times);
  }

  @Override
  public Icon getIcon() {
    return FontIcon.createIcon(FontAwesome.REPEAT, 14f);
  }

  @Override
  public void accept(Autotune a) {
    String s = String.format("repeat%d( %d, %d )", id, lines, times);
    int ridx = IntStream.range(0, a.getActionList().size())
        .filter(i->a.getActionList().get(i).getText().equals(s))
        .findFirst()
        .getAsInt();
    int skip = ridx - lines;
    for(int i = 0; i < times; i++) {
      a.getActionList().stream()
          .skip(ridx - lines)
          .limit(lines)
          .peek(r->a.delay(30))
          .forEach(r->r.accept(a));
      a.delay(150);
    }
  }
  
}
