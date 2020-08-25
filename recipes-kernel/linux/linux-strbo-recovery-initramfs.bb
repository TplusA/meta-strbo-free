require conf/distro/include/partitions.inc

DESCRIPTION = "T+A Streaming Board recovery system initramfs"
SECTION = "kernel"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

PR = "r2"

FILES_${PN} = "${RECOVERYBOOT_PARTITION_MOUNTPOINT}/init.${RECOVERYROOTFS_TYPE}"

do_install() {
    install -d ${D}${RECOVERYBOOT_PARTITION_MOUNTPOINT}
    install -m 0644 ${DEPLOY_DIR_IMAGE}/${RECOVERYROOTFS_INITRAMFS_IMAGE}-${MACHINE}.${RECOVERYROOTFS_TYPE} ${D}${RECOVERYBOOT_PARTITION_MOUNTPOINT}/init.${RECOVERYROOTFS_TYPE}
}
do_install[depends] += "strbo-recovery-image:do_image_complete"

pkg_preinst_${PN} () {
if [ x"$D" = x ]; then mount -o remount,rw ${RECOVERYBOOT_PARTITION_MOUNTPOINT}; fi
}

pkg_postinst_${PN} () {
if [ x"$D" = x ]; then mount -o remount,ro ${RECOVERYBOOT_PARTITION_MOUNTPOINT}; fi
}

pkg_prerm_${PN} () {
if [ x"$D" = x ]; then mount -o remount,rw ${RECOVERYBOOT_PARTITION_MOUNTPOINT}; fi
}

pkg_postrm_${PN} () {
if [ x"$D" = x ]; then mount -o remount,ro ${RECOVERYBOOT_PARTITION_MOUNTPOINT}; fi
}
