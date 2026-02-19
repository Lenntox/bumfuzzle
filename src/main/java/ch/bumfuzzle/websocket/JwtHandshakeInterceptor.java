package ch.bumfuzzle.websocket;

import ch.bumfuzzle.websocket.jwt.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Component
public class JwtHandshakeInterceptor implements HandshakeInterceptor {

  private final JwtService jwtService;

  public JwtHandshakeInterceptor(final JwtService jwtService) {
    this.jwtService = jwtService;
  }

  @Override
  public boolean beforeHandshake(
      final ServerHttpRequest request,
      final ServerHttpResponse response,
      final WebSocketHandler wsHandler,
      final Map<String, Object> attributes
  ) {
    if (request instanceof final ServletServerHttpRequest servletRequest) {

      final String token = servletRequest
          .getServletRequest()
          .getParameter("token");

      if (token == null || !jwtService.isValid(token)) {
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return false;
      }
    }

    return true;
  }

  @Override
  public void afterHandshake(
      final ServerHttpRequest request,
      final ServerHttpResponse response,
      final WebSocketHandler wsHandler,
      final Exception exception
  ) {
  }
}
