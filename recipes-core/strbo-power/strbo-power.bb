SUMMARY = "T+A Streaming Board power down event handling"
SECTION = "base"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI = " \
    file://strbo-power.service \
    file://watch_power_gpio \
    file://polkit-shutdown.rules \
"

S = "${WORKDIR}"

PR = "r3"

SYSTEMD_SERVICE:${PN} = "strbo-power.service"

RDEPENDS:${PN} += "polkit offevent"

DIRFILES = "1"

inherit systemd

do_install() {
    # systemd
    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}
    then
        install -d \
            ${D}${systemd_unitdir} \
            ${D}${systemd_unitdir}/system \
            ${D}${sysconfdir} \
            ${D}${sysconfdir}/polkit-1 \
            ${D}${sysconfdir}/polkit-1/rules.d
        install -m 644 ${WORKDIR}/strbo-power.service ${D}${systemd_unitdir}/system
        install -m 644 ${WORKDIR}/polkit-shutdown.rules ${D}${sysconfdir}/polkit-1/rules.d/99-shutdown.rules
    fi

    install -d ${D}${libexecdir}
    install -m 755 ${WORKDIR}/watch_power_gpio  ${D}${libexecdir}
}
