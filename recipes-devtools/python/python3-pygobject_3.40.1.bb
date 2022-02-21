SUMMARY = "Python GObject bindings"
HOMEPAGE = "https://gitlab.gnome.org/GNOME/pygobject"
DESCRIPTION = "PyGObject is a Python package which provides bindings for GObject based libraries such as GTK, GStreamer, WebKitGTK, GLib, GIO and many more."
SECTION = "devel/python"
LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=a916467b91076e631dd8edb7424769c7"

GNOMEBASEBUILDCLASS = "meson"
inherit gnomebase distutils3-base upstream-version-is-even

DEPENDS += "python3 glib-2.0"

# Generating introspection data depends on a combination of native and target
# introspection tools, and qemu to run the target tools.
DEPENDS:append:class-target = " gobject-introspection gobject-introspection-native qemu-native prelink-native"

# Even though introspection is disabled on -native, gobject-introspection package is still
# needed for m4 macros.
DEPENDS:append:class-native = " gobject-introspection-native"
DEPENDS:append:class-nativesdk = " gobject-introspection-native"

SRCNAME="pygobject"

FILESEXTRAPATHS:prepend := "${COREBASE}/meta/recipes-devtools/python/${PN}:"
SRC_URI = " \
    http://ftp.gnome.org/pub/GNOME/sources/${SRCNAME}/${@gnome_verdir("${PV}")}/${SRCNAME}-${PV}.tar.xz \
    file://0001-Do-not-build-tests.patch \
"
SRC_URI[sha256sum] = "00c6d591f4cb40c335ab1fd3e8c17869ba15cfda54416fe363290af766790035"

S = "${WORKDIR}/${SRCNAME}-${PV}"

PACKAGECONFIG ??= "${@bb.utils.contains_any('DISTRO_FEATURES', [ 'directfb', 'wayland', 'x11' ], 'cairo', '', d)}"

RDEPENDS:${PN} += "python3-pkgutil"

# python3-pycairo is checked on configuration -> DEPENDS
# we don't link against python3-pycairo -> RDEPENDS
PACKAGECONFIG[cairo] = "-Dpycairo=enabled,-Dpycairo=disabled, cairo python3-pycairo, python3-pycairo"

BBCLASSEXTEND = "native"
PACKAGECONFIG:class-native = ""

# stupid hack against stupid package behavior
do_install:append () {
    ls -la ${D}/usr/usr/lib/python3.9
    ls -la ${D}/usr/lib
    mv ${D}/usr/usr/lib/python3.9 ${D}/usr/lib
    rmdir ${D}/usr/usr/lib
    rmdir ${D}/usr/usr
}
