package org.pdvl.factories

import groovy.io.FileType
import java.util.regex.Pattern

public class FileFactory extends AbstractFactory {

  public Object newInstance(FactoryBuilderSupport builder, 
    Object name, Object value, Map attributes) 
      throws InstantiationException, IllegalAccessException {
    def pattern = value
    if (pattern instanceof String) {
      pattern = pattern.bitwiseNegate()
    }
    def matchedFiles = []
    builder.baseDirectory.eachFileMatch(FileType.FILES, pattern) {      
      matchedFiles << it
    }

    if(matchedFiles.size() == 0) {
      throw new InstantiationException("No File matching ${pattern} found in ${builder.baseDirectory}")
    }
    else {
      println "[INFO] Found file(s) matching ${pattern}:\n\t ${matchedFiles.join(' ')}"
    }
    builder.context['files'] = matchedFiles
    return matchedFiles
  }
}
