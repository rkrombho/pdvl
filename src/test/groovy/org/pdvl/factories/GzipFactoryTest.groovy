package org.pdvl.factories

import spock.lang.Specification
import org.pdvl.PackageValidatingBuilder
import org.apache.commons.io.FileUtils
import java.util.zip.GZIPOutputStream
import java.io.FileInputStream
import java.io.FileOutputStream

/**
 * Test Class for the GzipFactory 
 *
 * @author Robert Krombholz
 */
class GzipFactoryTest extends Specification{

	/**
	 * Helper method to create a gzipped tile in the systems temp dir
	 */
	private File createGzippedTempFile(String name) {
		def tmpDir = new File(System.getProperty("java.io.tmpdir"))	
		def testFile = new File(tmpDir, 'blah')
		//create an empty file
		FileUtils.touch(testFile)
		def fis = new FileInputStream(testFile)
		def targetFile = new File(tmpDir, name)
		def fos = new FileOutputStream(targetFile)
		def gzos = new GZIPOutputStream(fos)
		byte[] buffer = new byte[1024];
		int len
		while((len=fis.read(buffer)) != -1){
			gzos.write(buffer, 0, len);		
		}
		gzos.close()
		fos.close()
		fis.close()		
		return targetFile
	}

    def "Test successful instantiation with one .gz file from parent context"() {  				
        setup:				
		def tmpDir = new File(System.getProperty("java.io.tmpdir"))		
		def testFile = createGzippedTempFile 'test.gz'
		//stubb the builders getContext and getParentContext
		def context = [:]
		def parentContext = ['files': [testFile]]
		PackageValidatingBuilder.metaClass.getContext = {
		  return context
		}
		PackageValidatingBuilder.metaClass.getParentContext = {
		  return parentContext
		}
		
        when:
		def pf = new GzipFactory()
		def fakeBuilder = new PackageValidatingBuilder(tmpDir, tmpDir)		
		//call newInstance as the Builder would normally do
		def pfInstance = pf.newInstance(fakeBuilder, 'file', null, null)

		
        then:
        notThrown InstantiationException	
		pfInstance == [ new File(tmpDir, 'test') ]
		context['files'] == [  new File(tmpDir, 'test') ]
		
		cleanup:				
		testFile?.delete()
		new File(tmpDir, 'test').delete()
    }
	
	def "Test successful instantiation with one .tgz file from parent context creating a .tar file"() {  				
        setup:				
		def tmpDir = new File(System.getProperty("java.io.tmpdir"))		
		def testFile = createGzippedTempFile 'test.tgz'
		//stubb the builders getContext and getParentContext
		def context = [:]
		def parentContext = ['files': [testFile]]
		PackageValidatingBuilder.metaClass.getContext = {
		  return context
		}
		PackageValidatingBuilder.metaClass.getParentContext = {
		  return parentContext
		}
		
        when:
		def pf = new GzipFactory()
		def fakeBuilder = new PackageValidatingBuilder(tmpDir, tmpDir)		
		//call newInstance as the Builder would normally do
		def pfInstance = pf.newInstance(fakeBuilder, 'file', null, null)

		
        then:
        notThrown InstantiationException	
		pfInstance == [ new File(tmpDir, 'test.tar') ]
		context['files'] == [  new File(tmpDir, 'test.tar') ]
		
		cleanup:				
		testFile?.delete()
		new File(tmpDir, 'test.tar').delete()
    }
	
	def "Test successful instantiation with multiple files from parent context"() {  				
        setup:				
		def tmpDir = new File(System.getProperty("java.io.tmpdir"))		
		def testFile1 = createGzippedTempFile 'test.tgz'
		def testFile2 = createGzippedTempFile 'test.gz'
		//stubb the builders getContext and getParentContext
		def context = [:]
		def parentContext = ['files': [testFile1, testFile2]]
		PackageValidatingBuilder.metaClass.getContext = {
		  return context
		}
		PackageValidatingBuilder.metaClass.getParentContext = {
		  return parentContext
		}
		
        when:
		def pf = new GzipFactory()
		def fakeBuilder = new PackageValidatingBuilder(tmpDir, tmpDir)		
		//call newInstance as the Builder would normally do
		def pfInstance = pf.newInstance(fakeBuilder, 'file', null, null)

		
        then:
        notThrown InstantiationException	
		pfInstance == [ new File(tmpDir, 'test.tar'), new File(tmpDir, 'test') ]
		context['files'] == [  new File(tmpDir, 'test.tar'), new File(tmpDir, 'test')  ]
		
		cleanup:				
		testFile1?.delete()
		testFile2?.delete()
		new File(tmpDir, 'test.tar').delete()
		new File(tmpDir, 'test').delete()
    }
}