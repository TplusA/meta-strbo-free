require conf/distro/include/partitions.inc
require u-boot-rpi.inc

BOOT_PARTITION_MOUNTPOINT = "${MAINBOOT_PARTITION_MOUNTPOINT}"

PR = "r9"

do_configure_prepend() {
    cat <<EOF >>${S}/include/config_distro_bootcmd.h

#ifndef _CONFIG_CMD_DISTRO_BOOTCMD_H_MORE
#ifdef CONFIG_BOOTCOMMAND
#undef CONFIG_BOOTCOMMAND
#endif /* CONFIG_BOOTCOMMAND */
#define CONFIG_BOOTCOMMAND "load mmc 0:${MAINBOOT_PARTITION_NUMBER} \${scriptaddr} /${BOOT_SCR}; source \${scriptaddr}"
#endif /* _CONFIG_CMD_DISTRO_BOOTCMD_H_MORE */
EOF
}

do_compile_append() {
    cat <<EOF >${WORKDIR}/boot.cmd
if test \${recovery_requested} -ne 0
then
    bootpart ${RECOVERY_KERNEL_DEVICE_UBOOT}
fi
fdt addr \${fdt_addr_r} && fdt get value bootargs /chosen bootargs
setenv mmcdev 0:${MAIN_KERNEL_DEVICE_UBOOT}
${MAIN_KERNEL_LOADCMD_UBOOT} mmc \${mmcdev} \${kernel_addr_r} ${MAIN_KERNEL_PATH_UBOOT}/${KERNEL_IMAGETYPE}
bootm \${kernel_addr_r} - \${fdt_addr_r}
EOF

    mkimage -A arm -O u-boot -T script -C none -n 'T+A Streaming Board Boot' -d ${WORKDIR}/boot.cmd ${BOOT_SCR}
}
