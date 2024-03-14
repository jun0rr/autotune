/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.script.fn;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URI;
import static java.util.Objects.requireNonNull;
import javax.tools.SimpleJavaFileObject;

/**
 *
 * @author Juno
 */
public class RuntimeFileObject extends SimpleJavaFileObject {
  
  private final FnSource source;
  
  private final ByteArrayOutputStream bytes;
  
  public RuntimeFileObject(String name, Kind kind) {
    super(URI.create("string:///" + name.replace('.', '/')
        + kind.extension), kind);
    this.source = null;
    this.bytes = new ByteArrayOutputStream();
  }
  
  public RuntimeFileObject(FnSource src) {
    super(URI.create("string:///" 
        + src.getQualifiedClassName().replace('.', '/') 
        + Kind.SOURCE.extension), Kind.SOURCE
    );
    this.source = requireNonNull(src, "sourceCode must not be null");
    this.bytes = new ByteArrayOutputStream();
  }
  
  @Override
  public CharSequence getCharContent(boolean ignoreEncodingErrors) {
    return source.toString();
  }
  
  public byte[] getBytes() {
    return bytes.toByteArray();
  }

  @Override
  public OutputStream openOutputStream() {
    bytes.reset();
    return bytes;
  }
  
}
