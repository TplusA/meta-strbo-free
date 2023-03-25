SUMMARY = "T+A system updater"
SECTION = "core"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
                    file://COPYING.GPLv3;md5=d32239bcb673463ab874e80d47fae504"

SRCREV = "712126045d2c84115e33b3b04b6c4073b2d53bd5"

SRC_URI = " \
    git://github.com/TplusA/UpdaTA.git;branch=master;protocol=https \
    file://updata.service \
    file://updata.sudoers \
"

S = "${WORKDIR}/git"

RDEPENDS:${PN} += " \
    python3-requests \
    util-linux-mount \
    util-linux-umount \
    sudo \
"

FILES:${PN} += " \
    ${sysconfdir}/sudoers.d/50-updata \
    ${datadir}/${PN}/updata_system_update.template.sh \
    ${systemd_unitdir}/system/updata.service \
    ${systemd_unitdir}/system/system-update.target.wants/updata.service \
"

DIRFILES = "1"

SYSTEMD_SERVICE:${PN} = "updata.service"

USERADD_PACKAGES = "${PN}"
USERADD_PARAM:${PN} = "-r -N -g updata updata"
GROUPADD_PARAM:${PN} = "-r updata"
GROUPMEMS_PARAM:${PN} = ""

inherit allarch setuptools3 useradd

do_install:append() {
    install -d \
        ${D}${systemd_unitdir} \
        ${D}${systemd_unitdir}/system \
        ${D}${systemd_unitdir}/system/system-update.target.wants \
        ${D}${sysconfdir} \
        ${D}${sysconfdir}/sudoers.d \
        ${D}${datadir} \
        ${D}${datadir}/${PN}
    install -m 644 ${WORKDIR}/updata.service ${D}${systemd_unitdir}/system
    install -m 644 ${WORKDIR}/updata.sudoers ${D}${sysconfdir}/sudoers.d/50-updata
    install -m 644 ${S}/updata_system_update.template.sh ${D}${datadir}/${PN}

    ln -sf ../updata.service ${D}${systemd_unitdir}/system/system-update.target.wants/updata.service
    chown root:root ${D}${systemd_unitdir}/system/system-update.target.wants/updata.service
}
