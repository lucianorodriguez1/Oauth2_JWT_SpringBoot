# Oauth2
En este proyecto se encuentra la autenticacion por google
## 1. Agregar dependencias

Al iniciar mi proyecto de Spring no agregue ninguna dependencia por defecto.
Voy a la pagina de Maven repository y descargo las que voy a usar.

### Lista de dependencias
* spring-boot-starter-web
* spring-security-oauth2-client
* spring-boot-starter-security
* spring-security-oauth2-jose

## 2. Agregar Spring Security

Ya viene por defecto la autenticacion por google, hace falta poner el id del cliente y el secret client en las propiedades de la aplicacion.
Ir a la pagina de consola google cloud y descargar una credencial, de alli se obtienen las claves mencionadas anteriormente.

## 3. Iniciar sesion con Google.


## Variables de entorno

1. `GOOGLE_CLIENT_ID` : Id del cliente de la cuenta de google
2. `GOOGLE_CLIENT_SECRET` : secret del cliente de la cuenta de google


## Material extra
Para saber lo basico de Spring me guio con este video 1: http://youtube.com/watch?v=IPWBQDMIYkc&list=PLr23_YfwEbPRCK4IbemQGwYdgSwfd2aZu&index=2
Al instalar la dependencia de spring security se activa automaticante.
En aplication. properties podemos configurar el usuario por defecto.
Crear archivo de configuracion de spring security en una clase.