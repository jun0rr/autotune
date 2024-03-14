/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.script.fn;

import br.com.bb.autotune.Autotune;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Juno
 */
public class FnSource {
  
  private static final String CLASSPATH = "br.com.bb.autotune.script.fn.custom";
  
  private static final String PACKAGE = String.format("package %s; ", CLASSPATH);
  
  private static final String IMPLEMENTS = "public class %s implements Fn { %s }";
  
  private static final String METHOD = "public void run(FnContext ctx) { %s }";
  
  private final List<Class> imports;
  
  private final String name;
  
  private final String content;
  
  public FnSource(String name, String content) {
    this.name = Objects.requireNonNull(name);
    this.content = Objects.requireNonNull(content);
    this.imports = new LinkedList<>();
    this.imports.add(Fn.class);
    this.imports.add(FnContext.class);
    this.imports.add(Autotune.class);
  }
  
  public List<Class> getImportList() {
    return imports;
  }
  
  public String getName() {
    return name;
  }
  
  public String getQualifiedClassName() {
    return String.format("%s.%s", CLASSPATH, name);
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 79 * hash + Objects.hashCode(this.name);
    hash = 79 * hash + Objects.hashCode(this.content);
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
    final FnSource other = (FnSource) obj;
    if (!Objects.equals(this.name, other.name)) {
      return false;
    }
    return Objects.equals(this.content, other.content);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder(PACKAGE);
    imports.stream()
        .map(c->String.format("import %s; ", c.getName()))
        .forEach(s->sb.append(s));
    sb.append(String.format(
        IMPLEMENTS, name, 
        String.format(METHOD, content)
    ));
    return sb.toString();
  }
  
}
