SUMMARY = "Enable HTTP access to systemd journal"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

PR = "r3"

ALLOW_EMPTY_${PN} = "1"
DEPENDS = "systemd-systemctl-native"
RDEPENDS_${PN} = "systemd systemd-journal-gatewayd"
PACKAGE_WRITE_DEPS += "systemd-systemctl-native"

inherit allarch

pkg_postinst_${PN} () {
if [ x"$D" = x ]
then
    SYSTEMCTL_OPTS=""
else
    SYSTEMCTL_OPTS="--root=$D"
fi

if type systemctl >/dev/null 2>/dev/null
then
    systemctl $SYSTEMCTL_OPTS enable systemd-journal-gatewayd.socket

    if [ x"$D" = x ]
    then
        systemctl $SYSTEMCTL_OPTS restart systemd-journal-gatewayd.socket
    fi
fi
}

pkg_prerm_${PN} () {
if [ x"$D" = x ]
then
    SYSTEMCTL_OPTS=""
else
    SYSTEMCTL_OPTS="--root=$D"
fi

if type systemctl >/dev/null 2>/dev/null
then
    if [ x"$D" = x ]
    then
        systemctl stop systemd-journal-gatewayd.socket
    fi

    systemctl $OPTS disable systemd-journal-gatewayd.socket
fi
}
