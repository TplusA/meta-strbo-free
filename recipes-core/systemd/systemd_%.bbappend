PACKAGECONFIG_remove = "timesyncd"
PACKAGECONFIG_append = " coredump"

PR = "r2"

do_install_append() {
    sed -i -e 's,#DefaultEnvironment=,DefaultEnvironment=DBUS_SESSION_BUS_ADDRESS=unix:path=/tmp/strbo_bus_socket,' ${D}${sysconfdir}/systemd/system.conf
}
