SUMMARY = "T+A Player Switch"
SECTION = "sound"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
                    file://COPYING.GPLv3;md5=d32239bcb673463ab874e80d47fae504"

SRCREV = "b966b4de34082b6c614dba9d9bc38809c51452a6"

SRC_URI = "gitsm://git.tua.local/repo/TAPSwitch;branch=master;protocol=http \
           file://0001-extract_documentation-Use-Python-3-shebang.patch \
           file://tapswitch.service"

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
"

FILES:${PN} += "${systemd_unitdir}/system/tapswitch.service"

SYSTEMD_SERVICE:${PN} = "tapswitch.service"
SYSTEMD_AUTO_ENABLE = "enable"

CFLAGS += "-std=c11"
CPPFLAGS += "-DMSG_BACKTRACE_ENABLED=0"

USERADD_PACKAGES = "${PN}"
USERADD_PARAM:${PN} = "-r -N -g strbo -G audio strbo-audio"
GROUPADD_PARAM:${PN} = "-r strbo; -r audio"
GROUPMEMS_PARAM:${PN} = ""

inherit autotools pkgconfig systemd useradd

do_install:append() {
    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}
    then
        install -d ${D}${systemd_unitdir} ${D}${systemd_unitdir}/system
        install -m 644 ${WORKDIR}/tapswitch.service ${D}${systemd_unitdir}/system
    fi
}
