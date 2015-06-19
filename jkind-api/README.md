API Users
=========
See the [examples](https://github.com/agacek/jkind/tree/master/jkind-api/src/jkind/api/examples) for how to use JKind API.

API Developers
==============
JKind API requires JFace and SWT libraries, the latter of which is
platform dependent. To handle this, the project depends on a user
library called JFace which needs to contain:

 * org.eclipse.swt.{platform}.{os}.{arch}
 * org.eclipse.jface
 * org.eclipse.core.commands
 * org.eclipse.equinox.common

For 64-bit Windows with Eclipse installed in c:/eclipse you may simply
import the user library from the provided file
"JFace.win32.x86_64.userlibraries". Otherwise you will need to
manually configure the user library (or modify and then import the
provided file).
