# Unifi

Default adopt URL

```
http://unifi:8080/inform
```

Controller Webinterface URL

```
https://unifi:8443
```

L3 inform

- Factory reset (10 s drücken)
- Mit putty per SSH verbinden (user/pass: ubnt/ubnt)
- set-inform http://<ip>:8080/inform
- Im Controller tauchte der AP unter Übersicht -> Geräte auf
- Ganz rechts auf "Adopt" (deutsch "Einbinden"= geklickt

# Cisco Switch

Allgemein

```
do show vlan
do show ip route
show ip dhcp binding
show ip interface
```

Port zu VLAN zuordnen


```
interface GE1
switchport mode access
switchport access vlan 5
```

IP für ein VLAN zuordnen

```
interface vlan5
ip address 192.168.5.1 255.255.255.0
```