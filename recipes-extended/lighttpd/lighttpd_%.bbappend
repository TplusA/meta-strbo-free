FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
PR := "r8"

do_install_append() {
    sed -i -e 's,^PIDFile=/var/run/,PIDFile=/run/,' ${D}${systemd_unitdir}/system/lighttpd.service
}
