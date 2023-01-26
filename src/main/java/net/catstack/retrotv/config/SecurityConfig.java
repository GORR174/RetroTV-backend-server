package net.catstack.retrotv.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.catstack.retrotv.dto.AdapterError;
import net.catstack.retrotv.dto.AdapterResponse;
import net.catstack.retrotv.exception.AuthException;
import net.catstack.retrotv.exception.BaseServiceException;
import net.catstack.retrotv.exception.InternalServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {
    @Value("${retrorv.auth.login}")
    private String adminLogin;
    @Value("${retrorv.auth.password}")
    private String adminPassword;

    private final String[] publicUrls = new String[] {
            "/headings/getHeadings",
            "/schedule/getSchedule",
            "/sources/getSources",
    };

    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails admin = User.withUsername(adminLogin)
                .password(passwordEncoder.encode(adminPassword))
                .roles("USER", "ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui/**",
                        "/webjars/**")
                    .permitAll()
                .antMatchers(publicUrls)
                    .permitAll()
                .anyRequest()
                    .permitAll()
//                    .authenticated()
                    .and()
                .httpBasic()
                    .and()
                .exceptionHandling()
                    .authenticationEntryPoint((httpServletRequest, httpServletResponse, e) -> {
                        var mapper = new ObjectMapper();
                        httpServletResponse.setHeader("Content-Type", "application/json");
                        var exception = new AuthException("Unauthorized");
                        httpServletResponse.getWriter().write(mapper.writeValueAsString(getErrorAdapterResponse(exception)));
                    })
                    .accessDeniedHandler((httpServletRequest, httpServletResponse, e) -> {
                        log.error("Access Error", e);
                        var mapper = new ObjectMapper();
                        httpServletResponse.setHeader("Content-Type", "application/json");
                        var exception = new AuthException("Forbidden");
                        httpServletResponse.getWriter().write(mapper.writeValueAsString(getErrorAdapterResponse(exception)));
                    });
        return http.build();
    }

    private AdapterResponse getErrorAdapterResponse(BaseServiceException e) {
        var response = new AdapterResponse<>();

        response.setStatus(1);

        var adapterError = new AdapterError();

        adapterError.setCode(e.getCode());
        adapterError.setMessage(e.getMessage());

        response.setError(adapterError);

        return response;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return encoder;
    }
}
