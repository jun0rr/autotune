/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.settings;

/**
 *
 * @author Juno
 */
public enum SettingsChangeEvent {
  
  COLOR,
  FONT,
  DRAW_MODE,
  DRAW_THICKNESS,
  DRAW_FILL,
  DELAY,
  RECORD;
  
  private Settings settings;
  
  public SettingsChangeEvent settings(Settings s) {
    this.settings = s;
    return this;
  }
  
  public Settings settings() {
    return settings;
  }
  
}
