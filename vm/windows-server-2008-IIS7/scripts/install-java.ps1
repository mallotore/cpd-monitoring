echo "Installing java"
$Install = Start-Process "C:\vagrant_shared\software\jdk-7u79-windows-x64.exe" -ArgumentList "/s" -Wait 
Write-Host "New Version of Java is Installing..."
write-host "Successfully installed"