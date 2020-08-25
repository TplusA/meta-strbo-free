#! /bin/bash
git -C "${1:-$HOME/tua/gerbera}" format-patch -N v1.2.0.. -o "$(readlink -f "$BASH_SOURCE" | xargs dirname)"
