/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.settings;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 *
 * @author Juno
 */
public class Settings {
  
  public static final ColorInfo BLUE_DARKEN3 = new ColorInfo(Color.decode("#1565c0"), "Blue", 0);
  
  public static final ColorInfo LIGHT_BLUE_DARKEN3 = new ColorInfo(Color.decode("#2196f3"), "Light Blue", 1);
  
  public static final ColorInfo CYAN_DARKEN3 = new ColorInfo(Color.decode("#00838f"), "Cyan", 2);
  
  public static final ColorInfo INDIGO_DARKEN3 = new ColorInfo(Color.decode("#283593"), "Indigo", 3);
  
  public static final ColorInfo RED_DARKEN3 = new ColorInfo(Color.decode("#c62828"), "Red", 4);
  
  public static final ColorInfo TEAL_DARKEN3 = new ColorInfo(Color.decode("#00695c"), "Teal", 5);
  
  public static final ColorInfo GREEN_DARKEN3 = new ColorInfo(Color.decode("#2e7d32"), "Green", 6);
  
  public static final ColorInfo LIGHT_GREEN_DARKEN3 = new ColorInfo(Color.decode("#558b2f"), "Light Green", 7);
  
  public static final ColorInfo LIME_DARKEN3 = new ColorInfo(Color.decode("#9e9d24"), "Lime", 8);
  
  public static final ColorInfo YELLOW_DARKEN3 = new ColorInfo(Color.decode("#f9a825"), "Yellow", 9);
  
  public static final ColorInfo AMBER_DARKEN3 = new ColorInfo(Color.decode("#ff8f00"), "Amber", 10);
  
  public static final ColorInfo ORANGE_DARKEN3 = new ColorInfo(Color.decode("#ef6c00"), "Orange", 11);
  
  public static final ColorInfo DEEP_ORANGE_DARKEN3 = new ColorInfo(Color.decode("#d84315"), "Deep Orange", 12);
  
  public static final ColorInfo BROWN_DARKEN3 = new ColorInfo(Color.decode("#4e342e"), "Brown", 13);
  
  public static final ColorInfo GRAY = new ColorInfo(Color.decode("#9e9e9e"), "Gray", 14);
  
  public static final ColorInfo LIGHT_GRAY = new ColorInfo(Color.LIGHT_GRAY, "Light Gray", 15);
  
  public static final ColorInfo DARK_GRAY = new ColorInfo(Color.DARK_GRAY, "Dark Gray", 16);
  
  public static final ColorInfo BLACK = new ColorInfo(Color.BLACK, "Black", 17);
  
  public static final ColorInfo WHITE = new ColorInfo(Color.WHITE, "White", 18);
  
  private final List<ColorInfo> colors;
  
  private int colorIndex;
  
  private final List<Consumer<SettingsChangeEvent>> listeners;
  
  private Font font;
  
  private DrawSettings draw;
  
  private int delay;
  
  private boolean record;
  
  public Settings() {
    this(new Font("Consolas", Font.BOLD, 14), 0);
  }
  
  public Settings(Font font, int delay) {
    this.font = font;
    this.delay = delay;
    this.record = false;
    this.colors = new LinkedList<>();
    this.colorIndex = 0;
    colors.add(BLUE_DARKEN3);
    colors.add(LIGHT_BLUE_DARKEN3);
    colors.add(CYAN_DARKEN3);
    colors.add(INDIGO_DARKEN3);
    colors.add(RED_DARKEN3);
    colors.add(TEAL_DARKEN3);
    colors.add(GREEN_DARKEN3);
    colors.add(LIGHT_GREEN_DARKEN3);
    colors.add(LIME_DARKEN3);
    colors.add(YELLOW_DARKEN3);
    colors.add(AMBER_DARKEN3);
    colors.add(ORANGE_DARKEN3);
    colors.add(DEEP_ORANGE_DARKEN3);
    colors.add(BROWN_DARKEN3);
    colors.add(GRAY);
    colors.add(LIGHT_GRAY);
    colors.add(DARK_GRAY);
    colors.add(BLACK);
    colors.add(WHITE);
    this.draw = new DrawSettings(this);
    this.listeners = new LinkedList<>();
  }
  
  public List<ColorInfo> colors() {
    return colors;
  }
  
  public ColorInfo nextColor() {
    return colors.get(colorIndex >= colors.size() ? 0 : ++colorIndex);
  }
  
  public List<ColorInfo> next4colors() {
    List<ColorInfo> next4 = new ArrayList<>(4);
    int idx = colorIndex;
    for(int i = 0; i < 4; i++) {
      if(idx >= colors.size()) {
        idx = 0;
      }
      next4.add(colors.get(idx++));
    }
    return next4;
  }
  
  public Settings setColorIndex(int ci) {
    int index = ci < 0 ? colors.size()-1 : ci;
    this.colorIndex = index >= colors.size() ? 0 : index;
    fireEvent(SettingsChangeEvent.COLOR);
    return this;
  }
  
  public int getColorIndex() {
    return colorIndex;
  }
  
  public Settings addColor(Color c) {
    colorIndex = colors.size();
    colors.add(new ColorInfo(c, ColorInfo.colorToHex(c), colorIndex));
    fireEvent(SettingsChangeEvent.COLOR.settings(this));
    return this;
  }
  
  public ColorInfo getCurrentColor() {
    return colors.get(colorIndex);
  }
  
  public Settings addListener(Consumer<SettingsChangeEvent> c) {
    if(!listeners.contains(c)) {
      this.listeners.add(c);
    }
    return this;
  }
  
  public Settings fireEvent(SettingsChangeEvent e) {
    listeners.forEach(l->l.accept(e.settings(this)));
    return this;
  }
  
  public Font getFont() {
    return font;
  }
  
  public Settings setFont(Font font) {
    this.font = font;
    fireEvent(SettingsChangeEvent.FONT);
    return this;
  }
  
  public DrawSettings getDrawSettings() {
    return draw;
  }
  
  public int getAutoDelay() {
    return delay;
  }
  
  public Settings setAutoDelay(int delay) {
    this.delay = delay;
    fireEvent(SettingsChangeEvent.DELAY);
    return this;
  }
  
  public boolean isRecord() {
    return record;
  }
  
  public Settings setRecord(boolean record) {
    this.record = record;
    fireEvent(SettingsChangeEvent.RECORD);
    return this;
  }
  
  @Override
  public int hashCode() {
    int hash = 5;
    hash = 97 * hash + Objects.hashCode(this.font);
    hash = 97 * hash + Objects.hashCode(this.draw);
    hash = 97 * hash + this.delay;
    hash = 97 * hash + (this.record ? 1 : 0);
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
    final Settings other = (Settings) obj;
    if (this.delay != other.delay) {
      return false;
    }
    if (this.record != other.record) {
      return false;
    }
    if (!Objects.equals(this.font, other.font)) {
      return false;
    }
    return Objects.equals(this.draw, other.draw);
  }
  
  @Override
  public String toString() {
    return "PanelSettings{font=" + font + ", delay=" + delay + ", record=" + record + ", draw=" + draw + ", colors=" + colors + '}';
  }
  
}
