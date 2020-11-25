SUMMARY = "T+A Streaming Board image for Raspberry Compute Module"

require conf/distro/include/partitions.inc

IMAGE_ROOTFS_SIZE = "${MAINROOTFS_PARTITION_SIZE}"
IMAGE_ROOTFS_EXTRA_SPACE_append = "${@bb.utils.contains("PACKAGE_INSTALL", "dnf", " - 102400", "" ,d)}"
IMAGE_FSTYPES += "${MAINROOTFS_TYPE}"
EXTRA_IMAGECMD_${MAINROOTFS_TYPE} ?= "${MAINROOTFS_MKFS_EXTRA}"

IMAGE_INSTALL = "packagegroup-strbo-main-base packagegroup-strbo-main-free packagegroup-strbo-main-extra"
IMAGE_INSTALL += "u-boot-rpi-main u-boot-rpi3-main"
IMAGE_FEATURES += "package-management"
IMAGE_LINGUAS = "en-us"
GLIBC_GENERATE_LOCALES = "en_US.UTF-8"

IMAGE_INSTALL += "${@bb.utils.contains("HAVE_DEVELOPER_ACCOUNT", "yes", "developer-account", "", d)}"

PACKAGE_EXCLUDE = " \
    taroon-src \
    taroon-dev \
    taroon-dbg \
    listbrokers-airable-src \
    listbrokers-airable-dev \
    listbrokers-airable-dbg \
"

PACKAGE_EXCLUDE_COMPLEMENTARY = "openssh"

LICENSE = "MIT"

PR = "r6"

inherit core-image

SYSTEMD_DEFAULT_TARGET = "strbo.target"
TOOLCHAIN_HOST_TASK_append = " \
    nativesdk-python3-modules \
    nativesdk-autorevision \
"

IMAGE_PREPROCESS_COMMAND_append = "remove_boot_files"

#
# Remove files installed by the u-boot-rpi-main package (and its dependencies).
#
# The deleted files will still be contained in the final image for the whole
# flash, but installed on a different file system, thus different (sub-)image.
# The sole reason for installing the packages (whose files are deleted here) on
# the main image is to let the package management system know about them. This
# way, upgrading these packages will work without playing any stupid tricks on
# the running target, such as giving "special" packages a "special" treatment.
#
remove_boot_files() {
    cd ${IMAGE_ROOTFS}
    rm -r bootpartm/overlays
    rm bootpartm/*
}
