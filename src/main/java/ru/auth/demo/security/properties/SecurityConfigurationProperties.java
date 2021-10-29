package ru.auth.demo.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "user.auth")
@Data
public class SecurityConfigurationProperties implements Validator {

  private AuthType type;
  private String secret;
  private String jwkResourceServer;

  public enum AuthType {
    JWK, SECRET
  }

  public byte[] getSecretBytes() {
    return secret.getBytes();
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return SecurityConfigurationProperties.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    SecurityConfigurationProperties config = (SecurityConfigurationProperties) target;
    if (config.getType() == AuthType.SECRET) {
      ValidationUtils.rejectIfEmpty(errors, "secret", "required-non-empty", "secret key is required with secret auth type");
    }
    if (config.getType() == AuthType.JWK) {
      ValidationUtils.rejectIfEmpty(
          errors,
          "jwkResourceServer",
          "required-non-empty",
          "jwkResourceServer is required with jwk auth type");
    }
  }

}
