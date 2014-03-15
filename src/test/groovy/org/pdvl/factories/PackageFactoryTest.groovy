package org.pdvl.factories

import spock.lang.Specification
import org.pdvl.PackageValidatingBuilder


/**
 * Test Class for the PackageFactory 
 *
 * @author Robert Krombholz
 */
class PackageFactoryTest extends Specification{

    def "Test successful instantiation with readable source and writeable working directory"() {  
        setup:				
		//get the system temp dir
		def tmpDir = new File(System.getProperty("java.io.tmpdir"))		
        //create a working directory		  
		def workDir = new File(tmpDir, 'package')
		workDir.mkdir()

        when:
		def pf = new PackageFactory()
		def fakeBuilder = new PackageValidatingBuilder(tmpDir, workDir)
		//call newInstance as the Builder would normally do
		def pfInstance = pf.newInstance(fakeBuilder, 'package', 'testpackage', null)
		
        then:
        notThrown InstantiationException
		
		cleanup:
		//Expect an InstantiationException
		workDir?.delete()
    }
	
	def "Expecting Intiantiation Exception with a non-existing package directory"() {
		setup:				
		//get the system temp dir
		def tmpDir = new File(System.getProperty("java.io.tmpdir"))		
	
		when:
		def pf = new PackageFactory()
		def fakeBuilder = new PackageValidatingBuilder(new File('non-existing-file'), tmpDir)
		//call newInstance as the Builder would normally do
		def pfInstance = pf.newInstance(fakeBuilder, 'package', 'testpackage', null)
		
		then:
		//Expect an InstantiationException
        thrown InstantiationException		
	}
	
	def "Expecting Intiantiation Exception with a non-existing working directory"() {
		setup:				
		//get the system temp dir
		def tmpDir = new File(System.getProperty("java.io.tmpdir"))		
	
		when:
		def pf = new PackageFactory()
		def fakeBuilder = new PackageValidatingBuilder(tmpDir, new File('non-existing-file'))
		//call newInstance as the Builder would normally do
		def pfInstance = pf.newInstance(fakeBuilder, 'package', 'testpackage', null)
		
		then:
		//Expect an InstantiationException
        thrown InstantiationException		
	}
}
