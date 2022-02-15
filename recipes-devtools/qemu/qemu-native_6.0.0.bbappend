PR := "r1"

do_ar_patched_prepare_fix[doc] = "Fixes dead symbolic link to /opt/X11/include"

do_ar_patched_prepare_fix() {
bbnote "${PF}: Removing dead symbolic link to /opt/X11/include"
ARCHIVER_S="${ARCHIVER_WORKDIR}/qemu-${PV}"
rm ${ARCHIVER_S}/roms/edk2/EmulatorPkg/Unix/Host/X11IncludeHack
}

addtask do_ar_patched_prepare_fix before do_ar_patched after do_unpack_and_patch
