#
# Copyright (C) 2015, 2018, 2020 T+A elektroakustik GmbH & Co. KG
#

SUMMARY = "Packages used on the T+A Streaming Board"
LICENSE = "MIT"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PR = "r13"

inherit packagegroup

RDEPENDS_${PN} = " \
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
    alsa-state \
    strbo-release \
    strbo-startup \
    strbo-licenses \
    strbo-power \
    strbo-appliance-icons \
    strbo-appliance-icons-lighttpd \
    strbo-rest-lighttpd \
    updata \
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
    gerbera \
    quota \
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
