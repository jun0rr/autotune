/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.autotune;

import java.util.Objects;

/**
 *
 * @author Juno
 */
public class Reference<T> {
  
  private T ref;
  
  public Reference() {
    this(null);
  }
  
  public Reference(T ref) {
    this.ref = ref;
  }
  
  public boolean isEmpty() {
    return ref == null;
  }
  
  public boolean isPresent() {
    return !isEmpty();
  }
  
  public T get() {
    return ref;
  }
  
  public void set(T ref) {
    this.ref =  ref;
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 47 * hash + Objects.hashCode(this.ref);
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
    final Reference<?> other = (Reference<?>) obj;
    return Objects.equals(this.ref, other.ref);
  }

  @Override
  public String toString() {
    return "Reference{" + ref + '}';
  }
  
}
