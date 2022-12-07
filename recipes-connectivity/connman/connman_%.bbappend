require conf/distro/include/partitions.inc

FILESEXTRAPATHS:append := ":${THISDIR}/${PN}"

SRC_URI:remove = " \
    file://CVE-2022-32293_p1.patch \
    file://CVE-2022-32293_p2.patch \
    file://CVE-2022-32292.patch \
"

SRC_URI += " \
    file://connman_master-19789ae039.patch \
    file://0001-systemd-Run-service-at-nice-level-10.patch \
    file://main.conf \
"

PR = "r1"

do_install:append() {
    install -d ${D}/${sysconfdir} ${D}/${sysconfdir}/connman
    install -m 644 ${WORKDIR}/main.conf ${D}/${sysconfdir}/connman

    install -d ${D}${localstatedir} ${D}${localstatedir}/lib
    ln -s ${MAINCONFIGFS_MOUNTPOINT}/connman     ${D}${localstatedir}/lib
    ln -s ${MAINCONFIGFS_MOUNTPOINT}/connman-vpn ${D}${localstatedir}/lib
}
