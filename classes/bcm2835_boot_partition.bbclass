inherit image
inherit image_fat32

IMAGE_FEATURES = ""
EXTRA_IMAGE_FEATURES = ""
IMAGE_LINGUAS = " "
PACKAGE_CLASSES = ""
IMAGE_ROOTFS_REMOVE_FILES ?= ""

LICENSE = "MIT"

IMAGE_PREPROCESS_COMMAND_append = "move_release_files; delete_clutter; move_boot_files; "

delete_clutter() {
    if test "x${IMAGE_ROOTFS}" != 'x' && test "$(basename ${IMAGE_ROOTFS})" != '/'
    then
        for d in bin dev etc home lib media mnt proc run sbin sys tmp usr var
        do
            rm -rf "${IMAGE_ROOTFS}/${d}"
        done
    fi

    if test "x${IMAGE_ROOTFS_REMOVE_FILES}" != x
    then
        for f in ${IMAGE_ROOTFS_REMOVE_FILES}
        do
            rm -f "${IMAGE_ROOTFS}/${f}"
        done
    fi
}

move_boot_files() {
    mv ${IMAGE_ROOTFS}${BOOT_PARTITION_MOUNTPOINT}/* ${IMAGE_ROOTFS}
    rmdir ${IMAGE_ROOTFS}${BOOT_PARTITION_MOUNTPOINT}

    if test -d ${IMAGE_ROOTFS}/boot
    then
        if test "x$(ls ${IMAGE_ROOTFS}/boot)" != 'x'
        then
            mv ${IMAGE_ROOTFS}/boot/* ${IMAGE_ROOTFS}
        fi
        rmdir ${IMAGE_ROOTFS}/boot
    fi
}

move_release_files() {
    mv ${IMAGE_ROOTFS}${nonarch_libdir}/strbo-release ${IMAGE_ROOTFS}
}
