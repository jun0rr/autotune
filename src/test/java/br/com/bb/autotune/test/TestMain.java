/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.test;

import br.com.bb.autotune.Main;
import java.util.concurrent.CountDownLatch;
import org.junit.jupiter.api.Test;

/**
 *
 * @author F6036477
 */
public class TestMain {
  
  @Test 
  public void test() throws Exception {
    try {
      Main main = new Main();
      CountDownLatch cd = new  CountDownLatch(1);
      main.setExitAction(m->cd.countDown());
      main.setTrayIcon();
      main.showMain();
      cd.await();
    }
    catch(Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
}
