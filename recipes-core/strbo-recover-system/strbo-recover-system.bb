SUMMARY = "T+A Streaming Board system recovery scripts"
SECTION = "base"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI = " \
    file://strbo-recovery.service \
    file://recover-system \
"

S = "${WORKDIR}"

PR = "r2"

SYSTEMD_SERVICE_${PN} = "strbo-recovery.service"

RDEPENDS_${PN} += "util-linux strbo-systeminfo-recovery"

inherit allarch systemd

do_install() {
    # systemd
    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}
    then
        install -d ${D}${systemd_unitdir} ${D}${systemd_unitdir}/system ${D}${libexecdir}

        install -m 644 ${WORKDIR}/strbo-recovery.service ${D}${systemd_unitdir}/system
        install -m 755 ${WORKDIR}/recover-system ${D}${libexecdir}
    fi
}

FILES_${PN} += " \
    ${systemd_unitdir}/system/strbo-recovery.service \
    ${libexecdir}/recover-system \
"
