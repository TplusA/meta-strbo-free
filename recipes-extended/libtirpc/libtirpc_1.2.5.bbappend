FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
SRC_URI += "file://libtirpc-1.2.5-avoid-multiple-definition.patch"
PR = "r1"
