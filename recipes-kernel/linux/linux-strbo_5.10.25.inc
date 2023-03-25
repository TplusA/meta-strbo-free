LINUX_VERSION = "5.10.25"
SRCREV = "b62f64b7df524956bef0c09566a04eec883c3214"
SRC_URI = " \
    git://github.com/TplusA/RPI-Kernel.git;protocol=https;branch=rpi-5.10.25-tahifi \
    file://0001-ASoC-tahifi-codec-generic-Add-T-A-HiFi-appliances-co.patch \
    file://0002-ASoC-tahifi-generic-Add-T-A-HiFi-appliances-driver-f.patch \
    file://0003-bcm2835-i2s-Bit-clock-is-always-on-for-SND_SOC_DAIFM.patch \
    file://0004-bcm2835-i2s-Frame-sync-is-always-on-for-SND_SOC_DAIF.patch \
    file://0005-ASoC-tahifi-codec-generic-Restrict-maximum-rate-to-1.patch \
    file://strbo_cm_${LINUX_VERSION}.config \
    file://defconfig \
"

DEPENDS += "xz-native bc-native"
DEPENDS += "${@bb.utils.contains('ARCH', 'x86', 'elfutils-native', '', d)}"
DEPENDS += "openssl-native util-linux-native"
DEPENDS += "gmp-native"
