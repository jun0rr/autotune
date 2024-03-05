/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.action;

import br.com.bb.autotune.EditablePanel;
import br.com.bb.autotune.script.RecordScript;
import br.com.bb.autotune.script.RecordScriptEntry;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Juno
 */
public class OpenRecordsAction extends AbstractPanelAction {
  
  private final RecordScript rscript;
  
  public OpenRecordsAction() {
    this(new RecordScript());
  }
  
  public OpenRecordsAction(RecordScript rs) {
    super("OpenRecords");
    this.rscript = Objects.requireNonNull(rs);
  }
  
  @Override
  public boolean accept(EditablePanel p) {
    return p.getLastKeyEvents()[0] != null
        && KeyEvent.VK_O == p.getLastKeyEvents()[0].getExtendedKeyCode() 
        && p.getLastKeyEvents()[0].isAltDown()
        && KeyEvent.KEY_RELEASED == p.getLastKeyEvents()[0].getID();
  }
  
  @Override
  public void perform(EditablePanel p) {
    removeShortcutRecords(p, KeyEvent.VK_ALT, KeyEvent.VK_O);
    open(p, p.getRecordActions());
  }
  
  public void open(Component owner, List<RecordAction> records) {
    JFileChooser chooser = new JFileChooser();
    chooser.setFileFilter(new FileFilter() {
      @Override
      public boolean accept(File f) {
        return f.getName().endsWith("txt") || f.getName().endsWith("rec");
      }
      @Override
      public String getDescription() {
        return "Record Script File (.rec, .txt)";
      }
    });
    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    chooser.setMultiSelectionEnabled(false);
    int opt = chooser.showOpenDialog(owner);
    if(JFileChooser.APPROVE_OPTION == opt) {
      File f = chooser.getSelectedFile();
      try {
        records.clear();
        String script = Files.readString(f.toPath(), StandardCharsets.UTF_8);
        records.addAll(rscript.parseScript(script));
        if(records.isEmpty()) {
          throw new IllegalStateException("No records found in script file: " + f.toString());
        }
        String msg = String.format("Record Script loaded successfully (%d lines)!", records.size());
        JOptionPane.showMessageDialog(owner, msg, "Success", JOptionPane.INFORMATION_MESSAGE);
      }
      catch(IOException e) {
        JOptionPane.showMessageDialog(owner, e, "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }
  
}
