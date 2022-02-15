DESCRIPTION = "Configuration files for StrBo recovery system"
SECTION = "base"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI = "file://fstab.common \
           file://fstab.recovery \
           file://e2fsck.conf \
           file://locale.conf \
           file://10-locale.sh"

S = "${WORKDIR}"

DEPENDS = "base-files"

PACKAGE_ARCH = "${MACHINE_ARCH}"
CONFFILES:${PN} = "${sysconfdir}/fstab"
FILES:${PN} = "/"

require conf/distro/include/partitions.inc

do_install () {
    install -m 0755 -d \
        ${D}${sysconfdir} \
        ${D}${localstatedir} \
        ${D}${localstatedir}/local
    install -m 0 -d \
        ${D}${RECOVERYDATAFS_MOUNTPOINT} \
        ${D}${RECOVERYBOOT_PARTITION_MOUNTPOINT}

    install -m 0644 ${WORKDIR}/fstab.common ${D}${sysconfdir}/fstab
    sed "s/@RECOVERYDATAFS_PARTITION_NUMBER@/${RECOVERYDATAFS_PARTITION_NUMBER}/g;\
         s,@RECOVERYDATAFS_MOUNTPOINT@,${RECOVERYDATAFS_MOUNTPOINT},g;\
         s/@RECOVERYDATAFS_TYPE@/${RECOVERYDATAFS_TYPE}/g;
         s/@RECOVERYBOOT_PARTITION_NUMBER@/${RECOVERYBOOT_PARTITION_NUMBER}/g;\
         s,@RECOVERYBOOT_PARTITION_MOUNTPOINT@,${RECOVERYBOOT_PARTITION_MOUNTPOINT},g;"\
        fstab.recovery >>${D}${sysconfdir}/fstab

    install -m 0644 ${WORKDIR}/e2fsck.conf ${D}${sysconfdir}/e2fsck.conf

    install -m 0644 ${WORKDIR}/locale.conf ${D}${sysconfdir}/locale.conf
    install -m 0755 -d ${D}${sysconfdir}/profile.d
    install -m 0644 ${WORKDIR}/10-locale.sh ${D}${sysconfdir}/profile.d/10-locale.sh
}

pkg_preinst:${PN} () {
    umount /src || true
}

pkg_postinst:${PN} () {
    if [ x"$D" = "x" ]
    then
        mount /src || true
    fi
}

pkg_prerm:${PN} () {
    umount /src || true
}
