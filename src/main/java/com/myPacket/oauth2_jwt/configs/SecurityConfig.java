package com.myPacket.oauth2_jwt.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        //csrf : Cross-Site Request Forgery. Es una vulnerabilidad que tienen las aplicaciones web, mas que nada los formularios.
        //crsf. Spring security trae csrf por defecto.

        //dentro de authorizeHttpRequests van a etsarlas url que van a estar protegidad y cuales no.
        //requestmatchers son las peticiones que coincidan con los endpoints que queremos que se acceda sin autorizacion.
        //anyrequest se refiere a que los demas urls deben estar autenticados.
        //formLogin.permitAll permite que los demas accedan al formulario.
        //si despues de iniciar sesion quiero que se haga algo en especifico tengo que poner cosas despues del formLogin() con un successHandler() y creo una funcion aparte.
        //el sessionManagement dice como se maneja la sesion. La ventaja de esto es que podemos guardar
        //info del usuario sin pedirle autenticacion a cada rato. Hay cuatro opciones.
        return httpSecurity
                .authorizeHttpRequests(
                request -> {
                    request.requestMatchers(HttpMethod.GET,"/api/v1/test").permitAll();
                    request.anyRequest().authenticated();
                })
                .formLogin(Customizer.withDefaults())
                .oauth2Login(Customizer.withDefaults())
                .build();

    }

    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /*
    //se encarga de manejar la autenticacion en nuestra aplicacion.
    @Bean
    AuthenticationManager authenticationManager(HttpSecurity httpSecurity){

    }
*/




     /*
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .anyRequest().authenticated())
                .oauth2Login(Customizer.withDefaults());
        return http.build();
    }
*/





    /*

    // @formatter:off
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
    // @formatter:on
*/
}