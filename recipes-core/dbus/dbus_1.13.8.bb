SUMMARY = "D-Bus message bus"
DESCRIPTION = "D-Bus is a message bus system, a simple way for applications to talk to one another. In addition to interprocess communication, D-Bus helps coordinate process lifecycle; it makes it simple and reliable to code a \"single instance\" application or daemon, and to launch applications and daemons on demand when their services are needed."
HOMEPAGE = "https://dbus.freedesktop.org"
SECTION = "base"
LICENSE = "AFL-2.1 | GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=10dded3b58148f3f1fd804b26354af3e \
                    file://dbus/dbus.h;beginline=6;endline=20;md5=7755c9d7abccd5dbd25a6a974538bb3c"
FILESEXTRAPATHS_append := ":${THISDIR}/${PN}:${COREBASE}/meta/recipes-core/dbus/dbus"
DEPENDS = "expat virtual/libintl autoconf-archive"
RDEPENDS_dbus_class-native = ""
RDEPENDS_dbus_class-nativesdk = ""
PACKAGES += "${@bb.utils.contains('DISTRO_FEATURES', 'ptest', '${PN}-ptest', '', d)}"
ALLOW_EMPTY_dbus-ptest = "1"
RDEPENDS_dbus-ptest_class-target = "dbus-test-ptest"

SRC_URI = "https://dbus.freedesktop.org/releases/dbus/dbus-${PV}.tar.xz \
           file://0001-Revert-Only-support-systemd-transport-if-we-have-sys.patch \
           file://0002-Use-systemd-service-type-simple-not-notify.patch \
           file://0003-configure.ac-Forbid-AX_-prefixed-patterns-more-selec.patch \
           file://0004-Adapt-to-API-change-in-AX_CODE_COVERAGE-version-28.patch \
           file://tmpdir.patch \
           file://dbus-1.init \
           file://clear-guid_from_server-if-send_negotiate_unix_f.patch \
"

SRC_URI[md5sum] = "dc83da313e716506451b1cb8d8477fe6"
SRC_URI[sha256sum] = "82a89f64e1b55e459725186467770995f33cac5eb8a050b5d8cbeb338078c4f6"

PR = "r2"

inherit useradd autotools pkgconfig gettext update-rc.d

INITSCRIPT_NAME = "dbus-1"
INITSCRIPT_PARAMS = "start 02 5 3 2 . stop 20 0 1 6 ."

python __anonymous() {
    if not bb.utils.contains('DISTRO_FEATURES', 'sysvinit', True, False, d):
        d.setVar("INHIBIT_UPDATERCD_BBCLASS", "1")
}

USERADD_PACKAGES = "${PN}"
GROUPADD_PARAM_${PN} = "-r netdev"
USERADD_PARAM_${PN} = "--system --home ${localstatedir}/lib/dbus \
                       --no-create-home --shell /bin/false \
                       --user-group messagebus"

CONFFILES_${PN} = "${sysconfdir}/dbus-1/system.conf ${sysconfdir}/dbus-1/session.conf"

DEBIANNAME_${PN} = "dbus-1"

PACKAGES =+ "${PN}-lib"

OLDPKGNAME = "dbus-x11"
OLDPKGNAME_class-nativesdk = ""

# for compatibility
RPROVIDES_${PN} = "${OLDPKGNAME}"
RREPLACES_${PN} += "${OLDPKGNAME}"

FILES_${PN} = "${bindir}/dbus-daemon* \
               ${bindir}/dbus-uuidgen \
               ${bindir}/dbus-cleanup-sockets \
               ${bindir}/dbus-send \
               ${bindir}/dbus-monitor \
               ${bindir}/dbus-launch \
               ${bindir}/dbus-run-session \
               ${bindir}/dbus-update-activation-environment \
               ${libexecdir}/dbus* \
               ${sysconfdir} \
               ${localstatedir} \
               ${datadir}/dbus-1/services \
               ${datadir}/dbus-1/system-services \
               ${datadir}/dbus-1/session.d \
               ${datadir}/dbus-1/session.conf \
               ${datadir}/dbus-1/system.d \
               ${datadir}/dbus-1/system.conf \
               ${datadir}/xml/dbus-1 \
               ${systemd_system_unitdir} \
               ${systemd_user_unitdir} \
               ${nonarch_libdir}/sysusers.d/dbus.conf \
               ${nonarch_libdir}/tmpfiles.d/dbus.conf \
"
FILES_${PN}-lib = "${libdir}/lib*.so.*"
RRECOMMENDS_${PN}-lib = "${PN}"
FILES_${PN}-dev += "${libdir}/dbus-1.0/include ${libdir}/cmake/DBus1 ${bindir}/dbus-test-tool"

PACKAGE_WRITE_DEPS += "${@bb.utils.contains('DISTRO_FEATURES','systemd sysvinit','systemd-systemctl-native','',d)}"
pkg_postinst_dbus() {
	# If both systemd and sysvinit are enabled, mask the dbus-1 init script
        if ${@bb.utils.contains('DISTRO_FEATURES','systemd sysvinit','true','false',d)}; then
		if [ -n "$D" ]; then
			OPTS="--root=$D"
		fi
		systemctl $OPTS mask dbus-1.service
	fi

	if [ -z "$D" ] && [ -e /etc/init.d/populate-volatile.sh ] ; then
		/etc/init.d/populate-volatile.sh update
	fi
}

EXTRA_OECONF = "--disable-tests \
                --disable-xml-docs \
                --disable-doxygen-docs \
                --disable-libaudit \
                --disable-systemd \
                --enable-largefile \
                --with-system-socket=/run/dbus/system_bus_socket \
                "

EXTRA_OECONF_append_class-target = " SYSTEMCTL=${base_bindir}/systemctl"
EXTRA_OECONF_append_class-native = " --disable-selinux"

PACKAGECONFIG ??= "${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'systemd', '', d)} \
                   ${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'x11', '', d)} \
                   user-session \
                  "

PACKAGECONFIG_class-native = ""
PACKAGECONFIG_class-nativesdk = ""

# Would like to --enable-systemd but that's a circular build-dependency between
# systemd<->dbus
PACKAGECONFIG[systemd] = "--with-systemdsystemunitdir=${systemd_system_unitdir},--without-systemdsystemunitdir"
PACKAGECONFIG[x11] = "--with-x --enable-x11-autolaunch,--without-x --disable-x11-autolaunch, virtual/libx11 libsm"
PACKAGECONFIG[user-session] = "--enable-user-session --with-systemduserunitdir=${systemd_user_unitdir},--disable-user-session"

do_install() {
	autotools_do_install

	if ${@bb.utils.contains('DISTRO_FEATURES', 'sysvinit', 'true', 'false', d)}; then
		install -d ${D}${sysconfdir}/init.d
		sed 's:@bindir@:${bindir}:' < ${WORKDIR}/dbus-1.init >${WORKDIR}/dbus-1.init.sh
		install -m 0755 ${WORKDIR}/dbus-1.init.sh ${D}${sysconfdir}/init.d/dbus-1
		install -d ${D}${sysconfdir}/default/volatiles
		echo "d messagebus messagebus 0755 ${localstatedir}/run/dbus none" \
		     > ${D}${sysconfdir}/default/volatiles/99_dbus
	fi

	if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
		for i in dbus.target.wants sockets.target.wants multi-user.target.wants; do \
			install -d ${D}${systemd_system_unitdir}/$i; done
		install -m 0644 ${B}/bus/dbus.service ${B}/bus/dbus.socket ${D}${systemd_system_unitdir}/
		ln -fs ../dbus.socket ${D}${systemd_system_unitdir}/dbus.target.wants/dbus.socket
		ln -fs ../dbus.socket ${D}${systemd_system_unitdir}/sockets.target.wants/dbus.socket
		ln -fs ../dbus.service ${D}${systemd_system_unitdir}/multi-user.target.wants/dbus.service
	fi


	mkdir -p ${D}${localstatedir}/lib/dbus

	chown messagebus:messagebus ${D}${localstatedir}/lib/dbus

	chown root:messagebus ${D}${libexecdir}/dbus-daemon-launch-helper
	chmod 4755 ${D}${libexecdir}/dbus-daemon-launch-helper

	# Remove Red Hat initscript
	rm -rf ${D}${sysconfdir}/rc.d

	# Remove empty testexec directory as we don't build tests
	rm -rf ${D}${libdir}/dbus-1.0/test

	# Remove /var/run as it is created on startup
	rm -rf ${D}${localstatedir}/run
}

do_install_class-native() {
	autotools_do_install

	# dbus-launch has no X support so lets not install it in case the host
	# has a more featured and useful version
	rm -f ${D}${bindir}/dbus-launch
}

do_install_class-nativesdk() {
	autotools_do_install

	# dbus-launch has no X support so lets not install it in case the host
	# has a more featured and useful version
	rm -f ${D}${bindir}/dbus-launch

	# Remove /var/run to avoid QA error
	rm -rf ${D}${localstatedir}/run
}
BBCLASSEXTEND = "native nativesdk"

INSANE_SKIP_${PN}-ptest += "build-deps"
