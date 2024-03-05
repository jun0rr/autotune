/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.action;

import br.com.bb.autotune.icon.FontAwesome;
import br.com.bb.autotune.icon.FontIcon;
import java.util.List;
import java.util.stream.IntStream;

/**
 *
 * @author F6036477
 */
public class RepeatRecordAction extends DefaultRecordAction {
  
  public RepeatRecordAction(List<RecordAction> recs, int lines, int times) {
    super(a->{
      String s = String.format("repeat( %d, %d )", lines, times);
      int ridx = IntStream.range(0, recs.size())
          .filter(i->recs.get(i).getText().equals(s))
          .findFirst()
          .getAsInt();
      int skip = ridx - lines;
      for(int i = 0; i < times; i++) {
        recs.stream()
            .skip(ridx - lines)
            .limit(lines)
            .peek(r->a.delay(30))
            .forEach(r->r.accept(a));
        a.delay(150);
      }
    }, FontIcon.createIcon(FontAwesome.REPEAT, 14f), 
    "repeat( %d, %d )", lines, times);
  }
  
}
