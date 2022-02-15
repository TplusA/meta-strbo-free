#
# Copyright (C) 2020, 2021 T+A elektroakustik GmbH & Co. KG
#

SUMMARY = "Extra packages used on the T+A Streaming Board"
LICENSE = "MIT"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PR = "r1"

inherit packagegroup

RDEPENDS:${PN} += " \
    systemd-analyze \
    less \
    vim \
    joe \
    nano \
    diffutils \
    alsa-utils-alsamixer \
    alsa-utils-aplay \
    alsa-utils-alsactl \
    strbo-logging \
    gdbserver \
"
