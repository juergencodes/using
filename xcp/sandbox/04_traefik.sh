docker network create web

docker run -d \
  --name=traefik \
  --restart=always \
  -v /var/run/docker.sock:/var/run/docker.sock \
  --net=web \
  -p 80:80 \
  -p 8080:8080 \
  traefik:v2.4.3 \
  --api.insecure=true \
  --providers.docker \
#  --log.level=DEBUG