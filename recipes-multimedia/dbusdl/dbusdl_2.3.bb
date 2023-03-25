SUMMARY = "D-Bus downloader daemon"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
                    file://COPYING.GPLv3;md5=d32239bcb673463ab874e80d47fae504"

SRCREV = "1f4ea0965380c3d88ee671acce4b955768e349a5"

SRC_URI = "gitsm://github.com/TplusA/DBusDL.git;branch=master;protocol=https \
           file://0001-extract_documentation-Use-Python-3-shebang.patch"

S = "${WORKDIR}/git"

DEPENDS = " \
    glib-2.0 \
    glib-2.0-native \
    curl \
    autorevision-native \
"
RDEPENDS:${PN} += " \
    glib-2.0 \
    glib-2.0-utils \
    glib-2.0-codegen \
    strbo-dbus \
    libcurl \
"

PACKAGES += "${PN}-dbus-service"

FILES:${PN}-dbus-service += "${datadir}/dbus-1/services/de.tahifi.DBusDL.service"

CFLAGS += "-std=c11"
CPPFLAGS += "-DMSG_BACKTRACE_ENABLED=0"

inherit autotools pkgconfig

pkg_postinst:${PN} () {
if [ x"$D" = x ]; then pkill dbusdl; fi
}

pkg_prerm:${PN} () {
if [ x"$D" = x ]; then pkill dbusdl; fi
}
