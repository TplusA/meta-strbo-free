FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PR = "r1"

SRC_URI += " \
    file://0001-Applied-patch-created-by-Carlos-Rafael-Giani.patch \
    file://0002-mpegaudioparse-Drop-first-frame-if-it-s-an-info-tag.patch \
    file://0003-mpegaudioparse-Fix-delay-samples-computation.patch \
    file://0004-qtdemux-Parse-iTunes-iTunSMPB-atom-for-gapless-playb.patch \
    file://0005-qtdemux-Gapless-AAC-playback-for-files-with-iTunes-i.patch \
"
