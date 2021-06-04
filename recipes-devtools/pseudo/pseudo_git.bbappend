FILESEXTRAPATHS_append := ":${THISDIR}/${PN}"
SRC_URI += " \
    file://0001-Fix-compilation-with-gcc-10.patch \
    file://0002-linux-portdefs.h-Fix-pseudo-to-work-with-glibc-2.33.patch \
"
PR := "r2"
