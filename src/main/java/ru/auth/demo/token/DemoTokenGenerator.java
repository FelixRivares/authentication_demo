package ru.auth.demo.token;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.auth.demo.security.properties.SecurityConfigurationProperties;
import ru.auth.demo.security.properties.SecurityConfigurationProperties.AuthType;
import ru.auth.demo.token.model.User;

@Service
public class DemoTokenGenerator {

  private final SecurityConfigurationProperties securityConfigurationProperties;

  @Autowired
  public DemoTokenGenerator(SecurityConfigurationProperties securityConfigurationProperties) {
    this.securityConfigurationProperties = securityConfigurationProperties;
  }

  public String generateToken(User user) {
    if (securityConfigurationProperties.getType() != AuthType.SECRET) {
      throw new UnsupportedOperationException("Token generation is supported only for secret auth type");
    }
    return Jwts.builder()
               .setClaims(buildClaims(user))
               .signWith(SignatureAlgorithm.HS512, securityConfigurationProperties.getSecretBytes())
               .compact();
  }

  private Map<String, Object> buildClaims(User user) {
    HashMap<String, Object> claims = new HashMap<>();
    claims.put("sub", RandomStringUtils.random(10, true, true));
    claims.put("name", user.getName());
    return claims;
  }

}
