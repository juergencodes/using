# Install sudo

apt-get update
apt-get install sudo

apt install xe-guest-utilities


# Install guest tools

PATH="/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin"

mount /dev/cdrom /mnt
bash /mnt/Linux/install.sh
umount /dev/cdrom

