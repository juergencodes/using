# Installation WSL2 unter Windows 10

## Features einschalten

```
dism.exe /online /enable-feature /featurename:Microsoft-Windows-Subsystem-Linux /all /norestart

dism.exe /online /enable-feature /featurename:VirtualMachinePlatform /all /norestart
```

## Version 2 als Standard setzen

In PowerShell oder CMD mit Admin-Rechten folgendes ausführen.

```
wsl --set-default-version 2
```

## Installation Terminal

Microsoft Store: Windows Terminal


## Installation Distributionen ohne Store

In PowerShell ausführen

```
Invoke-WebRequest -Uri https://aka.ms/wsl-debian-gnulinux -OutFile wsl-debian-gnulinux.appx -UseBasicParsing

Add-AppxPackage .\wsl-debian-gnulinux.appx
```

```
Invoke-WebRequest -Uri https://aka.ms/wslubuntu2004 -OutFile wslubuntu2004.appx -UseBasicParsing

Add-AppxPackage .\wslubuntu2004.appx
```

Weitere Links unter https://docs.microsoft.com/de-de/windows/wsl/install-manual