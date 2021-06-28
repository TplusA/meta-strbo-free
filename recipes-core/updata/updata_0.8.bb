SUMMARY = "T+A system updater"
SECTION = "core"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
                    file://COPYING.GPLv3;md5=d32239bcb673463ab874e80d47fae504"

SRCREV = "91ed6f4c8c9ddf1c61ed8c975c238f77baa29472"
PR = "r0"

SRC_URI = " \
    git://git.tua.local/repo/UpdaTA;branch=master;protocol=http \
    file://strbo_base.repo \
    file://strbo_flavor.repo \
    file://strbo_dev.repo \
    file://updata.service \
    file://updata.sudoers \
"

S = "${WORKDIR}/git"

RDEPENDS_${PN} += " \
    python3-requests \
    util-linux-mount \
    util-linux-umount \
    sudo \
"

FILES_${PN} += " \
    ${sysconfdir}/yum.repos.d \
    ${sysconfdir}/dnf/vars \
    ${sysconfdir}/sudoers.d/50-updata \
    ${datadir}/${PN}/updata_system_update.template.sh \
    ${systemd_unitdir}/system/updata.service \
    ${systemd_unitdir}/system/system-update.target.wants/updata.service \
"

DIRFILES = "1"

SYSTEMD_SERVICE_${PN} = "updata.service"

USERADD_PACKAGES = "${PN}"
USERADD_PARAM_${PN} = "-r -N -g updata updata"
GROUPADD_PARAM_${PN} = "-r updata"

inherit allarch setuptools3 useradd

do_install_append() {
    install -d \
        ${D}${systemd_unitdir} \
        ${D}${systemd_unitdir}/system \
        ${D}${systemd_unitdir}/system/system-update.target.wants \
        ${D}${sysconfdir} \
        ${D}${sysconfdir}/yum.repos.d \
        ${D}${sysconfdir}/dnf \
        ${D}${sysconfdir}/dnf/vars \
        ${D}${sysconfdir}/sudoers.d \
        ${D}${datadir} \
        ${D}${datadir}/${PN}
    install -m 644 ${WORKDIR}/updata.service ${D}${systemd_unitdir}/system
    install -m 644 ${WORKDIR}/strbo_base.repo ${D}${sysconfdir}/yum.repos.d
    install -m 644 ${WORKDIR}/strbo_flavor.repo ${D}${sysconfdir}/yum.repos.d
    install -m 644 ${WORKDIR}/strbo_dev.repo ${D}${sysconfdir}/yum.repos.d
    install -m 644 ${WORKDIR}/updata.sudoers ${D}${sysconfdir}/sudoers.d/50-updata
    install -m 644 ${S}/updata_system_update.template.sh ${D}${datadir}/${PN}

    ln -sf ../updata.service ${D}${systemd_unitdir}/system/system-update.target.wants/updata.service
    chown root:root ${D}${systemd_unitdir}/system/system-update.target.wants/updata.service

    for V in strbo_update_baseurl strbo_release_line strbo_flavor strbo_flavor_enabled
    do
        install -m 664 -g updata /dev/null ${D}${sysconfdir}/dnf/vars/$V
    done
    echo "${DISTRO_LINE}" >${D}${sysconfdir}/dnf/vars/strbo_release_line
    echo 0 >${D}${sysconfdir}/dnf/vars/strbo_flavor_enabled
}
