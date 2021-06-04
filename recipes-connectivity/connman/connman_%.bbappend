require conf/distro/include/partitions.inc

FILESEXTRAPATHS_prepend := "${COREBASE}/meta/recipes-connectivity/${PN}/${PN}:"
FILESEXTRAPATHS_append := ":${THISDIR}/${PN}"

PR = "r1"

SRC_URI += "file://main.conf"

DEPENDS += "libmnl"

do_install_append() {
    install -d ${D}/${sysconfdir} ${D}/${sysconfdir}/connman
    install -m 644 ${WORKDIR}/main.conf ${D}/${sysconfdir}/connman

    install -d ${D}${localstatedir} ${D}${localstatedir}/lib
    ln -s ${MAINCONFIGFS_MOUNTPOINT}/connman     ${D}${localstatedir}/lib
    ln -s ${MAINCONFIGFS_MOUNTPOINT}/connman-vpn ${D}${localstatedir}/lib
}
