SUMMARY = "ISO 8601 date/time parser."
HOMEPAGE = "https://github.com/gweis/isodate"
LICENSE = "BSD"
LIC_FILES_CHKSUM = " \
    file://${WORKDIR}/LICENSE;md5=f660bf5af1d6ffdeff90b767b5ebc5cd \
    file://setup.py;endline=27;md5=e470a5d74091dc728436b206ee7f9470 \
"

SRC_URI += "file://LICENSE"
SRC_URI[md5sum] = "0e1203fce27ce65e2d01c5f21c4d428f"
SRC_URI[sha256sum] = "2e364a3d5759479cdb2d37cce6b9376ea504db2ff90252a2e5b7cc89cc9ff2d8"

PYPI_PACKAGE = "isodate"

CLEANBROKEN = "1"

RDEPENDS_${PN} += " \
    ${PYTHON_PN}-datetime \
    ${PYTHON_PN}-six \
"
