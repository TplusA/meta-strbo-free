# File is based on linux-raspberrypi.inc from Raspberry Pi BSP

KERNEL_DEVICETREE := "bcm2708-rpi-cm.dtb bcm2710-rpi-cm3.dtb"

PR = "${STRBO_KERNEL_PR}"
PE = "1"
PV = "${LINUX_VERSION}+git${SRCPV}"

inherit kernel siteinfo
require recipes-kernel/linux/linux-yocto.inc

SECTION = "kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = " \
    file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46 \
    file://LICENSES/preferred/GPL-2.0;md5=e6a75371ba4d16749254a51215d13f97 \
    file://LICENSES/exceptions/Linux-syscall-note;md5=6b0dff741019b948dfe290c05d6f361c \
"
COMPATIBLE_MACHINE = "^rpi$"
UDEV_GE_141 ?= "1"

KERNEL_DEFCONFIG_raspberrypi = "strbo_cm_${LINUX_VERSION}.config"

KERNEL_EXTRA_ARGS += "LOADADDR=0x00008000"

FILES_${KERNEL_PACKAGE_NAME}-devicetree := "${BOOT_PARTITION_MOUNTPOINT}/*.dtb"

DIRFILES = "1"

# dummy package to make do_deploy and QA checks happy while still getting the
# -devicetree packages we want and need; moving dtb files away from the
# original installation directory in do_install_append messes up do_deploy, so
# we keep the files in place instead and package them as required by QA
PACKAGES += "${KERNEL_PACKAGE_NAME}-devicetree-dummy"
FILES_${KERNEL_PACKAGE_NAME}-devicetree-dummy = " \
    ${KERNEL_IMAGEDEST}/bcm2708-rpi-cm.dtb \
    ${KERNEL_IMAGEDEST}/bcm2710-rpi-cm3.dtb \
"

do_configure_prepend() {
    rm -f "${WORKDIR}/defconfig" "${B}/.config"
    install -m 0644 ${WORKDIR}/${KERNEL_DEFCONFIG_${MACHINE}} ${WORKDIR}/defconfig || die "Kernel configuration ${KERNEL_DEFCONFIG_${MACHINE}} not available."
}

do_install_append() {
    install -d ${D}${BOOT_PARTITION_MOUNTPOINT}

    for DTB in ${KERNEL_DEVICETREE}; do
        cp ${D}/${KERNEL_IMAGEDEST}/${DTB} ${D}/${BOOT_PARTITION_MOUNTPOINT}/${DTB}
    done
}

pkg_preinst_kernel-devicetree () {
if [ x"$D" = x ]; then mount -o remount,rw ${BOOT_PARTITION_MOUNTPOINT}; fi
}

pkg_postinst_kernel-devicetree () {
if [ x"$D" = x ]; then mount -o remount,ro ${BOOT_PARTITION_MOUNTPOINT}; fi
}

pkg_prerm_kernel-devicetree () {
if [ x"$D" = x ]; then mount -o remount,rw ${BOOT_PARTITION_MOUNTPOINT}; fi
}

pkg_postrm_kernel-devicetree () {
if [ x"$D" = x ]; then mount -o remount,ro ${BOOT_PARTITION_MOUNTPOINT}; fi
}
