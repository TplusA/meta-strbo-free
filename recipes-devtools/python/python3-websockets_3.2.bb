inherit setuptools3

SUMMARY = "An implementation of the WebSocket Protocol (RFC 6455)"
HOMEPAGE = "https://github.com/aaugustin/websockets"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=dd6fe435c697ec9f28d4bbb38b955e64"

SRC_URI = "https://files.pythonhosted.org/packages/c5/5c/fd5cbe294146421596459ba7ae55f39972d722e77da4c791fdc25be5c8e8/websockets-3.2.tar.gz"
SRC_URI[sha256sum] = "b536827d433a79e85108becdac0b0909e7ce02c3613df53da6d96aee950b50b5"
SRC_URI[md5sum] = "c1842c03d14169082e065a109fe1cb2c"

S = "${WORKDIR}/websockets-${PV}"
