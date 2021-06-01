SUMMARY = "T+A Streaming Board REST API"
SECTION = "net"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRCREV = "fda2ddbff99577b851cf6bb64112cab32ee48ddd"

SRC_URI = " \
    git://git.tua.local/repo/StrBo-REST;branch=master;protocol=http \
    file://launcher.sh \
    file://strbo-rest.conf \
    file://strbo-rest.sudoers \
    file://polkit.rules \
"

S = "${WORKDIR}/git"

PACKAGES += "${PN}-lighttpd"

DEPENDS = " \
    strbo-startup (>= 1.0-r15) \
"

RDEPENDS_${PN} = " \
    python3-werkzeug \
    python3-flipflop \
    python3-halogen \
    python3-dbus \
    python3-pygobject \
    python3-ctypes \
    coreutils \
    util-linux \
    util-linux-mount \
    util-linux-umount \
    tar \
    gnupg \
    sudo \
    systemd \
    polkit \
"

RRECOMMENDS_${PN} = "updata"

RDEPENDS_${PN}-lighttpd = " \
    ${PN} \
    lighttpd \
    lighttpd-module-alias \
    lighttpd-module-fastcgi \
    lighttpd-module-rewrite \
    lighttpd-module-wstunnel \
    coreutils \
    sed \
    shadow-base \
    systemd \
"

PACKAGE_WRITE_DEPS += "systemd-systemctl-native"

FILES_${PN} = " \
    /www/strbo-rest/strbo/*.py \
    /www/strbo-rest/helpers/*.sh \
    ${sysconfdir}/sudoers.d/50-rest \
    ${sysconfdir}/polkit-1/rules.d/99-strbo-rest-api.rules \
"

FILES_${PN}-lighttpd = " \
    /www/strbo-rest/v1_wsgi.py \
    /www/strbo-rest/v1_wsgi.sh \
    ${sysconfdir}/lighttpd.d \
"

DIRFILES = "1"

EXTRA_OEMAKE = "SPHINXBUILD=echo"

USERADD_PACKAGES = "${PN}"
USERADD_PARAM_${PN} = " -r -N -g rest -G strbo -M -d /www/strbo-rest strbo-rest"
GROUPADD_PARAM_${PN} = "-r rest; -r strbo"

inherit allarch useradd

do_install() {
    install -d ${D}/www/strbo-rest
    install -m 755 ${S}/v1_wsgi.py ${D}/www/strbo-rest
    install -m 755 ${WORKDIR}/launcher.sh ${D}/www/strbo-rest/v1_wsgi.sh

    install -d ${D}/www/strbo-rest/strbo
    for f in ${S}/strbo/*.py
    do
        install -m 644 ${f} ${D}/www/strbo-rest/strbo
    done

    install -m 750 -g rest -d ${D}/www/strbo-rest/helpers
    for f in ${S}/helpers/*.sh
    do
        install -m 750 -g rest ${f} ${D}/www/strbo-rest/helpers
    done

    install -d ${D}${sysconfdir}/lighttpd.d
    install -m 644 ${WORKDIR}/strbo-rest.conf ${D}${sysconfdir}/lighttpd.d

    install -d ${D}${sysconfdir} ${D}${sysconfdir}/sudoers.d
    install -m 644 ${WORKDIR}/strbo-rest.sudoers ${D}${sysconfdir}/sudoers.d/50-rest

    install -d ${D}${sysconfdir}/polkit-1 ${D}${sysconfdir}/polkit-1/rules.d
    install -m 644 ${WORKDIR}/polkit.rules ${D}${sysconfdir}/polkit-1/rules.d/99-strbo-rest-api.rules
}
