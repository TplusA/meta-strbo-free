SUMMARY = "Tool for waiting for power down events"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
                    file://COPYING.GPLv3;md5=d32239bcb673463ab874e80d47fae504"

SRCREV = "b0b6de93c555ee4ef25d431ad87342057a1913f6"

SRC_URI = "git://github.com/TplusA/OffEvent.git;branch=master;protocol=https"

S = "${WORKDIR}/git"

CFLAGS += "-std=c11"

inherit autotools
