SUMMARY = "Licenses for used packages"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

PR = "r1"

inherit allarch
RDEPENDS:${PN} = "lighttpd lighttpd-module-alias"

SRC_URI += "file://10-licenses-alias.conf"

do_install() {
install -d ${D}/etc/lighttpd.d/
install -m 644 ${WORKDIR}/10-licenses-alias.conf ${D}/etc/lighttpd.d/
}
