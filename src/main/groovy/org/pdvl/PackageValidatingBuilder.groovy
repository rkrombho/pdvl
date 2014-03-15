package org.pdvl

import org.pdvl.factories.*
import java.util.logging.Level
import java.util.logging.Logger

public class PackageValidatingBuilder extends FactoryBuilderSupport {
  File baseDirectory
  File workDirectory

  public PackageValidatingBuilder(File baseDirectory, File workDirectory, boolean init = true) {
    super(init)
    //Disable the warning messages that get printed by FactoryBuilderSupport
    Logger.getLogger( FactoryBuilderSupport.class.getName() ).level = Level.SEVERE
    this.baseDirectory = baseDirectory
    this.workDirectory = workDirectory
  }

  public registerObjectFactories() {
    registerFactory('package', new PackageFactory())
    registerFactory('file', new FileFactory())
    registerFactory('gzip', new GzipFactory())
  }
}
