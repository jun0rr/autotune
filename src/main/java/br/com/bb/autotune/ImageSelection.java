/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune;

import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Objects;

/**
 *
 * @author F6036477
 */
public class ImageSelection implements Transferable {
  
  private final Image image;
  
  public Image getImage() {
    return image;
  }
  
  public ImageSelection(Image img) {
    this.image = Objects.requireNonNull(img);
  }

  @Override
  public DataFlavor[] getTransferDataFlavors() {
    return new DataFlavor[] {DataFlavor.imageFlavor};
  }

  @Override
  public boolean isDataFlavorSupported(DataFlavor flavor) {
    return DataFlavor.imageFlavor == flavor;
  }

  @Override
  public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
    return isDataFlavorSupported(flavor) ? image : null;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 23 * hash + Objects.hashCode(this.image);
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
    final ImageSelection other = (ImageSelection) obj;
    return Objects.equals(this.image, other.image);
  }

  @Override
  public String toString() {
    return "ImageSelection{" + "image=" + image + '}';
  }
  
}
