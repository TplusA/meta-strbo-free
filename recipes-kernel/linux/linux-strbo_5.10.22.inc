LINUX_VERSION = "5.10.22"
SRCREV = "5d38ca56948115501283c396a8965f86fa161209"
SRC_URI = " \
    git://git.tua.local/repo/RPI-Kernel.git;protocol=http;branch=rpi-5.10.22-tahifi \
    file://0001-ASoC-tahifi-codec-generic-Add-T-A-HiFi-appliances-co.patch \
    file://0002-ASoC-tahifi-generic-Add-T-A-HiFi-appliances-driver-f.patch \
    file://0003-bcm2835-i2s-Bit-clock-is-always-on-for-SND_SOC_DAIFM.patch \
    file://0004-bcm2835-i2s-Frame-sync-is-always-on-for-SND_SOC_DAIF.patch \
    file://0005-bcm2835-i2s-Allow-samples-rates-of-up-to-768000.patch \
    file://strbo_cm_${LINUX_VERSION}.config \
    file://defconfig \
"

DEPENDS += "xz-native bc-native"
DEPENDS += "${@bb.utils.contains('ARCH', 'x86', 'elfutils-native', '', d)}"
DEPENDS += "openssl-native util-linux-native"
DEPENDS += "gmp-native"
