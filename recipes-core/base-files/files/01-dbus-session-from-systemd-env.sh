SESSION_FROM_SYSTEMD=$(systemctl show-environment | grep "^DBUS_SESSION_BUS_ADDRESS=")
if test -n "$SESSION_FROM_SYSTEMD"; then
        export $SESSION_FROM_SYSTEMD
fi
unset SESSION_FROM_SYSTEMD
