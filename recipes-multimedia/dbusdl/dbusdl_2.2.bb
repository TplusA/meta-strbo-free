SUMMARY = "D-Bus downloader daemon"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
                    file://COPYING.GPLv3;md5=d32239bcb673463ab874e80d47fae504"

SRCREV = "d66b87712c8d5c99222f28a183156da0b0a8d8a4"

SRC_URI = "gitsm://git.tua.local/repo/DBusDL;branch=master;protocol=http \
           file://0001-extract_documentation-Use-Python-3-shebang.patch"

S = "${WORKDIR}/git"

DEPENDS = " \
    glib-2.0 \
    glib-2.0-native \
    curl \
    autorevision-native \
"
RDEPENDS_${PN} += " \
    glib-2.0 \
    glib-2.0-utils \
    glib-2.0-codegen \
    strbo-dbus \
    libcurl \
"

PACKAGES += "${PN}-dbus-service"

FILES_${PN}-dbus-service += "${datadir}/dbus-1/services/de.tahifi.DBusDL.service"

CFLAGS += "-std=c11"
CPPFLAGS += "-DMSG_BACKTRACE_ENABLED=0"

inherit autotools pkgconfig

pkg_postinst_${PN} () {
if [ x"$D" = x ]; then pkill dbusdl; fi
}

pkg_prerm_${PN} () {
if [ x"$D" = x ]; then pkill dbusdl; fi
}
