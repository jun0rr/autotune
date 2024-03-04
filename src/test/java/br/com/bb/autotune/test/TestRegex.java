/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.test;

import br.com.bb.autotune.icon.FontAwesome;
import br.com.bb.autotune.icon.FontIcon;
import br.com.bb.autotune.script.MouseMoveEntry;
import javax.swing.Icon;
import org.junit.jupiter.api.Test;

/**
 *
 * @author F6036477
 */
public class TestRegex {
  
  @Test 
  public void test() {
    try {
      Icon i = FontIcon.createIcon(FontAwesome.MOUSE_POINTER, 14f);
      String s = "mouseMove( 57, 602 )";
      MouseMoveEntry e = new MouseMoveEntry();
      System.out.printf("* input: %s%n", s);
      System.out.printf("* canParse: %s%n", e.canParse(s));
      System.out.printf("* parse: %s%n", e.parse(s));
      System.out.printf("* toString: %s%n", e.toString(e.parse(s)));
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  
}
