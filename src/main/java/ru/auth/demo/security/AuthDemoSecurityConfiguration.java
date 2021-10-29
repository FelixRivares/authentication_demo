package ru.auth.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.jwt.JwtDecoder;

@Configuration
public class AuthDemoSecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final JwtDecoder jwtDecoder;

  @Autowired
  public AuthDemoSecurityConfiguration(JwtDecoder jwtDecoder) {
    this.jwtDecoder = jwtDecoder;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf()
        .disable()
        .authorizeRequests()
        .antMatchers("/secret/**")
        .permitAll()
        .antMatchers("/token**")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .oauth2ResourceServer()
        .jwt()
        .decoder(jwtDecoder);
  }

}
