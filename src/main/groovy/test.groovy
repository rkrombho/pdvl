import org.pdvl.*


def b = new PackageValidatingBuilder(new File('/tmp'), new File('/tmp/package'))
b.package('ebilling') {
  file(~/ebilling_\d\.\d\.\d-?[a-zA-Z0-9-]*\.tgz/) {
    gzip {

    }
  }
}
