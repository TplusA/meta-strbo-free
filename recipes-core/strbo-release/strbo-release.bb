inherit allarch

SUMMARY = "T+A Streaming Board system information"
DESCRIPTION = "The /usr/lib/strbo-release file contains StrBo information."
LICENSE = "MIT"

FILES_${PN} += "${nonarch_libdir}/strbo-release"

do_compile() {
    echo -n '' >${B}/strbo-release
    echo "STRBO_VERSION=\"${DISTRO_VERSION}\"" >>${B}/strbo-release
    echo "STRBO_RELEASE_LINE=\"${DISTRO_LINE}\"" >>${B}/strbo-release
    echo "STRBO_FLAVOR=\"${DISTRO_FLAVOR}\"" >>${B}/strbo-release
    echo "STRBO_DATETIME=\"${DISTRO_DATETIME}\"" >>${B}/strbo-release
    echo "STRBO_GIT_COMMIT=\"${DISTRO_GIT_COMMIT}\"" >>${B}/strbo-release
}
do_compile[vardeps] += "${DISTRO_VERSION} ${DISTRO_LINE} ${DISTRO_FLAVOR}"
do_compile[vardeps] += "${DISTRO_DATETIME} ${DISTRO_GIT_COMMIT}"

do_install () {
    install -d ${D}${nonarch_libdir} ${D}${sysconfdir}
    install -m 0644 strbo-release ${D}${nonarch_libdir}/
    lnr ${D}${nonarch_libdir}/strbo-release ${D}${sysconfdir}/strbo-release
}
