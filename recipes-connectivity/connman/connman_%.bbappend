require conf/distro/include/partitions.inc

FILESEXTRAPATHS:append := ":${THISDIR}/${PN}"

SRC_URI += " \
    file://connman_master-ee0046efd0.patch \
    file://0001-wifi-Fix-use-after-free-when-tethering-is-disabled.patch \
    file://0002-wifi-Fix-memory-leak.patch \
    file://0003-wifi-Fix-indentation.patch \
    file://0004-technology-Fix-memory-leak.patch \
    file://0001-systemd-Run-service-at-nice-level-10.patch \
    file://main.conf \
"

PR = "r3"

do_install:append() {
    install -d ${D}/${sysconfdir} ${D}/${sysconfdir}/connman
    install -m 644 ${WORKDIR}/main.conf ${D}/${sysconfdir}/connman

    install -d ${D}${localstatedir} ${D}${localstatedir}/lib
    ln -s ${MAINCONFIGFS_MOUNTPOINT}/connman     ${D}${localstatedir}/lib
    ln -s ${MAINCONFIGFS_MOUNTPOINT}/connman-vpn ${D}${localstatedir}/lib
}
