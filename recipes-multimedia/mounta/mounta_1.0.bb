SUMMARY = "T+A mount daemon"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
                    file://COPYING.GPLv3;md5=d32239bcb673463ab874e80d47fae504"

SRCREV = "892519c67ece8af56ded139698d3c5cdacb715bc"
PR = "r1"

SRC_URI = " \
    gitsm://git.tua.local/repo/MounTA;branch=master;protocol=http \
    file://0001-extract_documentation-Use-Python-3-shebang.patch \
    file://mounta.service \
    file://mounta.sudoers \
    file://mounta.tmpfiles \
"

S = "${WORKDIR}/git"

DEPENDS = " \
    glib-2.0 \
    glib-2.0-native \
    autorevision-native \
    util-linux \
    autoconf-archive \
"
RDEPENDS_${PN} += " \
    glib-2.0 \
    glib-2.0-utils \
    glib-2.0-codegen \
    strbo-dbus \
    util-linux-mount \
    util-linux-umount \
    util-linux-blkid \
    sudo \
"

PACKAGES += "${PN}-dbus-service"

FILES_${PN} += " \
    ${systemd_unitdir}/system/mounta.service \
    ${sysconfdir}/sudoers.d/50-mounta \
    ${sysconfdir}/tmpfiles.d/50-mounta.conf \
"

DIRFILES = "1"

SYSTEMD_SERVICE_${PN} = "mounta.service"

CFLAGS += "-std=c11"
CPPFLAGS += "-DMSG_BACKTRACE_ENABLED=0"

inherit autotools pkgconfig systemd

do_install_append() {
    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}
    then
        install -d ${D}${systemd_unitdir} ${D}${systemd_unitdir}/system
        install -m 644 ${WORKDIR}/mounta.service ${D}${systemd_unitdir}/system
    fi

    install -d ${D}${sysconfdir} ${D}${sysconfdir}/sudoers.d ${D}${sysconfdir}/tmpfiles.d
    install -m 644 ${WORKDIR}/mounta.sudoers ${D}${sysconfdir}/sudoers.d/50-mounta
    install -m 644 ${WORKDIR}/mounta.tmpfiles ${D}${sysconfdir}/tmpfiles.d/50-mounta.conf

    install -d ${D}${bindir}
}

pkg_postinst_${PN}() {
    if [ -z "$D" ]; then
        if command -v systemd-tmpfiles >/dev/null; then
            systemd-tmpfiles --create ${sysconfdir}/tmpfiles.d/50-mounta.conf
        fi
    fi
}
