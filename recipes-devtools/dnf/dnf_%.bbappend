do_install:append() {
    cat >>${D}${sysconfdir}/dnf/dnf.conf <<EOF
color=never
keepcache=False
obsoletes=True
metadata_timer_sync=0
cachedir=/var/local/data/dnf
EOF
}
