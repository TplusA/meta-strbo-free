require ${COREBASE}/meta/recipes-bsp/u-boot/u-boot_2020.01.bb

FILESEXTRAPATHS_prepend = "${COREBASE}/meta/recipes-bsp/u-boot/files:"

PROVIDES = ""

SRC_URI += " \
        file://0001-New-command-bootpart-to-reboot-into-selected-partiti.patch \
        file://0002-Configure-pin-47-as-input-at-board-init-time.patch \
        file://0003-Set-GPIO-28-to-high-level-at-board-init-time.patch \
        file://0004-Wait-for-mains-power-before-booting-481.patch \
        file://0005-rpi-Remove-bcm2835-video-driver-the-hard-way.patch \
        file://0006-Remove-redundant-YYLOC-global-declaration.patch \
        file://0007-Alternative-way-to-trigger-system-recovery-472.patch \
"

SRC_URI += " \
        file://strbo_main_defconfig \
        file://strbo_main7_defconfig \
        file://strbo_recovery_defconfig \
        file://strbo_recovery7_defconfig \
"

DEPENDS += "u-boot-mkimage-native"
RDEPENDS_${PN}_remove = "${PN}-env"

UBOOT_MACHINE = ""
UBOOT_CONFIG[main] = "strbo_main_defconfig"
UBOOT_CONFIG[main7] = "strbo_main7_defconfig"
UBOOT_CONFIG[recovery] = "strbo_recovery_defconfig"
UBOOT_CONFIG[recovery7] = "strbo_recovery7_defconfig"

FILES_${PN} = " \
    ${BOOT_PARTITION_MOUNTPOINT}/${KERNEL_IMG} \
    ${BOOT_PARTITION_MOUNTPOINT}/${BOOT_SCR} \
"

DIRFILES = "1"

do_configure_prepend() {
    for f in strbo_main_defconfig strbo_main7_defconfig strbo_recovery_defconfig strbo_recovery7_defconfig
    do
        install -m 0644 ${WORKDIR}/${f} ${S}/configs/ || die "U-Boot configuration ${f} not available."
    done
}

do_install() {
    install -d ${D}${BOOT_PARTITION_MOUNTPOINT}

    # for-loop because there is a leading space in UBOOT_MACHINE...
    for cfg in ${UBOOT_MACHINE}
    do
        install -m 644 ${B}/${cfg}/${UBOOT_BINARY} ${D}${BOOT_PARTITION_MOUNTPOINT}/${KERNEL_IMG}
    done

    install -m 0644 ${BOOT_SCR} ${D}${BOOT_PARTITION_MOUNTPOINT}
}
