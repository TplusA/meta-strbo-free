DESCRIPTION = "Duktape - a minimalist Javascript engine"
HOMEPAGE = "http://duktape.org/"
PR = "r1"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=fddbe4ed8ac933555f193488d973da85"

SRC_URI = "http://duktape.org/duktape-${PV}.tar.xz"
SRC_URI[md5sum] = "0f7c9fac5547f7f3fc1c671fc90b2ccf"
SRC_URI[sha256sum] = "62f72206427633077cb02e7ccd2599ace4d254db409334593b86d262c0d50c14"

DEPENDS = ""

EXTRA_OEMAKE = "'CC=${CC}' 'RANLIB=${RANLIB}' 'AR=${AR} CFLAGS=${CFLAGS} -I${S}/include -DWITHOUT_XATTR' 'BUILDDIR=${S}' INSTALL_PREFIX='${D}/usr'"

do_compile() {
   sed -i -e 's/gcc/$(CC)/g' Makefile.sharedlibrary
   oe_runmake -f Makefile.sharedlibrary
}

do_install() {

    mkdir -p ${D}/usr
    mkdir -p ${D}/usr/lib
    mkdir -p ${D}/usr/include
    oe_runmake -f Makefile.sharedlibrary install
}
