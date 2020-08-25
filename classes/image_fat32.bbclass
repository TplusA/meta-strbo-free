inherit image_types

FAT32_LOGICAL_SECTOR_SIZE ?= "512"

IMAGE_CMD_fat32 () {
    mkfs.vfat -S ${FAT32_LOGICAL_SECTOR_SIZE} ${EXTRA_IMAGECMD} -C ${IMGDEPLOYDIR}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.fat32 ${ROOTFS_SIZE}

    # mcopy won't strip the path, so we have to loop manually
    for f in $(ls ${IMAGE_ROOTFS})
    do
        mcopy -i ${IMGDEPLOYDIR}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.fat32 -s -Q ${IMAGE_ROOTFS}/${f} ::
    done
}

EXTRA_IMAGECMD_fat32 ?= ""

do_image_fat32[depends] += "dosfstools-native:do_populate_sysroot"
do_image_fat32[depends] += "mtools-native:do_populate_sysroot"

IMAGE_TYPES += "fat32"
