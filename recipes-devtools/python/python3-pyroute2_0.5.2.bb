inherit setuptools3

DESCRIPTION = "A pure Python netlink and Linux network configuration library"
LICENSE = "GPLv2 & Apache-2.0"

SRCNAME = "pyroute2"

LIC_FILES_CHKSUM = "file://LICENSE.GPL.v2;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
                    file://LICENSE.Apache.v2;md5=34281e312165f843a2b7d1f114fe65ce"


SRC_URI = "https://files.pythonhosted.org/packages/18/64/33858f79b0f2b44a0af7bb42eda3d477253de5311dae1eb12b966085909f/pyroute2-0.5.2.tar.gz"
SRC_URI[sha256sum] = "42bf74495d95a0196a74dd171357f660175aba2bfc23f9b5f63e3830ccbef9ac"
SRC_URI[md5sum] = "2f6f951e94356ae9d224f417dc6ebed2"

S = "${WORKDIR}/${SRCNAME}-${PV}"

RDEPENDS_${PN} += "\
  python3-distutils \
  python3-multiprocessing \
  python3-io \
  python3-pprint \
  python3-pickle \
  python3-logging \
  python3-threading \
  python3-textutils \
  python3-subprocess \
  python3-netclient \
  python3-pkgutil \
  python3-importlib \
  python3-json \
"
