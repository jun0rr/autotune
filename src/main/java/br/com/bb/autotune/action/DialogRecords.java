/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.action;

import br.com.bb.autotune.action.shortcut.SaveRecordsAction;
import br.com.bb.autotune.icon.FontAwesome;
import br.com.bb.autotune.icon.FontIcon;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

/**
 *
 * @author F6036477
 */
public class DialogRecords extends JDialog implements KeyListener {
  
  private final JFrame owner;
  
  private final JList recordList;
  
  private final JLabel infoLabel;
  
  private final List<RecordAction> records;
  
  public DialogRecords(JFrame owner, List<RecordAction> records) {
    super(owner, "Records", ModalityType.APPLICATION_MODAL);
    this.owner = Objects.requireNonNull(owner);
    this.records = Objects.requireNonNull(records);
    this.recordList = new JList();
    this.infoLabel = new JLabel(FontIcon.createIcon(FontAwesome.INFO_CIRCLE, Color.GRAY, 14f), SwingConstants.LEFT);
    recordList.addKeyListener(this);
    this.setLayout(new GridBagLayout());
    populate();
    pack();
  }
  
  public void showDialog() {
    Dimension fd = owner.getSize();
    Point fl = owner.getLocation();
    int x = fl.x;
    int y = fl.y;
    if(fd.width > getSize().width) {
      x = fl.x + (fd.width - getSize().width) / 2;
    }
    if(fd.height > getSize().height) {
      y = fl.y + (fd.height - getSize().height) / 2;
    }
    setRecordList(records);
    setLocation(x, y);
    setVisible(true);
    recordList.requestFocus();
  }
  
  private void populate() {
    JButton rmRecord = new JButton("Remove Record", FontIcon.createIcon(FontAwesome.TRASH_O, 14f));
    rmRecord.setPreferredSize(new Dimension(140, 25));
    rmRecord.addActionListener(e->removeSelectedRecords());
    GridBagConstraints c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = 0;
    c.gridheight = 1;
    c.anchor = GridBagConstraints.LINE_START;
    c.insets = new Insets(10, 20, 10, 5);
    add(rmRecord, c);
    
    JButton saveScript = new JButton("Save Script", FontIcon.createIcon(FontAwesome.FLOPPY_O, 14f));
    saveScript.setPreferredSize(new Dimension(140, 25));
    saveScript.addActionListener(e->new SaveRecordsAction().save(DialogRecords.this, records));
    c = new GridBagConstraints();
    c.gridx = 1;
    c.gridy = 0;
    c.gridheight = 1;
    c.anchor = GridBagConstraints.LINE_START;
    c.insets = new Insets(10, 5, 10, 20);
    add(saveScript, c);
    
    recordList.setCellRenderer(new DefaultListCellRenderer() {
      @Override
      public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        RecordAction action = (RecordAction) value;
        this.setText(action.getText());
        this.setIcon(action.getIcon());
        return this;
      }
    });
    recordList.addListSelectionListener(e->{
      int[] idx = recordList.getSelectedIndices();
      if(idx.length > 0) {
        infoLabel.setText(String.format(
            "Count: %d | First: %d | Last: %d | Indices: %s", 
            idx.length, idx[0], idx[idx.length-1], Arrays.toString(idx))
        );
        infoLabel.repaint();
      }
    });
    
    JScrollPane scroll = new JScrollPane(this.recordList);
    scroll.setPreferredSize(new Dimension(290, 350));
    c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = 1;
    c.gridwidth = 2;
    c.gridheight = 6;
    c.anchor = GridBagConstraints.LINE_START;
    c.insets = new Insets(5, 20, 5, 20);
    add(scroll, c);
    
    infoLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
    infoLabel.setForeground(Color.GRAY);
    infoLabel.setPreferredSize(new Dimension(290, 25));
    infoLabel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
    c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = 7;
    c.gridwidth = 2;
    c.gridheight = 1;
    c.anchor = GridBagConstraints.LINE_START;
    c.insets = new Insets(5, 20, 10, 20);
    add(infoLabel, c);
  }
  
  public void removeSelectedRecords() {
    IntStream.of(recordList.getSelectedIndices()).boxed()
        .sorted((a,b)->Integer.compare(a, b) * -1)
        .mapToInt(Integer::intValue)
        .forEach(records::remove);
    setRecordList(records);
  }
  
  public void setRecordList(List<RecordAction> ls) {
    recordList.setListData(Objects.requireNonNull(ls).toArray());
    recordList.repaint();
  }

  @Override 
  public void keyPressed(KeyEvent e) {
    if(KeyEvent.VK_A == e.getExtendedKeyCode() && e.isControlDown()) {
      int idx[] = new int[records.size()];
      for(int i = 0; i < idx.length; i++) {
        idx[i] = i;
      }
      recordList.setSelectedIndices(idx);
    }
    else if(KeyEvent.VK_DELETE == e.getExtendedKeyCode()) {
      removeSelectedRecords();
    }
  }
  
  @Override public void keyTyped(KeyEvent e) {}

  @Override public void keyReleased(KeyEvent e) {}
  
}
