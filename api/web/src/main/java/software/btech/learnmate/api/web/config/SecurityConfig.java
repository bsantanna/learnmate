package software.btech.learnmate.api.web.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableMethodSecurity
@Slf4j
public class SecurityConfig {

  @Bean
  SecurityFilterChain securityFilterChain(
    HttpSecurity http,
    CorsConfiguration corsConfiguration,
    @Value("${security.csrf.enabled}") boolean csrfEnabled
  ) throws Exception {


    http
      .authorizeHttpRequests(
        authorize ->
          authorize
            .requestMatchers("/actuator/**").permitAll()
            .anyRequest().authenticated()
      )
      .oauth2ResourceServer(
        oauth2 ->
          oauth2.jwt(Customizer.withDefaults())
      );

    http.cors(c -> c.configurationSource(req -> corsConfiguration));

    if (!csrfEnabled) {
      http.csrf(AbstractHttpConfigurer::disable);
    }

    return http.build();

  }

  @Bean
  CorsConfiguration corsConfiguration(
    @Value("${security.cors.allowed-origins}") List<String> corsAllowedOrigin
  ) {
    var conf = new CorsConfiguration();
    conf.setAllowedOrigins(corsAllowedOrigin);
    conf.setAllowedMethods(List.of(
      HttpMethod.GET.name(),
      HttpMethod.HEAD.name(),
      HttpMethod.POST.name()
    ));
    conf.setAllowedHeaders(List.of(
      HttpHeaders.AUTHORIZATION,
      HttpHeaders.CONTENT_TYPE
    ));
    return conf;
  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer(
    @Value("${security.debug.enabled}") boolean debugEnabled
  ) {
    return web -> web.debug(debugEnabled);
  }

}
