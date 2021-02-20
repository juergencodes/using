docker volume create portainer

docker run -d \
  --name=portainer \
  --restart=always \
  -v /var/run/docker.sock:/var/run/docker.sock \
  -v portainer:/data \
  --net=web \
  --label traefik.http.routers.portainer.rule=PathPrefix\(\`/pt\`\) \
  --label traefik.http.services.portainer.loadbalancer.server.port=9000 \
  --label traefik.http.middlewares.stripprefix-pt.stripprefix.prefixes=/pt \
  --label traefik.http.middlewares.stripprefix-pt.stripprefix.forceSlash=true \
  --label traefik.http.routers.portainer.middlewares=stripprefix-pt@docker \
  portainer/portainer-ce:2.1.1
  
# https://github.com/traefik/traefik/issues/5159
