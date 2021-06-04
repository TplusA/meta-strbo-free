SUMMARY = "T+A Streaming Board system distribution configuration file for main system"
SECTION = "base"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
SRC_URI = "file://systeminfo.rc.in"

require strbo-systeminfo.inc

do_install() {
    install -d ${D}${sysconfdir}
    generate_systeminfo
}
