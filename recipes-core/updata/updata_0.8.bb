SUMMARY = "T+A system updater"
SECTION = "core"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
                    file://COPYING.GPLv3;md5=d32239bcb673463ab874e80d47fae504"

SRCREV = "91ed6f4c8c9ddf1c61ed8c975c238f77baa29472"

SRC_URI = " \
    git://git.tua.local/repo/UpdaTA;branch=master;protocol=http \
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
