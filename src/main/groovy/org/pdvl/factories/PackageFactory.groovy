package org.pdvl.factories

public class PackageFactory extends AbstractFactory {

  public boolean isLeaf() {
    return false
  }

  public Object newInstance(FactoryBuilderSupport builder, 
    Object name, Object value, Map attributes) 
      throws InstantiationException, IllegalAccessException {
    if ( !builder.baseDirectory.canRead() 
      || !builder.baseDirectory.isDirectory() ) {
      throw new InstantiationException("Could not build node 'package'. Builder base directory (${builder.baseDirectory}) is not a readable directory")
    }    
    println "returning ${builder.baseDirectory}"
    return builder.baseDirectory 
  }
}
