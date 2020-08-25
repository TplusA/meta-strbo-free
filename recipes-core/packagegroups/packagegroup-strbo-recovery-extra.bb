#
# Copyright (C) 2020 T+A elektroakustik GmbH & Co. KG
#

SUMMARY = "Extra packages used on the T+A Streaming Board recovery system"
LICENSE = "MIT"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PR = "r0"

inherit packagegroup

RDEPENDS_${PN} += " \
    less \
    vim \
    diffutils \
    systemd-analyze \
    strbo-logging \
"
