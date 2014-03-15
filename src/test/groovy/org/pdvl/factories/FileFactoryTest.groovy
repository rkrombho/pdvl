package org.pdvl.factories

import spock.lang.Specification
import org.pdvl.PackageValidatingBuilder


/**
 * Test Class for the FileFactory 
 *
 * @author Robert Krombholz
 */
class FileFactoryTest extends Specification{

    def "Test successful instantiation with pattern value"() {  				
        setup:				
		def tmpDir = new File(System.getProperty("java.io.tmpdir"))		
		def testFile = new File(tmpDir, 'test.file')
		//create an empty file
		testFile.lastModified = new Date().time

        when:
		def pf = new PackageFactory()
		def fakeBuilder = new PackageValidatingBuilder(tmpDir, tmpDir)
		//call newInstance as the Builder would normally do
		def pfInstance = pf.newInstance(fakeBuilder, 'file', ~/test\.file/, null)
		
        then:
        notThrown InstantiationException
		
		cleanup:
		//Expect an InstantiationException
		testFile?.delete()
    }
}