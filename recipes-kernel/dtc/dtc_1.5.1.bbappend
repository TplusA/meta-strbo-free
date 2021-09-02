FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
SRC_URI += " \
    file://0001-Remove-redundant-YYLOC-global-declaration.patch \
    file://0002-fdtdump-Fix-gcc11-warning.patch \
"
PR = "r2"
