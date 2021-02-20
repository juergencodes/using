# Reboot

```
/usr/sbin/shutdown --reboot
```

# Mount NFS

```
apt install nfs-common

mkdir -p /mnt/nas

mount nas:/volume1/server /mnt/nas
```

unmount

```
umount /mnt/nas
```

permanent

```
sudo nano /etc/fstab


nas:/volume1/server /mnt/nas nfs defaults 0 0

mount -av

```
