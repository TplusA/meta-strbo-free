SUMMARY = "T+A Streamplayer"
SECTION = "sound"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
                    file://COPYING.GPLv3;md5=d32239bcb673463ab874e80d47fae504"

SRCREV = "a7f72948c82fd803d3b2696b99ca176047e18f14"
PR = "r2"

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
RDEPENDS_${PN} += " \
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

FILES_${PN} += "${systemd_unitdir}/system/streamplayer.service"

FILES_${PN}-dbus-service += "${datadir}/dbus-1/services/de.tahifi.Streamplayer.service"

SYSTEMD_SERVICE_${PN} = "streamplayer.service"
SYSTEMD_AUTO_ENABLE = "enable"

CFLAGS += "-std=c11"
CPPFLAGS += "-DMSG_BACKTRACE_ENABLED=0"

inherit autotools pkgconfig systemd

do_install_append() {
    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}
    then
        install -d ${D}${systemd_unitdir} ${D}${systemd_unitdir}/system
        install -m 644 ${WORKDIR}/streamplayer.service ${D}${systemd_unitdir}/system
    fi
}
