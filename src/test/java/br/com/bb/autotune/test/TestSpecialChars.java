/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.test;

import java.awt.AWTException;
import java.awt.Robot;
import org.junit.jupiter.api.Test;

/**
 *
 * @author F6036477
 */
public class TestSpecialChars {
  
  //@Test 
  public void test() throws AWTException {
    Robot r = new Robot();
    //r.keyPress(193);
    //r.keyRelease(193);
    r.keyPress(186);
    r.keyRelease(186);
  }
  
}
