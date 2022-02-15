SUMMARY = "U-Boot configuration for StrBo main system"
RDEPENDS:${PN} += "bcm2835-bootfiles-config-main"
UBOOT_CONFIG = "main"
BOOT_SCR = "boot.scr"
KERNEL_IMG = "kernel.img"

require u-boot-main.inc
