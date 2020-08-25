SUMMARY = "A decorator for caching properties in classes."
HOMEPAGE = "https://github.com/pydanny/cached-property"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=db7ff60c4e14f58534201242803d8abc"

SRC_URI[md5sum] = "e4f2c194492fabd80b353d89198e434d"
SRC_URI[sha256sum] = "bf093e640b7294303c7cc7ba3212f00b7a07d0416c1d923465995c9ef860a139"

PYPI_PACKAGE = "cached-property"

RDEPENDS_${PN} += " \
    ${PYTHON_PN}-asyncio \
    ${PYTHON_PN}-datetime \
    ${PYTHON_PN}-dateutil \
    ${PYTHON_PN}-isodate \
    ${PYTHON_PN}-pytz \
    ${PYTHON_PN}-six \
    ${PYTHON_PN}-threading \
"

CLEANBROKEN = "1"
