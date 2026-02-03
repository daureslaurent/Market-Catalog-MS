package lda.services.market.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.web.SecurityFilterChain;

@Profile("auth2")
@Configuration
public class JwtSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, OpaqueTokenIntrospector introspector) {
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/product/**")
                        .hasAuthority("SCOPE_read")
                        .requestMatchers(HttpMethod.POST, "/product")
                        .hasAuthority("SCOPE_write")
                        .anyRequest()
                        .authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .opaqueToken(token -> token.introspector(introspector)));
        return http.build();
    }

}
