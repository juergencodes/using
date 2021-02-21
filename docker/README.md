# Installation Docker unter Windows
- WLS2 installieren
- Docker Desktop installieren
- Test mit 
```
docker run hello-world
```

# Befehle
## Images ziehen
```
docker pull busybox
```

## Starten und einzelnen Befehl ausführen
```
docker run busybox echo "hello from busybox"
```

## Starten und in Shell bleiben
```
docker run -it busybox sh
```

## Im Hintergrund starten und Befehl ausführen
```
docker run --rm -itd --name my-alpine alpine:latest

docker container inspect my-alpine

docker exec my-alpine ip addr show eth0
```

## SSH in laufenden container
```
docker exec -it d9a8f2235f8f /bin/bash
```

## Alle container auflisten
```
docker ps -a
```

## Einzelnen container löschen
```
docker rm 305297d7a235
```

## Alle gestoppten Container löschen
```
docker container prune
```

## Container bauen
```
docker build -t mathit/xyz .
```

# Dockerfile

## apk Installation und workdir
```
FROM alpine
RUN apk update
RUN apk add git

VOLUME /work
WORKDIR /work
```

# VLAN

## VLAN anlegen per macvlan

```
docker network create -d macvlan \
    --subnet=192.168.20.0/24 \
    --gateway=192.168.20.1 \
    -o parent=eth0.20 macvlan20

docker network create -d macvlan \
    --subnet=192.168.30.0/24 \
    --gateway=192.168.30.1 \
    -o parent=eth0.30 macvlan30


docker run --net=macvlan20 --ip=192.168.20.64 --dns=192.168.20.1 -it --rm alpine /bin/sh
docker run --net=macvlan30 --ip=192.168.30.65 --dns=192.168.30.1 -it --rm alpine /bin/sh
```

https://hicu.be/bridge-vs-macvlan
https://hicu.be/docker-networking-macvlan-bridge-mode-configuration
https://hicu.be/docker-networking-macvlan-vlan-configuration

## Löschen
```
docker network ls

docker network rm mcv20
```

# NFS

## Docker CLI

docker volume create \
     --driver local \
     --opt type=nfs \
     --opt o=addr=nas,rw \
     --opt device=:/volume1/server/backup \
     backup

## Docker compose

```
...
volumes:
  backup:
    driver_opts:
      type: nfs
      o: "addr=nas,rw"
      device: ":/volume1/server/backup"
```