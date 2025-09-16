FROM node:20-alpine AS builder

WORKDIR /app

# Copia para cache
COPY package.json ./
COPY package-lock.json ./

RUN npm install

COPY . .

RUN npm run build

FROM nginx:stable-alpine


COPY --from=builder /app/build /usr/share/nginx/html

COPY nginx.conf /etc/nginx/conf.d/default.conf

# usar a porta 80
EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]