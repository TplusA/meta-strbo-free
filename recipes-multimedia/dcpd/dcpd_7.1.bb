SUMMARY = "DC protocol (DCP) daemon"
SECTION = "sound"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
                    file://COPYING.GPLv3;md5=d32239bcb673463ab874e80d47fae504"

SRCREV = "593680e17166fa2e2b4da832d9a252f3eb30ef4b"
PR = "r0"

SRC_URI = " \
    gitsm://git.tua.local/repo/DCPD;branch=master;protocol=http \
    file://dcpd.service \
    file://dcpd.sudoers \
    file://polkit-systemd-restart-units.rules \
    file://0001-extract_documentation-Use-Python-3-shebang.patch \
"

S = "${WORKDIR}/git"

DEPENDS = " \
    strbo-startup (>= 1.0-r15) \
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
    curlpp \
    strbo-dbus \
    dcpspi \
    tacaman \
    tapswitch \
    polkit \
"

PACKAGES += "${PN}-dbus-service"

FILES_${PN} += " \
    ${systemd_unitdir}/system/dcpd.service \
    ${sysconfdir}/polkit-1/rules.d/99-flagpole-restart.rules \
    ${sysconfdir}/sudoers.d/50-dcpd \
"

FILES_${PN}-dbus-service += "${datadir}/dbus-1/services/de.tahifi.Dcpd.service"

DIRFILES = "1"

SYSTEMD_SERVICE_${PN} = "dcpd.service"

CFLAGS += "-std=c11"
CPPFLAGS += "-DMSG_BACKTRACE_ENABLED=0"

USERADD_PACKAGES = "${PN}"
USERADD_PARAM_${PN} = "-r -N -g strbo -G netcfg strbo-dcpd"
GROUPADD_PARAM_${PN} = "-r strbo; -r netcfg"

inherit autotools pkgconfig systemd useradd

do_install_append() {
    install -d \
        ${D}${bindir} \
        ${D}${sysconfdir}

    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}
    then
        install -d \
            ${D}${systemd_unitdir} \
            ${D}${systemd_unitdir}/system \
            ${D}${sysconfdir}/polkit-1 \
            ${D}${sysconfdir}/polkit-1/rules.d
        install -m 644 ${WORKDIR}/dcpd.service ${D}${systemd_unitdir}/system
        install -m 644 ${WORKDIR}/polkit-systemd-restart-units.rules ${D}${sysconfdir}/polkit-1/rules.d/99-flagpole-restart.rules
    fi

    install -d ${D}${sysconfdir}/sudoers.d
    install -m 644 ${WORKDIR}/dcpd.sudoers ${D}${sysconfdir}/sudoers.d/50-dcpd
}
