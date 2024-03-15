/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune;

import br.com.bb.autotune.icon.FontAwesome;
import br.com.bb.autotune.icon.FontIcon;
import br.com.bb.autotune.settings.Settings;
import java.awt.AWTException;
import java.awt.Frame;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.function.Consumer;
import javax.swing.JFrame;

/**
 *
 * @author F6036477
 */
public class Main extends JFrame {
  
  private final EditorPanel panel;
  
  private final MenuItem iopen;
  
  private Consumer<Main> exitAction;
  
  private boolean visible;
  
  public Main() {
    this.panel = new EditorPanel(this, new Autotune());
    this.iopen = new MenuItem("Open");
    this.visible = false;
    iopen.addActionListener(e->openClose());
    setIconImage(FontIcon.createImage(FontAwesome.BOLT, Settings.LIGHT_BLUE_DARKEN3.color(), 30f));
    setTitle("Autotune");
    add(panel);
    setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    addComponentListener(new ComponentAdapter() {
      @Override
      public void componentShown(ComponentEvent e) {
        setOpened();
      }
      @Override
      public void componentHidden(ComponentEvent e) {
        setClosed();
      }
    });
  }
  
  public void showMain() {
    setVisible(true);
    setExtendedState(Frame.MAXIMIZED_BOTH);
    requestFocus();
    setOpened();
    panel.requestFocus();
  }
  
  public void setTrayIcon() {
    if(SystemTray.isSupported()) {
      PopupMenu menu = new PopupMenu();
      MenuItem iupdate = new MenuItem("Update");
      iupdate.addActionListener(e->panel.updateBackground());
      MenuItem iexit =  new MenuItem("Exit");
      iexit.addActionListener(e->exit());
      menu.add(iopen);
      menu.add(iupdate);
      menu.addSeparator();
      menu.add(iexit);
      TrayIcon tray = new TrayIcon(FontIcon.createImage(FontAwesome.BOLT, Settings.LIGHT_BLUE_DARKEN3.color(), 18f));
      tray.setToolTip("Autotune");
      tray.setImageAutoSize(false);
      tray.setPopupMenu(menu);
      try {
        SystemTray.getSystemTray().add(tray);
      }
      catch(AWTException e) {
        throw new RuntimeException(e);
      }
    }
  }
  
  private void setOpened() {
    visible = true;
    iopen.setLabel("Hide");
  }
  
  private void setClosed() {
    visible = false;
    iopen.setLabel("Open");
  }
  
  private void openClose() {
    if(visible) {
      setClosed();
      setVisible(false);
    }
    else {
      showMain();
    }
  }
  
  private void exit() {
    setVisible(false);
    if(exitAction != null) {
      exitAction.accept(this);
    }
    System.exit(0);
  }
  
  public void setExitAction(Consumer<Main> a) {
    this.exitAction = a;
  }
  
  public static void main(String[] args) {
    Main main = new Main();
    main.setTrayIcon();
    main.showMain();
  }
  
}
