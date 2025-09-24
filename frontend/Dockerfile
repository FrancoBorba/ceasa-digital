FROM node:20-alpine AS builder

WORKDIR /app

# Copia arquivos de configuração do projeto Vite React
COPY src/package.json ./
COPY src/package-lock.json ./

RUN npm install

# Copia todo o código fonte
COPY src/ ./

RUN npm run build

FROM nginx:stable-alpine

# Copia os arquivos buildados para o nginx
COPY --from=builder /app/dist /usr/share/nginx/html

COPY nginx.conf /etc/nginx/conf.d/default.conf

# usar a porta 80
EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]