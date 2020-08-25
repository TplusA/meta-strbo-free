SUMMARY = "U-Boot configuration for StrBo main system"
RDEPENDS_${PN} += "bcm2835-bootfiles-config-main"
UBOOT_CONFIG = "main7"
BOOT_SCR = "boot7.scr"
KERNEL_IMG = "kernel7.img"

require u-boot-main.inc
