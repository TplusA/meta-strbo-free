SUMMARY = "DCP to SPI bridge in user space"
SECTION = "sound"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
                    file://COPYING.GPLv3;md5=d32239bcb673463ab874e80d47fae504"

SRCREV = "a0f19960950e51a88f090b3448543ba9205f0d10"

SRC_URI = "gitsm://git.tua.local/repo/DCPSPI;branch=master;protocol=http \
           file://dcpspi.service \
           file://dcpspi_gpio \
           file://gpio.rules \
           file://spidev.rules \
"

S = "${WORKDIR}/git"

CFLAGS += "-std=c11"
CPPFLAGS += "-DMSG_BACKTRACE_ENABLED=0"

SYSTEMD_SERVICE:${PN} = "dcpspi.service"

DEPENDS = " \
    autoconf-archive \
    autorevision-native \
"

RDEPENDS:${PN} = "dcpd"

USERADD_PACKAGES = "${PN}"
USERADD_PARAM:${PN} = "-r -N -g strbo -G spi,gpio strbo-spi"
GROUPADD_PARAM:${PN} = "-r strbo; -r spi; -r gpio"
GROUPMEMS_PARAM:${PN} = ""

inherit autotools pkgconfig systemd useradd

do_install:append() {
    install -d ${D}${systemd_unitdir} ${D}${systemd_unitdir}/system
    install -m 644 ${WORKDIR}/dcpspi.service ${D}${systemd_unitdir}/system

    install -d ${D}${libexecdir}
    install -m 755 ${WORKDIR}/dcpspi_gpio ${D}${libexecdir}

    install -d ${D}${sysconfdir} ${D}${sysconfdir}/udev ${D}${sysconfdir}/udev/rules.d
    install -m 644 ${WORKDIR}/gpio.rules ${D}${sysconfdir}/udev/rules.d
    install -m 644 ${WORKDIR}/spidev.rules ${D}${sysconfdir}/udev/rules.d
}

FILES:${PN} += " \
    ${systemd_unitdir}/system/dcpspi.service \
    ${bindir}/dcpspi_gpio \
    ${sysconfdir}/udev/rules.d/gpio.rules \
    ${sysconfdir}/udev/rules.d/spidev.rules \
"
