#
# Copyright (C) 2015, 2018, 2020, 2022 T+A elektroakustik GmbH & Co. KG
#

SUMMARY = "Basic packages used on the T+A Streaming Board"
LICENSE = "MIT"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PR = "r2"

inherit packagegroup

RDEPENDS:${PN} = " \
    which \
    kernel \
    kernel-modules \
    kernel-image-uimage \
    kernel-devicetree \
    kmod \
    linux-firmware \
    \
    base-files-main \
    base-passwd \
    coreutils \
    util-linux \
    util-linux-agetty \
    util-linux-mount \
    util-linux-mkfs \
    e2fsprogs \
    systemd \
    systemd-journal-gatewayd \
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
    socat \
    inetutils \
    iproute2-devlink \
    iproute2-genl \
    iproute2-ifstat \
    iproute2-ip \
    iproute2-lnstat \
    iproute2-nstat \
    iproute2-rtacct \
    iproute2-ss \
    iproute2-tc \
    iproute2-tipc \
    connman \
    connman-tools \
    connman-client \
    packagegroup-core-ssh-dropbear \
    packagegroup-base-wifi \
    openssh-sftp-server \
    tree \
    \
    alsa-state \
    strbo-release \
    strbo-startup \
    strbo-licenses \
    strbo-power \
    strbo-rest-lighttpd \
    updata \
    strbo-package-repo \
    drcpd \
    drcpd-locale-de-de \
    drcpd-locale-en-us \
    drcpd-locale-fr-fr \
    drcpd-locale-it-it \
    listbrokers \
    listbrokers-dbus-service \
    streamplayer \
    dbusdl \
    dbusdl-dbus-service \
    flagpole \
    quota \
    libcap-bin \
    \
    drcpd-locale-de-de \
    drcpd-locale-en-us \
    drcpd-locale-fr-fr \
    drcpd-locale-it-it \
    glibc-localedata-de-de \
    glibc-localedata-en-us \
    glibc-localedata-es-es \
    glibc-localedata-fr-fr \
    glibc-localedata-it-it \
    glibc-binary-localedata-de-de \
    glibc-binary-localedata-en-us \
    glibc-binary-localedata-es-es \
    glibc-binary-localedata-fr-fr \
    glibc-binary-localedata-it-it \
    glibc-utils \
    localedef \
"
