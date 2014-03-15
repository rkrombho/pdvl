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
    if ( !builder.workDirectory.canWrite() 
      || !builder.workDirectory.isDirectory() ) {
      throw new InstantiationException("Could not build node 'package'. Working directory (${builder.workDirectory}) is not a writeable directory")
    }
    println "[INFO] Validating package ${value}..."
    return null
  }
}
