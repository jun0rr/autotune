/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.action;

import br.com.bb.autotune.EditablePanel;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Juno
 */
public class SaveRecordsAction extends AbstractPanelAction {
  
  public SaveRecordsAction() {
    super("SaveRecords");
  }
  
  @Override
  public boolean accept(EditablePanel p) {
    return p.getLastKeyEvents()[0] != null
        && KeyEvent.VK_F4 == p.getLastKeyEvents()[0].getExtendedKeyCode() 
        && p.getLastKeyEvents()[0].isControlDown();
  }
  
  @Override
  public void perform(EditablePanel p) {
    save(p, p.getRecordActions());
  }
  
  public void save(Component owner, List<RecordAction> records) {
    JFileChooser chooser = new JFileChooser();
    chooser.setFileFilter(new FileFilter() {
      @Override
      public boolean accept(File f) {
        return f.getName().endsWith("txt") || f.getName().endsWith("rec");
      }
      @Override
      public String getDescription() {
        return "Record Script File";
      }
    });
    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    chooser.setMultiSelectionEnabled(false);
    int opt = chooser.showSaveDialog(owner);
    if(JFileChooser.APPROVE_OPTION == opt) {
      File f = chooser.getSelectedFile();
      try {
        StringBuilder sb = new StringBuilder();
        records.forEach(r->sb.append(r.getText()).append('\n'));
        Files.writeString(f.toPath(), sb.toString(), 
            StandardCharsets.UTF_8, 
            StandardOpenOption.CREATE, 
            StandardOpenOption.WRITE, 
            StandardOpenOption.TRUNCATE_EXISTING
        );
        JOptionPane.showMessageDialog(owner, "Record Script saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
      }
      catch(IOException e) {
        JOptionPane.showMessageDialog(owner, e, "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }
  
}
