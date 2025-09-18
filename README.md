# Proyecto: Autenticación con Google y JWT

Este proyecto implementa un sistema de **autenticación y autorización** usando **Spring Boot**, **OAuth2 con Google** y **JWT** para proteger endpoints de una API.

---

## 📦 Dependencias principales

- `spring-boot-starter-web` → API REST
- `spring-boot-starter-security` → seguridad
- `spring-security-oauth2-client` → login con Google
- `spring-security-oauth2-jose` → soporte para JWT
- `spring-boot-starter-data-jpa` → persistencia
- `mysql-connector-j` → conector MySQL
- `spring-boot-starter-validation` → validaciones
- `modelmapper` → DTOs
- `jjwt-api`, `jjwt-impl`, `jjwt-jackson` → manejo de JWT

---

## 2. Agregar Spring Security

Ya viene por defecto la autenticacion por google, hace falta poner el id del cliente y el secret client en las propiedades de la aplicacion.
Ir a la pagina de consola google cloud y descargar una credencial, de alli se obtienen las claves mencionadas anteriormente.

## 3. Iniciar sesion con Google.


## ⚙️ Configuración

### Variables de entorno
- `GOOGLE_CLIENT_ID` → obtenido de Google Cloud Console
- `GOOGLE_CLIENT_SECRET` → secreto del cliente de Google
- `JWT_SECRET_KEY` → clave secreta para firmar tokens
- `JWT_EXPIRATION` → tiempo de expiración en milisegundos

### application.properties
```properties
# Google OAuth2
spring.security.oauth2.client.registration.google.client-id=${GOOGLE_CLIENT_ID}
spring.security.oauth2.client.registration.google.client-secret=${GOOGLE_CLIENT_SECRET}

# JWT
jwt.secret.key=${JWT_SECRET_KEY}
jwt.expiration=${JWT_EXPIRATION}

# Base de datos
spring.datasource.url=jdbc:mysql://localhost:3306/tu_basedatos
spring.datasource.username=tu_usuario
spring.datasource.password=tu_password
spring.jpa.hibernate.ddl-auto=update
 ```
--- 

## Material extra
Para saber lo basico de Spring me guio con este video 1: http://youtube.com/watch?v=IPWBQDMIYkc&list=PLr23_YfwEbPRCK4IbemQGwYdgSwfd2aZu&index=2
Al instalar la dependencia de spring security se activa automaticante.
En aplication. properties podemos configurar el usuario por defecto.
Crear archivo de configuracion de spring security en una clase.
La app la pruebo por POSTMAN, y para iniciar sesion lo que hago es en Authorizacion agregar las variables de mi api de google.

Para usar spring security yambien uso una base de datos.

Un token nos da una capa de seguridad bastante alta. 

## 31/08/2025

Empiezo creando un archivo SeederConfig donde tengo la creacion de usuarios por defecto al crear la APP.
Recomendaciones que no hice y estaria bueno implementar:
- Lombok. 
- Patron builder.

### Pasos
- Mejorar el archivo de seguridad.
- Crear una carpeta util con la clase de JwtUtils

## 1/09/2025

### Pasos
- Ponemos en la variable de app.proper:
  * jwt.secret.key (va a ser la firma)
- Importar dependencias para trabajar con jwt: 
  * jjwt-api
  * jjwt-jackson
  * jjwt-impl
