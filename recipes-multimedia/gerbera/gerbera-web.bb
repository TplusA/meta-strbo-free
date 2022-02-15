DESCRIPTION = "Simple web UI for gerbera for testing"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING.MIT;md5=838c366f69b72c5df05c96dff79b35f2"

FILESPATH = "${FILE_DIRNAME}/gerbera-web"
SRC_URI = " \
    file://gerbera-web \
    file://gerbera-web.service \
    file://COPYING.MIT \
"
S = "${WORKDIR}"

SYSTEMD_SERVICE:${PN} = "gerbera-web.service"

RDEPENDS:${PN} += "gerbera python3-asyncio python3-misc python3-logging python3-websockets \
    python3-threading python3-multiprocessing python3-shell python3-compression python3-crypt \
    python3-subprocess python3-importlib python3-email python3-textutils"

inherit pkgconfig systemd

do_install:append() {
	install -D -m 0755 ${WORKDIR}/gerbera-web ${D}/usr/bin/gerbera-web
    install -D -m 0644 ${WORKDIR}/gerbera-web.service ${D}${systemd_system_unitdir}/gerbera-web.service
}

FILES:${PN} += "*"
