SUMMARY = "T+A Streamplayer"
SECTION = "sound"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
                    file://COPYING.GPLv3;md5=d32239bcb673463ab874e80d47fae504"

SRCREV = "f0c97c385693621170fa85ff0922e1fc837fdc6c"

SRC_URI = "gitsm://git.tua.local/repo/Streamplayer;branch=master;protocol=http \
           file://0001-extract_documentation-Use-Python-3-shebang.patch \
           file://streamplayer.service"

S = "${WORKDIR}/git"

DEPENDS = " \
    glib-2.0 \
    glib-2.0-native \
    gstreamer1.0 \
    gstreamer1.0-plugins-good \
    gstreamer1.0-plugins-bad \
    gstreamer1.0-plugins-ugly \
    autorevision-native \
"
RDEPENDS:${PN} += " \
    glib-2.0 \
    glib-2.0-utils \
    glib-2.0-codegen \
    strbo-dbus \
    tapswitch \
    tacaman \
    gstreamer1.0-meta-audio \
    gstreamer1.0-plugins-good-meta \
    gstreamer1.0-plugins-bad-meta \
    gstreamer1.0-plugins-ugly-meta \
    gstreamer1.0-libav \
    glibc-gconv-unicode \
    glibc-gconv-utf-16 \
    glibc-gconv-iso8859-1 \
"

PACKAGES += "${PN}-dbus-service"

FILES:${PN} += "${systemd_unitdir}/system/streamplayer.service"

FILES:${PN}-dbus-service += "${datadir}/dbus-1/services/de.tahifi.Streamplayer.service"

SYSTEMD_SERVICE:${PN} = "streamplayer.service"
SYSTEMD_AUTO_ENABLE = "enable"

CFLAGS += "-std=c11"
CPPFLAGS += "-DMSG_BACKTRACE_ENABLED=0"

inherit autotools pkgconfig systemd

do_install:append() {
    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}
    then
        install -d ${D}${systemd_unitdir} ${D}${systemd_unitdir}/system
        install -m 644 ${WORKDIR}/streamplayer.service ${D}${systemd_unitdir}/system
    fi
}
