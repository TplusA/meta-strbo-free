require conf/distro/include/partitions.inc

BOOT_PARTITION_MOUNTPOINT = "${MAINBOOT_PARTITION_MOUNTPOINT}"

require recipes-bsp/common/raspberrypi-firmware.inc
require bcm2835-bootfiles.inc

SUMMARY = "Broadcom boot loader configuration files for StrBo main system"

do_install:append() {
    echo "smsc95xx.macaddr=: dwc_otg.lpm_enable=0 console=ttyAMA0,115200 kgdboc=ttyAMA0,115200 root=${MAIN_KERNEL_ROOTFS_DEVICE} rootfstype=${MAINROOTFS_TYPE} rootwait" >${D}${BOOT_PARTITION_MOUNTPOINT}/cmdline.txt
}
