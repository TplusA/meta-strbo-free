SUMMARY = "Python HAL generation/parsing library."
HOMEPAGE = "https://github.com/paylogic/halogen"
LICENSE = "MIT"
LIC_FILES_CHKSUM = " \
    file://${WORKDIR}/LICENSE.txt;md5=a1a55911d4d0f8d4eba62faceb151a92 \
    file://README.rst;beginline=1067;md5=c2056cb6bd7f2eb8e59e76f8e7a70424 \
    file://setup.py;beginline=57;md5=1fe993dffbe6a91a2d81fae152e0c347 \
"

SRC_URI += "file://LICENSE.txt"
SRC_URI[md5sum] = "287afe6561dd43b993cf838b66f62db0"
SRC_URI[sha256sum] = "70be735c4a19cfeef6b3296dc1c29221ad06d92eaf371913e1a17bdee27ea70b"

PYPI_PACKAGE = "halogen"

CLEANBROKEN = "1"

RDEPENDS:${PN} += " \
    ${PYTHON_PN}-cached-property \
    ${PYTHON_PN}-datetime \
    ${PYTHON_PN}-dateutil \
    ${PYTHON_PN}-isodate \
    ${PYTHON_PN}-pytz \
    ${PYTHON_PN}-json \
"
