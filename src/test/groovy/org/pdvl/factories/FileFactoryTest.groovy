package org.pdvl.factories

import spock.lang.Specification
import org.pdvl.PackageValidatingBuilder
import org.apache.commons.io.FileUtils

/**
 * Test Class for the FileFactory 
 *
 * @author Robert Krombholz
 */
class FileFactoryTest extends Specification{

	private File createTempFile(String name) {
		def tmpDir = new File(System.getProperty("java.io.tmpdir"))	
		def testFile = new File(tmpDir, name)
		//create an empty file
		FileUtils.touch(testFile)
		return testFile
	}

    def "Test successful instantiation with pattern value"() {  				
        setup:				
		def tmpDir = new File(System.getProperty("java.io.tmpdir"))		
		def testFile = createTempFile 'test.file'
		//stubb the builders getContext
		def context = [:]
		PackageValidatingBuilder.metaClass.getContext = {
		  return context
		}
		
        when:
		def pf = new FileFactory()
		def fakeBuilder = new PackageValidatingBuilder(tmpDir, tmpDir)		
		//call newInstance as the Builder would normally do
		def pfInstance = pf.newInstance(fakeBuilder, 'file', ~/test\.file/, null)

		
        then:
        notThrown InstantiationException	
		pfInstance == [ testFile ]
		context['files'] == [ testFile ]
		
		cleanup:
		//Expect an InstantiationException
		testFile?.delete()
    }
	
	def "Test successful instantiation with String value"() {  				
        setup:				
		def tmpDir = new File(System.getProperty("java.io.tmpdir"))		
		def testFile = createTempFile 'test.file'
		//stubb the builders getContext
		def context = [:]
		PackageValidatingBuilder.metaClass.getContext = {
		  return context
		}

        when:
		def pf = new FileFactory()
		def fakeBuilder = new PackageValidatingBuilder(tmpDir, tmpDir)
		//call newInstance as the Builder would normally do
		def pfInstance = pf.newInstance(fakeBuilder, 'file', 'test.file', null)
		
        then:
        notThrown InstantiationException		
		pfInstance == [ testFile ]
		context['files'] == [ testFile ]
				
		cleanup:
		//Expect an InstantiationException
		testFile?.delete()
    }
	
	def "Should throw InstantiationException when file does not exist"() {  				
		setup:				
		def tmpDir = new File(System.getProperty("java.io.tmpdir"))		
        when:		
		def pf = new FileFactory()
		def fakeBuilder = new PackageValidatingBuilder(tmpDir, tmpDir)
		//call newInstance as the Builder would normally do
		pf.newInstance(fakeBuilder, 'file', 'blahfile009922.txt', null)
		
        then:
        thrown InstantiationException		
    }
}