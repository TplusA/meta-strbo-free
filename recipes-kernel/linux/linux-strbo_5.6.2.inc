LINUX_VERSION = "5.6.2"
SRCREV = "35fc9e5412ac2a6491ee1a5787128bc555909d7c"
SRC_URI = " \
    git://git.tua.local/repo/RPI-Kernel.git;protocol=http;branch=rpi-5.6.2 \
    file://0001-ASoC-Add-T-A-HiFi-appliances-codec-driver.patch \
    file://0002-ASoC-Add-T-A-HiFi-appliances-driver-for-RasPi.patch \
    file://0003-bcm2835-i2s-Bit-clock-is-always-on-for-SND_SOC_DAIFM.patch \
    file://0004-bcm2835-i2s-Frame-sync-is-always-on-for-SND_SOC_DAIF.patch \
    file://0005-ASoC-tahifi-generic-use-modern-dai_link-style.patch \
    file://0006-bcm2835-i2s-Allow-samples-rates-of-up-to-768000.patch \
    file://0007-ASoC-tahifi-codec-generic-Support-rates-of-up-to-768.patch \
    file://0008-ASoC-tahifi-codec-generic-Also-support-S24_LE-and-S1.patch \
    file://strbo_cm_${LINUX_VERSION}.config \
    file://defconfig \
"
