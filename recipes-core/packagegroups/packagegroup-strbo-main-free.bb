#
# Copyright (C) 2015, 2018, 2020 T+A elektroakustik GmbH & Co. KG
#

SUMMARY = "Packages used only on free version of the T+A Streaming Board"
LICENSE = "MIT"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PR = "r0"

inherit packagegroup

RDEPENDS_${PN} = " \
    strbo-appliance-icons \
    strbo-appliance-icons-lighttpd \
"
