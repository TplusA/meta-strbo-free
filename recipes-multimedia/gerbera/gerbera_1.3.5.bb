# Adapted from gerbera_git.bb in Rocko
#
# Changes:
#
# - Fixed version to 1.3.5
# - Taglib is not optional. Let's keep it simple.
# - Added custom user and group

Description = "Gerbera UPnP media server"
PR = "r1"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=25cdec9afe3f1f26212ead6bd2f7fac8"

SRC_URI = "git://github.com/v00d00/gerbera.git;protocol=https;tag=v${PV} \
    file://strbo-patches/0001-Add-log-messages-to-Gerbera-for-file-addition-remova.patch \
    file://strbo-patches/0002-Add-build.sh-script.patch \
    file://strbo-patches/0003-Write-out-file-count-after-addition-removal-rescan.patch \
    file://strbo-patches/0004-Omit-date-time-prefix-from-output.patch \
    file://strbo-patches/0005-Remove-All-audio-All-full-name.patch \
    file://strbo-patches/0006-Add-DBUS-interface-using-systemd-sd-bus-library.patch \
    file://strbo-patches/0007-Remove-extra-busy-idle-transition-on-start.patch \
    file://strbo-patches/0008-Update-media-server-meta-data-for-T-A.patch \
    file://strbo-patches/0009-Add-graceful-exit-on-unhandled-exception.patch \
    file://gerbera.service \
    file://config.xml \
    file://gerbera-update-config-from-strbo \
    file://gerbera-reset-database \
    file://mt-icon120.bmp \
    file://mt-icon120.jpg \
    file://mt-icon120.png \
    file://mt-icon48.bmp \
    file://mt-icon48.jpg \
    file://mt-icon48.png \
    file://mt-icon32.bmp \
    file://mt-icon32.jpg \
    file://mt-icon32.png \
    file://index.html \
    file://gerberad \
"

S = "${WORKDIR}/git"

DEPENDS = "expat zlib curl taglib libupnp e2fsprogs sqlite3 libav libexif file duktape flagpole"
DEPENDS += "systemd"

DEPENDS += " \
    strbo-startup (>= 1.0-r15) \
"

RDEPENDS_${PN} += "xmlstarlet \
    mounta (>= 0.10) \
    flagpole-upnpgate (>= 0.2-r4) \
    bash \
    python3 \
    "
RRECOMMENDS_${PN} += "listbrokers (>= 4.1)"

SYSTEMD_SERVICE_${PN} = "gerbera.service"
SYSTEMD_AUTO_ENABLE_${PN} = "disable"

USERADD_PACKAGES = "${PN}"
USERADD_PARAM_${PN} = "-r -N -g strbo strbo-mediaserver"
GROUPADD_PARAM_${PN} = "-r strbo"

inherit pkgconfig cmake systemd useradd

EXTRA_OECMAKE = "-DWITH_JS=1 -DWITH_AVCODEC=1 -DWITH_TAGLIB=0 "

do_install_append() {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/gerbera.service ${D}${systemd_system_unitdir}/
    install -d ${D}/etc/gerbera
    install -m 0644 -o strbo ${WORKDIR}/config.xml ${D}/etc/gerbera/config-default.xml
    install -m 0755 ${WORKDIR}/gerbera-update-config-from-strbo ${D}/usr/bin
    install -m 0755 ${WORKDIR}/gerbera-reset-database ${D}/usr/bin
    install -m 0644 ${WORKDIR}/mt-icon* ${D}/usr/share/gerbera/web/icons
    install -m 0644 ${WORKDIR}/index.html ${D}/usr/share/gerbera/web
    install -m 0755 ${WORKDIR}/gerberad ${D}/usr/bin
    ln -s -f -T /run/mount-by-label ${D}/Storage
    ln -s -f -T /var/local/etc/gerbera/config.xml ${D}/etc/gerbera/config.xml
}

FILES_${PN} += "/Storage"
