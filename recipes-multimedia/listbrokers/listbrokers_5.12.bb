SUMMARY = "T+A list broker daemons"
SECTION = "sound"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
                    file://COPYING.GPLv3;md5=d32239bcb673463ab874e80d47fae504"

SRCREV = "8f48b45a917911679ec6203035d7dc97b2b02f84"

SRC_URI = " \
    gitsm://git.tua.local/repo/Listbrokers;branch=master;protocol=http \
    file://0001-extract_documentation-Use-Python-3-shebang.patch \
"

S = "${WORKDIR}/git"

DEPENDS = " \
    glib-2.0 \
    glib-2.0-native \
    curlpp \
    autoconf-archive \
    autorevision-native \
"

RDEPENDS:${PN} += " \
    glib-2.0 \
    glib-2.0-utils \
    glib-2.0-codegen \
    strbo-dbus \
    curlpp \
    mounta \
    dleyna-server \
"

PACKAGES += "${PN}-dbus-service"

FILES:${PN}-dbus-service += " \
    ${datadir}/dbus-1/services/de.tahifi.FileBroker.service \
    ${datadir}/dbus-1/services/de.tahifi.UPnPBroker.service \
"

CFLAGS += "-std=c11"
CPPFLAGS += "-DMSG_BACKTRACE_ENABLED=0"

inherit autotools pkgconfig

do_install:append() {
    install -d ${D}${bindir}
}

pkg_postinst:${PN} () {
if [ x"$D" = x ]; then
    pkill strbo_lb_usb
    pkill strbo_lb_upnp
fi
exit 0
}

pkg_prerm:${PN} () {
if [ x"$D" = x ]; then
    pkill strbo_lb_usb
    pkill strbo_lb_upnp
fi
exit 0
}
