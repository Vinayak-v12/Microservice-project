package com.gateway.API_gateway.config;
import org.springframework.stereotype.Component;

import java.util.Base64;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import reactor.core.publisher.Mono;
@Component
public class JwtGlobalFilter implements GlobalFilter, Ordered {

    private static final String SECRET = "cJA9qVoXjVdLcGuuXc0QDdtaWmV5d8m+EZrclCxpfLU=";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        // Bypass login/register
        if (path.contains("/USERSERVICE/login") || path.contains("/USERSERVICE/register")) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        try {
        	
        	
            String token = authHeader.substring(7);
          
            //comapres and verifies jwt
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Base64.getDecoder().decode(SECRET))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String userId = claims.get("userId", String.class);

            // Forward header to downstream service
            ServerWebExchange mutated = exchange.mutate()
                    .request(r -> r.header("X-USER-ID", userId))
                    .build();

            return chain.filter(mutated);

        } catch (Exception e) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
