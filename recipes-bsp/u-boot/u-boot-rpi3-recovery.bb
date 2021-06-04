SUMMARY = "U-Boot configuration for StrBo recovery system"
RDEPENDS_${PN} += "bcm2835-bootfiles-config-recovery"
UBOOT_CONFIG = "recovery7"
BOOT_SCR = "boot7.scr"
KERNEL_IMG = "kernel7.img"

require u-boot-recovery.inc
