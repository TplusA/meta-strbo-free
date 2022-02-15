SUMMARY = "FastCGI wrapper for WSGI applications."
HOMEPAGE = "https://github.com/Kozea/flipflop"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=6a9de3747a80159a9263f09abbe13735"

SRC_URI[md5sum] = "9c129c4ef7b269a73113410b640edadd"
SRC_URI[sha256sum] = "32a23ed7bdb53bab03271c4c92abceae1ee3a337dd7fd5a18334fd64930ebcfd"

PYPI_PACKAGE = "flipflop"

RDEPENDS:${PN} += "${PYTHON_PN}-fcntl ${PYTHON_PN}-resource ${PYTHON_PN}-threading"

CLEANBROKEN = "1"
