FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PR = "r1"

SRC_URI += " \
    file://0001-linux-cm-Fds-can-be-0.patch \
    file://0002-all-Fix-minor-compiler-warnings.patch \
    file://0003-linux-cm-Fix-filtering-for-address-families.patch \
"
