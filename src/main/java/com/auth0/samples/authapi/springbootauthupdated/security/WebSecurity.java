package com.auth0.samples.authapi.springbootauthupdated.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import com.auth0.samples.authapi.springbootauthupdated.user.UserDetailsServiceImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.context.annotation.Bean;

import static com.auth0.samples.authapi.springbootauthupdated.security.SecurityConstants.CREATE_TASKS_URL;
import static com.auth0.samples.authapi.springbootauthupdated.security.SecurityConstants.SIGN_UP_URL;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    private UserDetailsServiceImpl userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public WebSecurity(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //Sets permissions
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
                .antMatchers(HttpMethod.GET, SIGN_UP_URL).permitAll()
                .antMatchers(HttpMethod.DELETE, SIGN_UP_URL + "/**").permitAll()
                .antMatchers(HttpMethod.PUT, SIGN_UP_URL + "/**").permitAll()
                .antMatchers(HttpMethod.POST, CREATE_TASKS_URL).permitAll()
                .antMatchers(HttpMethod.GET, CREATE_TASKS_URL).permitAll()
                .antMatchers(HttpMethod.DELETE, CREATE_TASKS_URL + "/**").permitAll()
                .antMatchers(HttpMethod.PUT, CREATE_TASKS_URL + "/**").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                // this disables session creation on Spring Security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.headers().frameOptions().disable();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        String[] headers = {"Access-Control-Allow-Headers",
                "Access-Control-Allow-Origin",
                "Access-Control-Expose-Headers",
                "Authorization",
                "Cache-Control",
                "Content-Type",
                "Origin"};

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.applyPermitDefaultValues();
        for (String header :
                headers) {
            corsConfiguration.addExposedHeader(header);
        }
        corsConfiguration.addAllowedMethod("DELETE");
        corsConfiguration.addAllowedMethod("PUT");
        corsConfiguration.addAllowedMethod("OPTIONS");
        corsConfiguration.addAllowedMethod("POST");
        corsConfiguration.addAllowedMethod("GET");


        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }
}