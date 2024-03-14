/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune.script.fn;

import br.com.bb.autotune.Autotune;
import br.com.bb.autotune.EditorPanel;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;

/**
 *
 * @author Juno
 */
public class FnContext {
  
  private final EditorPanel panel;
  
  private final Map<String,Object> varmap;
  
  private final Map<String,Fn> fnmap;
  
  public FnContext(EditorPanel p) {
    this.panel = Objects.requireNonNull(p);
    this.varmap = new HashMap<>();
    this.fnmap = new HashMap<>();
  }
  
  public EditorPanel getEditorPanel() {
    return panel;
  }
  
  public Autotune getAutotune() {
    return panel.getAutotune();
  }
  
  public FnContext setVar(String name, Object value) {
    varmap.put(name, value);
    return this;
  }
  
  public <T> T getVar(String name) {
    return (T) varmap.get(name);
  }
  
  public <T> T rmVar(String name) {
    return (T) varmap.remove(name);
  }
  
  public FnContext clearVars() {
    varmap.clear();
    return this;
  }
  
  public Stream<Map.Entry<String, Object>> streamVars() {
    return varmap.entrySet().stream();
  }
  
  public Fn createFunction(FnSource src) {
    JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
    RuntimeFileManager manager = new RuntimeFileManager(compiler.getStandardFileManager(null, null, null));
    List<JavaFileObject> sourceFiles = Collections.singletonList(new RuntimeFileObject(src));
    JavaCompiler.CompilationTask task = compiler.getTask(null, manager, diagnostics, null, null, sourceFiles);
    boolean result = task.call();
    if(!result) {
      StringBuilder err = new StringBuilder("Compilation Error: ")
          .append(src.getQualifiedClassName())
          .append("\n");
      diagnostics.getDiagnostics()
          .forEach(d->err.append("  -> ").append(String.valueOf(d)).append("\n"));
      throw new RuntimeException(err.toString());
    } else {
      ClassLoader classLoader = manager.getClassLoader(null);
      try {
        Class<Fn> clazz = (Class<Fn>) classLoader.loadClass(src.getQualifiedClassName());
        Fn fn = clazz.getDeclaredConstructor().newInstance();
        fnmap.put(src.getName(), fn);
        return fn;
      }
      catch(RuntimeException r) {
        throw r;
      }
      catch(Exception e) {
        throw new RuntimeException(e);
      }
    }
  }
  
  public Fn getFunction(String name) {
    return fnmap.get(name);
  }
  
  public Fn rmFunction(String name) {
    return fnmap.remove(name);
  }
  
  public FnContext clearFunctions() {
    fnmap.clear();
    return this;
  }
  
  public Stream<Map.Entry<String, Fn>> streamFunctions() {
    return fnmap.entrySet().stream();
  }
  
}
