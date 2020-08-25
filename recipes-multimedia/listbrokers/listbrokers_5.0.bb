SUMMARY = "T+A list broker daemons"
SECTION = "sound"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
                    file://COPYING.GPLv3;md5=d32239bcb673463ab874e80d47fae504"

SRCREV = "90762aece94631fc10b8ecb1d90ce19cd9118e65"
PR = "r2"

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

RDEPENDS_${PN} += " \
    glib-2.0 \
    glib-2.0-utils \
    glib-2.0-codegen \
    strbo-dbus \
    curlpp \
    mounta \
    dleyna-server \
"

PACKAGES += "${PN}-dbus-service"

FILES_${PN}-dbus-service += " \
    ${datadir}/dbus-1/services/de.tahifi.FileBroker.service \
    ${datadir}/dbus-1/services/de.tahifi.UPnPBroker.service \
"

CFLAGS += "-std=c11"
CPPFLAGS += "-DMSG_BACKTRACE_ENABLED=0"

inherit autotools pkgconfig

do_install_append() {
    install -d ${D}${bindir}
}

pkg_postinst_${PN} () {
if [ x"$D" = x ]; then
    pkill strbo_lb_usb
    pkill strbo_lb_upnp
fi
}

pkg_prerm_${PN} () {
if [ x"$D" = x ]; then
    pkill strbo_lb_usb
    pkill strbo_lb_upnp
fi
}
