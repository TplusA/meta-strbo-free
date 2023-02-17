# remove /etc/fstab, file is provided by either base-files-main or
# base-files-recovery
CONFFILES:${PN} = "$@{string.replace(CONFFILES:${PN}, '${sysconfdir}/fstab', '')}"

PR .= ".2"

BASEFILESISSUEINSTALL = "do_strbo_basefileissue"

do_strbo_basefileissue () {
    install -m 644 /dev/null ${D}${sysconfdir}/issue
    install -m 644 /dev/null ${D}${sysconfdir}/issue.net
    if [ -n "${DISTRO_NAME}" ]
    then
        echo "${DISTRO_NAME}" >${D}${sysconfdir}/issue
        echo "${DISTRO_NAME}" >${D}${sysconfdir}/issue.net
    fi
}

do_install:append () {
    rm ${D}${sysconfdir}/fstab
}
