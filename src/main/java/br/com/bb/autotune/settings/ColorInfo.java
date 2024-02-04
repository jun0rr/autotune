/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.settings;

import java.awt.Color;
import java.util.Objects;

/**
 *
 * @author Juno
 */
public class ColorInfo {
  
  private final Color color;
  
  private final int index;
  
  private final String name;
  
  public ColorInfo(Color c, String name, int idx) {
    this.color = c;
    this.name = name;
    this.index = idx;
  }
  
  public Color color() {
    return color;
  }
  
  public String name() {
    return name;
  }
  
  public String colorHex() {
    return String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue());
  }
  
  public static String colorToHex(Color color) {
    return String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue());
  }
  
  public int index() {
    return index;
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 37 * hash + Objects.hashCode(this.color);
    hash = 37 * hash + Objects.hashCode(this.name);
    hash = 37 * hash + this.index;
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final ColorInfo other = (ColorInfo) obj;
    return Objects.equals(this.color, other.color) 
        && this.index == other.index 
        && this.name.equalsIgnoreCase(other.name);
  }

  @Override
  public String toString() {
    return "ColorInfo{" + "color=" + color + ", name=" + name + ", index=" + index + '}';
  }
  
}
