SUMMARY = "pytz brings the Olson tz database into Python."
HOMEPAGE = "http://pythonhosted.org/pytz"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=4878a915709225bceab739bdc2a18e8d"

SRC_URI[md5sum] = "f054437920c895dd14a4509fabafe029"
SRC_URI[sha256sum] = "c06425302f2cf668f1bba7a0a03f3c1d34d4ebeef2c72003da308b3947c7f749"

PYPI_PACKAGE = "pytz"

CLEANBROKEN = "1"

RDEPENDS_${PN} += "${PYTHON_PN}-datetime ${PYTHON_PN}-threading"
