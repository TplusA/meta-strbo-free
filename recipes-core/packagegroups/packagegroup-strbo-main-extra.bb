#
# Copyright (C) 2020 T+A elektroakustik GmbH & Co. KG
#

SUMMARY = "Extra packages used on the T+A Streaming Board"
LICENSE = "MIT"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PR = "r0"

inherit packagegroup

RDEPENDS_${PN} += " \
    systemd-analyze \
    less \
    vim \
    diffutils \
    alsa-utils-alsamixer \
    alsa-utils-aplay \
    alsa-utils-alsactl \
    strbo-logging \
    gdbserver \
"
