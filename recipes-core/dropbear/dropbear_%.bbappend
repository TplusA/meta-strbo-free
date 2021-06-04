PR = "r2"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

WRITE_KEYS_TO_TMPFS = '${@bb.utils.contains("IMAGE_FEATURES", "read-only-rootfs", "yes", "no", d)}'

SRC_URI += "file://allow_password \
            file://disallow_password"

DEFAULT_CONFIG = "${@bb.utils.contains("HAVE_DEVELOPER_ACCOUNT", "yes", "allow_password", "disallow_password", d)}"

do_install_append() {
    if test "x${WRITE_KEYS_TO_TMPFS}" = 'xyes'
    then
        rmdir ${D}${sysconfdir}/dropbear
        ln -s /tmp ${D}${sysconfdir}/dropbear
    fi

    install -m 0644 ${WORKDIR}/${DEFAULT_CONFIG} ${D}${sysconfdir}/default/dropbear
}
