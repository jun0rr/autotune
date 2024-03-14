/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.script.fn;

import static java.util.Objects.requireNonNull;

/**
 *
 * @author Juno
 */
public class RuntimeClassLoader extends ClassLoader {
  
  private RuntimeFileManager manager;
  
  public RuntimeClassLoader(ClassLoader parent, RuntimeFileManager manager) {
    super(parent);
    this.manager = requireNonNull(manager, "manager must not be null");
  }
  
  @Override
  protected Class<?> findClass(String name) throws ClassNotFoundException {
    if(manager.getBytesMap().containsKey(name)) {
      byte[] bytes = manager.getBytesMap().get(name).getBytes();
      return defineClass(name, bytes, 0, bytes.length);
    } else {
      throw new ClassNotFoundException(name);
    }
  }
  
}