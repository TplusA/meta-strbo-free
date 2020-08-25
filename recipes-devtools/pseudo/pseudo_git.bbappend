FILESEXTRAPATHS_append := ":${THISDIR}/${PN}"
SRC_URI += "file://0001-Fix-compilation-with-gcc-10.patch"
PR := "r1"
