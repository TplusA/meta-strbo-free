# File is based on linux-raspberrypi.inc from Raspberry Pi BSP

KERNEL_DEVICETREE := "bcm2708-rpi-cm.dtb bcm2710-rpi-cm3.dtb"
KERNEL_DANGLING_FEATURES_WARN_ONLY = "1"

PR = "${STRBO_KERNEL_PR}"

inherit kernel siteinfo
require recipes-kernel/linux/linux-yocto.inc

#
# We have a problem during kernel update here. As a matter of fact, the kernel
# build class puts the kernel version number into the package name. That is,
# for versions 5.6.2 and 5.6.3 we don't get packages "kernel-image-uimage" for
# these two versions; instead, we get packages "kernel-image-uimage-5.6.2" and
# "kernel-image-uimage-5.6.3". Their versions are 5.6.2 and 5.6.3,
# respectively, but from the package manager's point of view, these packages
# are completely unrelated because their basename are different.
#
# This wouldn't be a problem if these packages wouldn't share a common file, a
# symlink named "/boot/uImage" which points to the real uImage file with a
# version number appended in /boot/. Trying to step from one kernel version to
# the other involves installing the new package, then removing the old one.
# Asking dnf/rpm to do this yields an error about the symlink being owned by
# two different packages. Since dnf has no option for ignoring this error, the
# upgrade can only be done by hand using rpm, double-crossing dnf and thus
# introducing inconsistencies into the package management system.
#
# Removing the symlink in do_install:append() from the packages and managing it
# in postinst scriptlets is tricky because the build system plays stupid tricks
# in Python functions and it also requires the symlink to be around to finish
# its job. Moving the symlink to a dummy package is also tricky and it works,
# sort of, but the automatic update procedure still doesn't quite succeed.
#
# Since the root of the problem is the version number encoded into the package
# name, we simply remove it. The build class adds the version version number in
# a Python function, so we the same to revert the deed.
#
python __anonymous () {
    kname = d.getVar('KERNEL_PACKAGE_NAME')
    typelower = d.getVar('KERNEL_IMAGETYPE').lower()
    d.setVar('PKG_' + kname + '-image-' + typelower, kname + '-image-' + typelower)
}

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

FILES:${KERNEL_PACKAGE_NAME}-devicetree := "${BOOT_PARTITION_MOUNTPOINT}/*.dtb"

DIRFILES = "1"

# dummy package to make do_deploy and QA checks happy while still getting the
# -devicetree packages we want and need; moving dtb files away from the
# original installation directory in do_install:append messes up do_deploy, so
# we keep the files in place instead and package them as required by QA
PACKAGES += "${KERNEL_PACKAGE_NAME}-devicetree-dummy"
FILES:${KERNEL_PACKAGE_NAME}-devicetree-dummy = " \
    ${KERNEL_IMAGEDEST}/bcm2708-rpi-cm.dtb \
    ${KERNEL_IMAGEDEST}/bcm2710-rpi-cm3.dtb \
"

do_configure:prepend() {
    rm -f "${WORKDIR}/defconfig" "${B}/.config"
    install -m 0644 ${WORKDIR}/${KERNEL_DEFCONFIG_${MACHINE}} ${WORKDIR}/defconfig || die "Kernel configuration ${KERNEL_DEFCONFIG_${MACHINE}} not available."
}

do_install:append() {
    install -d ${D}${BOOT_PARTITION_MOUNTPOINT}

    for DTB in ${KERNEL_DEVICETREE}; do
        cp ${D}/${KERNEL_IMAGEDEST}/${DTB} ${D}/${BOOT_PARTITION_MOUNTPOINT}/${DTB}
    done
}
