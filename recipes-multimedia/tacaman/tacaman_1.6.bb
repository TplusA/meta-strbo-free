SUMMARY = "T+A Cover Art Manager"
SECTION = "sound"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
                    file://COPYING.GPLv3;md5=d32239bcb673463ab874e80d47fae504"

SRCREV = "78294c7fe58e815d72e95e70cd84517a29b80c50"

SRC_URI = "gitsm://github.com/TplusA/TACAMan.git;branch=master;protocol=https \
           file://0001-extract_documentation-Use-Python-3-shebang.patch \
           file://tacaman.service"

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
    imagemagick \
    wget \
"

FILES:${PN} += "${systemd_unitdir}/system/tacaman.service"

SYSTEMD_SERVICE:${PN} = "tacaman.service"
SYSTEMD_AUTO_ENABLE = "enable"

CFLAGS += "-std=c11"
CPPFLAGS += "-DMSG_BACKTRACE_ENABLED=0"

USERADD_PACKAGES = "${PN}"
USERADD_PARAM:${PN} = "-r -N -g tacaman strbo-tacaman"
GROUPADD_PARAM:${PN} = "-r tacaman"
GROUPMEMS_PARAM:${PN} = " "

inherit autotools pkgconfig systemd useradd

do_install:append() {
    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}
    then
        install -d ${D}${systemd_unitdir} ${D}${systemd_unitdir}/system
        install -m 644 ${WORKDIR}/tacaman.service ${D}${systemd_unitdir}/system
    fi
}
