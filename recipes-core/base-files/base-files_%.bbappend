# remove /etc/fstab, file is provided by either base-files-main or
# base-files-recovery
CONFFILES_${PN} = "$@{string.replace(CONFFILES_${PN}, '${sysconfdir}/fstab', '')}"

PR .= ".1"

do_install_append () {
    rm ${D}${sysconfdir}/fstab
}
