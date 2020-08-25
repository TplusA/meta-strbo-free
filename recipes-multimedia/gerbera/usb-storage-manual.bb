DESCRIPTION = "Deactivated autoprobing and manual control tool for usb-storage"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING.MIT;md5=838c366f69b72c5df05c96dff79b35f2"

FILESPATH = "${FILE_DIRNAME}/usb-storage-manual"
SRC_URI += "file://*"
S = "${WORKDIR}"

do_install_append() {
    cp -R --no-dereference --preserve=mode,links -v ${WORKDIR}/rootfs/* ${D}
}

inherit pkgconfig systemd

FILES_${PN} += "*"
