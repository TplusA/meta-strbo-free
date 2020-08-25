SUMMARY = "T+A system updater"
SECTION = "core"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
                    file://COPYING.GPLv3;md5=d32239bcb673463ab874e80d47fae504"

SRCREV = "81c572a5150d0c6af0848257adac1c2c85947ad0"
PR = "r0"

SRC_URI = " \
    git://git.tua.local/repo/UpdaTA;branch=master;protocol=http \
    file://strbo_base.repo \
    file://strbo_flavor.repo \
    file://strbo_dev.repo \
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
"

DIRFILES = "1"

USERADD_PACKAGES = "${PN}"
USERADD_PARAM_${PN} = "-r -N -g updata updata"
GROUPADD_PARAM_${PN} = "-r updata"

inherit allarch setuptools3 useradd

do_install_append() {
    install -d \
        ${D}${sysconfdir} \
        ${D}${sysconfdir}/yum.repos.d \
        ${D}${sysconfdir}/dnf \
        ${D}${sysconfdir}/dnf/vars \
        ${D}${sysconfdir}/sudoers.d \
        ${D}${datadir} \
        ${D}${datadir}/${PN}
    install -m 644 ${WORKDIR}/strbo_base.repo ${D}${sysconfdir}/yum.repos.d
    install -m 644 ${WORKDIR}/strbo_flavor.repo ${D}${sysconfdir}/yum.repos.d
    install -m 644 ${WORKDIR}/strbo_dev.repo ${D}${sysconfdir}/yum.repos.d
    install -m 644 ${WORKDIR}/updata.sudoers ${D}${sysconfdir}/sudoers.d/50-updata
    install -m 644 ${S}/updata_system_update.template.sh ${D}${datadir}/${PN}

    for V in strbo_update_baseurl strbo_release_line strbo_flavor strbo_flavor_enabled
    do
        install -m 664 -g updata /dev/null ${D}${sysconfdir}/dnf/vars/$V
    done
    echo "${DISTRO_LINE}" >${D}${sysconfdir}/dnf/vars/strbo_release_line
    echo 0 >${D}${sysconfdir}/dnf/vars/strbo_flavor_enabled
}
