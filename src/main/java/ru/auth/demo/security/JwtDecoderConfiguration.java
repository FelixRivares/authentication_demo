package ru.auth.demo.security;

import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.web.client.RestTemplate;
import ru.auth.demo.security.properties.SecurityConfigurationProperties;

@Configuration
public class JwtDecoderConfiguration {

  private final SecurityConfigurationProperties securityConfigurationProperties;

  @Autowired
  public JwtDecoderConfiguration(SecurityConfigurationProperties securityConfigurationProperties) {
    this.securityConfigurationProperties = securityConfigurationProperties;
  }

  @Bean
  @ConditionalOnProperty(value = "user.auth.type", havingValue = "JWK", matchIfMissing = true)
  public JwtDecoder createJwtDecoderWithJwk() {
    OAuth2TokenValidator<Jwt> jwtValidator = JwtValidators.createDefault();
    NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withJwkSetUri(securityConfigurationProperties.getJwkResourceServer())
                                                  .restOperations(new RestTemplate())
                                                  .build();
    jwtDecoder.setJwtValidator(jwtValidator);
    return jwtDecoder;
  }

  @Bean
  @ConditionalOnProperty(value = "user.auth.type", havingValue = "SECRET")
  public JwtDecoder createJwtDecoderWithSecret() {
    OAuth2TokenValidator<Jwt> jwtValidator = JwtValidators.createDefault();
    NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withSecretKey(new SecretKeySpec(
                                                      securityConfigurationProperties.getSecretBytes(),
                                                      MacAlgorithm.HS512.getName()))
                                                  .macAlgorithm(MacAlgorithm.HS512)
                                                  .build();
    jwtDecoder.setJwtValidator(jwtValidator);
    return jwtDecoder;
  }

}
