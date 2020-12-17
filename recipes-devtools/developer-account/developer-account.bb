SUMMARY = "Developer account for remove access via SSH"
SECTION = "base"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
S = "${WORKDIR}"
PR = "r1"

SRC_URI = " \
    file://bashrc \
    file://profile \
    file://vimrc \
    file://sudoers \
"

FILES_${PN} = " \
    /home/developer/.bashrc \
    /home/developer/.profile \
    /home/developer/.vimrc \
    ${sysconfdir}/sudoers.d/50-developer-account \
"

RRECOMMENDS_${PN} = "sudo"

DIRFILES = "1"

PASSWORD = "\\\$1\\\$c4\\\$iAPIHXTXO1uZNLhEncD1v."
USERADD_PACKAGES = "${PN}"
USERADD_PARAM_${PN} = "-N -p ${PASSWORD} developer"

inherit allarch useradd

do_install() {
    install -d ${D}/home
    install -o developer -g users -d ${D}/home/developer
    install -m 755 -o developer -g users ${S}/bashrc ${D}/home/developer/.bashrc
    install -m 755 -o developer -g users ${S}/profile ${D}/home/developer/.profile
    install -m 644 -o developer -g users ${S}/vimrc ${D}/home/developer/.vimrc

    install -d ${D}${sysconfdir} ${D}${sysconfdir}/sudoers.d
    install -m 644 ${WORKDIR}/sudoers ${D}${sysconfdir}/sudoers.d/50-developer-account
}

pkg_postrm_${PN}() {
    userdel -r developer
}
