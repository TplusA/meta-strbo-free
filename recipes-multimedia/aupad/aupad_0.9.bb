SUMMARY = "T+A Audio Path Daemon"
SECTION = "sound"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
                    file://COPYING.GPLv3;md5=d32239bcb673463ab874e80d47fae504"

SRCREV = "dcbcaec4eea55b7b63c40b9a5a86e349d7edac96"

SRC_URI = "gitsm://github.com/TplusA/AuPaD.git;branch=master;protocol=https \
           file://0001-extract_documentation-Use-Python-3-shebang.patch \
           file://aupad.service"

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
"

RRECOMMENDS:${PN} += "${PN}-data"

PACKAGES += "${PN}-data"

FILES:${PN} += "${systemd_unitdir}/system/aupad.service"

FILES:${PN}-data += "${datadir}/aupad/models.json"

SYSTEMD_SERVICE:${PN} = "aupad.service"
SYSTEMD_AUTO_ENABLE = "enable"

CPPFLAGS += "-DMSG_BACKTRACE_ENABLED=0"

inherit autotools pkgconfig systemd

do_install:append() {
    install -d ${D}${datadir}/aupad
    install -m 644 ${S}/documentation/models.json ${D}${datadir}/aupad/models.json

    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}
    then
        install -d ${D}${systemd_unitdir} ${D}${systemd_unitdir}/system
        install -m 644 ${WORKDIR}/aupad.service ${D}${systemd_unitdir}/system
    fi
}
