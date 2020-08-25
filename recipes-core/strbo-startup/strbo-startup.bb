SUMMARY = "T+A Streaming Board start-up scripts"
SECTION = "base"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI = " \
    file://strbo.target \
    file://strbo-startup.service \
    file://recovery.target \
    file://recovery-prepare.service \
    file://startup.init \
    file://format-fs \
    file://flash.rules \
"

S = "${WORKDIR}"

PR = "r16"

INITSCRIPT_NAME = "startup"
INITSCRIPT_PARAMS = "start 30 S ."

SYSTEMD_SERVICE_${PN} = "strbo-startup.service"

DEPENDS_append = " update-rc.d-native"
RDEPENDS_${PN} += "gawk sed util-linux strbo-systeminfo-main"
RDEPENDS_${PN} += "quota"

USERADD_PACKAGES = "${PN}"
USERADD_PARAM_${PN} = "-r -N -g strbo strbo"
GROUPADD_PARAM_${PN} = "-r strbo"

inherit allarch update-rc.d systemd useradd

do_install() {
    # SysV
    if ${@bb.utils.contains('DISTRO_FEATURES','sysvinit','true','false',d)}
    then
        install -d ${D}${sysconfdir} ${D}${sysconfdir}/init.d
        install -m 755 ${WORKDIR}/startup.init ${D}${sysconfdir}/init.d/startup
    fi

    # systemd
    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}
    then
        install -d ${D}${systemd_unitdir} ${D}${systemd_unitdir}/system ${D}${libexecdir}

        for f in strbo.target strbo-startup.service recovery.target recovery-prepare.service
        do
            install -m 644 ${WORKDIR}/${f} ${D}${systemd_unitdir}/system
        done

        install -m 755 ${WORKDIR}/format-fs ${D}${libexecdir}
    fi

    install -d ${D}${sysconfdir} ${D}${sysconfdir}/udev ${D}${sysconfdir}/udev/rules.d
    install -m 644 ${WORKDIR}/flash.rules ${D}${sysconfdir}/udev/rules.d
}

# need to append and specialize because simply adding recovery-prepare.service
# to SYSTEMD_SERVICE_${PN} would cause the postinst script to restart the
# service, resulting in reboot during upgrade
systemd_postinst_append() {
if type systemctl >/dev/null 2>/dev/null; then
	systemctl $OPTS ${SYSTEMD_AUTO_ENABLE} recovery-prepare.service
fi
systemctl $OPTS mask systemd-quotacheck.service
systemctl $OPTS mask quotaon.service
}

systemd_prerm_append() {
	systemctl $OPTS disable recovery-prepare.service
}

FILES_${PN} += " \
    ${systemd_unitdir}/system/strbo.target \
    ${systemd_unitdir}/system/strbo-startup.service \
    ${systemd_unitdir}/system/recovery.target \
    ${systemd_unitdir}/system/recovery-prepare.service \
    ${libexecdir}/format-fs \
    ${sysconfdir}/udev/rules.d/flash.rules \
"
