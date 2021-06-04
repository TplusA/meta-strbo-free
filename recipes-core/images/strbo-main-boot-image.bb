SUMMARY = "T+A Streaming Board boot image for Raspberry Compute Module"

PR = "r2"

require conf/distro/include/partitions.inc

inherit bcm2835_boot_partition

ROOTFS_SIZE = "${MAINBOOT_PARTITION_SIZE}"
IMAGE_ROOTFS_SIZE = "${MAINBOOT_PARTITION_SIZE}"
IMAGE_ROOTFS_EXTRA_SPACE_append = "${@bb.utils.contains("PACKAGE_INSTALL", "dnf", " - 102400", "" ,d)}"
IMAGE_FSTYPES += "${MAINBOOT_TYPE}"
EXTRA_IMAGECMD_${MAINBOOT_TYPE} ?= "${MAINBOOT_MKFS_EXTRA}"
BOOT_PARTITION_MOUNTPOINT = "${MAINBOOT_PARTITION_MOUNTPOINT}"

IMAGE_INSTALL_append = " \
    strbo-release \
    u-boot-rpi-main \
    u-boot-rpi3-main \
    kernel-devicetree \
    bash \
"
IMAGE_LINGUAS = " "
