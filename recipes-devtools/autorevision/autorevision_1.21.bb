SUMMARY = "Autorevision (script for extracting revision information from repositories)"
DESCRIPTION = "A shell script for extracting revision information useful in release/build scripting from repositories"
SECTION = "utils"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING.md;md5=849d500e03ff2c119d96032b7be5753a"

DEPENDS = "sed-native gzip-native asciidoc-native libxml2-native libxslt-native"

BBCLASSEXTEND = "native nativesdk"

SRC_URI = "gitsm://github.com/Autorevision/autorevision.git;branch=master;protocol=https"
SRCREV = "726cff14de9f9aee1d80458b1873b7aede6263d2"

S = "${WORKDIR}/git"

inherit allarch

do_compile() {
    oe_runmake all
}

do_install () {
    unset mandir
    oe_runmake install DESTDIR="${D}"
}
