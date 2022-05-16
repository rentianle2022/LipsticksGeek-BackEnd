package fun.tianlefirstweb.www.security;

import fun.tianlefirstweb.www.security.jwt.JwtConfig;
import fun.tianlefirstweb.www.security.jwt.JwtTokenVerifier;
import fun.tianlefirstweb.www.security.jwt.JwtUsernamePasswordAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import javax.crypto.SecretKey;
import java.util.List;

import static fun.tianlefirstweb.www.user.enums.Role.ADMIN;
import static fun.tianlefirstweb.www.user.enums.Role.USER;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;

    public WebSecurityConfig(JwtConfig jwtConfig, SecretKey secretKey) {
        this.jwtConfig = jwtConfig;
        this.secretKey = secretKey;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000","http://192.168.101.19:3000","http://www.tianlefirstweb.fun"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("authorization", "content-type"));
        configuration.setExposedHeaders(List.of("authorization"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();

        http.authorizeRequests()
                .antMatchers("/crawler").hasRole(ADMIN.name())
                .antMatchers(POST,"/brands","/lipsticks","/color").hasRole(ADMIN.name())
                .antMatchers(PUT,"/brands","/lipsticks","/color").hasRole(ADMIN.name())
                .antMatchers(DELETE,"/brands","/lipsticks","/color").hasRole(ADMIN.name())
                .antMatchers(POST,"/users/register","/ws/**").permitAll()
                .antMatchers("/users/**","/chat/**").hasRole(USER.name());

        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //session management
                .and()
                .addFilter(new JwtUsernamePasswordAuthFilter(authenticationManager(), jwtConfig, secretKey)) //sign JSON token
                .addFilterAfter(new JwtTokenVerifier(jwtConfig, secretKey), JwtUsernamePasswordAuthFilter.class) //verify JSON token
                .authorizeRequests()
                .anyRequest().permitAll();
    }
}
