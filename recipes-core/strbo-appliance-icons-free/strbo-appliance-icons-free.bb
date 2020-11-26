SUMMARY = "Free icons"
LICENSE = "CC0-1.0"
LIC_FILES_CHKSUM = "file://${THISDIR}/COPYING;md5=65d3616852dbf7b1a6d4b53b00626032"

SRC_URI = " \
    file://device-184x80.png \
    file://device-368x160.png \
    file://device-552x240.png \
    file://logo-32x23.bmp \
    file://logo-64x64.bmp \
"

S = "${WORKDIR}"

PR = "r0"

inherit allarch

PACKAGES += "${PN}-lighttpd"

FILES_${PN} = "${datadir}/strbo-appliance-icons"

RDEPENDS_${PN}-lighttpd = "lighttpd"
FILES_${PN}-lighttpd = "${sysconfdir} /www"

APPLIANCES = "\
    MP1000E \
    MP2000R \
    MP2500R \
    MP3000HV \
    MP3100HV \
    MP8 \
    R1000E \
    SD3100HV \
    SDV3100HV \
"

do_install() {
    install -d ${D}${datadir}/strbo-appliance-icons \
               ${D}${datadir}/strbo-appliance-icons/generic

    install ${WORKDIR}/logo-32x23.bmp ${WORKDIR}/logo-64x64.bmp ${D}${datadir}/strbo-appliance-icons/generic

    for d in ${APPLIANCES}
    do
        install -d ${D}${datadir}/strbo-appliance-icons/${d}
        install ${WORKDIR}/device-184x80.png ${WORKDIR}/device-368x160.png ${WORKDIR}/device-552x240.png ${D}${datadir}/strbo-appliance-icons/${d}
    done

    install -d ${D}/www/pages
    ln -s ${datadir}/strbo-appliance-icons ${D}/www/pages/images
}
