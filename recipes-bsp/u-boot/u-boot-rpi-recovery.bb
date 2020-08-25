SUMMARY = "U-Boot configuration for StrBo recovery system"
RDEPENDS_${PN} += "bcm2835-bootfiles-config-recovery"
UBOOT_CONFIG = "recovery"
BOOT_SCR = "boot.scr"
KERNEL_IMG = "kernel.img"

require u-boot-recovery.inc
