FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://0001-journal-Run-at-nice-level-19.patch"

PACKAGECONFIG:remove = " \
    backlight \
    timesyncd \
    networkd \
    resolved \
    nss-resolve \
"
PACKAGECONFIG:append = "\
    coredump \
"

do_install:append() {
    sed -i -e 's,#DefaultEnvironment=,DefaultEnvironment=DBUS_SESSION_BUS_ADDRESS=unix:path=/tmp/strbo_bus_socket,' ${D}${sysconfdir}/systemd/system.conf
    sed -i -e 's,#MaxUse=,MaxUse=30M,' ${D}${sysconfdir}/systemd/coredump.conf
}
