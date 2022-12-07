SUMMARY = "T+A Streaming Board recovery data image for Raspberry Compute Module"

require conf/distro/include/partitions.inc

inherit image

ROOTFS_SIZE = "${RECOVERYDATAFS_PARTITION_SIZE}"
IMAGE_ROOTFS_SIZE = "${RECOVERYDATAFS_PARTITION_SIZE}"
IMAGE_ROOTFS_EXTRA_SPACE:append = "${@bb.utils.contains("PACKAGE_INSTALL", "dnf", " - 102400", "" ,d)}"
IMAGE_FSTYPES += "${RECOVERYDATAFS_TYPE}"
EXTRA_IMAGECMD:${RECOVERYDATAFS_TYPE} ?= "${RECOVERYDATAFS_MKFS_EXTRA}"

IMAGE_INSTALL:append = "bash strbo-release"

EXTRA_IMAGE_FEATURES = ""
IMAGE_LINGUAS = " "
PACKAGE_CLASSES = ""

LICENSE = "MIT"

DEPENDS = "xz-native"

PART_BOOT = "${OTHER_DEPLOY_DIR_IMAGE}/strbo-main-boot-image-${MACHINE}.${MAINBOOT_TYPE}"
PART_ROOTFS = "${OTHER_DEPLOY_DIR_IMAGE}/strbo-main-image-${MACHINE}.${MAINROOTFS_TYPE}"
DESTDIR = "${IMAGE_ROOTFS}/images"

IMAGE_PREPROCESS_COMMAND:append = "delete_clutter; "
ROOTFS_POSTPROCESS_COMMAND:append = "more_rootfs; "

delete_clutter() {
    if test "x${IMAGE_ROOTFS}" != 'x' && test "$(basename ${IMAGE_ROOTFS})" != '/'
    then
        for d in bin boot dev etc home lib media mnt proc run sbin sys tmp usr var
        do
            rm -rf "${IMAGE_ROOTFS}/${d}"
        done
    fi
}

more_rootfs() {
    install -d ${DESTDIR}
    xz -T 0 -c ${PART_BOOT}   >${DESTDIR}/${MAINBOOT_PARTITION_NUMBER}.bin
    xz -T 0 -c ${PART_ROOTFS} >${DESTDIR}/${MAINROOTFS_PARTITION_NUMBER}.bin
    mv ${IMAGE_ROOTFS}${nonarch_libdir}/strbo-release ${DESTDIR}
    (cd ${DESTDIR} && sha256sum * >SHA256SUMS)
}
