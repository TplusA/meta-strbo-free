SUMMARY = "T+A package repository"
SECTION = "core"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI = " \
    file://strbo_base.repo \
    file://strbo_flavor.repo \
    file://strbo_dev.repo \
"

FILES:${PN} += " \
    ${sysconfdir}/yum.repos.d \
    ${sysconfdir}/dnf/vars \
"

DIRFILES = "1"

USERADD_PACKAGES = "${PN}"
USERADD_PARAM:${PN} = "-r -N -g updata updata"
GROUPADD_PARAM:${PN} = "-r updata"
GROUPMEMS_PARAM:${PN} = ""

inherit allarch useradd

do_install:append() {
    install -d \
        ${D}${sysconfdir} \
        ${D}${sysconfdir}/yum.repos.d \
        ${D}${sysconfdir}/dnf \
        ${D}${sysconfdir}/dnf/vars
    install -m 644 ${WORKDIR}/strbo_base.repo ${D}${sysconfdir}/yum.repos.d
    install -m 644 ${WORKDIR}/strbo_flavor.repo ${D}${sysconfdir}/yum.repos.d
    install -m 644 ${WORKDIR}/strbo_dev.repo ${D}${sysconfdir}/yum.repos.d

    for V in strbo_update_baseurl strbo_release_line strbo_flavor
    do
        install -m 664 -g updata /dev/null ${D}${sysconfdir}/dnf/vars/$V
    done
    echo "${DISTRO_LINE}" >${D}${sysconfdir}/dnf/vars/strbo_release_line
}

pkg_postinst:${PN} () {
    for V in strbo_base_enabled strbo_flavor_enabled
    do
        VARFILE=$D${sysconfdir}/dnf/vars/$V
        if [ ! -f $VARFILE ]
        then
            install -m 664 -g updata /dev/null $VARFILE
            echo 0 >$VARFILE
        fi
    done
    exit 0
}
