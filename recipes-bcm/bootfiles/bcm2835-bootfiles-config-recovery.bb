require conf/distro/include/partitions.inc

BOOT_PARTITION_MOUNTPOINT = "${RECOVERYBOOT_PARTITION_MOUNTPOINT}"

include recipes-bsp/common/raspberrypi-firmware.inc
require bcm2835-bootfiles.inc

SUMMARY = "Broadcom boot loader configuration files for StrBo recovery system"

PR = "r4"

do_install_append() {
    #
    # The rootfs archive is assumed to exist at this point.
    #
    # P.S.: I know this is a shitty solution. It is a stupid workaround for
    #       U-Boot which for some unknown reason cannot be convinced to extend
    #       the bootargs variable at runtime from within a script. We are
    #       speaking here about a trivial non-recursive variable expansion and
    #       assignment! Dear Lord...
    #
    # P.S.: Maybe storing the compressed rootfs in a U-Boot image and using the
    #       second bootm parameter would help.
    #
    ROOTFSSIZE=$(printf '0x%x' $(stat -L -c '%s' ${DEPLOY_DIR_IMAGE}/${RECOVERYROOTFS_INITRAMFS_IMAGE}-${MACHINE}.${RECOVERYROOTFS_TYPE}))

    echo "smsc95xx.macaddr=: dwc_otg.lpm_enable=0 console=ttyAMA0,115200 kgdboc=ttyAMA0,115200 initrd=0x04800000,${ROOTFSSIZE} rootfstype=ramfs rootwait" >${D}${BOOT_PARTITION_MOUNTPOINT}/cmdline.txt
}
