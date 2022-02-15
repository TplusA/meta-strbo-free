DESCRIPTION = "Deactivated autoprobing and manual control tool for usb-storage"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING.MIT;md5=838c366f69b72c5df05c96dff79b35f2"

FILESPATH = "${FILE_DIRNAME}/usb-storage-manual"
SRC_URI += " \
    file://rootfs/etc/sudoers.d/60-usb-storage \
    file://rootfs/etc/usb-storage-manual \
    file://rootfs/etc/systemd/system/mounta.service.d/override.conf \
    file://rootfs/usr/share/named-usb-devices/front \
    file://rootfs/usr/share/named-usb-devices/rear \
    file://rootfs/usr/bin/usb-storage \
    file://COPYING.MIT \
"
S = "${WORKDIR}"

do_install:append() {
    cp -R --no-dereference --preserve=mode,links -v ${WORKDIR}/rootfs/* ${D}
}

inherit pkgconfig systemd

FILES:${PN} += "*"
