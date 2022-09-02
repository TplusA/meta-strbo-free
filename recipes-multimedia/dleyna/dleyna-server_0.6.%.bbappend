FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"
SRC_URI += " \
    file://0001-dbus-Run-service-at-nice-level-19.patch \
    file://0002-Fix-typo-in-error-message.patch \
    file://0003-Avoid-crash-due-to-NULL-result-in-prv_get_sleeping_f.patch \
"
