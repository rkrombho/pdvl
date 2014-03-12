package org.pdvl

class TestBuilder extends BuilderSupport {
  
  protected void setParent(Object parent, Object child) {
    println "setParent($parent, $child)"
  }

  protected Object createNode(Object name) {
    println "createNode($name)"
  }

  protected Object createNode(Object name, Object value) {
    println "createNode($name, $value)"
  }

  protected Object createNode(Object name, Map attributes) {
    println "createNode($name, $attributes)"
  }

  protected Object createNode(Object name, Map attributes, Object value) {
    println "createNode($name, $attributes, value)"
  }

  protected void nodeCompleted(Object parent, Object node) {
    println "nodeCompleted($parent, $node)"
  }

}

