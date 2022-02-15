SUMMARY = "UPnP root device service"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
                    file://COPYING.GPLv3;md5=d32239bcb673463ab874e80d47fae504"

DEPENDS = "gupnp gupnp-dlna"

SRCREV = "8cb91b64c3febea6e8c7730981d1327de9c1b3b4"
PR = "r3"

SRC_URI = "git://git.tua.local/repo/Flagpole;branch=master;protocol=http \
           file://flagpole.xml.in \
           file://flagpole.service \
           file://flagpole_launcher \
           "

S = "${WORKDIR}/git"

FILES:${PN}= " \
    ${bindir}/flagpole \
    ${bindir}/flagpole_launcher \
    ${datadir}/flagpole/flagpole.xml.in \
    ${systemd_unitdir}/system/flagpole.service \
"

SYSTEMD_SERVICE:${PN} = "flagpole.service"

DEPENDENCIES = "glib-2.0 gobject-2.0 gupnp-1.2 libsoup-2.4"
CFLAGS += "`pkg-config --cflags-only-other ${DEPENDENCIES}`"
CPPFLAGS += "`pkg-config --cflags-only-I ${DEPENDENCIES}`"
LDFLAGS += "`pkg-config --libs-only-L ${DEPENDENCIES}`"
LDLIBS += "`pkg-config --libs-only-l --libs-only-other ${DEPENDENCIES}`"

EXTRA_OEMAKE = "'CC=${CC}' 'CFLAGS=${CFLAGS}' 'CPPFLAGS=${CPPFLAGS}' 'LDFLAGS=${LDFLAGS}' 'LDLIBS=${LDLIBS}'"

inherit pkgconfig systemd

do_install () {
    install -d ${D}${bindir}
    install -m 755 ${S}/flagpole ${D}${bindir}
    install -m 755 ${WORKDIR}/flagpole_launcher ${D}${bindir}

    install -d ${D}${datadir} ${D}${datadir}/flagpole
    install -m 644 ${WORKDIR}/flagpole.xml.in ${D}${datadir}/flagpole

    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}
    then
        install -d ${D}${systemd_unitdir} ${D}${systemd_unitdir}/system
        install -m 644 ${WORKDIR}/flagpole.service ${D}${systemd_unitdir}/system
    fi
}
