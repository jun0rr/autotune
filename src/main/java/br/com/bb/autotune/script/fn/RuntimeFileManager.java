/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.script.fn;

import java.util.HashMap;
import java.util.Map;
import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.StandardJavaFileManager;

/**
 *
 * @author Juno
 */
public class RuntimeFileManager extends ForwardingJavaFileManager<JavaFileManager> {
  
  private final Map<String, RuntimeFileObject> classes;
  
  private final RuntimeClassLoader loader;

  public RuntimeFileManager(StandardJavaFileManager standardManager) {
    super(standardManager);
    this.classes = new HashMap<>();
    this.loader = new RuntimeClassLoader(this.getClass().getClassLoader(), this);
  }

  @Override
  public JavaFileObject getJavaFileForOutput(Location location, String className, Kind kind, FileObject sibling) {
    RuntimeFileObject classBytes = new RuntimeFileObject(className, kind);
    classes.put(className, classBytes);
    return classBytes;
  }

  public Map<String, RuntimeFileObject> getBytesMap() {
    return classes;
  }
  
  @Override
  public ClassLoader getClassLoader(Location l) {
    return loader;
  }
  
}
