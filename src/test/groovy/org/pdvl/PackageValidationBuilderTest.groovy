package org.pdvl

import spock.lang.Specification
import org.apache.commons.io.FileUtils
/**
 * Test Class for the package validation and description DSL 
 * implemented within PackagValidationBuilder.
 *
 * @author Robert Krombholz
 */
class PackageValidationBuilderTest extends Specification{

    /**
	 * Helper method to copy a file from the tests resources directory to the systems temp dir
	 */
    def copyResourceToDir(String resourcesPackageName, File dest) {
		//get a the test package in the tests resource dir as File object
		def samplePackage = new File(Thread.currentThread().getContextClassLoader()
		  .getResource(resourcesPackageName).path)
		//copy the test package to the destination dir				
		FileUtils.copyFileToDirectory(samplePackage, dest)
	}

    def "With a valid package the DSL should not throw exceptions"() {  
        setup:				
		def tmpDir = new File(System.getProperty("java.io.tmpdir"))		
		//copy test package to the systems temp
		copyResourceToDir 'sampleapp_0.0.1-alpha.tar.gz', tmpDir
        //create a working directory		  
		def workDir = new File(tmpDir, 'package')
		workDir.mkdir()
		
        when:
		//instantiate a new PackageValidationBuilder with the package directory
		//as the systems temp dir and a working directory as temp/package
        def b = new PackageValidatingBuilder(tmpDir, workDir)
		//describe the sample package with all available Factories
		b.package('sampleapp') {
          file(~/sampleapp_\d\.\d\.\d-?[a-zA-Z0-9-]*\.tar.gz/) {
            gzip {

            }
          }
        }  
        then:
        notThrown InstantiationException
		
		cleanup:
		//delete the created working directory and the copied file		
		if(workDir != null && workDir.canRead()) {		
			FileUtils.deleteDirectory(workDir)
		}
		new File(tmpDir, 'sampleapp_0.0.1-alpha.tar.gz').delete()
		
    }
}
