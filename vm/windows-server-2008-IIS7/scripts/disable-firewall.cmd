@echo off

echo Disabling firewall
netsh advfirewall set allprofiles state off
echo Status firewall
Netsh Advfirewall show allprofiles
echo Done!