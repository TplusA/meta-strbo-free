SUMMARY = "Gstreamer validation tool"
DESCRIPTION = "A Tool to test GStreamer components"
HOMEPAGE = "https://gstreamer.freedesktop.org/documentation/gst-devtools/index.html"
SECTION = "multimedia"

LICENSE = "LGPL-2.1-or-later"
LIC_FILES_CHKSUM = "file://validate/COPYING;md5=a6f89e2100d9b6cdffcea4f398e37343"

#S = "${WORKDIR}/gst-devtools-${PV}"

SRC_URI = "https://gstreamer.freedesktop.org/src/gst-devtools/gst-devtools-${PV}.tar.xz \
           file://0001-connect-has-a-different-signature-on-musl.patch \
           file://gst-devtools-1.21.2-266-gdec3aa55e9.patch;pnum=3 \
           file://0001-Revert-meson-Use-implicit-builtin-dirs-in-pkgconfig-.patch;pnum=3 \
           "
PR = "r4"

SRC_URI[sha256sum] = "bbbd45ead703367ea8f4be9b3c082d7b62bef47b240a39083f27844e28758c47"

DEPENDS = "json-glib glib-2.0 glib-2.0-native gstreamer1.0 gstreamer1.0-plugins-base"
RRECOMMENDS:${PN} = "git"

FILES:${PN} += "${datadir}/gstreamer-1.0/* ${libdir}/gst-validate-launcher/* ${libdir}/gstreamer-1.0/*"

inherit meson pkgconfig gettext upstream-version-is-even gobject-introspection

# TODO: put this in a gettext.bbclass patch
def gettext_oemeson(d):
    if d.getVar('USE_NLS') == 'no':
        return '-Dnls=disabled'
    # Remove the NLS bits if USE_NLS is no or INHIBIT_DEFAULT_DEPS is set
    if d.getVar('INHIBIT_DEFAULT_DEPS') and not oe.utils.inherits(d, 'cross-canadian'):
        return '-Dnls=disabled'
    return '-Dnls=enabled'

# Build GstValidateVideo
PACKAGECONFIG[cairo] = "-Dcairo=enabled,-Dcairo=disabled,cairo"

EXTRA_OEMESON += " \
    -Ddoc=disabled \
    -Ddebug_viewer=disabled \
    -Dtests=disabled \
    -Dvalidate=enabled \
    ${@gettext_oemeson(d)} \
"

do_install:append () {
     for fn in ${bindir}/gst-validate-launcher \
         ${libdir}/gst-validate-launcher/python/launcher/config.py; do
             sed -i -e 's,${B},/usr/src/debug/${PN},g' -e 's,${S},/usr/src/debug/${PN},g' ${D}$fn
     done
}

GIR_MESON_ENABLE_FLAG = "enabled"
GIR_MESON_DISABLE_FLAG = "disabled"
