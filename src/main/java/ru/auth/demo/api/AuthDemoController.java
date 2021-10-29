package ru.auth.demo.api;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.auth.demo.token.DemoTokenGenerator;
import ru.auth.demo.token.model.User;

@RestController
public class AuthDemoController {

  private final DemoTokenGenerator demoTokenGenerator;

  @Autowired
  public AuthDemoController(DemoTokenGenerator demoTokenGenerator) {
    this.demoTokenGenerator = demoTokenGenerator;
  }

  @GetMapping("/test")
  public Map<String, String> testAuth(@AuthenticationPrincipal Jwt principal) {
    Map<String, String> map = new HashMap<>();
    map.put("sub", principal.getClaimAsString("sub"));
    map.put("name", principal.getClaimAsString("name"));
    return Collections.unmodifiableMap(map);
  }

  @PostMapping("/token")
  public String generateToken(@RequestBody User user) {
    return demoTokenGenerator.generateToken(user);
  }

  @GetMapping("/secret/{size}")
  public String generateSampleSecret(@PathVariable int size) {
    return RandomStringUtils.random(size, true, true);
  }

}
