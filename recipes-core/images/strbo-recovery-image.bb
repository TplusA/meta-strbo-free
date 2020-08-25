SUMMARY = "Recovery system for T+A Streaming Board for Raspberry Compute Module"

require conf/distro/include/partitions.inc

IMAGE_ROOTFS_SIZE = "${RECOVERYROOTFS_PARTITION_SIZE}"
IMAGE_ROOTFS_EXTRA_SPACE_append = "${@bb.utils.contains("PACKAGE_INSTALL", "dnf", " - 102400", "" ,d)}"
IMAGE_FSTYPES += "${RECOVERYROOTFS_TYPE}"
EXTRA_IMAGECMD_${RECOVERYROOTFS_TYPE} ?= "${RECOVERYROOTFS_MKFS_EXTRA}"

IMAGE_INSTALL = "packagegroup-strbo-recovery"
IMAGE_FEATURES += "read-only-rootfs"
IMAGE_LINGUAS = "en-us"
GLIBC_GENERATE_LOCALES = "en_US.UTF-8"

PACKAGE_EXCLUDE_COMPLEMENTARY = "openssh"

LICENSE = "MIT"

PR = "r2"

inherit core-image

IMAGE_PREPROCESS_COMMAND_append = "remove_boot_files"

#
# Remove files installed by the u-boot-rpi-recovery package (and its
# dependencies).
#
# The deleted files will still be contained in the final image for the whole
# flash, but installed on a different file system, thus different (sub-)image.
# The sole reason for installing the packages (whose files are deleted here)
# on
# the recovery image is to let the package management system know about them.
# This way, upgrading these packages will work without playing any stupid
# tricks on the running target, such as giving "special" packages a "special"
# treatment.
#
remove_boot_files() {
    cd ${IMAGE_ROOTFS}
    rm bootpartr/*
}
