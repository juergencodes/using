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

