LINUX_VERSION = "4.19.60"
SRCREV = "20565c94ca877a099a3c9c5fa39a0380f3d16491"
SRC_URI = " \
    git://github.com/raspberrypi/linux.git;protocol=git;branch=rpi-4.19.y \
    file://0001-ASoC-Add-T-A-HiFi-appliances-codec-driver.patch \
    file://0002-ASoC-Add-T-A-HiFi-appliances-driver-for-RasPi.patch \
    file://0003-bcm2835-i2s-Bit-clock-is-always-on-for-SND_SOC_DAIFM.patch \
    file://0004-bcm2835-i2s-Frame-sync-is-always-on-for-SND_SOC_DAIF.patch \
    file://strbo_cm_${LINUX_VERSION}.config \
    file://defconfig \
"
