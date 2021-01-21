#
# Copyright (C) 2020, 2021 T+A elektroakustik GmbH & Co. KG
#

SUMMARY = "Extra packages used on the T+A Streaming Board recovery system"
LICENSE = "MIT"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PR = "r1"

inherit packagegroup

RDEPENDS_${PN} += " \
    less \
    vim \
    joe \
    nano \
    diffutils \
    systemd-analyze \
    strbo-logging \
"
