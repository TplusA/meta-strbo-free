LICENSE = "MIT & Proprietary"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420 \
                    file://${RPIFW_S}/boot/LICENCE.broadcom;md5=c403841ff2837657b2ed8e5bb474ac8d"

SRC_URI += " \
    file://strbo-bcm2835-gpio.dts \
    file://strbo-tahifi-i2s.dts \
    file://dt-blob-GPIO-47-is-an-input-on-CM1.patch;patchdir=${RPIFW_S} \
"

RDEPENDS_${PN} = ""
DEPENDS += "dtc-native"

#
# The architecture for the .elf files is "Broadcom VideoCore III processor".
# They are the startup files read by the GPU core, and it is the GPU that
# actually brings the system to life (the ARM core we are targetting is just
# its sidekick).
#
# Therefore, the QA checks for matching architecture has to be disabled.
#
INSANE_SKIP_${PN} += "arch"

COMPATIBLE_MACHINE = "^rpi$"
PACKAGE_ARCH = "${MACHINE_ARCH}"

FILES_${PN} = " \
    ${BOOT_PARTITION_MOUNTPOINT}/config.txt \
    ${BOOT_PARTITION_MOUNTPOINT}/cmdline.txt \
    ${BOOT_PARTITION_MOUNTPOINT}/bootcode.bin \
    ${BOOT_PARTITION_MOUNTPOINT}/fixup_cd.dat \
    ${BOOT_PARTITION_MOUNTPOINT}/start_cd.elf \
    ${BOOT_PARTITION_MOUNTPOINT}/dt-blob.bin \
    ${BOOT_PARTITION_MOUNTPOINT}/LICENCE.broadcom \
    ${BOOT_PARTITION_MOUNTPOINT}/bcm2708-rpi-cm.dtb \
    ${BOOT_PARTITION_MOUNTPOINT}/bcm2710-rpi-cm3.dtb \
    ${BOOT_PARTITION_MOUNTPOINT}/overlays/* \
"

DIRFILES = "1"

do_compile() {
    dtc -W no-unit_address_vs_reg -@ -I dts -O dtb -o ${B}/strbo-bcm2835-gpio.dtbo ${WORKDIR}/strbo-bcm2835-gpio.dts
    dtc -W no-unit_address_vs_reg -@ -I dts -O dtb -o ${B}/strbo-tahifi-i2s.dtbo   ${WORKDIR}/strbo-tahifi-i2s.dts
    dtc -I dts -O dtb -o ${B}/dt-blob.bin ${RPIFW_S}/extra/dt-blob.dts
}

do_install() {
    install -m 0755 -d \
        ${D}${BOOT_PARTITION_MOUNTPOINT} \
        ${D}${BOOT_PARTITION_MOUNTPOINT}/overlays

    STRBO_OVERLAYS="strbo-bcm2835-gpio.dtbo strbo-tahifi-i2s.dtbo"

    for f in bootcode.bin fixup_cd.dat start_cd.elf LICENCE.broadcom
    do
        install -m 0644 ${RPIFW_S}/boot/${f} ${D}${BOOT_PARTITION_MOUNTPOINT}
    done

    install -m 0644 ${B}/dt-blob.bin ${D}${BOOT_PARTITION_MOUNTPOINT}

    for f in ${STRBO_OVERLAYS}
    do
        install -m 0644 ${B}/${f} ${D}${BOOT_PARTITION_MOUNTPOINT}/overlays
    done

    cat <<EOF >${D}${BOOT_PARTITION_MOUNTPOINT}/config.txt
gpu_mem=16
boot_delay=0
boot_delay_ms=0
disable_splash=1
device_tree_address=0x2600000
dtparam=i2s=on,spi=on
dtoverlay=strbo-bcm2835-gpio
dtoverlay=strbo-tahifi-i2s
EOF
}

pkg_preinst_${PN} () {
if [ x"$D" = x ]; then mount -o remount,rw ${BOOT_PARTITION_MOUNTPOINT}; fi
}

pkg_postinst_${PN} () {
if [ x"$D" = x ]; then mount -o remount,ro ${BOOT_PARTITION_MOUNTPOINT}; fi
}

pkg_prerm_${PN} () {
if [ x"$D" = x ]; then mount -o remount,rw ${BOOT_PARTITION_MOUNTPOINT}; fi
}

pkg_postrm_${PN} () {
if [ x"$D" = x ]; then mount -o remount,ro ${BOOT_PARTITION_MOUNTPOINT}; fi
}
