#! /bin/sh

LAUNCH_PY="$(/bin/echo "$0" | /bin/sed 's/\.sh$/.py/')"

set -eu

if /usr/bin/test $(/usr/bin/id -u) -eq 0
then
    exec /bin/su strbo-rest -p -c "${LAUNCH_PY}" $@
else
    exec "${LAUNCH_PY}" $@
fi
