FROM node:18 AS builder

WORKDIR /app

COPY /package-lock.json ./

COPY /package.json ./

RUN npm install --silent

COPY . .

RUN npm run build --silent

FROM nginx:1.13.9-alpine

COPY --from=builder /app/dist /usr/share/nginx/html

RUN echo "server {" > /etc/nginx/conf.d/default.conf \
    && echo "    listen 80;" >> /etc/nginx/conf.d/default.conf \
    && echo "    server_name *.localhost;" >> /etc/nginx/conf.d/default.conf \
    && echo "    location / {" >> /etc/nginx/conf.d/default.conf \
    && echo "        root /usr/share/nginx/html;" >> /etc/nginx/conf.d/default.conf \
    && echo "        try_files \$uri \$uri/ /index.html;" >> /etc/nginx/conf.d/default.conf \
    && echo "    }" >> /etc/nginx/conf.d/default.conf \
    && echo "}" >> /etc/nginx/conf.d/default.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
