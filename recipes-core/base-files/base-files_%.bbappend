# remove /etc/fstab, file is provided by either base-files-main or
# base-files-recovery
CONFFILES:${PN} = "$@{string.replace(CONFFILES:${PN}, '${sysconfdir}/fstab', '')}"

PR .= ".1"

do_install:append () {
    rm ${D}${sysconfdir}/fstab
}
