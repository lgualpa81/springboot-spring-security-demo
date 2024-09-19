package com.app.config;

import com.app.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity //permite hacer configuraciones con anotaciones propias de spring
public class SecurityConfig {
  /*@Autowired
  AuthenticationConfiguration authenticationConfiguration;*/
/*  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
            .csrf(csrf -> csrf.disable()) //autenticacion basada en token, usada en app web con formularios
            .httpBasic(Customizer.withDefaults())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //STATELESS depende de la expiracion del token autorizacion
            .authorizeHttpRequests(http -> {
              //configurar endpoints publicos
              http.requestMatchers(HttpMethod.GET, "/auth/hello")
                  .permitAll();
              //configurar endpoints privados
              http.requestMatchers(HttpMethod.GET, "/auth/hello-secured")
                  .hasAuthority("READ");
              http.requestMatchers(HttpMethod.POST, "/auth/post")
                  .hasAnyRole("ADMIN", "DEVELOPER");
              http.requestMatchers(HttpMethod.PUT, "/auth/put")
                  .hasRole("ADMIN");
              //configurar endpoints restantes NO especificados
              http.anyRequest()
                  .denyAll(); //denyAllRechaza todo lo que no este especificado a si este autenticado. Es mas restrictivo
              //http.anyRequest().authenticated(); //Es mas permisivo
            })
            .build();
  }*/

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    //las rutas se definen en el controlador decorando con anotacion @PreAuthorize
    return httpSecurity
            .csrf(csrf -> csrf.disable())
            .httpBasic(Customizer.withDefaults())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public AuthenticationProvider authenticationProvider(UserDetailServiceImpl userDetailService) {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setPasswordEncoder(passwordEncoder());
    provider.setUserDetailsService(userDetailService);
    return provider;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    //NoOpPasswordEncoder usarlo SOLO en pruebas
    //return NoOpPasswordEncoder.getInstance();
    return new BCryptPasswordEncoder();
  }

  /*@Bean
  public UserDetailsService userDetailsService() {
    //simular que se recupera un usuario de una bd
    List<UserDetails> userDetailsList = new ArrayList<>();
    userDetailsList.add(User.withUsername("joe")
                            .password("1234")
                            .roles("ADMIN")
                            .authorities("READ", "CREATE")
                            .build());

    userDetailsList.add(User.withUsername("mary")
                            .password("1234")
                            .roles("USER")
                            .authorities("READ")
                            .build());
    return new InMemoryUserDetailsManager(userDetailsList);
  }*/

/*  public static void main(String[] args) {
    System.out.println(new BCryptPasswordEncoder().encode("1234"));
  }*/
}
