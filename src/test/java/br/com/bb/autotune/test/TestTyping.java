/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.test;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.Test;

/**
 *
 * @author F6036477
 */
public class TestTyping {
  
  private static Random rnd = new Random();
  
  @Test public void test() throws AWTException, InterruptedException {
    Robot r = new Robot();
    //522, 703
    //65, 100
    Map<Character, int[]> km = new HashMap();
    km.put('\'', new int[] {222});
    km.put('1', new int[] {49});
    km.put('2', new int[] {50});
    km.put('3', new int[] {51});
    km.put('4', new int[] {52});
    km.put('5', new int[] {53});
    km.put('6', new int[] {54});
    km.put('7', new int[] {55});
    km.put('8', new int[] {56});
    km.put('9', new int[] {57});
    km.put('0', new int[] {48});
    km.put('-', new int[] {45});
    km.put('=', new int[] {61});
    km.put('"', new int[] {16, 222});
    km.put('“', new int[] {16, 222});
    km.put('”', new int[] {16, 222});
    km.put('!', new int[] {16, 49});
    km.put('@', new int[] {16, 50});
    km.put('#', new int[] {16, 51});
    km.put('$', new int[] {16, 52});
    km.put('%', new int[] {16, 53});
    km.put('¨', new int[] {16, 54});
    km.put('&', new int[] {16, 55});
    km.put('*', new int[] {16, 56});
    km.put('(', new int[] {16, 57});
    km.put(')', new int[] {16, 48});
    km.put('_', new int[] {16, 45});
    km.put('+', new int[] {16, 61});
    km.put('q', new int[] {81});
    km.put('w', new int[] {87});
    km.put('e', new int[] {69});
    km.put('r', new int[] {82});
    km.put('t', new int[] {84});
    km.put('y', new int[] {89});
    km.put('u', new int[] {85});
    km.put('i', new int[] {73});
    km.put('o', new int[] {79});
    km.put('p', new int[] {80});
    km.put('´', new int[] {129});
    km.put('[', new int[] {91});
    km.put('Q', new int[] {16, 81});
    km.put('W', new int[] {16, 87});
    km.put('E', new int[] {16, 69});
    km.put('R', new int[] {16, 82});
    km.put('T', new int[] {16, 84});
    km.put('Y', new int[] {16, 89});
    km.put('U', new int[] {16, 85});
    km.put('I', new int[] {16, 73});
    km.put('O', new int[] {16, 79});
    km.put('P', new int[] {16, 80});
    km.put('`', new int[] {16, 129});
    km.put('[', new int[] {16, 91});
    km.put('a', new int[] {65});
    km.put('s', new int[] {83});
    km.put('d', new int[] {68});
    km.put('f', new int[] {70});
    km.put('g', new int[] {71});
    km.put('h', new int[] {72});
    km.put('j', new int[] {74});
    km.put('k', new int[] {75});
    km.put('l', new int[] {76});
    km.put('ç', new int[] {67});
    km.put('~', new int[] {131});
    km.put(']', new int[] {93});
    km.put('A', new int[] {16, 65});
    km.put('S', new int[] {16, 83});
    km.put('D', new int[] {16, 68});
    km.put('F', new int[] {16, 70});
    km.put('G', new int[] {16, 71});
    km.put('H', new int[] {16, 72});
    km.put('J', new int[] {16, 74});
    km.put('K', new int[] {16, 75});
    km.put('L', new int[] {16, 76});
    km.put('Ç', new int[] {16, 67});
    km.put('^', new int[] {16, 131});
    km.put(']', new int[] {16, 93});
    km.put('\\', new int[] {92});
    km.put('z', new int[] {90});
    km.put('x', new int[] {88});
    km.put('c', new int[] {67});
    km.put('v', new int[] {86});
    km.put('b', new int[] {66});
    km.put('n', new int[] {78});
    km.put('m', new int[] {77});
    km.put(',', new int[] {44});
    km.put('.', new int[] {46});
    km.put(';', new int[] {59});
    km.put('/', new int[] {47});
    km.put('|', new int[] {16, 92});
    km.put('Z', new int[] {16, 90});
    km.put('X', new int[] {16, 88});
    km.put('C', new int[] {16, 67});
    km.put('V', new int[] {16, 86});
    km.put('B', new int[] {16, 66});
    km.put('N', new int[] {16, 78});
    km.put('M', new int[] {16, 77});
    km.put('<', new int[] {16, 44});
    km.put('>', new int[] {16, 46});
    km.put(':', new int[] {16, 59});
    km.put('?', new int[] {16, 47});
    km.put('á', new int[] {129, 65});
    km.put('à', new int[] {16, 129, -1, 65});
    km.put('ã', new int[] {131, 65});
    km.put('â', new int[] {16, 131, -1, 65});
    km.put('Á', new int[] {129, 16, 65});
    km.put('À', new int[] {16, 129, 16, 65});
    km.put('Ã', new int[] {131, 16, 65});
    km.put('Â', new int[] {16, 131, 16, 65});
    km.put('é', new int[] {129, 69});
    km.put('è', new int[] {16, 129, -1, 69});
    km.put('ê', new int[] {16, 131, -1, 69});
    km.put('É', new int[] {129, 16, 69});
    km.put('È', new int[] {16, 129, 16, 69});
    km.put('Ê', new int[] {16, 131, 16, 69});
    km.put('í', new int[] {129, 73});
    km.put('ì', new int[] {16, 129, -1, 73});
    km.put('î', new int[] {16, 131, -1, 73});
    km.put('Í', new int[] {129, 16, 73});
    km.put('Ì', new int[] {16, 129, 16, 73});
    km.put('Î', new int[] {16, 131, 16, 73});
    km.put('ó', new int[] {129, 79});
    km.put('ò', new int[] {16, 129, -1, 79});
    km.put('õ', new int[] {131, 79});
    km.put('ô', new int[] {16, 131, -1, 79});
    km.put('Ó', new int[] {129, 16, 79});
    km.put('Ò', new int[] {16, 129, 16, 79});
    km.put('Õ', new int[] {131, 16, 79});
    km.put('Ô', new int[] {16, 131, 16, 79});
    km.put('ú', new int[] {129, 85});
    km.put('ù', new int[] {16, 129, -1, 85});
    km.put('û', new int[] {131, -1, 85});
    km.put('ü', new int[] {16, 131, -1, 85});
    km.put('Ú', new int[] {129, 16, 85});
    km.put('Ù', new int[] {16, 129, 16, 85});
    km.put('Û', new int[] {131, 16, 85});
    km.put('Ü', new int[] {16, 131, 16, 85});
    km.put('ª', new int[] {17, 91});
    km.put('º', new int[] {17, 93});
    km.put('§', new int[] {17, 61});
    km.put('°', new int[] {17, 93});
    km.put('¹', new int[] {17, 49});
    km.put('²', new int[] {17, 50});
    km.put('³', new int[] {17, 51});
    km.put('£', new int[] {17, 52});
    km.put('¢', new int[] {17, 53});
    km.put('¬', new int[] {17, 54});
    km.put(' ', new int[] {32});
    km.put('\n', new int[] {10});

    String text = "A edição da revista digital e-Canta, uma publicação da Editora Cantarino Brasileiro, traz neste mês de janeiro os ganhadores do Prêmio Banking Transformation 2023. O prêmio está em sua 19° edição e busca evidenciar as melhores práticas em inovação, contribuindo para o aprimoramento do setor financeiro e reconhecendo aquelas que se destacaram em suas categorias. E o BB não poderia ficar de fora, sendo premiado com os cases \"Registro Cartorário Eletrônico de Operações Rurais\", categoria Inovação com IA e “Aceleração da Implementação de Soluções nos Canais Digitais”, categoria Eficiência Operacional. \n" +
        "Ao todo, a organização do evento recebeu mais de 280 cases para avaliação. Veja um pouco dos cases do BB que se destacaram nesta edição.";
    //String text = "/";
    
    r.mouseMove(569,698);
    r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
    Thread.currentThread().sleep(rnd.nextLong(10, 50));
    r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    r.mouseMove(65, 100);
    r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
    Thread.currentThread().sleep(rnd.nextLong(10, 50));
    r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    char[] cs = text.toCharArray();
    for(int i = 0; i < cs.length; i++) {
      //System.out.printf("* Type: '%s' = %s%n", cs[i], Arrays.toString(km.get(cs[i])));
      type(r, km.get(cs[i]));
      r.delay(rnd.nextInt(10, 80));
    }
  }
  
  public static void type(Robot r, int ... keys) throws InterruptedException {
    int release = -1;
    for(int i = 0; i < keys.length; i++) {
      if(keys[i] == -1) {
        release = i;
        for(int j = 0; j < i; j++) {
          r.keyRelease(keys[j]);
        }
      }
      else {
        //System.out.printf("* KeyCode: %d%n", keys[i]);
        r.keyPress(keys[i]);
      }
    }
    r.delay(rnd.nextInt(50, 100));
    for(int i = keys.length -1; i > release; i--) {
      r.keyRelease(keys[i]);
    }
  }
  
}
