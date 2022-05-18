# Partition scheme versioning.
#
# 1. Major version is increased whenever the size, location, and/or numbering
#    of partitions are/is changed.
# 2. Increasing the major version implies changing the minor version to 0.
# 3. Minor version is increased whenever any partition properties, such as file
#    system type, file system options, or volume label, have changed.
#
# Equal major version implies that partition tables are compatible and
# partitions with different minor versions can be mixed.
# Different major version implies need for repartitioning and may involve a
# risky update procedure.
PARTITION_LAYOUT_MAJOR_VERSION = "2"
PARTITION_LAYOUT_MINOR_VERSION = "0"


#
# Main system partitions
#

# 16 MiB for main boot partition
MAINBOOT_PARTITION_NUMBER          ?= "1"
MAINBOOT_PARTITION_SIZE            ?= "16384"
MAINBOOT_PARTITION_MOUNTPOINT      ?= "/bootpartm"
MAINBOOT_TYPE                      ?= "fat32"
MAINBOOT_MKFS_EXTRA                ?= "-n Boot"

# How U-Boot should load the main system's kernel
MAIN_KERNEL_LOADCMD_UBOOT          ?= "ext4load"
MAIN_KERNEL_DEVICE_UBOOT           ?= "7"
MAIN_KERNEL_PATH_UBOOT             ?= "boot"

# Linux root filesystem device name passed as kernel parameter
MAIN_KERNEL_ROOTFS_DEVICE          ?= "/dev/mmcblk0p7"

# 1024 MiB for main rootfs
MAINROOTFS_PARTITION_NUMBER        ?= "7"
MAINROOTFS_PARTITION_SIZE          ?= "1048576"
MAINROOTFS_TYPE                    ?= "ext4"
MAINROOTFS_MKFS_EXTRA              ?= "-i 4096 -m 0 -L Rootfs"

# 128 MiB for user settings
MAINCONFIGFS_PARTITION_NUMBER      ?= "5"
MAINCONFIGFS_PARTITION_SIZE        ?= "131072"
MAINCONFIGFS_TYPE                  ?= "ext4"
MAINCONFIGFS_MKFS_EXTRA            ?= "-i 4096 -m 0 -O quota -L Settings"
MAINCONFIGFS_MOUNTPOINT            ?= "${localstatedir}/local/etc"


#
# Recovery system partitions
#

# 128 MiB for recovery boot partition
# this one needs to be large enough to hold the kernel, the initramfs rootfs
# image, and Broadcom BLOBs (see also RECOVERYROOTFS_PARTITION_SIZE below)
RECOVERYBOOT_PARTITION_NUMBER      ?= "2"
RECOVERYBOOT_PARTITION_SIZE        ?= "131072"
RECOVERYBOOT_PARTITION_MOUNTPOINT  ?= "/bootpartr"
RECOVERYBOOT_TYPE                  ?= "fat32"
RECOVERYBOOT_MKFS_EXTRA            ?= "-n BootR"

# 64 MiB for recovery rootfs
RECOVERYROOTFS_PARTITION_SIZE      ?= "65536"
RECOVERYROOTFS_TYPE                ?= "cpio.xz"
RECOVERYROOTFS_MKFS_EXTRA          ?= "-i 4096 -m 0 -L RootfsR"
RECOVERYROOTFS_INITRAMFS_IMAGE     ?= "strbo-recovery-image"

# How U-Boot should load the recovery system's kernel
RECOVERY_KERNEL_LOADCMD_UBOOT      ?= "fatload"
RECOVERY_KERNEL_DEVICE_UBOOT       ?= "2"
RECOVERY_KERNEL_PATH_UBOOT         ?= ""

# Data for full recovery of a broken system to known state.
RECOVERYDATAFS_PARTITION_NUMBER    ?= "6"
RECOVERYDATAFS_PARTITION_SIZE      ?= "1048576"
RECOVERYDATAFS_TYPE                ?= "ext4"
RECOVERYDATAFS_MKFS_EXTRA          ?= "-i 4096 -m 0 -T largefile -L DataR"
RECOVERYDATAFS_MOUNTPOINT          ?= "/src"


#
# Shared partitions
#

# Spare partition
SPAREFS_PARTITION_NUMBER           ?= "8"
SPAREFS_TYPE                       ?= "ext4"
SPAREFS_MKFS_EXTRA                 ?= "-i 4096 -m 0 -O quota -L Data"
SPAREFS_MOUNTPOINT                 ?= "${localstatedir}/local/data"
