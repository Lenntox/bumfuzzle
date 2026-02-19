package ch.bumfuzzle.auth;

import ch.bumfuzzle.websocket.jwt.JwtService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/auth")
public class AuthController {

  private final JwtService jwtService;

  public AuthController(final JwtService jwtService) {
    this.jwtService = jwtService;
  }

  @PostMapping("/login")
  public Map<String, String> login(@RequestBody final LoginRequest request) {

    // HARDCODED TEST USER
    if (!"admin".equals(request.username()) ||
        !"password".equals(request.password())) {
      throw new RuntimeException("Invalid credentials");
    }

    final String token = jwtService.generateToken(request.username());

    return Map.of("token", token);
  }
}
