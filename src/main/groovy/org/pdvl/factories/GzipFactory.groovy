package org.pdvl.factories

import java.util.zip.GZIPInputStream

/**
 * Factory that works on 'files' from the parent context and unzips them
 * in the temporary working location specified by the builder.
 * @author: Robert Krombholz
 */
public class GzipFactory extends AbstractFactory {

  public Object newInstance(FactoryBuilderSupport builder, 
    Object name, Object value, Map attributes) 
      throws InstantiationException, IllegalAccessException {
    byte[] buffer = new byte[1024];
    def outputFiles = []
    //for each file within the parent context
    builder.parentContext['files'].each { file ->
      try{
        def gzis = new GZIPInputStream(file.newDataInputStream())
        //remove the file ending of the current file
        def outputFileName = file.name.replaceFirst(~/\.[^\.]+$/, '')
        //if the name was a .tgz we got to append a .tar again
        outputFileName += file.name.endsWith('.tgz') ? '.tar' : ''
        def outputFile = new File(builder.workDirectory, outputFileName)
        outputFiles << outputFile
        def fos = new FileOutputStream(outputFile)
        int len;
        while ((len = gzis.read(buffer)) > 0) {
          fos.write(buffer, 0, len);
        }
        gzis.close()
        fos.close()
      }
      catch(IOException ex){
        throw new InstantiationException("${file}: ${ex.message}")
      }
    }
    println "[INFO] Uncopressed files too ${builder.workDirectory}"
    //Put the files into the builders context
    builder.context['files'] = outputFiles
	return outputFiles
  }
}
