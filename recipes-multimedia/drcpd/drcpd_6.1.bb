SUMMARY = "DRC protocol (DRCP) daemon"
SECTION = "sound"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
                    file://COPYING.GPLv3;md5=d32239bcb673463ab874e80d47fae504"

SRCREV = "b1c3d710a2b03223edde35a22df0ab3bf0fe8be1"

SRC_URI = "gitsm://github.com/TplusA/DRCPD.git;branch=master;protocol=https \
           file://0001-extract_documentation-Use-Python-3-shebang.patch \
           file://drcpd.service"

S = "${WORKDIR}/git"

DEPENDS = " \
    glib-2.0 \
    glib-2.0-native \
    autoconf-archive \
    autorevision-native \
"
RDEPENDS:${PN} += " \
    glib-2.0 \
    glib-2.0-utils \
    glib-2.0-codegen \
    strbo-dbus \
    dcpd \
    tapswitch \
"

PACKAGES += "${PN}-dbus-service"

FILES:${PN} += "${systemd_unitdir}/system/drcpd.service"

FILES:${PN}-dbus-service += "${datadir}/dbus-1/services/de.tahifi.Drcpd.service"

SYSTEMD_SERVICE:${PN} = "drcpd.service"
SYSTEMD_AUTO_ENABLE = "enable"

CFLAGS += "-std=c11"
CPPFLAGS += "-DMSG_BACKTRACE_ENABLED=0"

inherit autotools pkgconfig systemd gettext

do_configure:prepend () {
    mkdir -p ${S}/config
    cp ${STAGING_DATADIR_NATIVE}/gettext/config.rpath ${S}/config/config.rpath
}

do_install:append() {
    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}
    then
        install -d ${D}${systemd_unitdir} ${D}${systemd_unitdir}/system
        install -m 644 ${WORKDIR}/drcpd.service ${D}${systemd_unitdir}/system
    fi
}
