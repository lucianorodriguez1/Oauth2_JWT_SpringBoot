# Proyecto: Autenticación con Spring Security (Basic Auth, OAuth2 y JWT)

Este proyecto implementa tres sistemas de autenticación, organizados de forma independiente:

1. Autenticación básica con usuarios almacenados en base de datos.
2. Autenticación OAuth2 usando Google.
3. Autenticación con JWT para proteger endpoints de forma stateless.

---

## 📦 Dependencias principales

- `spring-boot-starter-web` → API REST
- `spring-boot-starter-security` → seguridad
- `spring-boot-starter-data-jpa` → persistencia
- `mysql-connector-j` → conector MySQL
- `spring-boot-starter-validation` → validaciones
- `modelmapper` → DTOs

OAUTH2
- `spring-security-oauth2-client` → login con Google
- `spring-security-oauth2-jose` → soporte para JWT

JWT
- `jjwt-api`
- `jjwt-impl` 
- `jjwt-jackson` 

---

## ⚙️ Configuración

### Variables de entorno
OAUTH 2
- `GOOGLE_CLIENT_ID` → obtenido de Google Cloud Console
- `GOOGLE_CLIENT_SECRET` → secreto del cliente de Google

JWT
- `JWT_SECRET_KEY` → clave secreta para firmar tokens
- `JWT_EXPIRATION` → tiempo de expiración en milisegundos

BASE DE DATOS
- `DB_PASSWORD` 
- `DB_URL`
- `DB_USERNAME`

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
spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true

logging.level.org.springframework.security=DEBUG

 ```
--- 

## 1️⃣ 🔐 Autenticación Básica (Form Login + Basic Auth)
Este módulo permite:
* Login por formulario HTML (/login)
* Login por Basic Auth (útil para Postman)
* Usuarios almacenados en base de datos
* Roles y permisos usando entidades UserEntity y Role
* Configurar la clase SecurityConfig para permitir el login con Google.

### Archivo de `SecurityConfig`

```java
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                //.sessionManagement(session ->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) -> esto es mejor cuando se trabaja con JWT.
                .authorizeHttpRequests(
                        request -> {
                            //request.requestMatchers(HttpMethod.GET,"/api/v1/test").permitAll();

                            //la diferencia
                            request.anyRequest().authenticated();
                        })
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())   // Basic Auth (Postman)

                .oauth2Login(Customizer.withDefaults())
                .build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    
    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsServiceImp userDetailService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }}
```
- Spring Security trae protección CSRF habilitada por defecto, lo cual es útil en aplicaciones con formularios HTML tradicionales.
  Sin embargo, cuando se trabaja con APIs REST, Postman o clientes externos, esta protección suele causar bloqueos innecesarios, por lo que se desactiva:
```
.csrf(csrf -> csrf.disable())
```
- authorizeHttpRequests: Este bloque define qué endpoints son públicos y cuáles requieren autenticación.
  - requestMatchers() → permite especificar rutas y métodos HTTP que NO requieren autenticación.
  - anyRequest().authenticated() → exige autenticación para todas las demás solicitudes.

  - Ejemplo:
    ```java
        .requestMatchers(HttpMethod.GET,"/api/v1/test").permitAll()
    ```
    Si no se configuran excepciones, todo queda protegido por defecto.
- Form Login: Spring Security habilita un formulario de login automático cuando se usa:
  - ```java
    .formLogin(Customizer.withDefaults())
    ``` 
- AuthenticationManager: El AuthenticationManager es el encargado de procesar el flujo de autenticación. Spring lo construye en base a: 
  - el AuthenticationProvider
  - el UserDetailsService
  - el PasswordEncoder

- AuthenticationProvider: El proveedor principal en este proyecto es DaoAuthenticationProvider, que:
  - utiliza el UserDetailsService para cargar usuarios desde base de datos;
  - aplica el PasswordEncoder para vaidar contraseñas ingresadas;
  - genera excepciones de autenticación cuando corresponde.
  - 
- PasswordEncoder (BCrypt): Se utiliza BCrypt para encriptar contraseñas, que es el estándar moderno para aplicaciones seguras: Spring usa este encoder para:
  - validar contraseñas al iniciar sesión
  - generar hashes en el seeder o lógica de creación de usuario

---

## 2️⃣ 🌐 Autenticación OAuth2 con Google

Permite a los usuarios iniciar sesión usando su cuenta de Google.  
1. El usuario accede a: 
```
http://localhost:8080/oauth2/authorization/google
```
2. Google maneja la autenticación
3. Google redirige a tu app con un authorization code
4. Spring obtiene el access token
5. Usuario queda autenticado en tu sistema

✔️ Requisitos
1. Crear credenciales OAuth2 en Google Cloud Console
2. Configurar Client ID y Client Secret
3. Agregar callback URL:
```
http://localhost:8080/login/oauth2/code/google
```
La linea : 
```java
.oauth2Login(Customizer.withDefaults())
```
Spring Security configura automáticamente la autenticación con proveedores OAuth2 (como Google), usando las credenciales definidas en application.properties.

Esto permite:
* delegar la autenticación en Google
* manejar redirecciones automáticamente
* obtener datos del perfil del usuario autenticado
---

## Material extra
Para saber lo basico de Spring me guio con este video: 
http://youtube.com/watch?v=IPWBQDMIYkc&list=PLr23_YfwEbPRCK4IbemQGwYdgSwfd2aZu&index=2
---

## Notas adicionales:
- Al instalar la dependencia de Spring Security, se activa automáticamente.
- Se puede configurar un usuario por defecto en application.properties.
- Es recomendable crear una clase de configuración (SecurityConfig) para personalizar permisos y endpoints. 
- Las pruebas se pueden realizar con Postman, agregando las credenciales o el token JWT en la pestaña Authorization.



