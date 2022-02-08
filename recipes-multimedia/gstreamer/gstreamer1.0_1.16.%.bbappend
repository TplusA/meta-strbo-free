FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PR = "r2"

SRC_URI += " \
    file://0001-Applied-patch-created-by-Carlos-Rafael-Giani.patch \
"
