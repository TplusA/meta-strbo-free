if [ -e /etc/locale.conf ]; then
	set -o allexport
	source /etc/locale.conf
	set +o allexport
fi
