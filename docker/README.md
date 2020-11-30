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

## Beste, lausige Versuche

```
docker network create -d ipvlan --subnet=192.168.5.0/24 --gateway=192.168.5.1 -o parent=eth0.5 ipvlan5

docker network create -d macvlan  --subnet=192.168.20.0/24 --gateway=192.168.20.1 -o parent=eth0.20 --ipam-opt dhcp_interface=eth0.20 mcv20
```

## Löschen
```
docker network ls

docker network rm mcv20
```

