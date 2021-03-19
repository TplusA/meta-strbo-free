SUMMARY = "DCP to SPI bridge in user space"
SECTION = "sound"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
                    file://COPYING.GPLv3;md5=d32239bcb673463ab874e80d47fae504"

SRCREV = "50092a463418b510d50d1bfe1e734f3bb2bfe07b"
PR = "r5"

SRC_URI = "gitsm://git.tua.local/repo/DCPSPI;branch=master;protocol=http \
           file://dcpspi.service \
           file://dcpspi_gpio \
           file://gpio.rules \
           file://spidev.rules \
"

S = "${WORKDIR}/git"

CFLAGS += "-std=c11"
CPPFLAGS += "-DMSG_BACKTRACE_ENABLED=0"

SYSTEMD_SERVICE_${PN} = "dcpspi.service"

DEPENDS = " \
    autoconf-archive \
    autorevision-native \
"

RDEPENDS_${PN} = "dcpd"

USERADD_PACKAGES = "${PN}"
USERADD_PARAM_${PN} = "-r -N -g strbo -G spi,gpio strbo-spi"
GROUPADD_PARAM_${PN} = "-r strbo; -r spi; -r gpio"

inherit autotools pkgconfig systemd useradd

do_install_append() {
    install -d ${D}${systemd_unitdir} ${D}${systemd_unitdir}/system
    install -m 644 ${WORKDIR}/dcpspi.service ${D}${systemd_unitdir}/system

    install -d ${D}${libexecdir}
    install -m 755 ${WORKDIR}/dcpspi_gpio ${D}${libexecdir}

    install -d ${D}${sysconfdir} ${D}${sysconfdir}/udev ${D}${sysconfdir}/udev/rules.d
    install -m 644 ${WORKDIR}/gpio.rules ${D}${sysconfdir}/udev/rules.d
    install -m 644 ${WORKDIR}/spidev.rules ${D}${sysconfdir}/udev/rules.d
}

FILES_${PN} += " \
    ${systemd_unitdir}/system/dcpspi.service \
    ${bindir}/dcpspi_gpio \
    ${sysconfdir}/udev/rules.d/gpio.rules \
    ${sysconfdir}/udev/rules.d/spidev.rules \
"
