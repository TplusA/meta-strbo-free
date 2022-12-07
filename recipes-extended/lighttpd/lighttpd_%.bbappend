FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

do_install:append() {
    sed -i -e 's,^PIDFile=/var/run/,PIDFile=/run/,' ${D}${systemd_unitdir}/system/lighttpd.service
}
