package org.pdvl

import org.pdvl.factories.*
import java.util.logging.Level
import java.util.logging.Logger

public class PackageValidatingBuilder extends FactoryBuilderSupport {
  File baseDirectory

  public PackageValidatingBuilder(File baseDirectory, boolean init = true) {
    super(init)
    //Disable the warning messages that get printed by FactoryBuilderSupport
    Logger.getLogger( FactoryBuilderSupport.class.getName() ).level = Level.SEVERE
    this.baseDirectory = baseDirectory
  }

  public registerObjectFactories() {
    registerFactory('package', new PackageFactory())
  }
}
