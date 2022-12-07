SUMMARY = "T+A Streaming Board recovery boot image for Raspberry Compute Module"

require conf/distro/include/partitions.inc

inherit bcm2835_boot_partition

ROOTFS_SIZE = "${RECOVERYBOOT_PARTITION_SIZE}"
IMAGE_ROOTFS_SIZE = "${RECOVERYBOOT_PARTITION_SIZE}"
IMAGE_ROOTFS_EXTRA_SPACE:append = "${@bb.utils.contains("PACKAGE_INSTALL", "dnf", " - 102400", "" ,d)}"
IMAGE_FSTYPES += "${RECOVERYBOOT_TYPE}"
EXTRA_IMAGECMD:${RECOVERYBOOT_TYPE} ?= "${RECOVERYBOOT_MKFS_EXTRA}"
INITRAMFS_IMAGE = "${RECOVERYROOTFS_INITRAMFS_IMAGE}"
INITRAMFS_FSTYPES = "${RECOVERYROOTFS_TYPE}"
BOOT_PARTITION_MOUNTPOINT = "${RECOVERYBOOT_PARTITION_MOUNTPOINT}"

IMAGE_INSTALL:append = " \
    strbo-release \
    u-boot-rpi-recovery \
    u-boot-rpi3-recovery \
    kernel-image \
    kernel-devicetree \
    linux-strbo-recovery-initramfs \
    bash \
"
IMAGE_LINGUAS = " "

IMAGE_PREPROCESS_COMMAND:append += "resolve_symlinks; "

resolve_symlinks () {
    cd ${IMAGE_ROOTFS}

    for f in $(find . -type l -maxdepth 1)
    do
        DEST=$(basename $(readlink $f))

        if test -z "$DEST"
        then
            bberror "Unable to resolve symlink \"$f\"."
        elif test ! -f "$DEST"
        then
            bberror "Symlink \"$f\" points to non-existent file \"$DEST\"."
        else
            bbnote "Replacing symlink \"$f\" with file \"$DEST\"."
            rm "$f"
            mv "$DEST" "$f"
        fi
    done
}
