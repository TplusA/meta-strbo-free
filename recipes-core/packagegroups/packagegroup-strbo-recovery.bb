#
# Copyright (C) 2015, 2020 T+A elektroakustik GmbH & Co. KG
#

SUMMARY = "Packages used on the T+A Streaming Board recovery system"
LICENSE = "MIT"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PR = "r7"

inherit packagegroup

RDEPENDS_${PN} = " \
    kernel \
    kernel-modules \
    kernel-devicetree \
    kmod \
    linux-firmware \
    \
    base-files-recovery \
    base-passwd \
    coreutils \
    util-linux \
    util-linux-agetty \
    util-linux-mount \
    util-linux-mkfs \
    systemd \
    udev \
    bash \
    procps \
    psmisc \
    grep \
    sed \
    gawk \
    tar \
    gzip \
    bzip2 \
    xz \
    zip \
    wget \
    findutils \
    eglibc-utils \
    glibc-charmap-utf-8 \
    packagegroup-base-ext2 \
    packagegroup-base-vfat \
    packagegroup-base-usbhost \
    \
    netbase \
    net-tools \
    ethtool \
    netcat \
    connman \
    connman-tools \
    connman-client \
    packagegroup-core-ssh-dropbear \
    packagegroup-base-wifi \
    openssh-sftp-server \
    \
    parted \
    strbo-release \
    strbo-recover-system \
    strbo-power \
"
