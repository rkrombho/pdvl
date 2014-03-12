import org.pdvl.*

/*
def b = new TestBuilder()
b.package('ebilling') {
  file 'kran'
  file (type: 'gz')
  directory('webserver') {
    file 'redirects.conf'
  }
}
*/

def b = new PackageValidatingBuilder(new File('/tmp'))
b.package()
b.package('test package')
