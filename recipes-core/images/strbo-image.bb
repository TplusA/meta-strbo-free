SUMMARY = "Full eMMC image for T+A Streaming Board for Raspberry Compute Module"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

IMAGE_FEATURES[validitems] += "tools-profile"
IMAGE_LINGUAS = " "

require conf/distro/include/partitions.inc

inherit nopackages

###
### Image dependencies
###
DEPENDS = "parted-native"

COMPATIBLE_MACHINE = "^rpi$"
PACKAGE_ARCH = "${MACHINE_ARCH}"

###
### Flash partitioning
###
EMMC_IMAGE_FILENAME = "${IMAGE_NAME}.emmc.image"
EMMC_IMAGE = "${DEPLOY_DIR_IMAGE}/${EMMC_IMAGE_FILENAME}"
EMMC_TOTAL_SPACE ?= "3817472"

PART_BOOT_MAIN_NAME             = "${DEPLOY_DIR_IMAGE}/strbo-main-boot-image-${MACHINE}.${MAINBOOT_TYPE}"
PART_BOOT_MAIN_SPACE            = "${MAINBOOT_PARTITION_SIZE}"
PART_BOOT_MAIN_TYPE             = "${MAINBOOT_TYPE}"

PART_BOOT_RECOVERY_NAME         = "${OTHER_DEPLOY_DIR_IMAGE}/strbo-recovery-boot-image-${MACHINE}.${RECOVERYBOOT_TYPE}"
PART_BOOT_RECOVERY_SPACE        = "${RECOVERYBOOT_PARTITION_SIZE}"
PART_BOOT_RECOVERY_TYPE         = "${RECOVERYBOOT_TYPE}"

PART_USER_SETTINGS_SPACE        = "${MAINCONFIGFS_PARTITION_SIZE}"
PART_USER_SETTINGS_TYPE         = "${MAINCONFIGFS_TYPE}"

PART_DATA_RECOVERY_NAME         = "${OTHER_DEPLOY_DIR_IMAGE}/strbo-recovery-data-image-${MACHINE}.${RECOVERYDATAFS_TYPE}"
PART_DATA_RECOVERY_SPACE        = "${RECOVERYDATAFS_PARTITION_SIZE}"
PART_DATA_RECOVERY_TYPE         = "${RECOVERYDATAFS_TYPE}"

PART_ROOTFS_MAIN_NAME           = "${DEPLOY_DIR_IMAGE}/strbo-main-image-${MACHINE}.${MAINROOTFS_TYPE}"
PART_ROOTFS_MAIN_SPACE          = "${MAINROOTFS_PARTITION_SIZE}"
PART_ROOTFS_MAIN_TYPE           = "${MAINROOTFS_TYPE}"

PART_DATA_MAIN_TYPE             = "${SPAREFS_TYPE}"

PART_DATA_JUNK_NAME             = "${DEPLOY_DIR_IMAGE}/strbo-junk-image.bin"
PART_DATA_JUNK_SPACE            = "${DEVICE_ID_PARTITION_SIZE}"

# Partitions should be aligned to erase block size for good write performance,
# which means 512 kiB on Samsungs's eMMC as used on the Raspberry Compute
# Module.
#
# There is another reason to align partitions: writing to the first few
# kilobytes of the first primary partition and to those of any logical
# partition always erases the partition information on the flash because
# writing usually implies erasing a whole erase block. This should be
# completely transparent and is probably told to be safe, but we don't want to
# put ourselves at risk here and do the alignment for ALL partitions.
#
# This value is specified in sectors (512 bytes).
PARTITION_ALIGNMENT ?= "1024"

###
### How to build the full image
###

IMAGEDATESTAMP = "${@time.strftime('%Y.%m.%d',time.gmtime())}"

do_emmc_image() {
    dd if=/dev/zero of=${EMMC_IMAGE} bs=1024 count=0 seek=${EMMC_TOTAL_SPACE} status=none

    # Space used for regular partitions
    REDUCED_EMMC_TOTAL_SPACE=$(expr ${EMMC_TOTAL_SPACE} - ${PART_DATA_JUNK_SPACE})

    # Partition table
    parted -s ${EMMC_IMAGE} mklabel msdos

    # Boot partition (primary)
    PART_SIZE=$(expr ${PART_BOOT_MAIN_SPACE} \* 2)
    PART_END=$(expr ${PARTITION_ALIGNMENT} - 1)
    PART_OFFSET=$(expr ${PART_END} + 1)
    PART_END=$(expr ${PART_OFFSET} + ${PART_SIZE} - 1)
    parted -s ${EMMC_IMAGE} unit s mkpart primary ${PART_BOOT_MAIN_TYPE} ${PART_OFFSET} ${PART_END}
    parted -s ${EMMC_IMAGE} set 1 boot on
    dd if=${PART_BOOT_MAIN_NAME} of=${EMMC_IMAGE} bs=512 count=${PART_SIZE} seek=${PART_OFFSET} conv=notrunc status=none

    # Boot partition for recovery system (primary)
    PART_SIZE=$(expr ${PART_BOOT_RECOVERY_SPACE} \* 2)
    PART_OFFSET=$(expr ${PART_END} + 1)
    PART_END=$(expr ${PART_OFFSET} + ${PART_SIZE} - 1)
    parted -s ${EMMC_IMAGE} unit s mkpart primary ${PART_BOOT_RECOVERY_TYPE} ${PART_OFFSET} ${PART_END}
    dd if=${PART_BOOT_RECOVERY_NAME} of=${EMMC_IMAGE} bs=512 count=${PART_SIZE} seek=${PART_OFFSET} conv=notrunc status=none

    # Extended partition spanning nearly the rest of the eMMC
    PART_OFFSET=$(expr ${PART_END} + 1)
    PART_END=$(expr ${REDUCED_EMMC_TOTAL_SPACE} \* 2 - 1)
    parted -s ${EMMC_IMAGE} unit s mkpart extended ${PART_OFFSET} ${PART_END}
    EXTENDED_PARTITION_OFFSET=${PART_OFFSET}
    EXTENDED_PARTITION_END=${PART_END}

    # Junk data partition
    PART_OFFSET=$(expr ${PART_END} + 1)
    parted -s ${EMMC_IMAGE} -- unit s mkpart primary ${PART_OFFSET} -1s

    # User settings (logical), left empty and formatted at runtime
    PART_SIZE=$(expr ${PART_USER_SETTINGS_SPACE} \* 2)
    PART_OFFSET=$(expr ${EXTENDED_PARTITION_OFFSET} + ${PARTITION_ALIGNMENT})
    PART_END=$(expr ${PART_OFFSET} + ${PART_SIZE} - 1)
    parted -s ${EMMC_IMAGE} unit s mkpart logical ${PART_USER_SETTINGS_TYPE} ${PART_OFFSET} ${PART_END}

    # Recovery data (logical)
    PART_SIZE=$(expr ${PART_DATA_RECOVERY_SPACE} \* 2)
    PART_OFFSET=$(expr ${PART_END} + ${PARTITION_ALIGNMENT} + 1)
    PART_END=$(expr ${PART_OFFSET} + ${PART_SIZE} - 1)
    parted -s ${EMMC_IMAGE} unit s mkpart logical ${PART_DATA_RECOVERY_TYPE} ${PART_OFFSET} ${PART_END}
    dd if=${PART_DATA_RECOVERY_NAME} of=${EMMC_IMAGE} bs=512 count=${PART_SIZE} seek=${PART_OFFSET} conv=notrunc status=none

    # Main root filesystem (logical)
    PART_SIZE=$(expr ${PART_ROOTFS_MAIN_SPACE} \* 2)
    PART_OFFSET=$(expr ${PART_END} + ${PARTITION_ALIGNMENT} + 1)
    PART_END=$(expr ${PART_OFFSET} + ${PART_SIZE} - 1)
    parted -s ${EMMC_IMAGE} unit s mkpart logical ${PART_ROOTFS_MAIN_TYPE} ${PART_OFFSET} ${PART_END}
    dd if=${PART_ROOTFS_MAIN_NAME} of=${EMMC_IMAGE} bs=512 count=${PART_SIZE} seek=${PART_OFFSET} conv=notrunc status=none

    # Spare data filesystem (logical), left empty and formatted at runtime
    PART_OFFSET=$(expr ${PART_END} + ${PARTITION_ALIGNMENT} + 1)
    parted -s ${EMMC_IMAGE} unit s mkpart logical ${PART_DATA_MAIN_TYPE} ${PART_OFFSET} ${EXTENDED_PARTITION_END}

    parted -s ${EMMC_IMAGE} unit s print free
}

addtask emmc_image after do_prepare_recipe_sysroot before do_build
do_emmc_image[deptask] += "do_rootfs"
