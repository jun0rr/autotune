/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.test;

import br.com.bb.autotune.script.MouseMoveEntry;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;

/**
 *
 * @author F6036477
 */
public class TestRegex {
  
  public static final String ENTRY_REGEX = "mouseMove\\(\\s?([0-9]{1,5}),\\s?([0-9]{1,5})\\s?\\)";
  
  public static final Pattern ENTRY_PATTERN = Pattern.compile(ENTRY_REGEX);
  
  @Test public void test() {
    String s = "mouseMove( 57, 602 )";
    MouseMoveEntry e = new MouseMoveEntry();
    System.out.printf("* input: %s%n", s);
    System.out.printf("* canParse: %s%n", e.canParse(s));
    System.out.printf("* parse: %s%n", e.parse(s));
    System.out.printf("* toString: %s%n", e.toString(e.parse(s)));
  }
  
}
