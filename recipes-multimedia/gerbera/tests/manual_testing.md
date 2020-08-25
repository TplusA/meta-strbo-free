Gerbera media server manual testing
===================================

This document is to list all significant findings that should be retested in the future but are not
included in the automatic tests at the moment.

1. Multiple devices on the same network must work
-------------------------------------------------

We found out on 23.11.2018 that same UDN (see config.xml) on two instances on the same network
creates problems. Random UDN generation on first run after factory-reset was implemented to fix
this.

