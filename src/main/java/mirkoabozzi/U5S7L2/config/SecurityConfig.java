package mirkoabozzi.U5S7L2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    // senza questa classe di configurazione il server avrà tutti i controlli di sicurezza abilitati
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.formLogin(http -> http.disable()); //disabilita schermata login accessibile in http://localhost:miaPorta
        httpSecurity.csrf(http -> http.disable()); //disabilita la protezione csrf
        httpSecurity.sessionManagement(http -> http.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); //disabilita le sessioni
        httpSecurity.authorizeHttpRequests(http -> http.requestMatchers("/**").permitAll()); //senza questo otterrò 401 a ogni richiesta
        return httpSecurity.build();
    }

    @Bean
    PasswordEncoder getBCrypt() {
        return new BCryptPasswordEncoder(11); // questo è un bean che mi consente di cryptare le password
    }
}
