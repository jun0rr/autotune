/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.action.shortcut;

import br.com.bb.autotune.EditorPanel;
import br.com.bb.autotune.action.AbstractPanelAction;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Juno
 */
public class SaveImageAction extends AbstractPanelAction {
  
  public SaveImageAction() {
    super("SaveImage");
  }
  
  @Override
  public boolean accept(EditorPanel p) {
    return p.getLastKeyEvents()[0] != null
        && KeyEvent.VK_F2 == p.getLastKeyEvents()[0].getExtendedKeyCode() 
        && p.getLastKeyEvents()[0].isAltDown()
        && KeyEvent.KEY_RELEASED == p.getLastKeyEvents()[0].getID();
  }
  
  @Override
  public void perform(EditorPanel p) {
    removeShortcutRecords(p, KeyEvent.VK_ALT, KeyEvent.VK_F2);
    JFileChooser chooser = new JFileChooser();
    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    chooser.setMultiSelectionEnabled(false);
    int opt = chooser.showSaveDialog(p);
    if(JFileChooser.APPROVE_OPTION == opt) {
      File f = chooser.getSelectedFile();
      try {
        BufferedImage img = new BufferedImage(
            p.getBackgroundImage().get().getWidth(), 
            p.getBackgroundImage().get().getHeight(), 
            p.getBackgroundImage().get().getType()
        );
        p.paint(img.getGraphics());
        String message = "Image saved successfully!";
        if(p.getSelectionShape().isPresent()) {
          message = "Image selection saved successfully!";
          Rectangle r = p.getSelectionShape().get().getShape().getBounds();
          img = img.getSubimage(r.x, r.y, r.width, r.height);
        }
        ImageIO.write(img, "jpg", f);
        JOptionPane.showMessageDialog(p, message, "Success", JOptionPane.INFORMATION_MESSAGE);
      }
      catch(IOException e) {
        JOptionPane.showMessageDialog(p, e, "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }
  
}
