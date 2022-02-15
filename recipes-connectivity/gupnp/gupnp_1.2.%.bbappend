FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
    file://0002-all-Fix-minor-compiler-warnings.patch \
    file://0003-linux-cm-Fix-filtering-for-address-families.patch \
"
