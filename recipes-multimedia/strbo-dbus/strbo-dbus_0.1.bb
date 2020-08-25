SUMMARY = "StrBo session D-Bus service"
SECTION = "sound"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

PR = "r6"

SRC_URI = " \
    file://strbo-dbus.service \
    file://strbo.conf \
    file://users.conf \
    file://strbo-allow-root.conf \
    file://users-allow-root.conf \
"

S = "${WORKDIR}"

DEPENDS = "dbus"
RDEPENDS_${PN} += "dbus"

SYSTEMD_SERVICE_${PN} = "strbo-dbus.service"

FILES_${PN} += " \
    ${sysconfdir}/dbus-1/system.d/strbo.conf \
    ${sysconfdir}/dbus-1/session.d/users.conf \
"

inherit systemd

do_install() {
    install -d ${D}${systemd_unitdir} ${D}${systemd_unitdir}/system
    install -m 644 ${WORKDIR}/strbo-dbus.service ${D}${systemd_unitdir}/system

    install -d \
        ${D}${sysconfdir} \
        ${D}${sysconfdir}/dbus-1 \
        ${D}${sysconfdir}/dbus-1/system.d \
        ${D}${sysconfdir}/dbus-1/session.d
    install -m 644 ${WORKDIR}/strbo.conf ${D}${sysconfdir}/dbus-1/system.d
    install -m 644 ${WORKDIR}/users.conf ${D}${sysconfdir}/dbus-1/session.d
    install -m 644 ${WORKDIR}/strbo-allow-root.conf ${D}${sysconfdir}/dbus-1/system.d
    install -m 644 ${WORKDIR}/users-allow-root.conf ${D}${sysconfdir}/dbus-1/session.d
}

FILES_${PN} += "${systemd_unitdir}/system/strbo-dbus.service"

PACKAGES =+ "strbo-dbus-allow-root"

FILES_strbo-dbus-allow-root = " \
    ${sysconfdir}/dbus-1/system.d/strbo-allow-root.conf \
    ${sysconfdir}/dbus-1/session.d/users-allow-root.conf \
    "

ALLOW_EMPTY_${PN}-allow-root = "1"
