#!/bin/bash

cd ~/git/update

svn co [pyserial.svn.sourceforge.net] pyserial
cd pyserial
patch -p0 < ../pyserial.patch
cd pyserial
python2 setup.py install --user
python3 setup.py install --user 
