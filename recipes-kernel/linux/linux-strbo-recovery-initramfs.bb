require conf/distro/include/partitions.inc

DESCRIPTION = "T+A Streaming Board recovery system initramfs"
SECTION = "kernel"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

PR = "r3"

FILES_${PN} = "${RECOVERYBOOT_PARTITION_MOUNTPOINT}/init.${RECOVERYROOTFS_TYPE}"

do_install() {
    install -d ${D}${RECOVERYBOOT_PARTITION_MOUNTPOINT}
    install -m 0644 ${DEPLOY_DIR_IMAGE}/${RECOVERYROOTFS_INITRAMFS_IMAGE}-${MACHINE}.${RECOVERYROOTFS_TYPE} ${D}${RECOVERYBOOT_PARTITION_MOUNTPOINT}/init.${RECOVERYROOTFS_TYPE}
}
do_install[depends] += "strbo-recovery-image:do_image_complete"
