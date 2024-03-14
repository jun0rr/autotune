/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.test;

import br.com.bb.autotune.Autotune;
import br.com.bb.autotune.EditorPanel;
import br.com.bb.autotune.script.fn.Fn;
import br.com.bb.autotune.script.fn.FnContext;
import br.com.bb.autotune.script.fn.FnSource;
import java.time.Duration;
import java.time.Instant;
import javax.swing.JFrame;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Juno
 */
public class TestFnContext {
  
  
  @Test public void test() {
    try {
      Autotune auto = new Autotune();
      JFrame f = new JFrame();
      EditorPanel panel = new EditorPanel(f, auto);
      FnContext ctx = new FnContext(panel);
      Instant start = Instant.now();
      String name = "TestFn";
      auto.putClipboard(name);
      FnSource src = new FnSource(name, "System.out.println(ctx.getAutotune().getClipboardString()); "
          + "ctx.setVar(\"clipboard\", ctx.getAutotune().getClipboardString());"
      );
      Fn fn = ctx.createFunction(src);
      Instant end = Instant.now();
      Duration dur = Duration.between(start, end);
      System.out.printf("%02d:%03d ms%n", dur.toSecondsPart(), dur.toMillisPart());
      fn.run(ctx);
      Assertions.assertEquals(name, ctx.getVar("clipboard"));
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  
}
