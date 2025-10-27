# Imagem com java 21 e maven
FROM maven:3.9-eclipse-temurin-21-alpine AS builder


WORKDIR /app

# primeiro copia o pom para cache
COPY pom.xml .

# Copia o resto da pasta src
COPY src ./src

# Roda o maven sem testes
RUN mvn clean package -DskipTests
#RUN mvn clean package -Dmaven.test.skip=true



# Usando uma imagem padrão mais leve para rodar depois de buildar

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copia o arquivo .jar que foi gerado no estágio 'builder' para a nossa imagem final.
COPY --from=builder /app/target/ceasa-digital-api-*.jar app.jar

EXPOSE 8080

ENTRYPOINT [ "java" , "-jar" , "app.jar"]