require conf/distro/include/partitions.inc

FILESEXTRAPATHS:append := ":${THISDIR}/${PN}"

SRC_URI += " \
    file://0001-systemd-Run-service-at-nice-level-10.patch \
    file://main.conf \
"

do_install:append() {
    install -d ${D}/${sysconfdir} ${D}/${sysconfdir}/connman
    install -m 644 ${WORKDIR}/main.conf ${D}/${sysconfdir}/connman

    install -d ${D}${localstatedir} ${D}${localstatedir}/lib
    ln -s ${MAINCONFIGFS_MOUNTPOINT}/connman     ${D}${localstatedir}/lib
    ln -s ${MAINCONFIGFS_MOUNTPOINT}/connman-vpn ${D}${localstatedir}/lib
}
